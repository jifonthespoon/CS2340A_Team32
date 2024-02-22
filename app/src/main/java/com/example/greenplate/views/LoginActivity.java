package com.example.greenplate.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import com.example.greenplate.R;

public class LoginActivity extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView passwordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_activity);
        usernameTextView = findViewById(R.id.usernameEditText);
        passwordTextView = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);
        Button exitButton = findViewById(R.id.exitButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CharSequence utv = usernameTextView.getText();
                CharSequence ptv = passwordTextView.getText();


                if (utv != null && ptv != null && !utv.toString().equals("") &&
                        !ptv.toString().equals("") && !utv.toString().trim().equals("") &&
                        !ptv.toString().trim().equals("")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    System.out.println("Something went wrong");
                }
            }
        });
    }
}

// TODO Make sure login functionality correct (and add Firebase checking)