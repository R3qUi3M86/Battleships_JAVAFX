package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import com.battleships.controller.GameController;
import com.battleships.model.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

public class CommonPhaseController {
    WindowPlacementController windowPlacementController;
    WindowRoundController windowRoundController;

    private Image gunboatImg;
    private Image cruiserImg;
    private Image battleshipImg;
    private Image carrierImg;

    public CommonPhaseController(WindowPlacementController controller){
        initShipImages();
        windowPlacementController = controller;
    }

    public CommonPhaseController(WindowRoundController controller){
        initShipImages();
        windowRoundController = controller;
    }

    private void initShipImages(){
        URL gunboatURL = BattleshipsWindowed.class.getResource("pictures/gunboat.png");
        URL cruiserURL = BattleshipsWindowed.class.getResource("pictures/cruiser.png");
        URL battleshipURL = BattleshipsWindowed.class.getResource("pictures/battleship.png");
        URL carrierURL = BattleshipsWindowed.class.getResource("pictures/carrier.png");

        assert gunboatURL != null;
        gunboatImg = new Image(gunboatURL.toExternalForm());
        assert cruiserURL != null;
        cruiserImg = new Image(cruiserURL.toExternalForm());
        assert battleshipURL != null;
        battleshipImg = new Image(battleshipURL.toExternalForm());
        assert carrierURL != null;
        carrierImg = new Image(carrierURL.toExternalForm());
    }

    public void addBoardGridUIActionListener(GridPane grid, GamePhase phase){
        for (int i = 0; i < GameController.getInstance().getBoardSize(); i++){
            for (int j = 0; j < GameController.getInstance().getBoardSize(); j++){
                Region region = new Region();
                grid.add(region, i, j);
                if (phase == GamePhase.DEPLOYMENT) {
                    region.setOnMouseEntered(e -> windowPlacementController.onGridMouseEnter(e));
                    region.setOnMouseClicked(e -> windowPlacementController.onGridMouseClick(e));
                } else {
//                    region.setOnMouseEntered(e -> windowRoundController.onGridMouseEnter(e));
//                    region.setOnMouseClicked(e -> windowRoundController.onGridMouseClick(e));
                }
            }
        }
    }

    public void drawShipsOnBoardGrid(GridPane shipGrid){
        shipGrid.getChildren().clear();
        Board board = GameController.getInstance().getGameState().getCurrentPlayerBoard();
        HashMap<String, ShipModule> shipModules = GameController.getInstance().getGameState().getCurrentPlayerShipsModules();

        for (int i = 0; i < GameController.getInstance().getBoardSize(); i++){
            for (int j = 0; j < GameController.getInstance().getBoardSize(); j++){

                int[] coordinate = new int[]{i,j};
                if (board.getBoardField(coordinate).getFieldContent() == FieldContent.MODULE &&
                        shipModules.get(Arrays.toString(coordinate)).isOrigin()){
                    Ship ship = shipModules.get(Arrays.toString(coordinate)).getShip();
                    ShipType shipType = ship.getShipType();
                    ShipOrientation orientation = ship.getShipOrientation();
                    drawShip(coordinate, shipType, orientation, shipGrid);
                }
            }
        }
    }

    private void drawShip(int[] coordinate, ShipType shipType, ShipOrientation shipOrientation, GridPane shipGrid){
        ImageView shipImgView = null;
        switch (shipType){
            case GUN_BOAT -> {
                shipImgView = new ImageView(gunboatImg);
                shipImgView.setFitWidth(50);
                shipImgView.setFitHeight(50);
            }
            case CRUISER -> {
                shipImgView = new ImageView(cruiserImg);
                shipImgView.setFitWidth(100);
                shipImgView.setFitHeight(50);
            }
            case BATTLESHIP -> {
                shipImgView = new ImageView(battleshipImg);
                shipImgView.setFitWidth(150);
                shipImgView.setFitHeight(50);
            }
            case CARRIER -> {
                shipImgView = new ImageView(carrierImg);
                shipImgView.setFitWidth(200);
                shipImgView.setFitHeight(50);
            }
        }
        if (shipOrientation == ShipOrientation.VERTICAL){
            shipImgView.setRotate(90);
            switch (shipType){
                case CRUISER -> {shipImgView.setTranslateX(-25); shipImgView.setTranslateY(25);}
                case BATTLESHIP -> {shipImgView.setTranslateX(-50); shipImgView.setTranslateY(50);}
                case CARRIER -> {shipImgView.setTranslateX(-75); shipImgView.setTranslateY(75);}
            }
        }
        shipGrid.add(shipImgView, coordinate[1], coordinate[0]);
    }
}
