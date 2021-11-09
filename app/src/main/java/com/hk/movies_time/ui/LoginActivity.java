package com.hk.movies_time.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.hk.movies_time.R;

import com.hk.movies_time.databinding.ActivityLoginBinding;
import com.hk.movies_time.models.User;
import com.hk.movies_time.utils.Common;
import com.hk.movies_time.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private boolean showPassword = true;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

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

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.userIdET.getText().toString().equals("")) {
                    binding.userIdET.setError("email is required");
                    binding.userIdET.requestFocus();
                } else if (!binding.userIdET.getText().toString().trim().matches(Common.emailPattern)) {
                    binding.userIdET.setError("invalid email");
                    binding.userIdET.requestFocus();
                } else if (binding.passET.getText().toString().equals("")) {
                    binding.passET.setError("pass is required");
                    binding.passET.requestFocus();
                } else {
                    loginUser(
                            binding.userIdET.getText().toString().trim(),
                            binding.passET.getText().toString()
                    );
                }
            }
        });

        binding.createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void loginUser(String email, String pass) {
        binding.progressLoad.setVisibility(View.VISIBLE);
        userViewModel.getUser(email, pass).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Common.currentUser = user;
                    binding.progressLoad.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    binding.progressLoad.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Wrong userId or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}