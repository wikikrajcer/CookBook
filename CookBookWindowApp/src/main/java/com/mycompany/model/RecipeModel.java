/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import com.mycompany.exception.WrongRecipeFormatException;
import java.time.Duration;
import java.util.ArrayList;

/**
 * The class is responsible for storing and modifying the recipe information
 *
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class RecipeModel {

    /**
     * dish recipe name
     */
    private String dish;
    /**
     * preparationTime recipe preparationTime
     */
    private Duration preparationTime;
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
     * Constructor with arguments
     *
     * @param dish recipe name
     * @param preparationTime recipe preparationTime
     * @param cuisine recipe cuisine
     * @param author recipe author
     * @param ingredients recipe ingredients
     */
    public RecipeModel(String dish, Duration preparationTime, Cuisine cuisine, String author, ArrayList<String> ingredients) {
        this.dish = dish;
        this.preparationTime = preparationTime;
        this.cuisine = cuisine;
        this.author = author;
        this.ingredients = ingredients;
    }

    /**
     * Default constructor
     */
    public RecipeModel() {
    }

    /**
     * Method which adds a new recipe
     *
     * @param dish recipe name to add
     * @param preparationTime recipe preparationTime to add
     * @param cuisine recipe cuisine to add
     * @param author recipe author to add
     * @param ingredients recipe ingredients to add
     */
    public void prepareRecipe(String dish, Duration preparationTime, Cuisine cuisine, String author, ArrayList<String> ingredients) {

        this.cuisine = cuisine;
        this.dish = dish;
        this.preparationTime = preparationTime;
        this.author = author;
        this.ingredients = ingredients;
    }

    /**
     * Method that sets recipe name
     *
     * @param dish recipe name to set
     */
    public void setDish(String dish) {
        this.dish = dish;
    }

    /**
     * Method that is responsible for dish validation
     *
     * @param dish recipe dish name to validate
     * @return validated dish name
     * @throws WrongRecipeFormatException when empty author list is entered
     */
    public String validateDish(String dish) throws WrongRecipeFormatException {
        if (dish == null || dish.isBlank() || !validateLetters(dish)) {
//             
            throw new WrongRecipeFormatException("Niepoprawna nazwa potrawy!");
        }
        return dish;
    }

    /**
     * Method that sets recipe preparationTime
     *
     * @param preparationTime recipe preparationTime to set
     */
    public void setPreparationTime(Duration preparationTime) {
        this.preparationTime = preparationTime;
    }

    /**
     * Method that is responsible for preparationTime validation
     *
     * @param preparationTime recipe preparationTime to validate
     * @return validated preparationTime
     * @throws WrongRecipeFormatException when the wrong preparationTime format
     * is entered
     */
    public Duration validatePreparationTime(String preparationTime) throws WrongRecipeFormatException {
        if (preparationTime == null || preparationTime.isBlank()) {
            throw new WrongRecipeFormatException("Nie mozesz utworzyc przepisu bez podania czasu przygotowania!");
        }
        if (!preparationTime.matches("^([0-1]?\\d:)(([1-5]?[0-9])||([0]?[1-9]))$")) {
            throw new WrongRecipeFormatException("Niepoprawny format czasu przygotowania!");
        }
        String[] splittedDuartion = preparationTime.split(":");
        Duration insertedPreparationTime = Duration.ofHours(Integer.parseInt(splittedDuartion[0]));
        insertedPreparationTime = insertedPreparationTime.plusMinutes(Integer.parseInt(splittedDuartion[1]));

        return insertedPreparationTime;
    }

    /**
     * Method that sets recipe cuisine
     *
     * @param cuisine recipe cuisine to set
     */
    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    /**
     * Method that is responsible for cuisine validation
     *
     * @param cuisine recipe cuisine to validate
     * @return validated cuisine
     */
    public Cuisine validateCuisine(String cuisine) {
        for (Cuisine c : Cuisine.values()) {
            if (c.name().equals(cuisine)) {
                return Cuisine.valueOf(c.name());
            }
        }
        return Cuisine.NIEZNANA;
    }

    /**
     * Method that sets recipe author
     *
     * @param author recipe author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Method that is responsible for author validation
     *
     * @param author recipe author to validate
     * @return validated author
     * @throws WrongRecipeFormatException when empty author list is entered
     */
    public String validateAuthor(String author) throws WrongRecipeFormatException {
        if (author == null || author.isBlank() || !validateLetters(author)) {
//            
            throw new WrongRecipeFormatException("Niepoprawny autor!");
        }
        author = author.substring(0, 1).toUpperCase() + author.substring(1).toLowerCase();

        return author;
    }

    public boolean validateLetters(String stringToValidate) throws WrongRecipeFormatException {
        if (!stringToValidate.matches("[a-zA-Z]+")) {
            return false;
        }
        return true;
    }

    /**
     * Method that sets recipe ingredients
     *
     * @param ingredients recipe ingredients to set
     */
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Method that is responsible for ingredients validation
     *
     * @param ingredients recipe ingredients to validate
     * @return validated ingredients
     * @throws WrongRecipeFormatException when empty ingredients list is entered
     */
    public ArrayList<String> validateIngredients(ArrayList<String> ingredients) throws WrongRecipeFormatException {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).isBlank() || !validateLetters(ingredients.get(i))) {
                throw new WrongRecipeFormatException("Niepoprawny składnik!");
            }
        }
        return ingredients;
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
     * Method that gets recipe preparationTime
     *
     * @return recipe preparationTime
     */
    public Duration getPreparationTime() {
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
