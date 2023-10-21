package me.lordierclaw.todoserver.repository;

import me.lordierclaw.todoserver.controller.socket.LiveDataSessionHandler;
import me.lordierclaw.todoserver.database.instance.AbstractDatabaseInstance;

import javax.inject.Inject;
import java.util.Set;

public class AbstractRepository {

    @Inject
    protected AbstractDatabaseInstance databaseInstance;

    private final LiveDataSessionHandler liveDataSessionHandler = LiveDataSessionHandler.getInstance();

    public void invalidate() {
        Set<String> invalidatedTables = databaseInstance.getTriggerTracker().getInvalidatedTables();
        liveDataSessionHandler.invalidate(invalidatedTables);
    }
}
