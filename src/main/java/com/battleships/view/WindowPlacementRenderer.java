package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class WindowPlacementRenderer {
    Scene placementScene;
    Stage window = BattleshipsWindowed.getWindow();
    WindowPlacementController windowPlacementController;

    private void loadScene(FXMLLoader fxmlLoader){
        try {
            this.placementScene = new Scene(fxmlLoader.load());
        } catch (IOException e){
            System.err.println("Could not load resource!");
        }
    }

    public void renderPlacementPhase() {
        FXMLLoader fxmlLoader = new FXMLLoader(BattleshipsWindowed.class.getResource("placement_10.fxml"));
        loadScene(fxmlLoader);
        windowPlacementController = fxmlLoader.getController();
        window.setScene(placementScene);
    }

    private void addPieceToBoard() {
    }

    public void askForMoveInput() {
    }

    private void getPreviousPlayerMove(){
    }

    private void getInvalidMoveInfo(){
    }

    public void renderFinalScore() {

    }

    public void pressAnyKeyPromptForBackToMenu() {
    }
}
