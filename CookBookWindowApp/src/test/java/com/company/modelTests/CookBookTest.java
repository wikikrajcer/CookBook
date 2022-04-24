package com.company.modelTests;

import com.mycompany.exception.WrongRecipeFormatException;
import com.mycompany.model.CookBookModel;
import com.mycompany.model.Cuisine;
import com.mycompany.model.RecipeModel;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * @author Wiktoria Krajcer
 * @version 1.0
 */
public class CookBookTest {

    private CookBookModel cookBook;
    private CookBookModel cookBookFromFile;
    private RecipeModel recipe;

    /**
     * is called before each test and is responsible for creating the objects
     * used in tests
     *
     * @throws WrongRecipeFormatException when the wrong recipe format is
     * entered
     */
    @BeforeEach
    public void setUp() throws WrongRecipeFormatException {
        recipe = new RecipeModel("pizza", Duration.ofMinutes(2), Cuisine.GRECKA, "Wiki",
                new ArrayList<>(List.of("maka", "woda")));
        cookBook = new CookBookModel();
        cookBookFromFile = new CookBookModel();
        cookBook.validateRecipeToAdd(recipe);
    }

    /**
     * A test to check if it is possible to add a recipe with an existing name
     * to a cookbook
     */
    @Test
    public void addRecipeWithExistingNameToCookBookTest() {
        try {
            recipe = new RecipeModel("pizza", Duration.ofMinutes(2), Cuisine.POLSKA, "autor",
                    new ArrayList<>(List.of("skladnik1", "skladnik2")));
            cookBook.validateRecipeToAdd(recipe);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");

        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Przepis o podanej nazwie już istnieje!","Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * A test to check if it is possible to add a blank recipe to a cookbook
     */
    @Test
    public void addBlankRecipeToCookBookTest() {
        try {
            cookBook.validateRecipeToAdd(null);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Przekazano pusty obiekt!","Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * A test to check if it is possible to add a correct recipe to a cookbook
     */
    @Test
    public void addCorrectRecipeToCookBookTest() {
        try {
            recipe = new RecipeModel("dish", Duration.ofMinutes(2), Cuisine.POLSKA, "author",
                    new ArrayList<>(List.of("skladnik1", "skladnik2")));
            cookBook.validateRecipeToAdd(recipe);
            assertEquals(cookBook.getCookBook().size(), 2,"Liczba dodanych przepisów się nie zgadza!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        }
    }
    
     /**
     * A test to check if it is possible to modify a recipe with an existing name
     * to a cookbook
     */
    @Test
    public void modifyRecipeWithExistingNameInCookBookTest() {
        try {
            recipe = new RecipeModel("pizza", Duration.ofMinutes(2), Cuisine.POLSKA, "autor",
                    new ArrayList<>(List.of("skladnik1", "skladnik2")));
            cookBook.validateRecipeToModify(recipe.getDish());
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");

        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Przepis o podanej nazwie już istnieje!","Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * A test to check if it is possible to modify recipe with blank name
     */
    @Test
    public void modifyRecipeWithBlankNameInCookBookTest() {
        try {
            cookBook.validateRecipeToModify(null);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Nie podano nazwy przepisu!","Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }
    
    /**
     * A test to check if it is possible to modify recipe with empty name
     */
    @Test
    public void modifyRecipeWithEmptyNameInCookBookTest() {
        try {
            cookBook.validateRecipeToModify(" ");
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Nie podano nazwy przepisu!","Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }
    
        /**
     * A test to check if it is possible to modify recipe with correct name
     */
    @Test
    public void modifyRecipeWithCorrectCookBookTest() {
        try {
            recipe = new RecipeModel("dish", Duration.ofMinutes(2), Cuisine.POLSKA, "author",
                    new ArrayList<>(List.of("skladnik1", "skladnik2")));
            cookBook.validateRecipeToModify(recipe.getDish());
            assertEquals(cookBook.getCookBook().size(), 1,"Liczba dodanych przepisów się nie zgadza!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        }
    }
    
    /**
     * A test to check if it is possible to remove a recipe with a non-existing
     * name from a cookbook
     */
    @Test
    public void deleteRecipeWhichDoesntExistsFromCookBookTest() {
        boolean isDeleted = cookBook.deleteRecipe("noname");
        assertFalse(isDeleted, "Usunięcie przepisu nie powinno się udać!");
        assertNull(cookBook.findRecipeByName("noname"), "Przepis powinien być nullem!");
    }

    /**
     * A test to check if it is possible to remove an existing recipe from a
     * cookbook
     */
    @Test
    public void deleteExistingRecipeFromCookBookTest() {
        boolean isDeleted = cookBook.deleteRecipe("pizza");
        assertTrue(isDeleted, "Nie udało się usunąc przepisu!");
        assertNull(cookBook.findRecipeByName("pizza"),"Przepis powinien być nullem!");
    }

    /**
     * A test to check if it is possible to find a recipe with a existing name
     * in a cookbook
     */
    @Test
    public void findExistingRecipeInCookBookTest() {
        var recipeRetrieved = cookBook.findRecipeByName("pizza");
        assertNotNull(recipeRetrieved, "Przepis nie został znaleziony!");
        assertEquals("pizza", recipeRetrieved.getDish());
    }

    /**
     * A test to check if it is possible to find a recipe with a non-existing
     * name in a cookbook
     */
    @Test
    public void findRecipeWhichDoesntExistsInCookBookTest() {
        var recipeRetrieved = cookBook.findRecipeByName("noname");
        assertNull(recipeRetrieved, "Przepis został znaleziony, mimo że nie powinien!");
    }
}
