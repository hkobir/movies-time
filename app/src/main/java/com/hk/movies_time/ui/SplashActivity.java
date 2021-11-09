package com.hk.movies_time.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hk.movies_time.R;
import com.hk.movies_time.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

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
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
    }
}