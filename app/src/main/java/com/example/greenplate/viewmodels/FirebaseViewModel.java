package com.example.greenplate.viewmodels;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;

import androidx.lifecycle.ViewModel;

import java.util.Dictionary;
import java.util.Hashtable;


/**
 * ViewModel class to handle Firebase authentication operations.
 * This class provides functionalities to check user authentication status
 * and retrieve FirebaseAuth instances for further authentication operations.
 */
public class FirebaseViewModel extends ViewModel {
    /**
     * Singleton instance of Firebase to access Firebase services.
     */
    private static Firebase firebase;
    private User user;

    /**
     * Constructs a new FirebaseViewModel and initializes the Firebase services.
     */
    public FirebaseViewModel() {
        firebase = new Firebase();
        if (firebase.getAuth().getCurrentUser() != null) {
            String email = firebase.getAuth().getCurrentUser().getEmail();
            Dictionary<String, String> userInfo = new Hashtable<>();
            firebase.getDatabase().getReference().child("users").orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        userInfo.put(child.getKey(), child.getValue().toString());
                    }
                    user = new User(userInfo.get("name"), Integer.valueOf(userInfo.get("weight")), userInfo.get("gender"), Integer.valueOf(userInfo.get("heightInInches")), userInfo.get("userId"), userInfo.get("email"));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                    System.out.println(dataSnapshot.getKey());
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                    System.out.println(dataSnapshot.getKey());
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    System.out.println(error);
                }
            });
        }
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

    public User createUser(String userId, String name, String email) {
        user = new User(name, userId, email);
        firebase.getDatabase().getReference().child("users").child(userId).setValue(user);
        return user;
    }

    public void addPersonalInformation(int weight, String gender, int heightInInches) {
        String email = firebase.getAuth().getCurrentUser().getEmail();
        user.heightInInches = heightInInches;
        user.weight = weight;
        user.gender = gender;
        firebase.getDatabase().getReference().child("users").child(user.userId).setValue(user);
    }
    
    public User getUser() {
       return user;
    }
}

