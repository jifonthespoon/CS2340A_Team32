package com.example.greenplate.views;

import com.example.greenplate.R;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;



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

public class ViewRecipeActivity extends AppCompatActivity {

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
        setContentView(R.layout.view_recipe_page);

        final ImageButton toHome = findViewById(R.id.toHomePage);
        final ImageButton toInput = findViewById(R.id.toInputPage);
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecipeActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecipeActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecipeActivity.this,
                        RecipeActivity.class);
                startActivity(intent);
            }
        });
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecipeActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecipeActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRecipeActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}

