/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cookbookwindowapp;

import com.mycompany.exception.WrongRecipeFormatException;
import com.mycompany.model.Cuisine;
import com.mycompany.model.RecipeModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML create recipe view controller class
 *
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class CreateRecipeViewController implements Initializable {

    @FXML
    private Label createRecipeLabel;
    @FXML
    private Label dishLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label cuisineLabel;
    @FXML
    private Label ingredientsLabel;
    @FXML
    private Label preparationTimeLabel;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField prepatationTimeTextField;
    @FXML
    private TextField dishTextField;
    @FXML
    private Button backButton;
    @FXML
    private Button createRecipeButton;
    @FXML
    private ComboBox<String> ingredientsComboBox;
    @FXML
    private Button addIngredientButton;
    @FXML
    private Button deleteIngredientButton;
    @FXML
    private ChoiceBox<Cuisine> cuisineChoiceBox;

    private ArrayList<String> ingredients = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cuisineChoiceBox.setItems(FXCollections.observableArrayList(Cuisine.values()));
        cuisineChoiceBox.getItems().setAll(Cuisine.values());
        cuisineChoiceBox.setValue(Cuisine.NIEZNANA);
        ingredientsComboBox.setPromptText("nowy składnik");
        prepatationTimeTextField.setPromptText("gg:mm");
        ingredientsComboBox.setEditable(true);
    }

    @FXML
    private void backtoMenuView(ActionEvent event) throws IOException {
        App.setRoot("menuView");
    }

    @FXML
    private void createRecipeInCookBook(ActionEvent event) throws IOException {

        try {
            this.createRecipe(dishTextField.getText(), prepatationTimeTextField.getText(),
                    cuisineChoiceBox.getValue(), authorTextField.getText(), ingredients);

        } catch (java.lang.NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wprowadzono znak zamiast liczby!", ButtonType.OK);
            alert.showAndWait();
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wprowadzono zły format czasu przygotowania!", ButtonType.OK);
            alert.showAndWait();
        };

    }

    private void clearData() {
        dishTextField.setText(null);
        prepatationTimeTextField.setText(null);
        cuisineChoiceBox.setValue(Cuisine.NIEZNANA);
        authorTextField.setText(null);
        ingredientsComboBox.setValue(null);
    }

    /**
     * Method that create recipe
     *
     * @param dish recipe name
     * @param preparationTime recipe preparation time
     * @param cuisine recipe cuisine
     * @param author recipe author
     * @param ingredients recipe ingredients
     */
    public void createRecipe(String dish, String preparationTime,
            Cuisine cuisine, String author, ArrayList<String> ingredients) {
        try {
            RecipeModel newRecipe = new RecipeModel();
            newRecipe.prepareRecipe(newRecipe.validateDish(dish), newRecipe.validatePreparationTime(preparationTime),
                    cuisine, newRecipe.validateAuthor(author), newRecipe.validateIngredients(ingredients));
            App.getModel().validateRecipeToAdd(newRecipe);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pomyślnie dodano nowy przepis!", ButtonType.OK);
            alert.showAndWait();
            clearData();
        } catch (WrongRecipeFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void addIngredient(ActionEvent event) {
        if (ingredientsComboBox.getValue() != null && !ingredientsComboBox.getValue().isBlank()) {
            ingredients.add(ingredientsComboBox.getValue());
            refreshIngredientsList(ingredients);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie wprowadzono składnika!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void refreshIngredientsList(ArrayList<String> ingredients) {
        ingredientsComboBox.getItems().removeAll(ingredientsComboBox.getItems());
        ingredientsComboBox.getItems().addAll(ingredients);
        ingredientsComboBox.setValue(null);
    }

    @FXML
    private void deleteIngredient(ActionEvent event) {
        if (ingredientsComboBox.getValue() != null && !ingredientsComboBox.getValue().isBlank()) {
            ingredients.remove(ingredientsComboBox.getSelectionModel().getSelectedItem());
            refreshIngredientsList(ingredients);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie zaznaczono składnika!", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
