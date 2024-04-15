package com.example.greenplate.views;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenplate.R;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;

public class AddShoppingActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting.
     * This method handles the initialization of the activity,
     * setting the content view from a layout resource defined
     * in R.layout.ingredient_page. It also
     * initializes each navigation button in the navigation
     * bar and sets onClickListeners to them, enabling navigation to
     * different parts of the application based on user interaction.
     *
     * @param savedInstanceState If the activity is being
     *                           re-initialized after previously
     *                           being shut down,
     *                           this Bundle contains the
     *                           data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     *                           This bundle can be used
     *                           to reconstruct the activity
     *                           to its previous state if necessary.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shopping_page);
        // Initialize navigation buttons and set their onClickListeners.
        final ImageButton toHome = findViewById(R.id.toHomePage);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShoppingActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShoppingActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe.recipeTab tab = RecipeViewModel.getRecipeTab();
                Intent intent = new Intent(AddShoppingActivity.this,
                        tab == Recipe.recipeTab.AtoZ ? RecipeActivityAtoZ.class : tab ==
                                Recipe.recipeTab.ZtoA ? RecipeActivityZtoA.class :
                                RecipeActivityCanCook.class);
                startActivity(intent);
            }
        });
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShoppingActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShoppingActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddShoppingActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });


        final Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = findViewById(R.id.shoppingList_name_enter);
                EditText quantityEditText = findViewById(R.id.shoppingList_quantity_enter);
                EditText caloriesEditText = findViewById(R.id.shoppingList_calories_enter);

                // Extract the text from each EditText
                String name = nameEditText.getText().toString();
                String quantityStr = quantityEditText.getText().toString();
                String caloriesStr = caloriesEditText.getText().toString();

                // Convert quantity and calories from String to int
                int quantity = 0;
                int calories = 0;
                try {
                    quantity = Integer.parseInt(quantityStr);
                    calories = Integer.parseInt(caloriesStr);
                } catch (NumberFormatException e) {
                    // Handle the case where either quantity or calories isn't a valid integer
                    Toast.makeText(AddShoppingActivity.this, "Quantity and Calories "
                            + "must be valid numbers.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate the input (e.g., check if name is empty, quantity is positive, etc.)
                if (name.isEmpty() || quantity <= 0) {
                    Toast.makeText(AddShoppingActivity.this, "Please fill out the "
                                    + "name and make sure quantity is positive.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                //IngredientsViewModel.addIngredient(name, calories, quantity, expirationDate);

                nameEditText.setText("");
                quantityEditText.setText("");
                caloriesEditText.setText("");

                Toast.makeText(AddShoppingActivity.this, "Item saved!",
                        Toast.LENGTH_SHORT).show();
                RecipeViewModel.fetchRecipes(FirebaseViewModel.getInstance().getUser());
            }
        });



    }
}
