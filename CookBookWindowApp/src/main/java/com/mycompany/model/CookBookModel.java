/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import com.mycompany.exception.WrongRecipeFormatException;
import com.mycompany.exception.WrongAmountException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * The class is responsible for storing and modifying the cookbook information
 *
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class CookBookModel {

    /**
     * cookBook recipe list
     */
    private ArrayList<RecipeModel> cookBook = new ArrayList<>();

    /**
     * Method that gets a cookBook
     *
     * @return cookBook
     */
    public ArrayList<RecipeModel> getCookBook() {
        return cookBook;
    }
    

    /**
     * Method that gets the index of the recipe
     *
     * @param recipeIndex recipe index
     * @return recipe index
     */
    public RecipeModel getRecipeByIndex(int recipeIndex) {
        return this.cookBook.get(recipeIndex);
    }

    /**
     * Method that validate new recipe
     *
     * @param recipeToAdd recipe that should be validate and add to cookBook
     * @throws WrongRecipeFormatException when entered recipe name already
     * exists in the cookBook
     */
    public void validateRecipeToAdd(RecipeModel recipeToAdd) throws WrongRecipeFormatException {
        if (recipeToAdd == null) {
            throw new WrongRecipeFormatException("Przekazano pusty obiekt!");
        }
        for (var recipe : cookBook) {
            if (recipeToAdd.getDish().equals(recipe.getDish())) {
                throw new WrongRecipeFormatException("Przepis o podanej nazwie już istnieje!");
            }
        }
        cookBook.add(recipeToAdd);
    }

    /**
     * Method that validate recipe that will be modify
     *
     * @param recipeToAdd recipe that should be validate before modifying
     * @throws WrongRecipeFormatException when entered recipe name already
     * exists in the cookBook
     */
    public void validateRecipeToModify(String recipeName) throws WrongRecipeFormatException {
        if (recipeName == null || recipeName.isBlank()) {
            throw new WrongRecipeFormatException("Nie podano nazwy przepisu!");
        }
        int amountOfRecipeNames = 0;
        for (var recipe : cookBook) {
            if (recipeName.equals(recipe.getDish())) {
                amountOfRecipeNames++;
            }
        }
        if (amountOfRecipeNames != 0) {
            throw new WrongRecipeFormatException("Przepis o podanej nazwie już istnieje!");
        }
    }

    /**
     * Method that removes the recipe with the given name
     *
     * @param recipeName name of recipe to delete
     * @return boolean which stores information on whether a recipe has been
     * successfully deleted
     */
    public boolean deleteRecipe(String recipeName) {

        int recipeIndex = 0;
        boolean foundRecipeToDelete = false;

        for (var recipe : cookBook) {
            if (recipe.getDish().equals(recipeName)) {
                recipeIndex = cookBook.indexOf(recipe);
                foundRecipeToDelete = true;
            }
        }
        if (foundRecipeToDelete == true) {
            cookBook.remove(recipeIndex);
        }
        return foundRecipeToDelete;
    }

    /**
     * Method that find the recipe with the given name
     *
     * @param recipeName name of recipe to find
     * @return when method find a recipe by name returns this recipe, but when
     * method doesn't find a recipe returns null
     */
    public RecipeModel findRecipeByName(String recipeName) {
        int index = 0;
        boolean foundRecipeToShow = false;
        for (var recipe : cookBook) {
            if (recipe.getDish().equals(recipeName)) {
                index = cookBook.indexOf(recipe);
                return recipe;
            }
        }
        return null;
    }

    /**
     * Method that reads recipes from a file and adds them to a cookBook
     *
     * @param fileName name of file that contains recipes details
     * @throws FileNotFoundException when the file cannot be found
     */
    public void readRecipeFromFile(String fileName) throws FileNotFoundException {

        try {
            Scanner file = new Scanner(new File(fileName));

            while (file.hasNextLine()) {

                RecipeModel recipeToAdd = new RecipeModel();
                recipeToAdd.setDish(recipeToAdd.validateDish(file.nextLine()));
                recipeToAdd.setPreparationTime(recipeToAdd.validatePreparationTime(file.nextLine()));
                recipeToAdd.setCuisine(recipeToAdd.validateCuisine(file.nextLine()));
                recipeToAdd.setAuthor(recipeToAdd.validateAuthor(file.nextLine()));
                int amountOfIngredients = Integer.valueOf(file.nextLine());
                if (amountOfIngredients <= 0) {
                    throw new WrongAmountException("Liczba składników musi być dodatnia!");
                }
                ArrayList<String> newIngredientsList = new ArrayList<>();
                for (int i = 0; i < amountOfIngredients; i++) {
                    newIngredientsList.add(file.nextLine());

                }
                recipeToAdd.setIngredients(recipeToAdd.validateIngredients(newIngredientsList));
                this.validateRecipeToAdd(recipeToAdd);
            }

        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wprowadzono zły format czasu przygotowania!", ButtonType.OK);
            alert.showAndWait();
        } catch (java.lang.NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wprowadzono znak zamiast liczby!", ButtonType.OK);
            alert.showAndWait();
        } catch (WrongRecipeFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        } catch (WrongAmountException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pomyślnie odczytano dane z pliku!", ButtonType.OK);
        alert.showAndWait();
    }
}
