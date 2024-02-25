package com.example.greenplate.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.greenplate.R;
import com.example.greenplate.viewmodels.FirebaseViewModel;

import android.content.Intent;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        FirebaseViewModel fvm = new FirebaseViewModel();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                boolean userLoggedIn = fvm.isUserLoggedIn();
                if (userLoggedIn) {
                    // Will be home activity when ready
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        System.exit(0);
    }
}