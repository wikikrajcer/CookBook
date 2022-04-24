package com.mycompany.cookbookwebapplication.model;


/**
 * The class is responsible for storing cookbook model singleton
 *
 * @version 4.0
 * @author Wiktoria Krajcer
 */
public class StartUp {

    /**
     * cookBook Model
     */
    private static CookBookModel cookBookModel = new CookBookModel();

    /**
     * Method that gets cookBook
     *
     * @return cookBook
     */
    static public CookBookModel getModel() {
        return cookBookModel;
    }
}
