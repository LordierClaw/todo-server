package me.lordierclaw.todoserver.database.instance;

import me.lordierclaw.todoserver.database.dao.*;
import me.lordierclaw.todoserver.database.utils.trigger.ITriggerTracker;

public interface IDatabaseInstance {
    ITaskDao getTaskDao();
    ISubtaskDao getSubtaskDao();
    ICategoryDao getCategoryDao();
    IAttachmentDao getAttachmentDao();
    IUserDao getUserDao();
    ITriggerTracker getTriggerTracker();
}
