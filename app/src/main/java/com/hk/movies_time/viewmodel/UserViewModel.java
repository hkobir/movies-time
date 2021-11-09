package com.hk.movies_time.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hk.movies_time.models.User;
import com.hk.movies_time.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    UserRepository userRepository;
    LiveData<User> userLiveData;
    LiveData<User> emailExistLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<User> getUser(String email, String password) {
        userLiveData = userRepository.getUser(email, password);
        return userLiveData;
    }

    public LiveData<User> getUserByEmail(String email) {
        emailExistLiveData = userRepository.getUserByEmail(email);
        return emailExistLiveData;
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }
}
