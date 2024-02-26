package com.example.greenplate.views;

import com.example.greenplate.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
/**
 * ShoppingActivity provides a user interface
 * for displaying and managing a shopping list.
 * This activity includes navigation buttons
 * that allow the user to switch between different parts of the application,
 * such as the home page, input page, recipe
 * page, ingredients page, and the shopping list itself for easy navigation
 * and access to various functionalities
 * within the app.
 */

public class ShoppingActivity extends AppCompatActivity {

    /**
     * Initializes the activity by inflating
     * its UI from the shopping_list layout,
     * setting up onClickListeners for
     * navigation buttons to enable users to navigate
     * to different sections of the
     * application. This setup facilitates easy access to
     * the application's features,
     * enhancing the user experience.
     *
     * @param savedInstanceState If the activity is being
     *                          re-initialized after previously
     *                          being shut down,
     *                           this Bundle contains the
     *                           data it most recently supplied
     *                           in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     *                           This bundle can be used
     *                           to recreate the activity
     *                           as it was prior to
     *                           being paused or stopped.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list);

        final ImageButton toHome = findViewById(R.id.toHomePage);
        final ImageButton toInput = findViewById(R.id.toInputPage);
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);

        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        RecipeActivity.class);
                startActivity(intent);
            }
        });
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        toShopping.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });

    }
}
