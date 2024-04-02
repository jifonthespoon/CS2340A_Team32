package com.example.greenplate.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.greenplate.R;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.Ingredient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ViewRecipeActivity extends AppCompatActivity {

    private DatabaseReference recipeRef;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe_page);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        recipeRef = database.getReference("recipes");

        // Initialize recipe list
        recipeList = new ArrayList<>();

        final ImageButton toHome = findViewById(R.id.toHomePage);
        final ImageButton toInput = findViewById(R.id.toInputPage);
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        final ImageButton toPersonalInfo = findViewById(R.id.toPersonalPage);

        // Set onClickListeners for navigation buttons
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this,
                        HomeActivity.class));
            }
        });
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this,
                        InputActivity.class));
            }
        });
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this,
                        RecipeActivity.class));
            }
        });
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this,
                        IngredientsActivity.class));
            }
        });
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this,
                        ShoppingActivity.class));
            }
        });
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this,
                        PersonalInfoActivity.class));
            }
        });

        // Retrieve EditTexts
        final TextView dishNameTextView = findViewById(R.id.dish_name_textView);
        final TextView ingredientTextView = findViewById(R.id.ingredient_textView);
        final TextView quantityTextView = findViewById(R.id.quantity_textView);

        // Example usage of retrieved TextViews
        String dishName = dishNameTextView.getText().toString();
        String ingredient = ingredientTextView.getText().toString();

        String quantity = quantityTextView.getText().toString();


        // Fetch recipes from Firebase
        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
                // Update TextViews with fetched recipe data
                if (!recipeList.isEmpty()) {
                    Recipe recipe = recipeList.get(0); // Assuming you want to
                    // display the first recipe from the list
                    dishNameTextView.setText(recipe.getRecipeName());

                    StringBuilder ingredientBuilder = new StringBuilder();
                    for (Ingredient ingredientd : recipe.getIngredients()) {
                        ingredientBuilder.append(ingredientd.getName()).append("\n");
                    }
                    ingredientTextView.setText(ingredientBuilder.toString());

                    // Assuming 'quantity' refers to the quantity of the first ingredient
                    if (!recipe.getIngredients().isEmpty()) {
                        quantityTextView.setText(Integer.toString(recipe.getIngredients()
                                .get(0).getQuantity()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewRecipeActivity.this, "Failed to load recipes: "
                        + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

