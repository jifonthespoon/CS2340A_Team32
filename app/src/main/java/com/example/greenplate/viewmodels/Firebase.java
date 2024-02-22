package com.example.greenplate.viewmodels;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("https://greenplate-df836-default-rtdb.firebaseio.com/");

    static FirebaseAuth mAuth = FirebaseAuth.getInstance()
    public static boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

}
