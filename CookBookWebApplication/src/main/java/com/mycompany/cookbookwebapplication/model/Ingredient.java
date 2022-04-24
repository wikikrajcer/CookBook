/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cookbookwebapplication.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The class is responsible for storing ingredient information
 * @author Wiktoria Krajcer
 * @version 5.0
 */
@Entity
@Table(name = "Ingredient")
public class Ingredient implements Serializable {
    
    /**
     * id value
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * ingredient
     */
    @Column(name = "ingredient")
    private String ingredient;

    /**
     * recipe id
     */
    @ManyToOne()
    @JoinColumn(name = "recipe_id")
    private RecipeModel recipe;
    
    /**
     * Default constructor
     */
    public Ingredient(){}
    
    /**
     * Constructor with one param
     * @param ingredient ingredient
     */
    public Ingredient(String ingredient)
    {
        this.ingredient=ingredient;
    }

    /**
     * Method that sets recipeModel
     * @param recipe RecipeModel to set
     */
    public void setRecipeModel(RecipeModel recipe)
    {
        this.recipe = recipe;
    }

    /**
     * Method that sets ingredients
     * @param ingredient ingredient to set
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Method that gets recipe ingredients
     * @return ingredient
     */
    public String getIngredient() {
        return this.ingredient;
    }
}
