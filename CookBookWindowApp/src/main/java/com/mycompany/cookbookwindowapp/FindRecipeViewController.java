/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cookbookwindowapp;

import com.mycompany.model.RecipeModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML find recipe view controller class
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class FindRecipeViewController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Label findRecipeLabel;
    @FXML
    private TextField recipeToFindTextField;
    @FXML
    private Label insertRecipeNameLabel;
    @FXML
    private Button findRecipeButton;
    @FXML
    private Label preparationTimeLabel;
    @FXML
    private Label ingredientsLabel;
    @FXML
    private Label cuisineLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label dishLabel;
    @FXML
    private Label foundDishLabel;
    @FXML
    private Label FoundAuthorLabel;
    @FXML
    private Label foundCuisineLabel;
    @FXML
    private Label foundIngerdientsLabel;
    @FXML
    private Label foundPreparationTimeLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void backToMenuView(ActionEvent event) throws IOException {
        App.setRoot("menuView");
    }

    @FXML
    private void findRecipe(ActionEvent event) throws IOException {
        RecipeModel recipe = this.findRecipeInCookBook(recipeToFindTextField.getText());
        if (recipe == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Przepis o podanej nazwie nie istnieje!", ButtonType.OK);
            alert.showAndWait();
        } else {
            foundDishLabel.setText(recipe.getDish());
            foundCuisineLabel.setText("" + recipe.getCuisine());
            foundPreparationTimeLabel.setText(String.format("%02dh %02dmin", recipe.getPreparationTime().toHoursPart(), recipe.getPreparationTime().toMinutesPart()));
            FoundAuthorLabel.setText(recipe.getAuthor());
            String ingredients = "";
            for (var ingredient : recipe.getIngredients()) {
                ingredients = ingredients + ingredient + " ";
            };
            foundIngerdientsLabel.setText(ingredients);
            recipeToFindTextField.setText(null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Znaleziono przepis!", ButtonType.OK);
            alert.showAndWait();
        }

    }

    private RecipeModel findRecipeInCookBook(String recipeToFind) {
        return App.getModel().findRecipeByName(recipeToFind);
    }
}
