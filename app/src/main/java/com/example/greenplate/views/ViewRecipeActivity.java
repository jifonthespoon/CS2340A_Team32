package com.example.greenplate.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.greenplate.R;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.viewmodels.RecipeViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewRecipeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe_page);
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("name");

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
                startActivity(new Intent(ViewRecipeActivity.this, HomeActivity.class));
            }
        });
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this, InputActivity.class));
            }
        });
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this, RecipeActivity.class));
            }
        });
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this, IngredientsActivity.class));
            }
        });
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this, ShoppingActivity.class));
            }
        });
        toPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewRecipeActivity.this, PersonalInfoActivity.class));
            }
        });

        // Retrieve EditTexts
        final TextView dishNameTextView = findViewById(R.id.dish_name_textView);
        final TextView ingredientTextView = findViewById(R.id.ingredient_textView);

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
            ingredients += ingredientName + " - " + recipeLookingFor.getIngredients().get(ingredientName) + " \n";
        }
        ingredientTextView.setText(ingredients);
    }
}

