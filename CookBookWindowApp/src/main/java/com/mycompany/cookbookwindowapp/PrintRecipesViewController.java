/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cookbookwindowapp;

import com.mycompany.model.Cuisine;
import com.mycompany.model.RecipeModel;
import com.mycompany.model.RecipeViewable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML print recipes view controller class
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class PrintRecipesViewController implements Initializable {

    @FXML
    private Label cookBookListLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private TableView<RecipeViewable> recipesTable;
    @FXML
    private TableColumn<RecipeViewable, Cuisine> dishColumn;
    @FXML
    private TableColumn<RecipeViewable, String> authorColumn;
    @FXML
    private TableColumn<RecipeViewable, String> preparationTimeColumn;
    @FXML
    private TableColumn<RecipeViewable, String> cuisineColumn;
    @FXML
    private TableColumn<RecipeViewable, ArrayList<String>> ingredientsColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dishColumn.setCellValueFactory(new PropertyValueFactory<>("dish"));
        cuisineColumn.setCellValueFactory(new PropertyValueFactory<>("cuisine"));
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        preparationTimeColumn.setCellValueFactory(new PropertyValueFactory<>("preparationTime"));
        loadData();
    }

    private void loadData() {
        ArrayList<RecipeModel> recipes = App.getModel().getCookBook();
        ArrayList<RecipeViewable> recipesViewable = new ArrayList<RecipeViewable>();

        for (var recipe : recipes) {
            RecipeViewable tmp = new RecipeViewable(recipe);
            recipesViewable.add(tmp);
        }
        ObservableList<RecipeViewable> data = FXCollections.observableArrayList(recipesViewable);
        recipesTable.getItems().clear();
        recipesTable.setItems(data);
    }

    @FXML
    private void deleteRecipe(ActionEvent event) throws IOException {
        if (recipesTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie zaznaczono przepisu!", ButtonType.OK);
            alert.showAndWait();
        } else {
            App.getModel().deleteRecipe(recipesTable.getSelectionModel().getSelectedItem().getDish());
            loadData();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pomyślnie usunięto przepis!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void backToMainView(ActionEvent event) throws IOException {
        App.setRoot("menuView");
    }

    @FXML
    private void editRecipe(ActionEvent event) throws IOException {

        if (recipesTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nie zaznaczono przepisu!", ButtonType.OK);
            alert.showAndWait();
        } else {
            App.setRecipeName(recipesTable.getSelectionModel().getSelectedItem().getDish());
            App.setRoot("modifyRecipeView");
        
        }

    }

}
