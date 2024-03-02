package com.example.greenplate.models;

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
    public int weight;
    public String gender;
    public int heightInInches;
    public String userId;
    public String email;

    /**
     * Constructs a new User instance with the specified name.
     *
     * @param name The name of the user to be set during object creation.
     */
    public User(String name, int weight, String gender, int heightInInches, String id) {
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.heightInInches = heightInInches;
        userId = id;
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String id, String email) {
        this.name = name;
        userId = id;
        this.email = email;
    }
}
