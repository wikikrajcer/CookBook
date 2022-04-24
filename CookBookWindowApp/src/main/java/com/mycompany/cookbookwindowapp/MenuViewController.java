/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cookbookwindowapp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML menu view controller class
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class MenuViewController implements Initializable {

    @FXML
    private Label selectMenuOptionLabel;
    @FXML
    private Button printRecipesButton;
    @FXML
    private Button createRecipeButton;
    @FXML
    private Button deleteRecipeButton;
    @FXML
    private Button findRecipeButton;
    @FXML
    private Button backButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void printAllRecipesFromCookBook(ActionEvent event) throws IOException {
        App.setRoot("printRecipesView");
    }

    @FXML
    private void createRecipeInCookBook(ActionEvent event) throws IOException {
        App.setRoot("createRecipeView");
    }

    @FXML
    private void deleteRecipeFromCookBook(ActionEvent event) throws IOException {
        App.setRoot("deleteRecipeView");
    }

    @FXML
    private void findRecipeInCookBook(ActionEvent event) throws IOException {
        App.setRoot("findRecipeView");
    }

    @FXML
    private void backToMainView(ActionEvent event) throws IOException {
        App.setRoot("startView");
    }

}
