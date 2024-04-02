package com.example.greenplate.viewmodels;

import com.example.greenplate.models.SortingStrategy;

import java.util.Arrays;

public class SortByName implements SortingStrategy {
    public String[] sortRecipes(String[] recipeList) {
        Arrays.sort(recipeList);
        return recipeList;
    }
}
