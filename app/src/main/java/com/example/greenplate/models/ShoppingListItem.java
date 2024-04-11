package com.example.greenplate.models;

import java.util.HashMap;
import java.util.Map;

public class ShoppingListItem {
    private String id;
    private String name;
    private int quantity;


    public ShoppingListItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public ShoppingListItem(String id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("quantity", quantity);
        return result;
    }
}