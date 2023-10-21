package me.lordierclaw.todoserver.database.dao;

import me.lordierclaw.todoserver.database.utils.query.IQueryExecutor;
import me.lordierclaw.todoserver.database.utils.query.IQueryExecutorBuilder;
import me.lordierclaw.todoserver.database.utils.query.PreparedExecutor;

public abstract class AbstractDao {
    protected final IQueryExecutorBuilder executorBuilder;

    public AbstractDao(IQueryExecutorBuilder executorBuilder) {
        this.executorBuilder = executorBuilder;
    }

    public PreparedExecutor prepareExecutor() {
        return new PreparedExecutor() {
            @Override
            protected IQueryExecutor buildExecutor() {
                return executorBuilder.getExecutor();
            }
        };
    }

    public PreparedExecutor prepareExecutorWithTypes(int... sqlTypes) {
        return new PreparedExecutor() {
            @Override
            protected IQueryExecutor buildExecutor() {
                return executorBuilder.getExecutor().setSQLTypes(sqlTypes);
            }
        };
    }
}


