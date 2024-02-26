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
    private String name;

    /**
     * Constructs a new User instance with the specified name.
     *
     * @param nameValue The name of the user to be set during object creation.
     */
    public User(String nameValue) {
        this.name = nameValue;
    }
}
