package com.example.greenplate.views;

import com.example.greenplate.R;

import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.SortingStrategy;

import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;
import com.example.greenplate.viewmodels.SortByName;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;


/**
 * RecipeActivity serves as the primary interface
 * for displaying recipes to the user.
 * It includes navigation buttons to various
 * sections of the app, such as the home page,
 * input page, ingredients page, and shopping
 * list, allowing for easy access to these resources.
 * This activity is part of the application's
 * effort to organize and display cooking recipes in a user-friendly manner.
 */

public class RecipeActivity extends AppCompatActivity {
    private SortingStrategy sortingStrategy;



    /**
     * Initializes the activity by setting
     * the content view to the recipe page layout
     * and configuring onClickListeners
     * for navigation buttons. These listeners enable
     * users to navigate to different
     * parts of the application, such
     * as the home activity,
     * input activity, recipe activity
     * (refreshing the current view), ingredients activity,
     * and shopping activity, enhancing
     * the app's usability and navigation experience.
     *
     * @param savedInstanceState If the activity is being
     *                           re-initialized after
     *                           previously being shut down,
     *                           this Bundle contains
     *                           the data it most recently
     *                           supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page);
        RecipeViewModel.fetchRecipes(FirebaseViewModel.getInstance().getUser());
        ArrayList<Recipe> recipes = FirebaseViewModel.getInstance().getUser().getRecipes();
        sortingStrategy = new SortByName();
        Recipe[] recipeListUnsorted = new Recipe[recipes.size()];
        for (int i = 0; i < recipes.size(); i++) {
            recipeListUnsorted[i] = recipes.get(i);
        }
        Recipe[] recipeList = sortingStrategy.sortRecipes(recipeListUnsorted);

        ListView listView = findViewById(R.id.recipe_list);
        ArrayList<String> recipeNameList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            recipeNameList.add(recipe.getRecipeName() + " " + (recipe.isCanMake() ? "✓" : "x"));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RecipeActivity.this,
                android.R.layout.simple_list_item_1, recipeNameList);
        listView.setAdapter(arrayAdapter);


        // Set item click listener for ListView
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = (String) adapterView.getItemAtPosition(i);

            // Move onClick method outside of lambda expression
            onClickItem(item);
        });


        final ImageButton toHome = findViewById(R.id.toHomePage);
        final ImageButton toInput = findViewById(R.id.toInputPage);
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this,
                        RecipeActivity.class);
                startActivity(intent);
            }
        });
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });


        final ImageButton toAddRecipe = findViewById(R.id.to_add_recipe_page);
        toAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this,
                        AddRecipeActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton toRecipeVegetarian = findViewById(R.id.toeRecipeVegetarian);
        toRecipeVegetarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this,
                        RecipeVegetarianActivity.class);
                startActivity(intent);
            }
        });


        final ImageButton to20minRecipe = findViewById(R.id.to_20min_recipe);
        to20minRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this,
                        Recipe20minActivity.class);
                startActivity(intent);
            }
        });


    }


    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    // Define onClick method outside of lambda expression
    public void onClickItem(String item) {
        Intent intent = new Intent(RecipeActivity.this, ViewRecipeActivity.class);
        intent.putExtra("name", item);
        startActivity(intent);
    }


}

