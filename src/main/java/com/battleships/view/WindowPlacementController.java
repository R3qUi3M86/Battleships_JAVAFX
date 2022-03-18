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
        addPortUIActionListeners();
        commonPhaseController.addBoardGridUIActionListener(boardGrid, GamePhase.DEPLOYMENT);
        doneBtn.setVisible(false);
        doneBtn.setOnAction(e -> doneShipPlacementHandler());
        boardRegion.setOnMouseEntered(e -> commonPhaseController.hideBoardFieldSelector(boardFldSelector));
    }

    public void initPlacement(){
        HashMap<ShipType, Integer> shipsInPort = GameController.getInstance().getGameState().getCurrentPlayerShipsInPort();
        setShipsAmount(shipsInPort);
        disableVoidShipsUI(shipsInPort);
        hidePortSelectors();
        commonPhaseController.setPlayerNumber(playerPhaseInfo);
        commonPhaseController.setPlayerNumber(donePlacementText);
    }

    private void setShipsAmount(HashMap<ShipType, Integer> shipsInPort){
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
            ShipType deployedShipType = getDeployedShipType();
            if(GameController.getInstance().getShipDeployer().shipDeploymentIsLegal(deployedShipType, coordinate)){
                commonPhaseController.showBoardFieldSelector(deployedShipType, coordinate, boardFldSelector);
            } else {
                commonPhaseController.hideBoardFieldSelector(boardFldSelector);
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
                setShipsAmount(GameController.getInstance().getGameState().getCurrentPlayerShipsInPort());
                disableVoidShipsUI(GameController.getInstance().getGameState().getCurrentPlayerShipsInPort());
                Board currPlayerBoard = GameController.getInstance().getGameState().getCurrentPlayerBoard();
                commonPhaseController.drawShipsOnBoardGrid(shipGrid, currPlayerBoard, GameController.getInstance().getCurrentPlayer());
                commonPhaseController.hideBoardFieldSelector(boardFldSelector);
                if (GameController.getInstance().getGameState().allShipsAreAfloat()){
                    doneBtn.setVisible(true);
                }
            }
        }
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
