package me.lordierclaw.todoserver.database.utils.trigger;

import java.util.Set;

public interface ITriggerTracker {
    void createTrackingTable();

    void startTracking();

    void stopTracking();

    Set<String> getInvalidatedTables();
}
