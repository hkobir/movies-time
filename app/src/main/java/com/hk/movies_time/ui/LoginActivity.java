package com.hk.movies_time.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.hk.movies_time.R;
import com.hk.movies_time.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private boolean showPassword = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //set pass visibility
        binding.visibilityIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPassword) {
                    binding.passET.setTransformationMethod(null);
                    binding.visibilityIV.setImageResource(R.drawable.ic_baseline_visibility_off);
                    showPassword = false;
                } else {
                    binding.passET.setTransformationMethod(new PasswordTransformationMethod());
                    binding.visibilityIV.setImageResource(R.drawable.ic_baseline_visibility);
                    showPassword = true;
                }
            }
        });
    }
}