/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.cookbookwindowapp;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML start view controller class
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class StartViewController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private Button menuButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToMenuView(ActionEvent event) throws IOException {
        App.setRoot("menuView");
    }

    @FXML
    private void loadDataFromFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter("Wybierz plik *.txt", "*.txt");
        fileChooser.getExtensionFilters().add(fileExtension);

        fileChooser.setTitle("Otwórz plik");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String fileName = file.getAbsolutePath();
            App.getModel().readRecipeFromFile(fileName);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Niepoprawny plik!", ButtonType.OK);
            alert.showAndWait();
        }

    }

}
