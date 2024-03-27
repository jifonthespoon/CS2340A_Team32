package com.example.greenplate.viewmodels;

import androidx.annotation.NonNull;

import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeViewModel {
    private static Firebase firebase = Firebase.getInstance();

    public static void addRecipe(Recipe recipe) {
        DatabaseReference recipesRef = firebase.getDatabase().getReference().child("recipe").push();
        recipesRef.setValue(recipe.toMap());
        recipe.setId(recipesRef.getKey());
    }

    public static void fetchRecipes(ValueEventListener listener) {
        DatabaseReference recipesRef = firebase.getDatabase().getReference().child("recipe");
        recipesRef.addListenerForSingleValueEvent(listener);
    }

    public static void removeRecipe(String recipeId) {
        firebase.getDatabase().getReference().child("recipe").child(recipeId).removeValue();
    }

}
