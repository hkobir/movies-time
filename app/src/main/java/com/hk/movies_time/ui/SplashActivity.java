package com.hk.movies_time.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hk.movies_time.R;
import com.hk.movies_time.databinding.ActivitySplashBinding;
import com.hk.movies_time.models.User;
import com.hk.movies_time.utils.Common;
import com.hk.movies_time.utils.DataPreference;
import com.hk.movies_time.viewmodel.UserViewModel;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private final String SESSION_KEY = "user";
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //animate
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.blink_icon);
        binding.splashIcon.startAnimation(animation);
        //post delayed on welcome screen
        splash();
    }

    private void splash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkInitialLogic();
            }


        }, 2000);
    }

    private void checkInitialLogic() {
        if (DataPreference.getString(SplashActivity.this, SESSION_KEY).equals("")) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        } else {
            goHome(
                    DataPreference.getString(SplashActivity.this, SESSION_KEY)
            );
        }
    }

    private void goHome(String email) {
        userViewModel.getUserByEmail(email).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Common.currentUser = user;
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
}