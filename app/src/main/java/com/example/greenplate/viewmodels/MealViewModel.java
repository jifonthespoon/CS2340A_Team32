package com.example.greenplate.viewmodels;

import androidx.lifecycle.ViewModel;
import com.example.greenplate.models.Meal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MealViewModel extends ViewModel {
    private DatabaseReference databaseMeals;

    public MealViewModel() {
        // Reference to the "meals" node in the Firebase Realtime Database
        databaseMeals = FirebaseDatabase.getInstance().getReference("meals");
    }

    // Method to add or update a meal in the database
    public void saveOrUpdateMeal(Meal meal) {
        if (meal != null && meal.mealId != null) {
            // Using the mealId as the key to store meal information
            databaseMeals.child(meal.mealId).setValue(meal);
        }
    }

    // Method to delete a meal from the database
    public void deleteMeal(String mealId) {
        if (mealId != null) {
            databaseMeals.child(mealId).removeValue();
        }
    }
}