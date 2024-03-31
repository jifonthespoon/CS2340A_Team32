package com.example.greenplate.models;

import com.example.greenplate.viewmodels.IngredientsViewModel;

import java.util.HashMap;
import java.util.Map;

public class Ingredient {
    private String name;
    private int calories;
    private int quantity;
    private String expirationDate = "";

    private String id;

    private String userId;

    public Ingredient(String n, int c, int q, String uI) {
        name = n;
        calories = c;
        quantity = q;
        userId = uI;
    }

    public Ingredient(String n, int c, int q, String eD, String uI) {
        name = n;
        calories = c;
        quantity = q;
        expirationDate = eD;
        userId = uI;
    }

    public Ingredient(String n, int c, int q, String eD, String uI, String id) {
        name = n;
        calories = c;
        quantity = q;
        expirationDate = eD;
        userId = uI;
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCalories() {
        return calories;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getMap() {
        Map<String, String> info = new HashMap<>();
        info.put("name", name);
        info.put("quantity", Integer.toString(quantity));
        info.put("calories", Integer.toString(calories));
        info.put("expirationDate", expirationDate);
        info.put("userId", userId);
        return info;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public int decreaseQuantity() {
        quantity--;
        return quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "You have " + quantity + " " + name + " and each are " + calories + " calories." + " " + id + " " + userId + " " + expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
}
