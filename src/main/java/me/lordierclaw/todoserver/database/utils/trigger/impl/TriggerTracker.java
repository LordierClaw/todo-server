package me.lordierclaw.todoserver.database.utils.trigger.impl;

import me.lordierclaw.todoserver.database.utils.query.IQueryExecutor;
import me.lordierclaw.todoserver.database.utils.query.IQueryExecutorBuilder;
import me.lordierclaw.todoserver.database.utils.trigger.ITriggerTracker;

import java.sql.SQLException;
import java.util.*;

public class TriggerTracker implements ITriggerTracker {

    private final static String[] TRIGGERS = new String[]{ "INSERT", "UPDATE", "DELETE" };
    private final static String LOG_TABLE_NAME = "modification_log";
    private final static String TABLE_ID_COLUMN_NAME = "table_id";
    private final static String INVALIDATED_COLUMN_NAME = "invalidated";

    private final HashMap<Integer, String> tableMap;

    private final IQueryExecutor queryExecutor;

    public TriggerTracker(IQueryExecutorBuilder executorBuilder, String... tables) {
        this.queryExecutor = executorBuilder.getExecutor();
        this.queryExecutor.setAutoCloseConnection(false);
        tableMap = new HashMap<>();
        int count = 0;
        for(String table: tables) {
            tableMap.put(count++, table);
        }
    }

    @Override
    public void createTrackingTable() {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s" +
                "(%s INTEGER PRIMARY KEY, %s INTEGER NOT NULL DEFAULT 0)",
                LOG_TABLE_NAME, TABLE_ID_COLUMN_NAME, INVALIDATED_COLUMN_NAME);
        queryExecutor.execute(sql);
    }

    private String getTriggerName(String table, String trigger) {
        return String.format("%s_%s", table.trim(), trigger.trim()).toLowerCase();
    }

    @Override
    public void startTracking() {
        String insertDefaultSql = String.format("INSERT INTO %s (%s, %s) VALUES (?, 0)",
                LOG_TABLE_NAME, TABLE_ID_COLUMN_NAME, INVALIDATED_COLUMN_NAME);
        String createTriggerSql =
                "CREATE TRIGGER %s " +
                "AFTER %s ON %s " +
                "FOR EACH ROW " +
                "BEGIN " +
                "    UPDATE %s SET %s = 1 WHERE %s = %d AND %s = 0; " +
                "END;";
        for(Map.Entry<Integer, String> entry: tableMap.entrySet()) {
            queryExecutor.execute(insertDefaultSql, entry.getKey());
            for(String trigger: TRIGGERS) {
                queryExecutor.execute(String.format(createTriggerSql,
                        getTriggerName(entry.getValue(), trigger),
                        trigger,
                        entry.getValue(),
                        LOG_TABLE_NAME,
                        INVALIDATED_COLUMN_NAME,
                        TABLE_ID_COLUMN_NAME,
                        entry.getKey(),
                        INVALIDATED_COLUMN_NAME
                ));
            }
        }
    }

    @Override
    public void stopTracking() {
        String dropTableSql = "DROP TABLE IF EXISTS " + LOG_TABLE_NAME;
        String dropTriggerSql = "DROP TRIGGER IF EXISTS %s";
        for(Map.Entry<Integer, String> entry: tableMap.entrySet()) {
            for(String trigger: TRIGGERS) {
                queryExecutor.execute(String.format(dropTriggerSql, getTriggerName(entry.getValue(), trigger)));
            }
        }
        queryExecutor.execute(dropTableSql);
    }

    @Override
    public Set<String> getInvalidatedTables() {
        String selectInvalidatedSql = String.format("SELECT * FROM %s WHERE %s = 1",
                LOG_TABLE_NAME, INVALIDATED_COLUMN_NAME);
        String resetInvalidatedStatus = String.format("UPDATE %s SET %s = 0 WHERE %s = 1",
                LOG_TABLE_NAME, INVALIDATED_COLUMN_NAME, INVALIDATED_COLUMN_NAME);
        List<String> resultList = queryExecutor.executeQuery(selectInvalidatedSql, rs -> {
            try {
                return tableMap.get(rs.getInt(TABLE_ID_COLUMN_NAME));
            } catch (SQLException e) {
                return null;
            }
        });
        queryExecutor.execute(resetInvalidatedStatus);
        return new TreeSet<>(resultList);
    }
}
