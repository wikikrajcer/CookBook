/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cookbookwebapplication.exception;

/**
 * Exception class that is called when a recipe with the name to be added
 * already exists
 *
 * @author Wiktoria Krajcer 
 * @version 2.0
 */
public class WrongRecipeFormatException extends Exception {

    /**
     * Constructor with argument
     *
     * @param message message to be printed
     */
    public WrongRecipeFormatException(String message) {
        super(message);
    }
}
