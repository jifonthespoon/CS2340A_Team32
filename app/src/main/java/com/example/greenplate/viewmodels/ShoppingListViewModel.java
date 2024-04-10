package com.example.greenplate.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.models.ShoppingListItem;
import com.example.greenplate.models.User;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ShoppingListViewModel extends ViewModel {
    private Firebase firebase = Firebase.getInstance();
    private User user;

    public ShoppingListViewModel() {
        this.user = FirebaseViewModel.getInstance().getUser();
    }
    public void addShoppingListItem(ShoppingListItem item) {
        DatabaseReference shoppingListRef = firebase.getDatabase().getReference()
                .child("users").child(user.getUserId()).child("shoppingList").push();
        item.setId(shoppingListRef.getKey());
        shoppingListRef.setValue(item.toMap());
        user.addShoppingListItem(item);
    }
    public void updateShoppingListItemQuantity(String itemId, int newQuantity) {
        if (newQuantity <= 0) {
            firebase.getDatabase().getReference().child("users").child(user.getUserId())
                    .child("shoppingList").child(itemId).removeValue();
            user.removeShoppingListItem(itemId);
        } else {
            firebase.getDatabase().getReference().child("users").child(user.getUserId())
                    .child("shoppingList").child(itemId).child("quantity").setValue(newQuantity);

            for (ShoppingListItem item : user.getShoppingList()) {
                if (item.getId().equals(itemId)) {
                    item.setQuantity(newQuantity);
                    break;
                }
            }
        }
    }
    public void purchaseSelectedItems(List<String> selectedItemIds) {
        for (String itemId : selectedItemIds) {
            ShoppingListItem item = user.getShoppingList().stream()
                    .filter(i -> i.getId().equals(itemId))
                    .findFirst().orElse(null);
            if (item != null) {
                // Logic to add item to pantry goes here
                addOrUpdateIngredientInPantry(item);
                // Remove the item from the shopping list
                updateShoppingListItemQuantity(itemId, 0);
            }
        }
    }
    private void addOrUpdateIngredientInPantry(ShoppingListItem shoppingItem) {
        List<Ingredient> pantry = user.getIngredients();
        boolean found = false;

        for (Ingredient ingredient : pantry) {
            if (ingredient.getName().equalsIgnoreCase(shoppingItem.getName())) {
                ingredient.setQuantity(ingredient.getQuantity() + shoppingItem.getQuantity());
                IngredientsViewModel.updateIngredient(ingredient);
                found = true;
                break;
            }
        }

        if (!found) {
            Ingredient newIngredient = new Ingredient(shoppingItem.getName(), 0, shoppingItem.getQuantity(), user.getUserId());
            IngredientsViewModel.addIngredient(newIngredient.getName(), newIngredient.getCalories(), newIngredient.getQuantity(), ""); // Adjust this call based on the actual method signature
        }
}
}

