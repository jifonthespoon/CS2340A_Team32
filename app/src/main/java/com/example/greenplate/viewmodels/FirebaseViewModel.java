package com.example.greenplate.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.greenplate.models.Meal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;


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
    private static User user;

    private static FirebaseViewModel viewModel;

    /**
     * Constructs a new FirebaseViewModel and initializes the Firebase services.
     */
    private FirebaseViewModel() {
        firebase = Firebase.getInstance();
        if (firebase.getAuth().getCurrentUser() != null) {
            String email = firebase.getAuth().getCurrentUser().getEmail();
            Dictionary<String, String> userInfo = new Hashtable<>();
            final Set<String>[] mealIds = new Set[]{new HashSet<>()};
            firebase.getDatabase().getReference().child("users").orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getKey().equals("meals")) {
                            Map<String, Boolean> meals = (Map<String, Boolean>) child.getValue();
                            mealIds[0] = meals.keySet();
                        } else {
                            userInfo.put(child.getKey(), child.getValue().toString());
                        }

                    }
                    user = new User(userInfo.get("name"), Integer.valueOf(userInfo.get("weight")), userInfo.get("gender"), Integer.valueOf(userInfo.get("heightInInches")), userInfo.get("userId"), userInfo.get("email"), mealIds[0]);
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

    public static FirebaseViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new FirebaseViewModel();
        }
        return viewModel;
    }

    public static void loadUser() {
        String email = firebase.getAuth().getCurrentUser().getEmail();
        Dictionary<String, String> userInfo = new Hashtable<>();
        final Set<String>[] mealIds = new Set[]{new HashSet<>()};
        firebase.getDatabase().getReference().child("users").orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.getKey().equals("meals")) {
                        Map<String, Boolean> meals = (Map<String, Boolean>) child.getValue();
                        mealIds[0] = meals.keySet();
                    } else {
                        userInfo.put(child.getKey(), child.getValue().toString());
                    }

                }
                user = new User(userInfo.get("name"), Integer.valueOf(userInfo.get("weight")), userInfo.get("gender"), Integer.valueOf(userInfo.get("heightInInches")), userInfo.get("userId"), userInfo.get("email"), mealIds[0]);
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

    public String getPersonalInformation() {
        if (user != null && user.heightInInches != 0 && !user.gender.isEmpty() && user.weight != 0) {
            return "" + user.getHeight() + " | " + user.weight + "lbs | " + user.gender;
        } else {
            return "Fill out personal information";
        }
    }

    public String[] getPersonalInformationArray() {
        if (user != null && user.heightInInches != 0 && !user.gender.isEmpty() && user.weight != 0) {
            return new String[] {String.valueOf(user.heightInInches), String.valueOf(user.weight), user.gender};
        } else {
            return null;
        }
    }

    public String getCalorieGoal() {
        if (user.heightInInches != 0 && !user.gender.isEmpty() && user.weight != 0) {
            return "" + user.getDailyCalorieIntake();
        } else {
            return "Fill out personal information";
        }
    }

    public void addMealToUser(String mealId) {
        firebase.getDatabase().getReference().child("users").child(user.userId).child("meals").child(mealId).setValue(true);
    }
    
    public User getUser() {
       return user;
    }

    public boolean saveOrUpdateMeal(Meal meal) {

        if (meal != null && meal.mealId != null && !meal.name.isEmpty()) {
            // Using the mealId as the key to store meal information
            firebase.getDatabase().getReference().child("meals").child(meal.mealId).setValue(meal.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        addMealToUser(meal.mealId);
                        Log.d("Meal Save", "Meal successfully saved to Firebase");
                    } else {
                        Log.d("Meal Save", "Failed to save meal to Firebase");
                    }
                }
            });
            return true;
        }
        return false;
    }

    // Method to delete a meal from the database
    public void deleteMeal(String mealId) {
        if (mealId != null) {
            firebase.getDatabase().getReference().child("meals").child(mealId).removeValue();
        }
    }
}

