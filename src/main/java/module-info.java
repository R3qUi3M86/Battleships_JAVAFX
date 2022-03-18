module com.battleships {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.battleships to javafx.fxml;
    exports com.battleships;
    opens com.battleships.view to javafx.fxml;
    opens com.battleships.model to javafx.fxml;
}