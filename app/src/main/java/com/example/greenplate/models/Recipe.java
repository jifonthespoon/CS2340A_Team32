package com.example.greenplate.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe {
    private String recipeName;
    private List<Ingredient> ingredients;
    private String userId; // The user who submitted the recipe
    private String id; // Unique identifier for the recipe

    public Recipe(String recipeName, List<Ingredient> ingredients, String userId) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.userId = userId;
    }

    // Getter for recipeName
    public String getRecipeName() {
        return recipeName;
    }

    // Setter for recipeName
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    // Getter for ingredients
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    // Setter for ingredients
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    // Getter for userId
    public String getUserId() {
        return userId;
    }

    // Setter for userId
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter for id
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> recipeMap = new HashMap<>();
        recipeMap.put("recipeName", recipeName);
        recipeMap.put("userId", userId);

        // Convert ingredients list to a map
        Map<String, String> ingredientsMap = new HashMap<>();
        for (Ingredient ingredient : ingredients) {
            ingredientsMap.put(ingredient.getName(), Integer.toString(ingredient.getQuantity()));
        }
        recipeMap.put("ingredients", ingredientsMap);

        return recipeMap;
    }
}