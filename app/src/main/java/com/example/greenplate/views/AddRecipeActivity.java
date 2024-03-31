package com.example.greenplate.views;

import com.example.greenplate.R;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
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


        final ImageButton to_add_ingredient_page = findViewById(R.id.to_add_ingredient_page);
        to_add_ingredient_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this,
                        AddIngredientActivity.class);
                startActivity(intent);
            }
        });


        final Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming EditTexts for recipe name and ingredient are present
                EditText recipeNameEditText = findViewById(R.id.ingredient_name_enter);
                EditText ingredientNameEditText = findViewById(R.id.ingredient_quantity_enter); // For demonstration, assuming one ingredient
                EditText quantityEditText = findViewById(R.id.ingredient_calories_enter); // Assuming this is for quantity input

                String recipeName = recipeNameEditText.getText().toString().trim();
                String ingredientName = ingredientNameEditText.getText().toString().trim();
                String quantityStr = quantityEditText.getText().toString().trim();
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(quantityStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(AddRecipeActivity.this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(recipeName.isEmpty() || ingredientName.isEmpty() || quantity <= 0) {
                    Toast.makeText(AddRecipeActivity.this, "Please fill all the fields correctly", Toast.LENGTH_SHORT).show();
                    return;
                }

                Ingredient newIngredient = new Ingredient(ingredientName, 0, quantity, "");

                List<Ingredient> ingredientsList = new ArrayList<>();
                ingredientsList.add(newIngredient);
                Recipe newRecipe = new Recipe(recipeName, ingredientsList, FirebaseViewModel.getInstance().getUser().getUserId());

                RecipeViewModel.addRecipe(newRecipe);

                recipeNameEditText.setText("");
                ingredientNameEditText.setText("");
                quantityEditText.setText("");

                Toast.makeText(AddRecipeActivity.this, "Recipe saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
