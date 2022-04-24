package com.company.modelTests;


import com.mycompany.exception.WrongRecipeFormatException;
import com.mycompany.model.Cuisine;
import com.mycompany.model.RecipeModel;
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
 * @author Wiktoria Krajcer
 * @version 1.0
 */
public class RecipeModelTest {

    private RecipeModel recipe;
    private RecipeModel recipe2;

    /**
     * is called before each test and is responsible for creating the objects used in tests
     */
    @BeforeEach
    public void setUp() {
        recipe = new RecipeModel();
        recipe2 = new RecipeModel("dish", Duration.ofMinutes(2), Cuisine.GRECKA, "author",
                new ArrayList<>(List.of("ingredient1", "ingredient2")));
    }

    /**
     * test to check if it is possible to create the correct recipe 
     */
    @Test
    public void createCorrectRecipeToCookBookTest() {
        recipe.prepareRecipe("dish", Duration.ofMinutes(2), Cuisine.GRECKA, "author",
                new ArrayList<>(List.of("ingredient1", "ingredient2")));
        assertEquals(recipe.getAuthor(), recipe2.getAuthor());
        assertEquals(recipe.getDish(), recipe2.getDish());
        assertEquals(recipe.getIngredients(), recipe2.getIngredients());
        assertEquals(recipe.getPreparationTime(), recipe2.getPreparationTime());
        assertEquals(recipe.getCuisine(), recipe2.getCuisine());
    }

    /**
     * test which checks the validation of the correct cuisine
     * @param cuisine all posibilites of correct cuisines
     */
    @ParameterizedTest
    @EnumSource(Cuisine.class)
    public void validateCorrectCuisine(Cuisine cuisine) {
            recipe.setCuisine(cuisine);
            assertEquals(recipe.getCuisine(), cuisine,"Nie udało sie poprawnie zwalidować kuchni!");
    }

    /**
     * test which checks the validation of the wrong cuisine
     * @param cuisine possibilities of wrong cuisines
     */
    @ParameterizedTest
    @ValueSource(strings = {"nie istniejaca", " ", "wlloska"})
    public void validateNonExistingCuisine(String cuisine) {
            assertEquals(recipe.validateCuisine(cuisine), Cuisine.NIEZNANA,"Nie udało sie dodać domyślnej wartości enuma!");

    }

    /**
     * test which checks the validation of the correct preparationTime
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
     * @param preparationTime possibilities of wrong preparationTime format
     */
    @ParameterizedTest
    @ValueSource(strings = {"3260","02:60","02:621","321:20","20:05","02,20","00:00"})
    public void validateIncorrectPreparationTime(String preparationTime) {
        try {
            var duration = recipe.validatePreparationTime(preparationTime);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(),"Niepoprawny format czasu przygotowania!","Wyjatek nie zostal rzucony mimo, że powinień!");
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
     * test which checks the validation of the correct authors with all upper and lower case possibilities
     * @param author
     */
    @ParameterizedTest
    @ValueSource(strings = {"wiktoria", "wIKTORIA", "WIKTORIA", "wIkToRiA", "Wiktoria"})
    public void validateUpperAndLowerCaseAuthor(String author) {
        try {
            assertEquals("Wiktoria", recipe.validateAuthor(author),"Nie udało sie poprawnie zwalidować autora!");
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
            assertEquals(e.getMessage(), "Niepoprawny autor!","Wyjatek nie zostal rzucony mimo, że powinień!");
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
            assertEquals(e.getMessage(), "Niepoprawny autor!","Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }


    /**
     * test which checks the validation of the correct dish
     */
    @Test
    public void validateCorrectDish() {
        try {
            assertEquals(recipe.validateDish("pizza"), "pizza","Nie udało sie poprawnie zwalidować nazwy potrawy!");
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
            assertEquals(e.getMessage(), "Niepoprawna nazwa potrawy!","Wyjatek nie zostal rzucony mimo, że powinień!");
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
            assertEquals(e.getMessage(), "Niepoprawna nazwa potrawy!","Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }
    
    /**
     * test which checks the validation of the correct ingredients
     */
    @Test
    public void validateCorrectIngredients() {
        try {
            ArrayList<String> ingredients = recipe.validateIngredients(new ArrayList<>(List.of("maka", "woda")));
            recipe.setIngredients(ingredients);
            assertEquals(recipe.validateIngredients(ingredients), ingredients,"Nie udało sie poprawnie zwalidować listy składników!");
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
            List<String> ingredients = recipe.validateIngredients(new ArrayList<>(List.of(" ")));
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Niepoprawny składnik!","Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }
    

}
