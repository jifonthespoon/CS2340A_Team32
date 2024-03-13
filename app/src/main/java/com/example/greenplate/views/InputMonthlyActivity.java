package com.example.greenplate.views;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anychart.core.cartesian.series.Line;
import com.example.greenplate.R;
import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.Meal;
import com.example.greenplate.viewmodels.FirebaseViewModel;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
                        Toast.makeText(InputMonthlyActivity.this, "Please enter valid meal name and calories.", Toast.LENGTH_LONG).show();
                        return; // Stop further execution
                    }
                    String dateAdded = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    Meal meal = new Meal(UUID.randomUUID().toString(), mealName, calories, dateAdded);
                    fvm.saveOrUpdateMeal(meal);
                    mealNameInput.setText("");
                    caloriesInput.setText("");
                } else {
                    Toast.makeText(InputMonthlyActivity.this, "Please enter valid meal name and calories.", Toast.LENGTH_LONG).show();
                    return;
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
        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.setDrawValues(false);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Calories per Day");
            set1.setDrawIcons(false);
            set1.setDrawValues(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
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

        final ImageButton backwards_time = findViewById(R.id.left_arrow_input_monthly_page);
        backwards_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1); // Subtract one month
                updateDate();
                updateVisualization();
            }
        });

        final ImageButton forwards_time = findViewById(R.id.right_arrow_input_monthly_page);
        forwards_time.setOnClickListener(new View.OnClickListener() {
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
        // ADD HERE
    }
}
