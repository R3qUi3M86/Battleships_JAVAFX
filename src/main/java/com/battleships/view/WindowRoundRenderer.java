package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import com.battleships.controller.GameController;
import com.battleships.model.ShotResult;
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

    public void renderPlayerRoundPhase() {
        setRoundScene();
        window.setScene(roundScene);
    }

    public void renderComputerRoundPhase(){
        setRoundScene();
        windowRoundController.setIncomingFireText();
        windowRoundController.removeBoardGridUI();
        window.setScene(roundScene);
    }

    public void setInfoElementsUI(){
        windowRoundController.setShotsAmountInfo();
        windowRoundController.setPlayerNumber();
    }

    private void setRoundScene(){
        String boardSizeStr = Integer.toString(GameController.getInstance().getBoardSize());
        FXMLLoader fxmlLoader = new FXMLLoader(BattleshipsWindowed.class.getResource("game_"+boardSizeStr+".fxml"));
        loadScene(fxmlLoader);
        windowRoundController = fxmlLoader.getController();
        windowRoundController.showPlayerPhaseOverlay(roundScene);
    }

    public void showAIShotResult(ShotResult shotResult){
        windowRoundController.takeShotFromComputerPlayer(shotResult);
    }
}
