package com.hk.movies_time.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.hk.movies_time.models.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("select * from user where userId=:email and password=:password")
    LiveData<User> getUser(String email, String password);

    @Query("select * from user where userId=:email")
    LiveData<User> getUserByEmail(String email);

}
