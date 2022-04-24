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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML delete recipe view controller class
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class DeleteRecipeViewController implements Initializable {

    @FXML
    private Label deleteRecipeLabel;
    @FXML
    private Label insertRecipeNameLabel;
    @FXML
    private TextField recipeToDeleteTextField;
    @FXML
    private Button backButton;
    @FXML
    private Button removeButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void backToMainView(ActionEvent event) throws IOException {
        App.setRoot("menuView");
    }

    @FXML
    private void removeRecipe(ActionEvent event) throws IOException {
        boolean foundRecipeToDelete = this.deleteRecipeFromCookBook(recipeToDeleteTextField.getText());
        if (!foundRecipeToDelete) {
            Alert alert = new Alert(AlertType.ERROR, "Przepis o podanej nazwie nie istnieje!", ButtonType.OK);
            alert.showAndWait();
        } else {
            recipeToDeleteTextField.setText(null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pomyślnie usunięto przepis!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public boolean deleteRecipeFromCookBook(String recipeToDelete) {
        boolean foundRecipeToDelete = App.getModel().deleteRecipe(recipeToDelete);
        return foundRecipeToDelete;
    }

}
