package com.example.greenplate;

import static com.example.greenplate.views.LoginActivity.checkInput;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.greenplate.models.Meal;
import com.example.greenplate.models.SortingStrategy;
import com.example.greenplate.models.User;
import com.example.greenplate.models.SortingStrategy;
import com.example.greenplate.viewmodels.FirebaseViewModel;
import com.example.greenplate.models.Recipe;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.viewmodels.SortByName;
import com.example.greenplate.viewmodels.SortByReverseName;
import com.example.greenplate.viewmodels.IngredientsViewModel;
import com.example.greenplate.viewmodels.SortByName;
import com.example.greenplate.viewmodels.SortByReverseName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void testNullEmail() {
        assertEquals(checkInput(null), false);
    }
    
    @Test
    public void testUserCreation() {
        User user = new User("Test User", 150, "Male", 70, "test-user", "test@gmail.com");
        assertNotNull(user);
    }

    @Test
    public void testValidIngredientName() {
        assertEquals(checkInput("Tomato"), true);
    }

    @Test
    public void testInvalidIngredientNameWithWhitespace() {
        assertEquals(checkInput("   "), false);
    }

    @Test
    public void testValidQuantity() {
        assertEquals(checkQuantity("2"), true);
    }

    @Test
    public void testInvalidQuantityWithNonNumeric() {
        assertEquals(checkQuantity("abc"), false);
    }

    @Test
    public void testValidDate() {
        assertEquals(checkDateFormat("2024-03-12"), true);
    }

    @Test
    public void testInvalidDateWithWrongFormat() {
        assertEquals(checkDateFormat("12-03-2024"), false);
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
    public void testMaleCalorieCount2() {
        //testing if a male and female of the same height and weight output different calories
        User user = new User("Test User", 346, "Male", 72, "test-user", "test@gmail.com");
        assertEquals(user.getDailyCalorieIntake(), 2717);
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
        assertEquals("123", meal.getMealId());
        assertEquals("Test Meal", meal.getName());
        assertEquals(350, meal.getCalories());
        //assertEquals("2024-03-15", meal.dateAdded);
    }

    public void updateUserPersonalInformation_updatesInfoCorrectly() {
        User user = new User("Jane Doe", "user123", "jane.doe@example.com");
        user.addPersonalInformation(65, 130, "Female");

        assertEquals(65, user.getHeightInInches());
        assertEquals(130, user.getWeight());
        assertEquals("Female", user.getGender());
    }
    public void getUserMap_outputsCorrectMap() {
        User user = new User("Jane Doe", 130, "Female", 65, "user123", "jane.doe@example.com");
        Map<String, Object> userMap = user.getUserMap();

        assertEquals("Jane Doe", userMap.get("name"));
        assertEquals(130, userMap.get("weight"));
        assertEquals("Female", userMap.get("gender"));
        assertEquals(65, userMap.get("heightInInches"));
        assertEquals("user123", userMap.get("userId"));
        assertEquals("jane.doe@example.com", userMap.get("email"));
        // Assuming mealIds is an empty list initially
        assertTrue(((ArrayList)userMap.get("mealIds")).isEmpty());
    }


    public static boolean checkQuantity(String quantity) {
        try {
            int qty = Integer.parseInt(quantity);
            // Quantity must be a positive integer
            return qty > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkDateFormat(String date) {
        // Check if the date matches the expected format (YYYY-MM-DD)
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }





    // SPRINT 3 UNIT TESTS

    // AUSTIN
    @Test
    public void decreaseIngredientQuantityWithReturn() {
        Ingredient potatoes = new Ingredient("Potatoes", 120, 2, "94h98vb3-bvgevfi2-ef34g");
        assertEquals(potatoes.decreaseQuantity(), 1);
    }

    @Test
    public void increaseIngredientQuantity() {
        Ingredient potatoes = new Ingredient("Potatoes", 120, 2, "94h98vb3-bvgevfi2-ef34g");
        potatoes.increaseQuantity();
        assertEquals(potatoes.getQuantity(), 3);
    }

    // ANYA

    // SUBHA
    @Test public void testRecipeConstructor() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Bread", 150, 2, "2023-12-30", "subha"));
        ingredients.add(new Ingredient("cheese", 200, 1, "2023-12-30", "subha"));
        Recipe recipe = new Recipe("Grilled cheese", ingredients, "subha");
        assertEquals("Grilled cheese", recipe.getRecipeName());
        assertEquals(ingredients, recipe.getIngredients());
        assertEquals("subha", recipe.getUserId());
    }

    @Test public void testIngredientConstructor() {
        Ingredient ingredient = new Ingredient("Milk", 50, 4, "2023-06-30", "subha", "milk");
        assertEquals("Milk", ingredient.getName());
        assertEquals(50, ingredient.getCalories());
        assertEquals(4, ingredient.getQuantity());
        assertEquals("subha", ingredient.getUserId());
        assertEquals("milk", ingredient.getId());
    }

    // NATHAN
    @Test
    public void testSorting() {
        String[] testList = {"C", "B", "A", "D"};
        SortingStrategy testSort = new SortByName();
        String[] sortedList = testSort.sortRecipes(testList);
        assertEquals("A", sortedList[0]);
        assertEquals("B", sortedList[1]);
        assertEquals("C", sortedList[2]);
        assertEquals("D", sortedList[3]);
    }

    @Test
    public void testReverseSorting() {
        String[] testList = {"C", "B", "A", "D"};
        SortingStrategy testSort = new SortByReverseName();
        String[] sortedList = testSort.sortRecipes(testList);
        assertEquals("D", sortedList[0]);
        assertEquals("C", sortedList[1]);
        assertEquals("B", sortedList[2]);
        assertEquals("A", sortedList[3]);
    }

    // DANIEL
    @Test
    public void testRecipeToMapIncludesAllIngredients() {
        Ingredient ingredient1 = new Ingredient("Salt", 0, 1, "2024-12-31", "user123", "ingredient1");
        Ingredient ingredient2 = new Ingredient("Pepper", 0, 2, "2024-12-31", "user123", "ingredient2");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        Recipe recipe = new Recipe("Seasoning Mix", ingredients, "user123");

        Map<String, Object> recipeMap = recipe.toMap();

        assertEquals("Seasoning Mix", recipeMap.get("recipeName"));
        Map<String, String> ingredientsMap = (Map<String, String>) recipeMap.get("ingredients");
        assertNotNull(ingredientsMap);
        assertEquals("1", ingredientsMap.get("Salt"));
        assertEquals("2", ingredientsMap.get("Pepper"));
    }

    @Test
    public void testAddingIngredientUpdatesRecipe() {
        Ingredient ingredient1 = new Ingredient("Sugar", 15, 1, "2024-12-31", "user123", "ingredient1");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        Recipe recipe = new Recipe("Sweet Mix", ingredients, "user123");

        Ingredient ingredient2 = new Ingredient("Cinnamon", 5, 1, "2024-12-31", "user123", "ingredient2");
        recipe.getIngredients().add(ingredient2);
        assertEquals(2, recipe.getIngredients().size());
        assertTrue(recipe.getIngredients().contains(ingredient1));
        assertTrue(recipe.getIngredients().contains(ingredient2));
    }
    // KUSHAL
    @Test public void testIngredient() {
        Ingredient ingredient = new Ingredient("Tomato", 25, 3, "2024-12-31", "user123", "ingredient123");
        assertEquals("Tomato", ingredient.getName());
        assertEquals(25, ingredient.getCalories());
        assertEquals(3, ingredient.getQuantity());
        assertEquals("user123", ingredient.getUserId());
        assertEquals("ingredient123", ingredient.getId());
    }
    @Test public void testRecipe() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Ingredient1", 100, 2, "2024-12-31", "user123"));
        ingredients.add(new Ingredient("Ingredient2", 200, 3, "2024-12-31", "user123"));
        Recipe recipe = new Recipe("TestRecipe", ingredients, "user123");
        assertEquals("TestRecipe", recipe.getRecipeName());
        assertEquals(ingredients, recipe.getIngredients());
        assertEquals("user123", recipe.getUserId()); }
}
