package com.mycompany.cookbookwebapplication;

import com.mycompany.cookbookwebapplication.exception.WrongRecipeFormatException;
import com.mycompany.cookbookwebapplication.model.CookBookModel;
import com.mycompany.cookbookwebapplication.model.Cuisine;
import com.mycompany.cookbookwebapplication.model.Ingredient;
import com.mycompany.cookbookwebapplication.model.RecipeModel;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * CookBook Model Tests
 * @author Wiktoria Krajcer
 * @version 5.0
 */
public class CookBookTest {

    /**
     * cookBook object to test CookBook model
     */
    private CookBookModel cookBook = new CookBookModel();
    /**
     * recipe object to test CookBook model
     */
    private RecipeModel recipe;
    /**
     * recipe object to test CookBook model
     */
    private RecipeModel recipe2;
    /**
     * recipe object to test CookBook model
     */
    private RecipeModel recipe3;

    /**
     * is called before each test and is responsible for creating the objects
     * used in tests
     *
     * @throws WrongRecipeFormatException when the wrong recipe format is
     * entered
     */
    @BeforeEach
    public void setUp() throws WrongRecipeFormatException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cookbook");
        EntityManager em = emf.createEntityManager();

        recipe = new RecipeModel("pizza testowa", Duration.ofMinutes(2), Cuisine.GRECKA, "Wiki",
                new ArrayList<Ingredient>(List.of(new Ingredient("maka"), new Ingredient("woda"))));

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipeModel(recipe);
        }

        recipe2 = new RecipeModel("burger testowy", Duration.ofMinutes(5), Cuisine.GRECKA, "Monia",
                new ArrayList<Ingredient>(List.of(new Ingredient("drozdze"), new Ingredient("woda"))));

        for (Ingredient ingredient : recipe2.getIngredients()) {
            ingredient.setRecipeModel(recipe2);
        }

        recipe3 = new RecipeModel("pizza testowa", Duration.ofMinutes(2), Cuisine.GRECKA, "Wiki",
                new ArrayList<Ingredient>(List.of(new Ingredient("skladnik"), new Ingredient("skladnikk"))));

        for (Ingredient ingredient : recipe3.getIngredients()) {
            ingredient.setRecipeModel(recipe3);
        }
    }

    /**
     * A test to check if it is possible to add a recipe with an existing name
     * to a cookbook
     */
    @Test
    public void addRecipeWithExistingNameToCookBookTest() {
        try {
            cookBook.validateAndAddRecipe(recipe);
            cookBook.validateAndAddRecipe(recipe3);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");

        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Przepis o podanej nazwie już istnieje!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        } finally {
            cookBook.deleteRecipe(recipe.getDish());
        }
    }

    /**
     * A test to check if it is possible to add a blank recipe to a cookbook
     */
    @Test
    public void addBlankRecipeToCookBookTest() {
        try {
            cookBook.validateAndAddRecipe(null);
            fail("Wyjatek nie zostal rzucony mimo, że powinień!");
        } catch (WrongRecipeFormatException e) {
            assertEquals(e.getMessage(), "Przekazano pusty obiekt!", "Wyjatek nie zostal rzucony mimo, że powinień!");
        }
    }

    /**
     * A test to check if it is possible to add a correct recipe to a cookbook
     */
    @Test
    public void addCorrectRecipeToCookBookTest() {
        try {
            cookBook.validateAndAddRecipe(recipe);
            int sizeBeforeAdding = cookBook.findAllRecipes().size();
            cookBook.validateAndAddRecipe(recipe2);
            int sizeAfterAdding = cookBook.findAllRecipes().size();
            assertEquals(sizeBeforeAdding + 1, sizeAfterAdding, "Liczba dodanych przepisów się nie zgadza!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        } finally {
            cookBook.deleteRecipe(recipe.getDish());
            cookBook.deleteRecipe(recipe2.getDish());
        }
    }

    /**
     * A test to check if it is possible to modify recipe with correct name
     */
    @Test
    public void modifyRecipeWithCorrectCookBookTest() {
        try {
            cookBook.validateAndAddRecipe(recipe);
            int sizeBeforeAdding = cookBook.findAllRecipes().size();
            cookBook.validateAndModifyRecipe(recipe.getDish(), "00:40", "POLSKA", "author", new ArrayList<String>(List.of("skladnik", "skladnikk")));
            int sizeAfterAdding = cookBook.findAllRecipes().size();
            assertEquals(sizeBeforeAdding, sizeAfterAdding, "Liczba dodanych przepisów się nie zgadza!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        } finally {
            cookBook.deleteRecipe(recipe.getDish());
        }
    }

    /**
     * A test to check if it is possible to remove a recipe with a non-existing
     * name from a cookbook
     */
    @Test
    public void deleteRecipeWhichDoesntExistsFromCookBookTest() {
        try {
            cookBook.validateAndAddRecipe(recipe);
            boolean isDeleted = cookBook.deleteRecipe("noname");
            assertFalse(isDeleted, "Usunięcie przepisu nie powinno się udać!");
            assertNull(cookBook.findRecipeByName("noname"), "Przepis powinien być nullem!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        } finally {
            cookBook.deleteRecipe(recipe.getDish());
        }
    }

    /**
     * A test to check if it is possible to remove an existing recipe from cookbook
     */
        @Test
    public void deleteExistingRecipeFromCookBookTest() {
        try {
            cookBook.validateAndAddRecipe(recipe);
            boolean isDeleted = cookBook.deleteRecipe(recipe.getDish());
            assertTrue(isDeleted, "Nie udało się usunąc przepisu!");
            assertNull(cookBook.findRecipeByName("pizza testowa"), "Przepis powinien być nullem!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        } finally {
            cookBook.deleteRecipe(recipe.getDish());
        }

    }

    /**
     * A test to check if it is possible to find a recipe with a existing name
     * in a cookbook
     */
    @Test
    public void findExistingRecipeInCookBookTest() {
        try {
            cookBook.validateAndAddRecipe(recipe);
            var recipeRetrieved = cookBook.findRecipeByName(recipe.getDish());
            assertNotNull(recipeRetrieved, "Przepis nie został znaleziony!");
            assertEquals(recipe.getDish(), recipeRetrieved.getDish());
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        } finally {
            cookBook.deleteRecipe(recipe.getDish());
        }
    }

    /**
     * A test to check if it is possible to find a recipe with a non-existing
     * name in a cookbook
     */
    @Test
    public void findRecipeWhichDoesntExistsInCookBookTest() {
        try {
            cookBook.validateAndAddRecipe(recipe);
            var recipeRetrieved = cookBook.findRecipeByName("noname");
            assertNull(recipeRetrieved, "Przepis został znaleziony, mimo że nie powinien!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        } finally {
            cookBook.deleteRecipe(recipe.getDish());
        }
    }

    /**
     * A test to check if it is possible to find recipes in not empty cookbook
     *
     */
    @Test
    public void findAllRecipesInCookBookTest() {
        try {
            cookBook.validateAndAddRecipe(recipe);
            cookBook.validateAndAddRecipe(recipe2);
            var recipesRetrieved = cookBook.findAllRecipes();
            assertNotNull(recipesRetrieved, "Przepisy nie zostały znalezione!");
        } catch (WrongRecipeFormatException e) {
            fail("Wystapil wyjatek: " + e.getMessage());
        } finally {
            cookBook.deleteRecipe(recipe.getDish());
            cookBook.deleteRecipe(recipe2.getDish());
        }
    }

}