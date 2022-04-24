/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cookbookwebapplication.model;

import com.mycompany.cookbookwebapplication.exception.WrongRecipeFormatException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * The class is responsible for storing and modifying the cookbook information
 *
 * @author Wiktoria Krajcer
 * @version 5.0
 */
public class CookBookModel {

    /**
     * Default contructor
     *
     */
    public CookBookModel() {
    }


    /**
     * Method that validate new recipe
     *
     * @param recipeToAdd recipe that should be validate and add to cookBook
     * @throws WrongRecipeFormatException when entered recipe name already
     * exists in the cookBook
     */
    public void validateAndAddRecipe(RecipeModel recipeToAdd) throws WrongRecipeFormatException {

        if (recipeToAdd == null) {
            throw new WrongRecipeFormatException("Przekazano pusty obiekt!");
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cookbook");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT s FROM RecipeModel s WHERE s.dish = " + "\'" + recipeToAdd.getDish() + "\'");
        List<RecipeModel> retrievedRecipes = query.getResultList();
        if (!retrievedRecipes.isEmpty()) {
            throw new WrongRecipeFormatException("Przepis o podanej nazwie już istnieje!");
        } else {

            em.getTransaction().begin();
            em.persist(recipeToAdd);
            em.getTransaction().commit();
            em.close();
        }
    }


    /**
     * Method that validate and modify recipe
     * @param recipeName recipe name
     * @param preparationTime preparation time
     * @param cuisine cuisine
     * @param author author
     * @param ingredients ingredients list
     * @throws WrongRecipeFormatException when entered recipe name already
     * exists in the cookBook
     */
    public void validateAndModifyRecipe(String recipeName, String preparationTime, String cuisine, String author, ArrayList<String> ingredients) throws WrongRecipeFormatException {
        if (recipeName == null || recipeName.isBlank()) {
            throw new WrongRecipeFormatException("Nie podano nazwy przepisu!");
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cookbook");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT s FROM RecipeModel s WHERE s.dish = " + "\'" + recipeName + "\'");
        List<RecipeModel> retrievedRecipes = query.getResultList();

        if (!retrievedRecipes.isEmpty()) {
            RecipeModel recipe = (RecipeModel) query.getSingleResult();
            em.getTransaction().begin();
            em.remove(recipe);
            recipe.prepareRecipe(recipeName, preparationTime, cuisine, author, ingredients);
            em.persist(recipe);
            em.getTransaction().commit();
            em.close();
        } else {
            throw new WrongRecipeFormatException("Przepis o podanej nazwie nie istnieje!");
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

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cookbook");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT s FROM RecipeModel s WHERE s.dish = " + "\'" + recipeName + "\'");
        List<RecipeModel> retrievedRecipes = query.getResultList();
        if (retrievedRecipes.isEmpty()) {
            return foundRecipeToDelete;
        } else {
            RecipeModel recipe = (RecipeModel) query.getSingleResult();
            em.getTransaction().begin();
            em.remove(recipe);
            em.getTransaction().commit();
            em.close();
            foundRecipeToDelete = true;
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cookbook");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT s FROM RecipeModel s WHERE s.dish = " + "\'" + recipeName + "\'");
        List<RecipeModel> retrievedRecipes = query.getResultList();
        if (retrievedRecipes.isEmpty()) {
            return null;
        } else {
            RecipeModel recipe = (RecipeModel) query.getSingleResult();
            return recipe;
        }
    }

    /**
     * Method that finds all recipes
     *
     * @return recipes lists or null if recipes list is empty
     */
    public List<RecipeModel> findAllRecipes() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cookbook");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT s FROM RecipeModel s");
        List<RecipeModel> retrievedRecipes = query.getResultList();
        if (retrievedRecipes.isEmpty()) {
            return null;
        } else {
            return retrievedRecipes;
        }
    }

}
