package com.example.greenplate.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a meal with basic information for logging and tracking in the application.
 */
public class Meal {
    public String mealId; // Unique ID for Firebase operations
    public String name; // Name of the meal
    public int calories; // Estimated calorie count of the meal

    // Default constructor for Firebase
    public Meal() {
    }

    // Constructor used for creating a new Meal instance
    public Meal(String mealId, String name, int calories) {
        this.mealId = mealId;
        this.name = name;
        this.calories = calories;
    }

    // Method to convert a meal object into a Map for Firebase
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("mealId", mealId);
        result.put("name", name);
        result.put("calories", calories);
        return result;
    }
}