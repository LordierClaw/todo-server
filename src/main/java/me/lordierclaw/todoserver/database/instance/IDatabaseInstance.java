package me.lordierclaw.todoserver.database.instance;

import me.lordierclaw.todoserver.database.dao.*;

public interface IDatabaseInstance {
    ITaskDao getTaskDao();
    ISubtaskDao getSubtaskDao();
    ICategoryDao getCategoryDao();
    IAttachmentDao getAttachmentDao();
    IUserDao getUserDao();
}
