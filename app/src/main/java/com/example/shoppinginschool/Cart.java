package com.example.shoppinginschool;

public class Cart {
    private String recipeImg;
    private String recipeName;
    private String recipePrice;
    private String recipeDes;
    public String getRecipeImg() {
        return recipeImg;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipePrice() {
        return recipePrice;
    }

    public String getRecipeDes() {
        return recipeDes;
    }

    public Cart(String recipeImg, String recipeName, String recipePrice, String recipeDes) {
        this.recipeImg = recipeImg;
        this.recipeName = recipeName;
        this.recipePrice = recipePrice;
        this.recipeDes = recipeDes;
    }
}
