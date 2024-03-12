package com.example.greenplate.views;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.greenplate.R;
import com.example.greenplate.models.Meal;
import com.example.greenplate.viewmodels.FirebaseViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class InputMonthlyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page_monthly);
        final EditText mealNameInput = findViewById(R.id.input_page_monthly_meal_enter);
        final EditText caloriesInput = findViewById(R.id.input_page_monthly_calorie_enter);
        final ImageButton toHome = findViewById(R.id.toHomePage);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMonthlyActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMonthlyActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMonthlyActivity.this,
                        RecipeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMonthlyActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMonthlyActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton toInputDaily = findViewById(R.id.toInputDaily);
        toInputDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMonthlyActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton submit = findViewById(R.id.input_page_monthly_submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = mealNameInput.getText().toString().trim();
                int calories = Integer.parseInt(caloriesInput.getText().toString().trim());
                String dateAdded = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                // Create a Meal object
                Meal meal = new Meal(UUID.randomUUID().toString(), mealName, calories, dateAdded);

                // Use FirebaseViewModel to save the meal
                FirebaseViewModel fvm = FirebaseViewModel.getInstance();
                fvm.saveOrUpdateMeal(meal);

                // Optionally, clear the input fields or navigate away after saving
                mealNameInput.setText("");
                caloriesInput.setText("");
            }
        });

        final ImageButton arrow = findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMonthlyActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });


    }
}