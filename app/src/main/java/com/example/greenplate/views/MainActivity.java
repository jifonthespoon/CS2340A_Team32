package com.example.greenplate.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.Firebase;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        boolean userLoggedIn = Firebase.isUserLoggedIn();
        if (userLoggedIn) {
            // Will be home activity when ready
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

    }



}