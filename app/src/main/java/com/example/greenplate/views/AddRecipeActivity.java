package com.example.greenplate.views;

import com.example.greenplate.R;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddRecipeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_add_page);

        // Initialize navigation buttons and set their onClickListeners.
        final ImageButton toHome = findViewById(R.id.toHomePage);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        RecipeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        // A HashMap to store the dish name and its corresponding ingredients and quantities
        final HashMap<String, List<Pair<String, Integer>>> dishIngredientsMap = new HashMap<>();

        final ImageButton to_add_ingredient_button = findViewById(R.id.to_add_ingredient_page);
        to_add_ingredient_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the dish name and ingredients and quantities
                EditText dishNameEditText = (EditText)findViewById(R.id.ingredient_name_enter);
                EditText ingredientEditText = (EditText)findViewById(R.id.ingredient_quantity_enter);
                EditText quantityEditText = (EditText)findViewById(R.id.ingredient_calories_enter);

                String dishName = dishNameEditText.getText().toString();
                String ingredient = ingredientEditText.getText().toString();
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityEditText.getText().toString());
                } catch (NumberFormatException e) {
                    // Handle exception for invalid number format
                    quantityEditText.setError("Please enter a valid number");
                    return;
                }

                // Check if dish name is not empty
                if (dishName.isEmpty()) {
                    dishNameEditText.setError("Please enter a dish name");
                    return;
                }

                // Check if ingredient is not empty
                if (ingredient.isEmpty()) {
                    ingredientEditText.setError("Please enter an ingredient");
                    return;
                }

                // Add the ingredient and quantity to the list for this dish name in the map
                List<Pair<String, Integer>> ingredientsList = dishIngredientsMap.getOrDefault(dishName, new ArrayList<>());
                ingredientsList.add(new Pair<>(ingredient, quantity));
                dishIngredientsMap.put(dishName, ingredientsList);

                // Clear the ingredient and quantity fields
                ingredientEditText.setText("");
                quantityEditText.setText("");

                // Optionally, you can display a toast to indicate the ingredient was added
                Toast.makeText(AddRecipeActivity.this, "Ingredient added", Toast.LENGTH_SHORT).show();
            }
        });


        final Button saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the dish name
                EditText dishNameEditText = findViewById(R.id.ingredient_name_enter);
                String dishName = dishNameEditText.getText().toString().trim();

                if (dishName.isEmpty()) {
                    Toast.makeText(AddRecipeActivity.this, "Please enter a dish name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Retrieve the list of ingredients (as pairs) for this dish
                List<Pair<String, Integer>> ingredientsPairs = dishIngredientsMap.get(dishName);
                if (ingredientsPairs == null || ingredientsPairs.isEmpty()) {
                    Toast.makeText(AddRecipeActivity.this, "No ingredients added for this dish", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert the pairs to Ingredient objects
                List<Ingredient> ingredientsForThisDish = new ArrayList<>();
                for (Pair<String, Integer> pair : ingredientsPairs) {
                    // Assuming the default constructor of Ingredient takes name, quantity, and userId in that order
                    // Also assuming 0 for calories and an empty string for the expiration date
                    ingredientsForThisDish.add(new Ingredient(pair.first, 0, pair.second, FirebaseViewModel.getInstance().getUser().getUserId()));
                }

                // Create the Recipe object
                String userId = FirebaseViewModel.getInstance().getUser().getUserId(); // Replace with actual user ID retrieval logic
                // Create the Recipe object
                Recipe newRecipe = new Recipe(dishName, ingredientsForThisDish, userId);

                // Use RecipeViewModel to add the recipe to the database
                RecipeViewModel.addRecipe(newRecipe);

                // Clear the inputs and the ingredients list from the map
                dishNameEditText.setText("");
                dishIngredientsMap.remove(dishName);

                Toast.makeText(AddRecipeActivity.this, "Recipe saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
