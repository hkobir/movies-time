package com.hk.movies_time.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hk.movies_time.R;
import com.hk.movies_time.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nameET.getText().toString().equals("")) {
                    binding.nameET.setError("name is required");
                    binding.nameET.requestFocus();
                } else if (binding.userIdET.getText().toString().equals("")) {
                    binding.userIdET.setError("email is required");
                    binding.userIdET.requestFocus();
                } else if (!binding.userIdET.getText().toString().matches(emailPattern)) {
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
                            binding.userIdET.getText().toString(),
                            binding.passET.getText().toString()
                    );
                }

            }
        });
    }

    private void saveData(String name, String email, String pass) {

    }
}