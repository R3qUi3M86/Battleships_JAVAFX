package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import com.battleships.controller.GameController;
import com.battleships.model.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

public class WindowRoundController {
    @FXML
    public Text playerPhaseInfo;
    public Text playerTurnCornerInfo;
    public Text shotsInfo;
    public Text shotPrompter;
    public ImageView seaImgView;
    public Rectangle boardFldSelector;
    public Button endTurnBtn;
    public GridPane enemyShipGrid;
    public GridPane enemyBoardGrid;
    public GridPane enemyShootingOverlay;
    public GridPane playerShipGrid;
    public GridPane playerShootingOverlay;
    public Region boardRegion;

    private Image hitImg;
    private Image missImg;
    private Image sunkImg;

    private final CommonPhaseController commonPhaseController = new CommonPhaseController(this);

    public void initialize(){
        loadMarkerImages();
        Player currPlayer = GameController.getInstance().getCurrentPlayer();
        Player enemyPlayer = GameController.getInstance().getEnemyPlayer();
        Board currPlayerBoard = GameController.getInstance().getGameState().getCurrentPlayerBoard();
        Board currPlayerFOWBoard = GameController.getInstance().getGameState().getCurrentFogOfWarBoard();
        Board enemyPlayerFOWBoard = GameController.getInstance().getGameState().getEnemyFogOfWarBoard();
        commonPhaseController.setPlayerNumber(playerPhaseInfo);
        commonPhaseController.setPlayerNumber(playerTurnCornerInfo);
        commonPhaseController.drawShipsOnBoardGrid(playerShipGrid, currPlayerBoard, currPlayer);
        commonPhaseController.drawShipsOnBoardGrid(enemyShipGrid, enemyPlayerFOWBoard, enemyPlayer);
        drawShotResultOverlay(playerShootingOverlay, currPlayerFOWBoard);
        drawShotResultOverlay(enemyShootingOverlay, enemyPlayerFOWBoard);
        commonPhaseController.addBoardGridUIActionListener(enemyBoardGrid, GamePhase.SHOOTING);
        setShotsAmountInfo();
        endTurnBtn.setVisible(false);
        endTurnBtn.setOnAction(e -> endTurnHandler());
        boardRegion.setOnMouseEntered(e -> commonPhaseController.hideBoardFieldSelector(boardFldSelector));
    }

    private void loadMarkerImages(){
        hitImg = ImageLoader.loadImage("hit.png");
        missImg = ImageLoader.loadImage("miss.png");
        sunkImg = ImageLoader.loadImage("sunk.png");
    }

    private void setShotsAmountInfo(){
        int shots = GameController.getInstance().getGameState().getCurrentPlayerShots();
        shotsInfo.setText(shotsInfo.getText().replaceAll("[0-9#]", Integer.toString(shots)));
    }

    public void onGridMouseEnter(MouseEvent event){
        if (GameController.getInstance().getGameState().getCurrentPlayerShots() > 0) {
            Node node = (Node) event.getTarget();
            int[] coordinate = new int[]{GridPane.getRowIndex(node), GridPane.getColumnIndex(node)};
            commonPhaseController.showBoardFieldSelector(ShipType.GUN_BOAT, coordinate, boardFldSelector);
        }
    }

    public void onGridMouseClick(MouseEvent event){
        if (GameController.getInstance().getGameState().getCurrentPlayerShots() > 0) {
            Node node = (Node) event.getTarget();
            int[] coordinate = new int[]{GridPane.getRowIndex(node), GridPane.getColumnIndex(node)};
            showShotResult(GameController.getInstance().takeShotInput(coordinate));
            setShotsAmountInfo();
            if (GameController.getInstance().getGameState().getCurrentPlayerShots() == 0) {
                endTurnBtn.setVisible(true);
                commonPhaseController.hideBoardFieldSelector(boardFldSelector);
            }
        }
    }

    private void showShotResult(ShotResult shotResult){
        Board enemyPlayerFOWBoard = GameController.getInstance().getGameState().getEnemyFogOfWarBoard();
        commonPhaseController.drawShipsOnBoardGrid(enemyShipGrid, enemyPlayerFOWBoard, GameController.getInstance().getEnemyPlayer());
        drawShotResultOverlay(enemyShootingOverlay, enemyPlayerFOWBoard);
        setShotResultText(shotResult);
    }

    private void drawShotResultOverlay(GridPane overlayGrid, Board board){
        overlayGrid.getChildren().clear();
        for (int i = 0; i < GameController.getInstance().getBoardSize(); i++){
            for (int j = 0; j < GameController.getInstance().getBoardSize(); j++){

                int[] coordinate = new int[]{i,j};
                FieldContent fieldContent = board.getBoardField(coordinate).getFieldContent();
                switch (fieldContent){
                    case MISS -> overlayGrid.add(new ImageView(missImg),j,i);
                    case HIT -> overlayGrid.add(new ImageView(hitImg),j,i);
                    case SUNK -> overlayGrid.add(new ImageView(sunkImg),j,i);
                }
            }
        }
    }

    private void setShotResultText(ShotResult shotResult){
        switch (shotResult){
            case MISSED -> shotPrompter.setText("We splashed some water...");
            case HIT_SOMETHING -> shotPrompter.setText("Direct hit!");
            case REPEATING -> shotPrompter.setText("We are only wasting ammo admiral...");
            case SUNK_GB -> shotPrompter.setText("Enemy gunboat sunk!");
            case SUNK_CR -> shotPrompter.setText("Enemy cruiser sunk!");
            case SUNK_BB -> shotPrompter.setText("Enemy battleship sunk!");
            case SUNK_CA -> shotPrompter.setText("Enemy carrier sunk!");
        }
    }

    private void endTurnHandler(){
        GameController.getInstance().playRound();
    }

    public void initRound(){

    }
}
