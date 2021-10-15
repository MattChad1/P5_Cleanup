package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.Dao.ProjectDao;
import com.cleanup.todoc.database.Dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Project.class, Task.class}, version = 3, exportSchema = false)
public abstract class CleanupDatabase extends RoomDatabase {
    
    private static String TAG = "Database class";

    // --- SINGLETON ---
    private static volatile CleanupDatabase INSTANCE;

    // --- DAO ---
    public abstract ProjectDao projectDao();

    public abstract TaskDao taskDao();

    // --- INSTANCE ---
    public static CleanupDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            Log.i(TAG, "getInstance: ");
            synchronized (CleanupDatabase.class) {
                if (INSTANCE == null) {
                    Log.i(TAG, "getInstance2: ");
                    INSTANCE =
                            Room.databaseBuilder(context, CleanupDatabase.class, "MyDatabase.db").addCallback(prepopulateDatabase()).build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase() {
        Log.i(TAG, "Callback function: ");
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Project[] startProjects = new Project[]{
                        new Project(0, "Projet Tartampion", 0xFFEADAD1),
                        new Project(0, "Projet Lucidia", 0xFFB4CDBA),
                        new Project(0, "Projet " + "Circus", 0xFFA3CED2),
                };

                for (Project project : startProjects) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", project.getId());
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());
                    db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
                }
            }
        };
    }
}
