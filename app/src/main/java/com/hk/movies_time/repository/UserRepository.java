package com.hk.movies_time.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hk.movies_time.dao.UserDao;
import com.hk.movies_time.database.UserDatabase;
import com.hk.movies_time.models.User;

public class UserRepository {
    UserDao userDao;
    LiveData<User> userLiveData;
    LiveData<User> emailExistLiveData;

    public UserRepository(Application application) {
        UserDatabase userDatabase = UserDatabase.getDatabase(application);
        userDao = userDatabase.taskDao();
    }

    public LiveData<User> getUser(String email, String password) {
        userLiveData = userDao.getUser(email, password);
        return userLiveData;
    }

    public LiveData<User> getUserByEmail(String email) {
        emailExistLiveData = userDao.getUserByEmail(email);
        return emailExistLiveData;
    }

    public void insertUser(final User user) {
        UserDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(user);
            }
        });
    }
}
