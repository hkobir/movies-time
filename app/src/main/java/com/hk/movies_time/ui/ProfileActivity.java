package com.hk.movies_time.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hk.movies_time.R;
import com.hk.movies_time.databinding.ActivityProfileBinding;
import com.hk.movies_time.utils.Common;
import com.hk.movies_time.utils.DataPreference;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private final String SESSION_KEY = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);


        if (Common.currentUser != null) {
            binding.profileUsername.setText(Common.currentUser.getName());
            binding.profileemails.setText(Common.currentUser.getUserId());
        }

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataPreference.clearData(ProfileActivity.this, SESSION_KEY);

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}