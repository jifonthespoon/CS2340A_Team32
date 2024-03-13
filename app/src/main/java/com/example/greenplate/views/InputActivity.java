package com.example.greenplate.views;

import com.example.greenplate.R;
import com.example.greenplate.models.Meal;
import com.example.greenplate.viewmodels.FirebaseCallback;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private TextView dateLabel;
    private Calendar calendar;

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

        dateLabel = findViewById(R.id.dayLabel);
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(calendar.getTime());
        updateDateLabel();





        //



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
        final ImageButton toInputDaily = findViewById(R.id.toInputDaily);
        toInputDaily.setOnClickListener(new View.OnClickListener() {
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
                String caloriesString = String.valueOf(caloriesInput.getText()).trim();
                if (caloriesString.isEmpty()) {
                    Toast.makeText(InputActivity.this, "Please enter valid meal name and calories.", Toast.LENGTH_SHORT).show();
                } else {
                    int calories = Integer.parseInt(caloriesInput.getText().toString().trim());
                    if (mealName.isEmpty()) {
                        // Show an error message or a toast to inform the user to input valid values
                        Toast.makeText(InputActivity.this, "Please enter valid meal name and calories.", Toast.LENGTH_SHORT).show();
                    }
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                    String dateAdded = formatDate.format(calendar.getTime());
                    // Create a Meal object
                    Meal meal = new Meal(UUID.randomUUID().toString(), mealName, calories, dateAdded);

                    // Use FirebaseViewModel to save the meal
                    fvm.saveOrUpdateMeal(meal);
                    //clears input boxes
                    mealNameInput.setText("");
                    caloriesInput.setText("");
                }
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


        /*BarChart mBarChart;
        mBarChart = findViewById(R.id.barChart);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateMonthYear = dateFormatter.format(calendar.getTime());
        fvm.queryMealsByDateCalories(dateMonthYear, new FirebaseCallback<Integer>() {
            @Override
            public void onCallback(Integer data) {
                int totalCaloriesConsumed = data;
                String calorieGoalString = calorieGoal.getText().toString();
                int recommendedTotalDailyCalorie = Integer.parseInt(calorieGoalString);
                // Inside onCreate or another appropriate method
                ArrayList<BarEntry> entries = new ArrayList<>();

                entries.add(new BarEntry(1f, new float[]{totalCaloriesConsumed}));
                entries.add(new BarEntry(2f, new float[]{recommendedTotalDailyCalorie}));

                BarDataSet dataSet = new BarDataSet(entries, "Calories");
                dataSet.setColors(ColorTemplate.rgb("#D64933")); // Setting color to red
                BarData barData = new BarData(dataSet);
                mBarChart.setData(barData);


                // Customize X-axis
                XAxis xAxis = mBarChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position of X-axis labels
                xAxis.setGranularity(1.45f); // Interval between each label
                xAxis.setCenterAxisLabels(true); // Center the labels between the bars
                xAxis.setAxisMinimum(0.5f); // Adjust the minimum value to center the first bar
                xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Calories Consumed", "Calorie Goal"})); // Customizing labels
            }
        });*/

        /*dateLabel = findViewById(R.id.dayLabel);
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(calendar.getTime());
        updateDateLabel();*/

        // ADD HERE

        final ImageButton backwards_time = findViewById(R.id.left_arrow_input_page);
        backwards_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                updateDateLabel();
                BarChart mBarChart;
                mBarChart = findViewById(R.id.barChart);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateMonthYear = dateFormatter.format(calendar.getTime());
                fvm.queryMealsByDateCalories(dateMonthYear, new FirebaseCallback<Integer>() {
                    @Override
                    public void onCallback(Integer data) {
                        int totalCaloriesConsumed = data;
                        String calorieGoalString = calorieGoal.getText().toString();
                        int recommendedTotalDailyCalorie = Integer.parseInt(calorieGoalString);
                        // Inside onCreate or another appropriate method
                        ArrayList<BarEntry> entries = new ArrayList<>();

                        entries.add(new BarEntry(1f, new float[]{totalCaloriesConsumed}));
                        entries.add(new BarEntry(2f, new float[]{recommendedTotalDailyCalorie}));

                        BarDataSet dataSet = new BarDataSet(entries, "Calories");
                        dataSet.setColors(ColorTemplate.rgb("#D64933")); // Setting color to red
                        BarData barData = new BarData(dataSet);
                        mBarChart.setData(barData);


                        // Customize X-axis
                        XAxis xAxis = mBarChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position of X-axis labels
                        xAxis.setGranularity(1.45f); // Interval between each label
                        xAxis.setCenterAxisLabels(true); // Center the labels between the bars
                        xAxis.setAxisMinimum(0.5f); // Adjust the minimum value to center the first bar
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Calories Consumed", "Calorie Goal"})); // Customizing labels
                    }
                });
            }
        });

        final ImageButton forwards_time = findViewById(R.id.right_arrow_input_page);
        forwards_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                updateDateLabel();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = dateFormat.format(calendar.getTime());
                BarChart mBarChart;
                mBarChart = findViewById(R.id.barChart);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateMonthYear = dateFormatter.format(calendar.getTime());
                fvm.queryMealsByDateCalories(dateMonthYear, new FirebaseCallback<Integer>() {
                    @Override
                    public void onCallback(Integer data) {
                        int totalCaloriesConsumed = data;
                        String calorieGoalString = calorieGoal.getText().toString();
                        int recommendedTotalDailyCalorie = Integer.parseInt(calorieGoalString);
                        // Inside onCreate or another appropriate method
                        ArrayList<BarEntry> entries = new ArrayList<>();

                        entries.add(new BarEntry(1f, new float[]{totalCaloriesConsumed}));
                        entries.add(new BarEntry(2f, new float[]{recommendedTotalDailyCalorie}));

                        BarDataSet dataSet = new BarDataSet(entries, "Calories");
                        dataSet.setColors(ColorTemplate.rgb("#D64933")); // Setting color to red
                        BarData barData = new BarData(dataSet);
                        mBarChart.setData(barData);


                        // Customize X-axis
                        XAxis xAxis = mBarChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Position of X-axis labels
                        xAxis.setGranularity(1.45f); // Interval between each label
                        xAxis.setCenterAxisLabels(true); // Center the labels between the bars
                        xAxis.setAxisMinimum(0.5f); // Adjust the minimum value to center the first bar
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Calories Consumed", "Calorie Goal"})); // Customizing labels
                    }
                });
            }
        });


    }

    private void updateDateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());
        dateLabel.setText(formattedDate);
    }

    private void updateVisualization() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(calendar.getTime());
    }

}