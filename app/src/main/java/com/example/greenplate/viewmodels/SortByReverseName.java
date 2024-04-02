package com.example.greenplate.viewmodels;

import com.example.greenplate.models.SortingStrategy;

import java.util.Arrays;
import java.util.Collections;

public class SortByReverseName implements SortingStrategy {
    public String[] sortRecipes(String[] recipeList) {
        Arrays.sort(recipeList, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        return recipeList;
    }
}
