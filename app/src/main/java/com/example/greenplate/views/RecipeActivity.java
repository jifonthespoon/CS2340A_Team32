package com.example.greenplate.views;

import com.example.greenplate.R;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.models.MyCustomAdapter;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.SortingStrategy;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;
import com.example.greenplate.viewmodels.SortByName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

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

    private String itemString[] = {"Margherita Pizza", "Chicken Parmesan", "Apple Pie", "PB&J",
            "Spaghetti Carbonara", "Birthday Cake", "Orange Chicken", "Braised Beef",
            "Tikki Masala", "Grilled Chicken Breast", "Calamari", "Smoked Salmon"};

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

        sortingStrategy = new SortByName();
        String[] recipeList = sortingStrategy.sortRecipes(itemString);

        ListView listView = findViewById(R.id.recipe_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RecipeActivity.this,
                android.R.layout.simple_list_item_1, recipeList);
        listView.setAdapter(arrayAdapter);


        // Set item click listener for ListView
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = (String) adapterView.getItemAtPosition(i);
            Toast.makeText(RecipeActivity.this, "Selected: " + item,
                    Toast.LENGTH_SHORT).show();

            // Move onClick method outside of lambda expression
            onClickItem();
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

    public void fetchRecipes(ArrayList<String> recipeNames) {
        RecipeViewModel.fetchRecipes(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recipeNames.clear();

                for (DataSnapshot recipeSnapshot: snapshot.getChildren()) {
                    String recipeName = (String) recipeSnapshot.child("recipeName").getValue();
                    recipeNames.add(recipeName);
                }
                handleFetchedRecipes(recipeNames);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Something went wrong");
            }
        });
    }
    public void handleFetchedRecipes(ArrayList<String> recipeNames) {
        System.out.println("The fetched recipes are " + recipeNames);
        ListView listView = findViewById(R.id.recipe_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RecipeActivity.this,
                android.R.layout.simple_list_item_1, recipeNames);
        listView.setAdapter(arrayAdapter);

        // Set item click listener for ListView
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedItem = recipeNames.get(i);
            Toast.makeText(RecipeActivity.this, "Selected: " + selectedItem,
                    Toast.LENGTH_SHORT).show();
        });
    }
    // Define onClick method outside of lambda expression
    public void onClickItem() {
        Intent intent = new Intent(RecipeActivity.this, ViewRecipeActivity.class);
        startActivity(intent);
    }


}

