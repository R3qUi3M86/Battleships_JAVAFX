package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        FXMLLoader fxmlLoader = new FXMLLoader(BattleshipsWindowed.class.getResource("placement.fxml"));
        loadScene(fxmlLoader);
        windowPlacementController = fxmlLoader.getController();
        window.setScene(placementScene);
        windowPlacementController.addRotateKeyPressListener(placementScene);
        windowPlacementController.initPlacement();
    }
}
