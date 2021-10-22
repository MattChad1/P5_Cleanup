package com.cleanup.todoc.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.BuildConfig;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class CleanupDatabase extends RoomDatabase {

    private static String TAG = "Database class";

    // --- SINGLETON ---
    private static volatile CleanupDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // --- DAO ---
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    // --- INSTANCE ---
    public static CleanupDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CleanupDatabase.class) {
                if (INSTANCE == null) {
                    if (BuildConfig.DEBUG) INSTANCE = Room.databaseBuilder(context, CleanupDatabase.class, "MyDatabase.db").addCallback(roomCallBack).build();
                    else INSTANCE = Room.databaseBuilder(context, CleanupDatabase.class, "MyDatabase.db").build();
                }
            }
        }
        return INSTANCE;
    }

    private final static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {

        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                ProjectDao projectDao = INSTANCE.projectDao();
                TaskDao taskDao = INSTANCE.taskDao();
                projectDao.insertProject(new Project(0, "Projet Tartampion", 0xFFEADAD1));
                projectDao.insertProject(new Project(0, "Projet Lucidia", 0xFFB4CDBA));
                projectDao.insertProject(new Project(0, "Projet " + "Circus", 0xFFA3CED2));
                taskDao.insertTask(new Task(0, 1, "Task 1 - Tartampion"));
                taskDao.insertTask(new Task(0, 1, "Task 2 - Tartampion"));
                taskDao.insertTask(new Task(0, 2, "Task 1 - Lucidia"));
                if (BuildConfig.DEBUG) Log.i(TAG, "Prepopulate DB");
            });
        }
    };
}

