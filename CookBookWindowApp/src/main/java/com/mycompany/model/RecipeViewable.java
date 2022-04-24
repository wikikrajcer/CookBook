/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;


import java.util.ArrayList;

/**
 * The class is responsible for storing viewable recipe
 *
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class RecipeViewable {

    /**
     * dish recipe name
     */
    private String dish;
    /**
     * preparationTime recipe preparationTime
     */
    private String preparationTime;
    /**
     * cuisine recipe cuisine
     */
    private Cuisine cuisine;
    /**
     * author recipe author
     */
    private String author;
    /**
     * ingredients recipe ingredients
     */
    private ArrayList<String> ingredients = new ArrayList<>();

    /**
     * recipe constructor
     * @param recipe 
     */
    public RecipeViewable(RecipeModel recipe) {
        this.dish = recipe.getDish();
        this.author = recipe.getAuthor();
        this.cuisine = recipe.getCuisine();
        this.ingredients = recipe.getIngredients();
        this.preparationTime = String.format("%02dh %02dmin", recipe.getPreparationTime().toHoursPart(), recipe.getPreparationTime().toMinutesPart());
    }

     /**
     * Method that gets recipe name
     *
     * @return recipe name
     */
    public String getDish() {
        return dish;
    }
    /**
     * Method that gets recipe preparation time
     *
     * @return recipe preparation time
     */
    public String getPreparationTime() {
        return preparationTime;
    }

    /**
     * Method that gets recipe cuisine
     *
     * @return recipe cuisine
     */
    public Cuisine getCuisine() {
        return cuisine;
    }

     /**
     * Method that gets recipe author
     *
     * @return recipe author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Method that gets recipe ingredients
     *
     * @return recipe ingredients
     */
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

}
