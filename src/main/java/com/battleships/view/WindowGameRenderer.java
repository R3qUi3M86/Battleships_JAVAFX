package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WindowGameRenderer {
    Scene menuScene;
    Scene gameScene;
    Image whitePawnImg;
    Image whiteQueenImg;
    Image blackPawnImg;
    Image blackQueenImg;
    WindowGameController windowGameController = new WindowGameController();

    public WindowGameRenderer(){
        URL whitePawnURL = BattleshipsWindowed.class.getResource("pictures/white_pawn.png");
        URL whiteQueenURL = BattleshipsWindowed.class.getResource("pictures/white_queen.png");
        URL blackPawnURL = BattleshipsWindowed.class.getResource("pictures/black_pawn.png");
        URL blackQueenURL = BattleshipsWindowed.class.getResource("pictures/black_queen.png");

        assert whitePawnURL != null;
        whitePawnImg = new Image(whitePawnURL.toExternalForm());
        assert whiteQueenURL != null;
        whiteQueenImg = new Image(whiteQueenURL.toExternalForm());
        assert blackPawnURL != null;
        blackPawnImg = new Image(blackPawnURL.toExternalForm());
        assert blackQueenURL != null;
        blackQueenImg = new Image(blackQueenURL.toExternalForm());
    }

    public void renderGameState() {
    }

    private void addPieceToBoard() {
    }

    private void addFieldDropZone(GridPane boardGrid, int row, int col, int fieldNo) {
        Region region = new Region();
        region.setMaxSize(70,70);
        region.setPrefSize(70,70);
        region.setId(String.valueOf(fieldNo));
        boardGrid.add(region, col, row);
        windowGameController.addDropzoneEnterListener(region);
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
