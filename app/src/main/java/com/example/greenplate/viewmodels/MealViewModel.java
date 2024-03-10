package com.example.greenplate.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.greenplate.models.Meal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * ViewModel class to handle Firebase operations related to meals.
 */
public class MealViewModel extends ViewModel {
    private DatabaseReference databaseReference;

    public MealViewModel() {
        // Initialize Firebase DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("meals");
    }

    public void addMeal(Meal meal) {
        // Add or update a meal in the Firebase database
        databaseReference.child(meal.mealId).setValue(meal.toMap());
    }

    public DatabaseReference getMealReference(String mealId) {
        // Get a reference to a specific meal by ID
        return databaseReference.child(mealId);
    }
}