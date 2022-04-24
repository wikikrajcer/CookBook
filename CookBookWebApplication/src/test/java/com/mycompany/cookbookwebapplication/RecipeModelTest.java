package com.mycompany.cookbookwebapplication;

import com.mycompany.cookbookwebapplication.exception.WrongRecipeFormatException;
import com.mycompany.cookbookwebapplication.model.Cuisine;
import com.mycompany.cookbookwebapplication.model.Ingredient;
import com.mycompany.cookbookwebapplication.model.RecipeModel;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Recipe Model Tests
 * @author Wiktoria Krajcer
 * @version 5.0
 */
public class RecipeModelTest {

    /**
     * recipe to test recipe model
     */
    private RecipeModel recipe;
    /**
     * recipe to test recipe model
     */
    private RecipeModel recipe2;
    /**
     * ingredient
     */
    private Ingredient ingredient1;
    /**
     * ingredient
     */
    private Ingredient ingredient2;
    /**
     * ingredients arraylist to test recipe model
     */
    private ArrayList<Ingredient> ingredients;

    /**
     * is called before each test and is responsible for creating the objects
     * used in tests
     */
    @BeforeEach
    public void setUp() {
        ingredients = new ArrayList<>();

        ingredient1 = new Ingredient();
        ingredient2 = new Ingredient();
        ingredient1.setIngredient("ingredient");
        ingredient2.setIngredient("ingredients");

        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        recipe = new RecipeModel();
        recipe2 = new RecipeModel("dish", Duration.ofMinutes(2), Cuisine.GRECKA, "author",
                ingredients);

        for (Ingredient ingredient : recipe2.getIngredients()) {
            ingredient.setRecipeModel(recipe2);
        }
    }

    /**
     * test which checks the validation of the correct cuisine
     *
     * @param cuisine all posibilites of correct cuisines
     */
    @ParameterizedTest
    @EnumSource(Cuisine.class)
    public void validateCorrectCuisine(Cuisine cuisine) {
        recipe.setCuisine(cuisine);
        assertEquals(recipe.getCuisine(), cuisine, "Nie udało sie poprawnie zwalidować kuchni!");
    }

    /**
     * test which checks the validation of the wrong cuisine
     *
     * @param cuisine possibilities of wrong cuisines
     */
    @ParameterizedTest
    @ValueSource(strings = {"nie istniejaca", " ", "wlloska"})
    public void validateNonExistingCuisine(String cuisine) {
        assertEquals(recipe.validateCuisine(cuisine), Cuisine.NIEZNANA, "Nie udało sie dodać domyślnej wartości enuma!");

    }

    /**
     * test which checks the validation of the correct preparationTime
     *
     * @param preparationTime possibilities of correct preparationTime format
     */
    @ParameterizedTest
    @ValueSource(strings = {"19:59", "02:29", "05:12", "04:01"})
    public void validateCorrectPreparationTime(String preparationTime) {
        try {
            var duration = recipe.validatePreparationTime(preparationTime);
            assertEquals(String.format("%02d:%02d", duration.toHoursPart(),
                    duration.toMinutesPart()), preparationTime, "Nie udało sie poprawnie zwalidować czasu trwania!");

        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        }
    }

    /**
     * test which checks the validation of the wrong preparationTime
     *
     * @param preparationTime possibilities of wrong preparationTime format
     */
    @ParameterizedTest
    @ValueSource(strings = {"3260", "02:60", "02:621", "321:20", "20:05", "02,20", "00:00"})
    public void validateIncorrectPreparationTime(String preparationTime) {
        try {
            var duration = recipe.validatePreparationTime(preparationTime);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawny format czasu przygotowania!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * test which checks the validation of the empty preparationTime
     */
    @Test
    public void validateEmptyPreparationTime() {
        try {
            var duration = recipe.validatePreparationTime(" ");
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Nie mozesz utworzyc przepisu bez podania czasu przygotowania!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * test which checks the validation of the correct authors with all upper
     * and lower case possibilities
     *
     * @param author
     */
    @ParameterizedTest
    @ValueSource(strings = {"wiktoria", "wIKTORIA", "WIKTORIA", "wIkToRiA", "Wiktoria"})
    public void validateUpperAndLowerCaseAuthor(String author) {
        try {
            assertEquals("Wiktoria", recipe.validateAuthor(author), "Nie udało sie poprawnie zwalidować autora!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        }
    }

    /**
     * test which checks the validation of the blank author
     */
    @Test
    public void validateBlankAuthor() {
        try {
            var author = recipe.validateAuthor(" ");
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawny autor!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * test which checks the validation of the empty author
     */
    @Test
    public void validateEmptyAuthor() {
        try {
            var author = recipe.validateAuthor(null);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawny autor!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * test which checks the validation of the incorrect author
     *
     * @param authorToValidate author to validate
     */
    @ParameterizedTest
    @ValueSource(strings = {"wiktoria1", "12345", "1wiktoria", ".wiktoria", "wiktoria/"})
    public void validateIncorrectAuthor(String authorToValidate) {
        try {
            var author = recipe.validateAuthor(authorToValidate);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawny autor!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * test which checks the validation of the correct dish
     */
    @Test
    public void validateCorrectDish() {
        try {
            assertEquals(recipe.validateDish("pizza"), "pizza", "Nie udało sie poprawnie zwalidować nazwy potrawy!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        }
    }

    /**
     * test which checks the validation of the blank dish
     */
    @Test
    public void validateBlankDish() {
        try {
            var dish = recipe.validateDish(" ");
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawna nazwa potrawy!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * test which checks the validation of the empty dish
     */
    @Test
    public void validateEmptyDish() {
        try {
            var dish = recipe.validateDish(null);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawna nazwa potrawy!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * test which checks the validation of the incorrect dish
     * @param dishToValidate dish to validate
     */
    @ParameterizedTest
    @ValueSource(strings = {"pizza1", "12345", "1pizza", ".pizza", "pizza/"})
    public void validateIncorrectDish(String dishToValidate) {
        try {
            var dish = recipe.validateDish(dishToValidate);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawna nazwa potrawy!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * test which checks the validation of the correct ingredients
     */
    @Test
    public void validateCorrectIngredients() {
        try {
            ArrayList<String> ingredients = new ArrayList<>(List.of("ingredient", "ingredients"));
            ArrayList<Ingredient> ingredients2 = this.ingredients;
            assertEquals(recipe.validateIngredients(ingredients).get(0).getIngredient(), ingredients2.get(0).getIngredient(), "Nie udało sie poprawnie zwalidować listy składników!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        }
    }

    /**
     * test which checks the validation of the blank ingredients
     */
    @Test
    public void validateBlankIngredients() {
        try {
            ArrayList<Ingredient> ingredients = recipe.validateIngredients(new ArrayList<>(List.of(" ")));
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawne składniki!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * test which checks the validation of the empty dish
     */
    @ParameterizedTest
    @ValueSource(strings = {"maka1", "12345", "1maka", ".maka", "maka/"})
    public void validateIncorrectIngredients(String ingredientsToValidate) {
        try {
            ArrayList<Ingredient> ingredients = recipe.validateIngredients(new ArrayList<>(List.of(ingredientsToValidate)));
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawne składniki!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

}
