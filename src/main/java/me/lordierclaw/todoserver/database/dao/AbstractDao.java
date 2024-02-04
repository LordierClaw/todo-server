package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.database.utils.query.PreparedExecutor;
import me.lordierclaw.todoserver.database.utils.query.QueryExecutor;
import me.lordierclaw.todoserver.database.utils.query.QueryExecutorBuilder;

public abstract class AbstractDao {
    protected final QueryExecutorBuilder executorBuilder;

    public AbstractDao(QueryExecutorBuilder executorBuilder) {
        this.executorBuilder = executorBuilder;
    }

    public PreparedExecutor prepareExecutor() {
        return new PreparedExecutor() {
            @Override
            protected QueryExecutor buildExecutor() {
                return executorBuilder.getExecutor();
            }
        };
    }

    public PreparedExecutor prepareExecutorWithTypes(int... sqlTypes) {
        return new PreparedExecutor() {
            @Override
            protected QueryExecutor buildExecutor() {
                return executorBuilder.getExecutor().setSQLTypes(sqlTypes);
            }
        };
    }
}


