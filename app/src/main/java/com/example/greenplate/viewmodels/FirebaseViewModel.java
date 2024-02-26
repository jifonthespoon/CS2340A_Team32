package com.example.greenplate.viewmodels;

import com.google.firebase.auth.FirebaseAuth;
import com.example.greenplate.models.Firebase;

/**
 * ViewModel class to handle Firebase authentication operations.
 * This class provides functionalities to check user authentication status
 * and retrieve FirebaseAuth instances for further authentication operations.
 */
public class FirebaseViewModel {
    /**
     * Singleton instance of Firebase to access Firebase services.
     */
    private static Firebase firebase;

    /**
     * Constructs a new FirebaseViewModel and initializes the Firebase services.
     */
    public FirebaseViewModel() {
        firebase = new Firebase();
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return true if a user is logged in, false otherwise.
     */
    public static boolean isUserLoggedIn() {
        return firebase.getAuth().getCurrentUser() != null;
    }

    /**
     * Retrieves the FirebaseAuth instance for managing user authentication.
     *
     * @return The FirebaseAuth instance.
     */
    public static FirebaseAuth getAuth() {
        return firebase.getAuth();
    }
}

