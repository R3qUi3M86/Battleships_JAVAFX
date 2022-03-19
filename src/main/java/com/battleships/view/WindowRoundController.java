package com.battleships.view;

import com.battleships.controller.GameController;
import com.battleships.controller.GameMode;
import com.battleships.controller.ViewController;
import com.battleships.model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class WindowRoundController {
    @FXML
    public Text playerPhaseInfo;
    public Text playerTurnCornerInfo;
    public Text shotsInfo;
    public Text shotPrompter;
    public ImageView seaImgView;
    public Rectangle boardFldSelector;
    public Button endTurnBtn;
    public Button backToMenuBtn;
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
        commonPhaseController.drawShipsOnBoardGrid(playerShipGrid, currPlayerBoard, currPlayer);
        commonPhaseController.drawShipsOnBoardGrid(enemyShipGrid, enemyPlayerFOWBoard, enemyPlayer);
        drawShotResultOverlay(playerShootingOverlay, currPlayerFOWBoard);
        drawShotResultOverlay(enemyShootingOverlay, enemyPlayerFOWBoard);
        commonPhaseController.addBoardGridUIActionListener(enemyBoardGrid, GamePhase.SHOOTING);
        setPlayerNumber();
        setShotsAmountInfo();
        endTurnBtn.setVisible(false);
        endTurnBtn.setOnAction(e -> endTurnHandler());
        backToMenuBtn.setVisible(false);
        backToMenuBtn.setOnAction(e -> backToMenuHandler());
        boardRegion.setOnMouseEntered(e -> commonPhaseController.hideBoardFieldSelector(boardFldSelector));
    }

    public void removeBoardGridUI(){
        enemyBoardGrid.getChildren().clear();
    }

    public void showPlayerPhaseOverlay(Scene scene){
        seaImgView.toFront();
        playerPhaseInfo.toFront();
        commonPhaseController.setPlayerNumber(playerPhaseInfo);
        scene.setOnMouseClicked(mouseEvent -> {
            playerPhaseInfo.setVisible(false);
            seaImgView.toBack();
            scene.setOnMouseClicked(evt->{});
            setAIShootingHandler();
        });
    }

    private void setAIShootingHandler(){
        Scene scene = boardRegion.getScene();
        if (GameController.getInstance().getGameMode() == GameMode.PLAYER_VS_COMPUTER &&
                GameController.getInstance().getCurrentPlayer() == Player.PLAYER2){
            scene.setOnMouseClicked(e -> {
                GameController.getInstance().aiShoot();
                if(GameController.getInstance().getGameState().getCurrentPlayerShots() == 0){
                    scene.setOnMouseClicked(evt->{});
                }
            });
        }
    }

    public void setIncomingFireText(){
        playerPhaseInfo.setText("Incoming fire from enemy fleet!");
    }

    private void loadMarkerImages(){
        hitImg = ImageLoader.loadImage("hit.png");
        missImg = ImageLoader.loadImage("miss.png");
        sunkImg = ImageLoader.loadImage("sunk.png");
    }

    public void setShotsAmountInfo(){
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
            showPlayerShotResult(GameController.getInstance().takeShotInput(coordinate));
            setShotsAmountInfo();
            if (GameController.getInstance().getGameState().getCurrentPlayerShots() == 0) {
                commonPhaseController.hideBoardFieldSelector(boardFldSelector);
                if (!GameController.getInstance().playerHasWon()) {
                    endTurnBtn.setVisible(true);
                }
            }
        }
    }

    public void takeShotFromComputerPlayer(ShotResult shotResult){
        showComputerShotResult(shotResult);
        setShotsAmountInfo();
        if (GameController.getInstance().getGameState().getCurrentPlayerShots() == 0 &&
                !GameController.getInstance().playerHasWon()) {
            endTurnBtn.setVisible(true);
        }
    }

    private void showPlayerShotResult(ShotResult shotResult){
        Board enemyPlayerFOWBoard = GameController.getInstance().getGameState().getEnemyFogOfWarBoard();
        commonPhaseController.drawShipsOnBoardGrid(enemyShipGrid, enemyPlayerFOWBoard, GameController.getInstance().getEnemyPlayer());
        drawShotResultOverlay(enemyShootingOverlay, enemyPlayerFOWBoard);
        setPlayerShotResultText(shotResult);
        checkWin(shotResult);
    }

    private void showComputerShotResult(ShotResult shotResult){
        Board playerBoard = GameController.getInstance().getGameState().getEnemyFogOfWarBoard();
        drawShotResultOverlay(playerShootingOverlay, playerBoard);
        setComputerShotResultText(shotResult);
        checkWin(shotResult);
    }

    private void drawShotResultOverlay(GridPane overlayGrid, Board board){
        overlayGrid.getChildren().clear();
        double imgSize = ImgConstantsProvider.getShipImgCellSize();
        for (int i = 0; i < GameController.getInstance().getBoardSize(); i++){
            for (int j = 0; j < GameController.getInstance().getBoardSize(); j++){

                int[] coordinate = new int[]{i,j};
                FieldContent fieldContent = board.getBoardField(coordinate).getFieldContent();
                switch (fieldContent){
                    case MISS -> {
                        ImageView missImgView = new ImageView(missImg);
                        missImgView.setFitWidth(imgSize);
                        missImgView.setFitHeight(imgSize);
                        overlayGrid.add(missImgView,j,i);
                    }
                    case HIT -> {
                        ImageView hitImgView = new ImageView(hitImg);
                        hitImgView.setFitWidth(imgSize);
                        hitImgView.setFitHeight(imgSize);
                        overlayGrid.add(hitImgView,j,i);
                    }
                    case SUNK -> {
                        ImageView sunkImgView = new ImageView(sunkImg);
                        sunkImgView.setFitWidth(imgSize);
                        sunkImgView.setFitHeight(imgSize);
                        overlayGrid.add(sunkImgView,j,i);
                    }
                }
            }
        }
    }

    private void setPlayerShotResultText(ShotResult shotResult){
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

    private void setComputerShotResultText(ShotResult shotResult){
        switch (shotResult){
            case MISSED -> shotPrompter.setText("Good! They missed...");
            case HIT_SOMETHING -> shotPrompter.setText("Our vessel took a direct hit!");
            case SUNK_GB -> shotPrompter.setText("They sunk our gunboat admiral!");
            case SUNK_CR -> shotPrompter.setText("They sunk our cruiser admiral!");
            case SUNK_BB -> shotPrompter.setText("They sunk our battleship admiral!");
            case SUNK_CA -> shotPrompter.setText("They sunk our carrier admiral!");
        }
    }

    private void checkWin(ShotResult shotResult){
        if ((shotResult == ShotResult.SUNK_GB || shotResult == ShotResult.SUNK_CR ||
                shotResult == ShotResult.SUNK_BB || shotResult == ShotResult.SUNK_CA) &&
                GameController.getInstance().playerHasWon()){
            showEndGameResults();
        }
    }

    private void showEndGameResults(){
        Board enemyBoard;
        Player enemyPlayer;
        if (GameController.getInstance().getGameMode() == GameMode.PLAYER_VS_COMPUTER &&
                GameController.getInstance().getCurrentPlayer() == Player.PLAYER2){
            enemyBoard = GameController.getInstance().getGameState().getCurrentPlayerBoard();
            enemyPlayer = GameController.getInstance().getCurrentPlayer();
        } else {
            enemyBoard = GameController.getInstance().getGameState().getEnemyPlayerBoard();
            enemyPlayer = GameController.getInstance().getEnemyPlayer();
        }
        commonPhaseController.drawShipsOnBoardGrid(enemyShipGrid, enemyBoard, enemyPlayer);

        playerTurnCornerInfo.setVisible(false);
        shotsInfo.setVisible(false);
        endTurnBtn.setVisible(false);
        backToMenuBtn.setVisible(true);
        setWinText();
    }

    private void setWinText(){
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1){
            shotPrompter.setText("Player 2 ships sunk into abyss...\nPlayer 1 is victorious!");
        } else {
            shotPrompter.setText("Player 1 ships sunk into abyss...\nPlayer 2 is victorious!");
        }
    }

    private void endTurnHandler(){
        GameController.getInstance().playRound();
    }

    private void backToMenuHandler(){
        ViewController.getInstance().displayMainMenu();
    }

    public void setPlayerNumber() {
        commonPhaseController.setPlayerNumber(playerTurnCornerInfo);
    }
}
