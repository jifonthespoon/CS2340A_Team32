package com.example.greenplate.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a meal with basic information for logging and tracking in the application.
 */
public class Meal {
    public String mealId; // Unique ID for Firebase operations
    public String name; // Name of the meal
    public int calories; // Estimated calorie count of the meal
    public String dateAdded; // The date the meal was added

    // Default constructor for Firebase
    public Meal() {
    }

    // Constructor used for creating a new Meal instance
    public Meal(String mealId, String name, int calories, String dateAdded) {
        this.mealId = mealId;
        this.name = name;
        this.calories = calories;
        this.dateAdded = dateAdded;
    }

    // Method to convert a meal object into a Map for Firebase
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("mealId", mealId);
        result.put("name", name);
        result.put("calories", calories);
        result.put("dateAdded", dateAdded);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return calories == meal.calories &&
                Objects.equals(mealId, meal.mealId) &&
                Objects.equals(name, meal.name) &&
                Objects.equals(dateAdded, meal.dateAdded);
    }
}