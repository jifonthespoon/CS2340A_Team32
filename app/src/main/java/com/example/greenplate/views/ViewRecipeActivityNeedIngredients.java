package com.example.greenplate.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.greenplate.R;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;

import java.util.ArrayList;

public class ViewRecipeActivityNeedIngredients extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe_page_need_ingredients);
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipe");

        final ImageButton toHome = findViewById(R.id.toHomePage);
        final ImageButton toInput = findViewById(R.id.toInputPage);
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);

        Recipe.recipeTab tab = RecipeViewModel.getRecipeTab();

        // Set onClickListeners for navigation buttons
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivityNeedIngredients.this,
                        HomeActivity.class));
            }
        });
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivityNeedIngredients.this,
                        InputActivity.class));
            }
        });
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tab == Recipe.recipeTab.AtoZ) {
                    startActivity(new Intent(ViewRecipeActivityNeedIngredients.this, RecipeActivityAtoZ.class));
                } else if (tab == Recipe.recipeTab.ZtoA) {
                    startActivity(new Intent(ViewRecipeActivityNeedIngredients.this, RecipeActivityZtoA.class));
                } else {
                    startActivity(new Intent(ViewRecipeActivityNeedIngredients.this, RecipeActivityCanCook.class));
                }

            }
        });
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivityNeedIngredients.this,
                        IngredientsActivity.class));
            }
        });
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivityNeedIngredients.this,
                        ShoppingActivity.class));
            }
        });
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivityNeedIngredients.this,
                        PersonalInfoActivity.class));
            }
        });

        final Button missingShoppingList = findViewById(R.id.missingShoppingList);
        missingShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to implement
            }
        });

        // Retrieve EditTexts
        final TextView dishNameTextView = findViewById(R.id.dish_name_textView);
        final TextView ingredientTextView = findViewById(R.id.ingredient_textView);
        final TextView missingIngredientTextView = findViewById(R.id.missingIngredient_textView);
        // to implement
        
        ArrayList<Recipe> recipes = FirebaseViewModel.getInstance().getUser().getRecipes();
        Recipe recipeLookingFor = recipes.get(0);
        for (Recipe recipe : recipes) {
            if (recipe.getRecipeName().equals(recipeName)) {
                recipeLookingFor = recipe;
            }
        }

        dishNameTextView.setText(recipeLookingFor.getRecipeName());
        String ingredients = "";
        for (String ingredientName : recipeLookingFor.getIngredients().keySet()) {
            ingredients += ingredientName + " - "
                    + recipeLookingFor.getIngredients().get(ingredientName) + " \n";
        }
        ingredientTextView.setText(ingredients);

    }
}

