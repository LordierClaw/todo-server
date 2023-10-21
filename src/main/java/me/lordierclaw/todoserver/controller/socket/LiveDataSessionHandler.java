package me.lordierclaw.todoserver.controller.socket;

import com.google.gson.Gson;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LiveDataSessionHandler {

    // Note: I can't annotate this class with @Singleton and casually @Inject it into [LiveDataWebSocket]
    // Time wasted on this: 4 hours
    private static LiveDataSessionHandler instance = null;

    public static LiveDataSessionHandler getInstance() {
        if (instance == null)
            instance = new LiveDataSessionHandler();
        return instance;
    }

    private final Map<Session, Set<String>> sessionTableMap;

    LiveDataSessionHandler() {
        sessionTableMap = new HashMap<>();
    }

    public void addSession(Session session) {
        sessionTableMap.put(session, new HashSet<>());
    }

    public void removeSession(Session session) {
        sessionTableMap.remove(session);
    }

    public void sessionAddTable(Session session, String table) {
        if (!sessionTableMap.containsKey(session)) return;
        Set<String> tables = sessionTableMap.get(session);
        tables.add(table);
    }

    public void sessionRemoveTable(Session session, String table) {
        if (!sessionTableMap.containsKey(session)) return;
        Set<String> tables = sessionTableMap.get(session);
        tables.remove(table);
    }

    public void invalidate(Collection<String> invalidatedTables) {
        for (Map.Entry<Session, Set<String>> entry: sessionTableMap.entrySet()) {
            Session session = entry.getKey();
            Set<String> tables = entry.getValue();
            List<String> invalidated = checkInvalidatedTables(tables, invalidatedTables);
            if (!invalidated.isEmpty()) {
                Gson gson = new Gson();
                try {
                    session.getBasicRemote().sendText(gson.toJson(invalidated));
                } catch (IOException e) {
                    removeSession(session);
                    Logger.getLogger(LiveDataSessionHandler.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    private List<String> checkInvalidatedTables(Collection<String> tables, Collection<String> invalidatedTables) {
        List<String> result = new ArrayList<>();
        for(String invalidatedTable: invalidatedTables) {
            if (tables.contains(invalidatedTable)) {
                result.add(invalidatedTable);
            }
        }
        return result;
    }
}
