package com.example.greenplate.views;

import com.example.greenplate.R;
import com.example.greenplate.models.Meal;
import com.example.greenplate.viewmodels.FirebaseViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * InputActivity is designed to serve as the user interface for
 * inputting new data into the application.
 * This could include new recipes, ingredients details,
 * or other forms of information relevant to the application's
 * functionality.
 * The activity also includes navigation buttons to
 * other pages of the app, such as the home page,
 * recipe page, ingredients page, and the shopping page,
 * facilitating easy movement across activities within the app.
 */

public class InputActivity extends AppCompatActivity {
    /**
     * Initializes the activity, sets the content view
     * from the input_page layout, and configures
     * the navigation buttons. Each button is
     * associated with an OnClickListener to navigate
     * to the respective activity, allowing
     * the user to easily access different parts of the application.
     *
     * @param savedInstanceState If the activity is being re-initialized
     *                           after previously being shut down,
     *                           this Bundle contains the data it
     *                           most recently supplied in
     *                           onSaveInstanceState(Bundle).
     *                           Otherwise, it is null. This
     *                           bundle can be used to recreate the activity
     *                           as it was prior to being paused or stopped.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page);
        // Initialize navigation buttons and set their onClickListeners.
        final EditText mealNameInput = findViewById(R.id.input_page_meal_enter);
        final EditText caloriesInput = findViewById(R.id.input_page_calorie_enter);
        final ImageButton toHome = findViewById(R.id.toHomePage);
        FirebaseViewModel fvm = FirebaseViewModel.getInstance();
        TextView userInfo = findViewById(R.id.userInfoLabel);
        TextView calorieGoal = findViewById(R.id.calorieGoalText);

        userInfo.setText(fvm.getPersonalInformation());
        calorieGoal.setText(fvm.getCalorieGoal());
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        HomeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInput = findViewById(R.id.toInputPage);
        toInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        InputActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toRecipe = findViewById(R.id.toRecipePage);
        toRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        RecipeActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toIngredients = findViewById(R.id.toIngredientsPage);
        toIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        IngredientsActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toShopping = findViewById(R.id.toShoppingPage);
        toShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        ShoppingActivity.class);
                startActivity(intent);
            }
        });
        final ImageButton toInputMonthly = findViewById(R.id.toInputMonthly);
        toInputMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this,
                        InputMonthlyActivity.class);
                startActivity(intent);
            }
        });

        final ImageButton submit = findViewById(R.id.input_page_submit_button);
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
                Intent intent = new Intent(InputActivity.this,
                        PersonalInfoActivity.class);
                startActivity(intent);
            }
        });


    }
}