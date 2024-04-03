package com.example.greenplate.models;

import java.util.HashMap;
import java.util.Map;

public class Recipe implements Comparable<Recipe> {
    private String recipeName;
    private HashMap<String, Integer> ingredients;
    private String id; // Unique identifier for the recipe
    private boolean canMake;

    public Recipe(String recipeName, HashMap<String, Integer> ingredients, String id, boolean canMake) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.id = id;
        this.canMake = canMake;
    }

    public Recipe(String recipeName, HashMap<String, Integer> ingredients) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
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
    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }

    // Setter for ingredients
    public void setIngredients(HashMap<String, Integer> ingredients) {
        this.ingredients = ingredients;
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

        // Convert ingredients list to a map
        Map<String, String> ingredientsMap = new HashMap<>();
        for (String ingredient : ingredients.keySet()) {
            ingredientsMap.put(ingredient, String.valueOf(ingredients.get(ingredient)));
        }
        recipeMap.put("ingredients", ingredientsMap);

        return recipeMap;
    }

    @Override
    public int compareTo(Recipe o) {
        return this.recipeName.toLowerCase().compareTo(o.getRecipeName().toLowerCase());
    }

    public boolean isCanMake() {
        return canMake;
    }
}