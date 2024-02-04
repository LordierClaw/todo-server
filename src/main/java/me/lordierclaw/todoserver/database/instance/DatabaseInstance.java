package me.lordierclaw.todoserver.database.instance;

import me.lordierclaw.todoserver.database.dao.*;
import me.lordierclaw.todoserver.database.utils.trigger.TriggerTracker;

public interface DatabaseInstance {
    TaskDao getTaskDao();

    SubtaskDao getSubtaskDao();

    CategoryDao getCategoryDao();

    AttachmentDao getAttachmentDao();

    UserDao getUserDao();

    TriggerTracker getTriggerTracker();
}
