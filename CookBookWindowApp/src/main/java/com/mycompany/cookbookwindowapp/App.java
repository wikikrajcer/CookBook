package com.mycompany.cookbookwindowapp;

import com.mycompany.model.CookBookModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 * @author Wiktoria Krajcer
 * @version 3.0
 */
public class App extends Application {

    /**
     * cookBook Model
     */
    private static CookBookModel cookBookModel = new CookBookModel();
    /**
     * recipe Name
     */
    private static String recipeName = "";
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("startView"), 700, 450);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *  Method that gets cookBook
     * @return cookBook
     */   
    static public CookBookModel getModel() {
        return cookBookModel;
    }

    /**
     * Method that gets recipe name
     * @return recipeName
     */  
    public static String getRecipeName() {
        return recipeName;
    }

    /**
     * Method that sets recipe name
     * @param recipeNameToSet recipe name to set
     */
    public static void setRecipeName(String recipeNameToSet) {
        recipeName = recipeNameToSet;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
