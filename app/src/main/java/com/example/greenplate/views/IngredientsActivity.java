package com.example.greenplate.views;

import static com.example.greenplate.R.id.toPersonalPage;

import com.example.greenplate.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * IngredientsActivity is responsible for displaying
 * the ingredients page within the app,
 * allowing users to access and view various
 * ingredients needed for cooking. It also includes
 * navigation options to other primary features
 * of the app like the home page, input page,
 * recipe page, shopping list, and the
 * ingredients page itself for refresh purposes.
 * This activity serves as a central point
 * for ingredient management and navigation.
 */

public class IngredientsActivity extends AppCompatActivity {

    private String itemString[] = {"ingredient 1", "ingredient 2", "ingredient 3", "ingredient 4",
            "ingredient 5", "ingredient 6", "ingredient 7", "ingredient 8",
            "ingredient 9", "ingredient 10", "ingredient 11", "ingredient 12"};
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
        setContentView(R.layout.ingredient_page);

        // add code here
        ListView listView = findViewById(R.id.ingredients_list);
        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<>(IngredientsActivity.this ,
                android.R.layout.simple_list_item_1, itemString);
        listView.setAdapter(arrayAdapter);


        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = (String) adapterView.getItemAtPosition(i);
            Toast.makeText(IngredientsActivity.this, "Selected" + item,
                    Toast.LENGTH_SHORT).show();

            // can implement add ingredients here
        });



        // Initialize navigation buttons and set their onClickListeners.
        final ImageButton toHome = findViewById(R.id.toHomePage);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        RecipeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton toAddIngredientActivity = findViewById(R.id.to_add_ingredient_page);
        toAddIngredientActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsActivity.this,
                        AddIngredientActivity.class);
                startActivity(intent);
            }
        });

    }
}
