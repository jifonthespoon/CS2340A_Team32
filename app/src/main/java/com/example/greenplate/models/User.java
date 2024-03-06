package com.example.greenplate.models;

import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a user in the application with basic user information.
 * This class is part of the models package used to encapsulate user data.
 */
public class User {
    /**
     * The name of the user. This field stores the user's name for
     * identification and personalization purposes.
     */
    public String name;
    public int weight = 0;
    public String gender = "";
    public int heightInInches = 0;
    public String userId;
    public String email;
    public ArrayList<String> meals = new ArrayList<>();

    /**
     * Constructs a new User instance with the specified name.
     *
     * @param name The name of the user to be set during object creation.
     */
    public User(String name, int weight, String gender, int heightInInches, String id, String email) {
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.heightInInches = heightInInches;
        userId = id;
        this.email = email;
        meals = new ArrayList<>();
    }

    public User(String name, int weight, String gender, int heightInInches, String id, String email, Set<String> mealIds) {
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.heightInInches = heightInInches;
        userId = id;
        this.email = email;
        meals = new ArrayList<>();
        for (String meal : mealIds) {
            meals.add(meal);
        }
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String id, String email) {
        this.name = name;
        userId = id;
        this.email = email;
    }

    public String getHeight() {
        int feet = heightInInches / 12;
        int inches = heightInInches -  ((heightInInches / 12) * 12);
        return "" + feet + "' " + inches + "\"";
    }
}
