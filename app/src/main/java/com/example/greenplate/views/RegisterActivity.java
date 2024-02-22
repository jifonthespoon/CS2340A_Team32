package com.example.greenplate.views;

import static com.example.greenplate.views.LoginActivity.checkInput;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.register);
        EditText emailInput = findViewById(R.id.emailAddress);
        EditText passwordInput = findViewById(R.id.passwordCreation);
        EditText passwordInputConfirmation = findViewById(R.id.passwordConfirmation);
        EditText nameInput = findViewById(R.id.fullName);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String passwordConfirmation = passwordInputConfirmation.getText().toString();
                String name = nameInput.getText().toString();

                if (!(checkInput(email) && checkInput(password) && checkInput(passwordConfirmation) && checkInput(name))) {
                    Toast.makeText(RegisterActivity.this, "Fill out all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!passwordConfirmation.equals(password)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth mAuth = Firebase.getAuth();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Account creation failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void onClick(View v) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
