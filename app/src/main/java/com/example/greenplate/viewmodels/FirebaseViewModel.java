package com.example.greenplate.viewmodels;

import com.google.firebase.auth.FirebaseAuth;
import com.example.greenplate.models.Firebase;

public class FirebaseViewModel {
    private static Firebase firebase;
    public FirebaseViewModel() {
        firebase = new Firebase();
    }
    public static boolean isUserLoggedIn() {
        return firebase.getAuth().getCurrentUser() != null;
    }

    public static FirebaseAuth getAuth() {
        return firebase.getAuth();
    }
}
