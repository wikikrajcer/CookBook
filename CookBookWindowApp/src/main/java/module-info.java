module com.mycompany.cookbookwindowapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    
    opens com.mycompany.model;
    opens com.mycompany.cookbookwindowapp to javafx.fxml;
    exports com.mycompany.cookbookwindowapp;
}
