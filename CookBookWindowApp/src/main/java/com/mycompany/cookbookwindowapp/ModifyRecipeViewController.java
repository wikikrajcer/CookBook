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
 * FXML modify recipe view controller class
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class ModifyRecipeViewController implements Initializable {

    @FXML
    private ChoiceBox<Cuisine> cuisineChoiceBox;
    @FXML
    private Button deleteIngredientButton;
    @FXML
    private Button addIngredientButton;
    @FXML
    private ComboBox<String> ingredientsComboBox;
    @FXML
    private Button modifyRecipeButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField dishTextField;
    @FXML
    private TextField prepatationTimeTextField;
    @FXML
    private TextField authorTextField;
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
    private Label modifyRecipeLabel;

    private ArrayList<String> ingredients = new ArrayList();
    private RecipeModel recipeToModify = new RecipeModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cuisineChoiceBox.setItems(FXCollections.observableArrayList(Cuisine.values()));
        cuisineChoiceBox.getItems().setAll(Cuisine.values());
        recipeToModify = App.getModel().findRecipeByName(App.getRecipeName());
        ingredients = recipeToModify.getIngredients();
        refreshIngredientsList(ingredients);
        ingredientsComboBox.setPromptText("nowy składnik");
        prepatationTimeTextField.setPromptText("gg:mm");
        dishTextField.setText(recipeToModify.getDish());
        prepatationTimeTextField.setText(String.format("%02d:%02d", recipeToModify.getPreparationTime().toHoursPart(), recipeToModify.getPreparationTime().toMinutesPart()));
        cuisineChoiceBox.setValue(recipeToModify.getCuisine());
        authorTextField.setText(recipeToModify.getAuthor());
        ingredientsComboBox.setValue(null);
        ingredientsComboBox.setEditable(true);
    }

    @FXML
    private void modifyRecipeInCookBook(ActionEvent event) throws IOException {

        try {
            modifyRecipe(dishTextField.getText(), prepatationTimeTextField.getText(),
                    cuisineChoiceBox.getValue(), authorTextField.getText(), ingredients);
        } catch (java.lang.NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wprowadzono znak zamiast liczby!", ButtonType.OK);
            alert.showAndWait();
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wprowadzono zły format czasu przygotowania!", ButtonType.OK);
            alert.showAndWait();
        };
    }

    /**
     * Method that modify recipe
     *
     * @param dish recipe name
     * @param preparationTime recipe preparation time
     * @param cuisine recipe cuisine
     * @param author recipe author
     * @param ingredients recipe ingredients
     * @throws IOException exception
     */
    public void modifyRecipe(String dish, String preparationTime,
            Cuisine cuisine, String author, ArrayList<String> ingredients) throws IOException {
        try {
            if (!dishTextField.getText().equals(App.getRecipeName())) {
                App.getModel().validateRecipeToModify(dishTextField.getText());
            }

            App.getModel().findRecipeByName(App.getRecipeName()).prepareRecipe(App.getModel().findRecipeByName(App.getRecipeName()).validateDish(dish), App.getModel().findRecipeByName(App.getRecipeName()).validatePreparationTime(preparationTime),
                    cuisine, App.getModel().findRecipeByName(App.getRecipeName()).validateAuthor(author), App.getModel().findRecipeByName(App.getRecipeName()).validateIngredients(ingredients));

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pomyślnie zmodyfikowano przepis!", ButtonType.OK);
            alert.showAndWait();
            App.setRoot("printRecipesView");
        } catch (WrongRecipeFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    private void backtoMenuView(ActionEvent event) throws IOException {
        App.setRoot("menuView");
    }

    @FXML
    private void addIngredient(ActionEvent event) {
        
      //App.getModel().findRecipeByName(App.getRecipeName()).validateIngredients(ingredients);
       
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
