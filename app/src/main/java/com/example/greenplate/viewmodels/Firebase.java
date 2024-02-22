package com.example.greenplate.viewmodels;
import androidx.annotation.NonNull;

import com.example.greenplate.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("https://greenplate-df836-default-rtdb.firebaseio.com/");
    private User user;

    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static boolean isUserLoggedIn() {
        System.out.println("Made it");
        return mAuth.getCurrentUser() != null;
    }

    public static FirebaseAuth getAuth() {
        return mAuth;
    }
}
