package com.battleships.view;

import com.battleships.controller.GameController;
import com.battleships.model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashMap;

public class CommonPhaseController {
    WindowPlacementController windowPlacementController;
    WindowRoundController windowRoundController;

    private int LAYOUT_X_START;
    private int LAYOUT_Y_START;
    private int BASE_SIZE;

    private Image gunboatImg;
    private Image cruiserImg;
    private Image battleshipImg;
    private Image carrierImg;

    public CommonPhaseController(WindowPlacementController controller){
        LAYOUT_X_START = 449;
        LAYOUT_Y_START = 149;
        BASE_SIZE = 52;
        loadShipImages();
        windowPlacementController = controller;
    }

    public CommonPhaseController(WindowRoundController controller){
        LAYOUT_X_START = 779;
        LAYOUT_Y_START = 149;
        BASE_SIZE = 52;
        loadShipImages();
        windowRoundController = controller;
    }

    public void setPlayerNumber(Text text){
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1) {
            text.setText(text.getText().replace("#", "1"));
        } else {
            text.setText(text.getText().replace("#", "2"));
        }
    }

    private void loadShipImages(){
        gunboatImg = ImageLoader.loadImage("gunboat.png");
        cruiserImg = ImageLoader.loadImage("cruiser.png");
        battleshipImg = ImageLoader.loadImage("battleship.png");
        carrierImg = ImageLoader.loadImage("carrier.png");
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
                    region.setOnMouseEntered(e -> windowRoundController.onGridMouseEnter(e));
                    region.setOnMouseClicked(e -> windowRoundController.onGridMouseClick(e));
                }
            }
        }
    }

    public void showBoardFieldSelector(ShipType shipType, int[] coordinate, Rectangle fldSelector){
        ShipOrientation shipOrientation = GameController.getInstance().getShipOrientation();
        int sizeMultiplier = 0;
        if (shipType == ShipType.CRUISER){
            sizeMultiplier = 1;
        } else if (shipType == ShipType.BATTLESHIP){
            sizeMultiplier = 2;
        } else if (shipType == ShipType.CARRIER){
            sizeMultiplier = 3;
        }
        if (shipOrientation == ShipOrientation.HORIZONTAL){
            fldSelector.setWidth(BASE_SIZE + (sizeMultiplier*50));
            fldSelector.setHeight(BASE_SIZE);
        } else {
            fldSelector.setHeight(BASE_SIZE + (sizeMultiplier*50));
            fldSelector.setWidth(BASE_SIZE);
        }
        fldSelector.setLayoutX(coordinate[1]*50+LAYOUT_X_START);
        fldSelector.setLayoutY(coordinate[0]*50+LAYOUT_Y_START);
        fldSelector.setVisible(true);
    }

    public void drawShipsOnBoardGrid(GridPane shipGrid, Board board, Player player){
        shipGrid.getChildren().clear();
        HashMap<String, ShipModule> shipModules = GameController.getInstance().getGameState().getPlayerShipsModules(player);

        for (int i = 0; i < GameController.getInstance().getBoardSize(); i++){
            for (int j = 0; j < GameController.getInstance().getBoardSize(); j++){

                int[] coordinate = new int[]{i,j};
                if ((board.getBoardField(coordinate).getFieldContent() == FieldContent.MODULE ||
                        board.getBoardField(coordinate).getFieldContent() == FieldContent.SUNK) &&
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

    public void hideBoardFieldSelector(Rectangle boardFldSelector){
        boardFldSelector.setVisible(false);
    }
}
