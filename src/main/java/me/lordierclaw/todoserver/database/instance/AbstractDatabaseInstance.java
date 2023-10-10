package me.lordierclaw.todoserver.database.instance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractDatabaseInstance implements IDatabaseInstance {
    private ExecutorService databaseExecutor;

    public void initExecutor(int nThreads) {
        databaseExecutor = Executors.newFixedThreadPool(nThreads);
    }

    public ExecutorService getDatabaseExecutor() {
        return databaseExecutor;
    }
}
