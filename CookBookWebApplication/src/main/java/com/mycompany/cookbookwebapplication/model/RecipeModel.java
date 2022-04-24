/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cookbookwebapplication.model;

import com.mycompany.cookbookwebapplication.exception.WrongRecipeFormatException;
import java.time.Duration;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The class is responsible for storing and modifying the recipe information
 *
 * @author Wiktoria Krajcer
 * @version 5.0
 */
@Entity
@Table(name = "Recipe")
public class RecipeModel {

    /**
     * dish recipe name
     */
    @Id
    @NotNull
    @Column(name = "dish_name")
    private String dish;
    /**
     * preparationTime recipe preparationTime
     */
    @Column(name = "preparation_time")
    private Duration preparationTime;
    /**
     * cuisine recipe cuisine
     */
    @Column(name = "cuisine")
    private Cuisine cuisine;
    /**
     * author recipe author
     */
    @Column(name = "author")
    private String author;
    /**
     * ingredients recipe ingredients
     */
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    /**
     * Constructor with arguments
     *
     * @param dish recipe name
     * @param preparationTime recipe preparationTime
     * @param cuisine recipe cuisine
     * @param author recipe author
     * @param ingredients recipe ingredients
     */
    public RecipeModel(String dish, Duration preparationTime, Cuisine cuisine, String author, ArrayList<Ingredient> ingredients) {
        this.dish = dish;
        this.preparationTime = preparationTime;
        this.cuisine = cuisine;
        this.author = author;
        this.ingredients = ingredients;
    }

    /**
     * Method that sets recipe ingredients
     *
     * @param ingredients recipe ingredients to set
     */
    public void setIngredients(ArrayList<Ingredient> ingredients) {
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
     * @throws WrongRecipeFormatException when recipe format is wrong
     */
    public void prepareRecipe(String dish, String preparationTime, String cuisine, String author, ArrayList<String> ingredients) throws WrongRecipeFormatException {

        this.setDish(this.validateDish(dish));
        this.setAuthor(this.validateAuthor(author));
        this.setCuisine(this.validateCuisine(cuisine));
        this.setPreparationTime(this.validatePreparationTime(preparationTime));
        this.setIngredients(this.validateIngredients(ingredients));
        for (Ingredient ingredient : this.getIngredients()) {
            ingredient.setRecipeModel(this);
        }
    }

    /**
     * Method that gets ingredients list
     * @return ingredients list
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
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
            throw new WrongRecipeFormatException("Niepoprawny autor!");
        }
        author = author.substring(0, 1).toUpperCase() + author.substring(1).toLowerCase();

        return author;
    }

    /**
     * Method that is responsible for ingredients validation
     *
     * @param ingredients recipe ingredients to validate
     * @return validated ingredients
     * @throws WrongRecipeFormatException when empty ingredients list is entered
     */
    public ArrayList<Ingredient> validateIngredients(ArrayList<String> ingredients) throws WrongRecipeFormatException {
        ArrayList<Ingredient> newIngredientsList = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).isBlank() || !validateLetters(ingredients.get(i))) {
                throw new WrongRecipeFormatException("Niepoprawne składniki!");
            } else {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setIngredient(ingredients.get(i));
                newIngredientsList.add(newIngredient);

            }
        }
        return newIngredientsList;
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
     * Method that validates if there are only letters in string
     *
     * @param stringToValidate string to validate
     * @return true if there are only letters, in other cases - false
     */
    public boolean validateLetters(String stringToValidate) {
        if (!stringToValidate.matches("[a-z A-Z]+")) {
            return false;
        }
        return true;
    }
}
