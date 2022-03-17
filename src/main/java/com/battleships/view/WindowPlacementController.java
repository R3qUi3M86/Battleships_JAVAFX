package com.battleships.view;
import com.battleships.controller.GameController;
import com.battleships.model.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.HashMap;

public class WindowPlacementController {
    private int LAYOUT_X_START;
    private int LAYOUT_Y_START;
    private int BASE_SIZE;

    @FXML
    public Text playerPhaseInfo;
    public Text donePlacementText;
    public Text portNoGB;
    public Text portNoCR;
    public Text portNoBB;
    public Text portNoCA;
    public ImageView seaImgView;
    public ImageView portImgGB;
    public ImageView portImgCR;
    public ImageView portImgBB;
    public ImageView portImgCA;
    public Rectangle selectPortGB;
    public Rectangle selectPortCR;
    public Rectangle selectPortBB;
    public Rectangle selectPortCA;
    public Rectangle boardFldSelector;
    public Button doneBtn;
    public GridPane shipGrid;
    public GridPane boardGrid;
    public Region boardRegion;

    private Rectangle activeSelector;
    private final CommonPhaseController commonPhaseController = new CommonPhaseController(this);

    public void initialize(){
        setSelectorConstants();
        addPortUIActionListeners();
        commonPhaseController.addBoardGridUIActionListener(boardGrid, GamePhase.DEPLOYMENT);
        doneBtn.setVisible(false);
        doneBtn.setOnAction(e -> doneShipPlacementHandler());
        boardRegion.setOnMouseEntered(e -> hideBoardFieldSelector());
    }

    public void initPlacement(){
        HashMap<ShipType, Integer> shipsInPort = GameController.getInstance().getGameState().getCurrentPlayerShipsInPort();
        setShipsAmmount(shipsInPort);
        disableVoidShipsUI(shipsInPort);
        hidePortSelectors();
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1) {
            playerPhaseInfo.setText(playerPhaseInfo.getText().replace("#", "1"));
            donePlacementText.setText(donePlacementText.getText().replace("#", "1"));
        } else {
            playerPhaseInfo.setText(playerPhaseInfo.getText().replace("#", "2"));
            donePlacementText.setText(donePlacementText.getText().replace("#", "2"));
        }
    }

    private void setShipsAmmount(HashMap<ShipType, Integer> shipsInPort){
        for (ShipType shipType : shipsInPort.keySet()){
            switch (shipType){
                case GUN_BOAT -> portNoGB.setText("x"+shipsInPort.get(ShipType.GUN_BOAT).toString());
                case CRUISER -> portNoCR.setText("x"+shipsInPort.get(ShipType.CRUISER).toString());
                case BATTLESHIP -> portNoBB.setText("x"+shipsInPort.get(ShipType.BATTLESHIP).toString());
                case CARRIER -> portNoCA.setText("x"+shipsInPort.get(ShipType.CARRIER).toString());
            }
        }
    }
    
    private void disableVoidShipsUI(HashMap<ShipType, Integer> shipsInPort){
        if (!shipsInPort.containsKey(ShipType.GUN_BOAT) || shipsInPort.get(ShipType.GUN_BOAT) == 0){
            portNoGB.setVisible(false);
            portImgGB.setVisible(false);
            selectPortGB.setVisible(false);
        }
        if (!shipsInPort.containsKey(ShipType.CRUISER) || shipsInPort.get(ShipType.CRUISER) == 0){
            portNoCR.setVisible(false);
            portImgCR.setVisible(false);
            selectPortCR.setVisible(false);
        }
        if (!shipsInPort.containsKey(ShipType.BATTLESHIP) || shipsInPort.get(ShipType.BATTLESHIP) == 0){
            portNoBB.setVisible(false);
            portImgBB.setVisible(false);
            selectPortBB.setVisible(false);
        }
        if (!shipsInPort.containsKey(ShipType.CARRIER) || shipsInPort.get(ShipType.CARRIER) == 0){
            portNoCA.setVisible(false);
            portImgCA.setVisible(false);
            selectPortCA.setVisible(false);
        }
        if (activeSelector != null && !activeSelector.isVisible()){
            activeSelector = null;
        }
    }
    
    private void hidePortSelectors(){
        selectPortGB.setOpacity(0);
        selectPortCR.setOpacity(0);
        selectPortBB.setOpacity(0);
        selectPortCA.setOpacity(0);
    }

    private void setSelectorConstants(){
        LAYOUT_X_START = 449;
        LAYOUT_Y_START = 149;
        BASE_SIZE = 52;
    }

    private void addPortUIActionListeners(){
        selectPortGB.setOnMouseClicked(e -> onPortUIBtnClick(selectPortGB));
        selectPortCR.setOnMouseClicked(e -> onPortUIBtnClick(selectPortCR));
        selectPortBB.setOnMouseClicked(e -> onPortUIBtnClick(selectPortBB));
        selectPortCA.setOnMouseClicked(e -> onPortUIBtnClick(selectPortCA));
    }

    private void onPortUIBtnClick(Rectangle shipSelector){
        hidePortSelectors();
        shipSelector.setOpacity(1);
        activeSelector = shipSelector;
    }

    private void doneShipPlacementHandler(){
        seaImgView.toFront();
        donePlacementText.toFront();
        donePlacementText.setVisible(true);
        seaImgView.getScene().setOnMouseClicked(e ->{
            if (GameController.getInstance().getGameState().getEnemyPlayerShips().size() == 0) {
                GameController.getInstance().startNextPlacementPhase();
            } else {
                GameController.getInstance().playRound();
            }
        });
    }

    public void addRotateKeyPressListener(Scene currentScene){
        currentScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.R){
                GameController.getInstance().switchShipOrientation();
            }
        });
    }

    public void onGridMouseEnter(MouseEvent event){
        if (activeSelector != null) {
            Node node = (Node) event.getTarget();
            int[] coordinate = new int[]{GridPane.getRowIndex(node), GridPane.getColumnIndex(node)};
            ShipType deployedShip = getDeployedShipType();
            if(GameController.getInstance().getShipDeployer().shipDeploymentIsLegal(deployedShip, coordinate)){
                showBoardFieldSelector(deployedShip, coordinate);
            } else {
                hideBoardFieldSelector();
            }
        }
    }

    public void onGridMouseClick(MouseEvent event){
        if (activeSelector != null) {
            Node node = (Node) event.getTarget();
            int[] coordinate = new int[]{GridPane.getRowIndex(node), GridPane.getColumnIndex(node)};
            ShipType deployedShip = getDeployedShipType();
            if(GameController.getInstance().getShipDeployer().shipDeploymentIsLegal(deployedShip, coordinate)){
                GameController.getInstance().getShipDeployer().deployShip(deployedShip, coordinate);
                setShipsAmmount(GameController.getInstance().getGameState().getCurrentPlayerShipsInPort());
                disableVoidShipsUI(GameController.getInstance().getGameState().getCurrentPlayerShipsInPort());
                commonPhaseController.drawShipsOnBoardGrid(shipGrid);
                hideBoardFieldSelector();
                if (GameController.getInstance().getGameState().allShipsAreAfloat()){
                    doneBtn.setVisible(true);
                }
            }
        }
    }

    private void showBoardFieldSelector(ShipType deployedShip, int[] coordinate){
        ShipOrientation shipOrientation = GameController.getInstance().getShipOrientation();
        int sizeMultiplier = 0;
        if (deployedShip == ShipType.CRUISER){
            sizeMultiplier = 1;
        } else if (deployedShip == ShipType.BATTLESHIP){
            sizeMultiplier = 2;
        } else if (deployedShip == ShipType.CARRIER){
            sizeMultiplier = 3;
        }
        if (shipOrientation == ShipOrientation.HORIZONTAL){
            boardFldSelector.setWidth(BASE_SIZE + (sizeMultiplier*50));
            boardFldSelector.setHeight(BASE_SIZE);
        } else {
            boardFldSelector.setHeight(BASE_SIZE + (sizeMultiplier*50));
            boardFldSelector.setWidth(BASE_SIZE);
        }
        boardFldSelector.setLayoutX(coordinate[1]*50+LAYOUT_X_START);
        boardFldSelector.setLayoutY(coordinate[0]*50+LAYOUT_Y_START);
        boardFldSelector.setVisible(true);
    }

    private void hideBoardFieldSelector(){
        boardFldSelector.setVisible(false);
    }

    private ShipType getDeployedShipType(){
        if (selectPortGB.equals(activeSelector)) {
            return ShipType.GUN_BOAT;
        } else if (selectPortCR.equals(activeSelector)) {
            return ShipType.CRUISER;
        } else if (selectPortBB.equals(activeSelector)) {
            return ShipType.BATTLESHIP;
        } else {
            return ShipType.CARRIER;
        }
    }
}
