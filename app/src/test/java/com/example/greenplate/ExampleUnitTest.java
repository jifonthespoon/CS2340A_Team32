package com.example.greenplate;

import static com.example.greenplate.views.LoginActivity.checkInput;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.greenplate.models.Meal;
import com.example.greenplate.models.User;
import com.example.greenplate.viewmodels.FirebaseViewModel;

import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testNull() {
        assertEquals(checkInput(null), false);
    }
    @Test
    public void testStrongPassword() {
        assertEquals(checkInput("Test123!"), true);
    }
    @Test
    public void testWeakPassword() {
        assertEquals(checkInput("Hi"), true);
    }
    @Test
    public void testSpacesString() {
        assertEquals(checkInput("   "), false);
    }
    @Test
    public void testEmptyString() {
        assertEquals(checkInput(""), false);
    }

    @Test
    public void testMaleCalorieCount() {
        User user = new User("Test User", 120, "Male", 68, "test-user", "test@gmail.com");
        assertEquals(user.getDailyCalorieIntake(), 1629);
    }

    @Test
    public void testFemaleCalorieCount() {
        User user = new User("Test User", 110, "Female", 64, "test-user", "test@gmail.com");
        assertEquals(user.getDailyCalorieIntake(), 1354);
    }

    @Test
    public void testHeight() {
        User user = new User("Test User", 110, "Female", 64, "test-user", "test@gmail.com");
        assertEquals(user.getHeight(), "5' 4" + "\"");
    }

    @Test
    public void testMealMap() {
        Meal meal = new Meal("testID", "testName", 500, "03-12-2024");
        HashMap<String, Object> result = new HashMap<>();
        result.put("mealId", "testID");
        result.put("name", "testName");
        result.put("calories", 500);
        result.put("dateAdded", "03-12-2024");
        assertEquals(meal.toMap(), result);
    }

    @Test
    public void testFemaleCalorieCount2() {
        //testing if a male and female of the same height and weight output different calories
        User user = new User("Test User", 346, "Female", 72, "test-user", "test@gmail.com");
        assertEquals(user.getDailyCalorieIntake(), 2551);
    }

    @Test
    public void testMealEquality() {
        Meal meal1 = new Meal("123", "Dummy Meal", 500, "2024-03-12");
        Meal meal2 = new Meal("123", "Dummy Meal", 500, "2024-03-12");
        assertEquals(meal1, meal2);
    }

    @Test
    public void testMealConstructor() {
        Meal meal = new Meal("123", "Test Meal", 350, "2024-03-15");
        assertEquals("123", meal.mealId);
        assertEquals("Test Meal", meal.name);
        assertEquals(350, meal.calories);
        assertEquals("2024-03-15", meal.dateAdded);
    }


}