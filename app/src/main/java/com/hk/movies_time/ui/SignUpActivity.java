package com.hk.movies_time.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hk.movies_time.R;
import com.hk.movies_time.databinding.ActivitySignUpBinding;
import com.hk.movies_time.models.User;
import com.hk.movies_time.utils.Common;
import com.hk.movies_time.viewmodel.UserViewModel;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nameET.getText().toString().equals("")) {
                    binding.nameET.setError("name is required");
                    binding.nameET.requestFocus();
                } else if (binding.userIdET.getText().toString().trim().equals("")) {
                    binding.userIdET.setError("email is required");
                    binding.userIdET.requestFocus();
                } else if (!binding.userIdET.getText().toString().trim().matches(Common.emailPattern)) {
                    binding.userIdET.setError("invalid email");
                    binding.userIdET.requestFocus();
                } else if (binding.passET.getText().toString().equals("")) {
                    binding.passET.setError("pass is required");
                    binding.passET.requestFocus();
                } else if (binding.confirmPassET.getText().toString().equals("")) {
                    binding.confirmPassET.setError("confirm pass is required");
                    binding.confirmPassET.requestFocus();
                } else if (!binding.passET.getText().toString().equals(binding.confirmPassET.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "password not match!", Toast.LENGTH_SHORT).show();
                } else {
                    saveData(
                            binding.nameET.getText().toString(),
                            binding.userIdET.getText().toString().trim(),
                            binding.passET.getText().toString()
                    );
                }

            }
        });

        binding.goLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void saveData(String name, String email, String pass) {
        binding.progressLoad.setVisibility(View.VISIBLE);
        userViewModel.getUserByEmail(email).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user == null) {
                    User userData = new User(name, email, pass);
                    userViewModel.insertUser(userData);
                    binding.progressLoad.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, "Signed up successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                    return;
                } else {
                    binding.userIdET.setError("Email already exist");
                    binding.userIdET.requestFocus();
                    binding.progressLoad.setVisibility(View.GONE);
                }
            }
        });
    }
}