package com.example.greenplate.viewmodels;

import androidx.annotation.NonNull;

import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeViewModel implements RecipeNamesCallback{
    private static Firebase firebase = Firebase.getInstance();

    public static void addRecipe(Recipe recipe) {
        DatabaseReference recipesRef = firebase.getDatabase().getReference().child("cookbook").push();
        recipesRef.setValue(recipe.toMap());
        recipe.setId(recipesRef.getKey());
    }

    public static void fetchRecipes(ValueEventListener listener) {
        DatabaseReference recipesRef = firebase.getDatabase().getReference().child("cookbook");
        recipesRef.addListenerForSingleValueEvent(listener);
    }
    public String[] onRecipeNamesFetched(String[] recipeNames) {
        return recipeNames;
    }
    public static void getRecipeNames(RecipeNamesCallback callback) {
        fetchRecipes(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> recipeNames = new ArrayList<>();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    String recipeName = recipeSnapshot.child("name").getValue(String.class);
                    recipeNames.add(recipeName);
                }

                // Convert List to Array before passing it to the callback
                String[] recipeNamesArray = recipeNames.toArray(new String[0]);
                callback.onRecipeNamesFetched(recipeNamesArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Listener was cancelled, error: " + databaseError.getMessage());
            }
        });
    }


    public static void removeRecipe(String recipeId) {
        firebase.getDatabase().getReference().child("cookbook").child(recipeId).removeValue();
    }
    public static void fetchAllRecipesWithIndication(User user, ValueEventListener listener) {
        DatabaseReference recipesRef = firebase.getDatabase().getReference().child("cookbook");
        recipesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipesWithIndication = new ArrayList<>();
                Map<String, Integer> userIngredientsQuantities = new HashMap<>();

                // Convert the user's ingredients list to a map for easier comparison
                for (Ingredient userIngredient : user.getIngredients()) {
                    userIngredientsQuantities.put(userIngredient.getName().toLowerCase(), userIngredient.getQuantity());
                }

                for (DataSnapshot recipeSnapshot : snapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    boolean hasAllIngredients = true;

                    for (Ingredient ingredient : recipe.getIngredients()) {
                        String ingredientName = ingredient.getName().toLowerCase();
                        int requiredQuantity = ingredient.getQuantity();

                        if (!userIngredientsQuantities.containsKey(ingredientName) ||
                                userIngredientsQuantities.get(ingredientName) < requiredQuantity) {
                            hasAllIngredients = false;
                            break;
                        }
                    }

                    // Optionally, use a custom object to hold the recipe and indication of ingredient sufficiency
                    if (hasAllIngredients) {
                        recipesWithIndication.add(recipe);
                    }
                    // If you wish to show all recipes but mark those the user can cook, add all to list but mark them differently
                }

                listener.onDataChange(snapshot); // Replace with passing recipesWithIndication to UI
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onCancelled(error);
            }
        });
    }

}
