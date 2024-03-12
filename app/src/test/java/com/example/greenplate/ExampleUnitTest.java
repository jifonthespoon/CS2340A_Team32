package com.example.greenplate;

import static com.example.greenplate.views.LoginActivity.checkInput;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.greenplate.models.User;
import com.example.greenplate.viewmodels.FirebaseViewModel;

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
}