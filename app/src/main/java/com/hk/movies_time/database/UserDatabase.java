package com.hk.movies_time.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hk.movies_time.dao.UserDao;
import com.hk.movies_time.models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao taskDao();

    private static volatile UserDatabase INSTANCE;   //access variable from any thread respect to any change
    private static final int NUMBER_OF_THREAD = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public static UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user_database")
                            .build();


                }
            }
        }
        return INSTANCE;
    }

}

