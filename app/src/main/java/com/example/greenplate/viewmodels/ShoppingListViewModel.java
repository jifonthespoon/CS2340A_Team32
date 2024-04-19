package com.example.greenplate.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.models.ShoppingListItem;
import com.example.greenplate.models.User;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListViewModel extends ViewModel {
    private static Firebase firebase = Firebase.getInstance();
    private static User user = FirebaseViewModel.getInstance().getUser();
    private static ArrayList<ShoppingListItem> selectedItems;

    public ShoppingListViewModel() {
        selectedItems = new ArrayList<>();
        this.user = FirebaseViewModel.getInstance().getUser();
    }
    public static void addShoppingListItem(ShoppingListItem item) {
        DatabaseReference shoppingListRef = firebase.getDatabase().getReference()
                .child("users").child(user.getUserId()).child("shoppingList").push();
        item.setId(shoppingListRef.getKey());
        shoppingListRef.setValue(item.toMap());
        user.addShoppingListItem(item);
        selectedItems.add(item);
    }
    public static void updateShoppingListItemQuantity(String name, int quantity) {
        int newQuantity = quantity;
        String itemId = "";
        for (ShoppingListItem item : user.getShoppingList()) {
            if (item.getName().equals(name)) {
                newQuantity += item.getQuantity();
                itemId = item.getId();
            }
        }
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

    public static void purchaseItems() {
        Firebase firebase = Firebase.getInstance();
        DatabaseReference databaseReference = firebase.getDatabase().getReference();

        for (ShoppingListItem shoppingItem : selectedItems) {
            Ingredient existingIngredient = user.findIngredient(shoppingItem.getName());

            if (existingIngredient != null) {
                existingIngredient.setQuantity(existingIngredient.getQuantity() + shoppingItem.getQuantity());
                databaseReference.child("pantry").child(existingIngredient.getId()).setValue(existingIngredient.getMap());
            } else {
                Ingredient newIngredient = new Ingredient(shoppingItem.getName(), 0, shoppingItem.getQuantity(), user.getUserId());
                DatabaseReference newIngredientRef = databaseReference.child("pantry").push();
                String newIngredientId = newIngredientRef.getKey();
                newIngredient.setId(newIngredientId);
                newIngredientRef.setValue(newIngredient.getMap());
                user.addIngredient(newIngredient);
            }
            databaseReference.child("users").child(user.getUserId()).child("shoppingList").child(shoppingItem.getId()).removeValue();
            user.removeShoppingListItem(shoppingItem.getId());
        }

        selectedItems.clear();

    }

    public static void selectItem(ShoppingListItem item) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item);
        } else {
            selectedItems.add(item);
        }
    }

    public static ArrayList<ShoppingListItem> getSelectedItems() {
        return selectedItems;
    }
    /*    public static void purchaseItem(ShoppingListItem item) {
        user.removeShoppingListItem(item.getId());
        ArrayList<Ingredient> ingredients = user.getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equals(item.getName())) {
                ingredient.setQuantity(ingredient.getQuantity() + item.getQuantity());
                IngredientsViewModel.updateIngredient(ingredient);
            }
        }
    }*/

    private static void addOrUpdateIngredientInPantry(ShoppingListItem shoppingItem) {
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
            Ingredient newIngredient = new Ingredient(shoppingItem.getName(),
                    0, shoppingItem.getQuantity(), user.getUserId());
            IngredientsViewModel.addIngredient(newIngredient.getName(),
                    newIngredient.getCalories(), newIngredient.getQuantity(), "");
            // Adjust this call based on the actual method signature
        }
    }
}

