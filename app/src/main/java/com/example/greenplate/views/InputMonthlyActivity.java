package com.example.greenplate.views;

import android.graphics.Color;
import android.graphics.DashPathEffect;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.greenplate.R;

import com.example.greenplate.models.Meal;
import com.example.greenplate.viewmodels.FirebaseViewModel;

import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import java.util.UUID;

public class InputMonthlyActivity extends AppCompatActivity {

    private TextView monthLabel;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_page_monthly);
        final EditText mealNameInput = findViewById(R.id.input_page_monthly_meal_enter);
        final EditText caloriesInput = findViewById(R.id.input_page_monthly_calorie_enter);
        final ImageButton toHome = findViewById(R.id.toHomePage);
        FirebaseViewModel fvm = FirebaseViewModel.getInstance();
        TextView userInfo = findViewById(R.id.userInfoLabel);
        TextView calorieGoal = findViewById(R.id.calorieGoalText);
        userInfo.setText(fvm.getPersonalInformation());
        calorieGoal.setText(fvm.getCalorieGoal());
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
                String caloriesString = String.valueOf(caloriesInput.getText()).trim();
                if (!caloriesString.isEmpty()) {
                    int calories = Integer.parseInt(caloriesInput.getText().toString().trim());
                    if (mealName.isEmpty() || caloriesString.isEmpty()) {
                        Toast.makeText(InputMonthlyActivity.this,
                                "Please enter valid meal name and calories.",
                                Toast.LENGTH_LONG).show();
                        return; // Stop further execution
                    }
                    String dateAdded = new SimpleDateFormat("yyyy-MM-dd",
                            Locale.getDefault()).format(new Date());
                    Meal meal = new Meal(UUID.randomUUID().toString(), mealName, calories,
                            dateAdded);
                    fvm.saveOrUpdateMeal(meal);
                    mealNameInput.setText("");
                    caloriesInput.setText("");
                } else {
                    Toast.makeText(InputMonthlyActivity.this,
                            "Please enter valid meal name and calories.",
                            Toast.LENGTH_LONG).show();
                }
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
        LineChart mChart = findViewById(R.id.mpandroidchart1);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        mChart.getDescription().setText("");
        ArrayList<Entry> values = new ArrayList<>();
        addValues(values);
        LineDataSet dataSet;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            dataSet = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            dataSet.setDrawValues(false);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            dataSet = new LineDataSet(values, "Calories per Day");
            configureSet(dataSet);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);
            LineData data = new LineData(dataSets);
            mChart.setData(data);
            YAxis leftAxis = mChart.getAxisLeft();
            String caloriesGoalString = calorieGoal.getText().toString();
        }
        monthLabel = findViewById(R.id.monthLabel);
        calendar = Calendar.getInstance();
        updateDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(calendar.getTime());
        final ImageButton backwardsTime = findViewById(R.id.left_arrow_input_monthly_page);
        backwardsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1); // Subtract one month
                updateDate();
                updateVisualization();
            }
        });
        final ImageButton forwardsTime = findViewById(R.id.right_arrow_input_monthly_page);
        forwardsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1); // Add one month
                updateDate();
                updateVisualization();
            }
        });
    }
    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());
        monthLabel.setText(formattedDate);
    }
    private void updateVisualization() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(calendar.getTime());
    }

    private void addValues(ArrayList<Entry> values) {
        values.add(new Entry(1, 2000));
        values.add(new Entry(2, 1930));
        values.add(new Entry(3, 1810));
        values.add(new Entry(4, 2110));
        values.add(new Entry(5, 2050));
        values.add(new Entry(6, 1590));
        values.add(new Entry(7, 1990));
        values.add(new Entry(8, 2000));
        values.add(new Entry(9, 1950));
        values.add(new Entry(10, 1890));
        values.add(new Entry(11, 2010));
        values.add(new Entry(12, 2020));
        values.add(new Entry(13, 1960));
        values.add(new Entry(14, 1990));
    }

    private void configureSet(LineDataSet dataSet) {
        dataSet.setDrawIcons(false);
        dataSet.setDrawValues(false);
        dataSet.enableDashedLine(10f, 5f, 0f);
        dataSet.enableDashedHighlightLine(10f, 5f, 0f);
        dataSet.setColor(Color.DKGRAY);
        dataSet.setCircleColor(Color.DKGRAY);
        dataSet.setLineWidth(1f);
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(9f);
        dataSet.setDrawFilled(true);
        dataSet.setFormLineWidth(1f);
        dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        dataSet.setFormSize(15.f);
    }
}
