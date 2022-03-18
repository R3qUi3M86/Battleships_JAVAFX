package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowRoundRenderer {
    Scene roundScene;
    Stage window = BattleshipsWindowed.getWindow();
    WindowRoundController windowRoundController;

    private void loadScene(FXMLLoader fxmlLoader){
        try {
            this.roundScene = new Scene(fxmlLoader.load());
        } catch (IOException e){
            System.err.println("Could not load resource!");
        }
    }

    public void renderRoundPhase() {
        FXMLLoader fxmlLoader = new FXMLLoader(BattleshipsWindowed.class.getResource("game.fxml"));
        loadScene(fxmlLoader);
        windowRoundController = fxmlLoader.getController();
        windowRoundController.showPlayerPhaseOverlay(roundScene);
        window.setScene(roundScene);
    }
}
