package com.example.greenplate.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
                    FirebaseAuth mAuth = Firebase.getAuth();
                    mAuth.signInWithEmailAndPassword(utv.toString(), ptv.toString()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("RegisterActivity", "createUserWithEmailAndPassword onComplete triggered");
                            if (task.isSuccessful()) {
                                Log.d("RegisterActivity", "createUserWithEmailAndPassword successful");
                                // Home screen
                                System.out.println("Success");
                                Toast.makeText(LoginActivity.this, "Account found.", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("RegisterActivity", "createUserWithEmailAndPassword failed", task.getException());
                                Toast.makeText(LoginActivity.this, "Account not found.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid input- please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}