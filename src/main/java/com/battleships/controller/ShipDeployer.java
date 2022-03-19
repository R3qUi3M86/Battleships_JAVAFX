package com.battleships.controller;

import com.battleships.model.*;
import com.battleships.util.Utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ShipDeployer {
    public boolean shipDeploymentIsLegal(ShipType shipType, int[] origin) {
        Set<int[]> shipFields = getShipDeploymentFields(shipType, origin);
        return !moduleOutsideBoard(shipFields)
                && !moduleCollidesWithAnotherShip(shipFields)
                && !moduleTouchAnotherShip(shipFields);
    }

    private Set<int[]> getShipDeploymentFields(ShipType shipType, int[] origin) {
        ShipOrientation shipOrientation = GameController.getInstance().getShipOrientation();
        Ship ship = new Ship(shipType, origin, shipOrientation);
        Set<int[]> shipFields = new HashSet<>();
        for (String field : ship.getShipModules().keySet()){
            shipFields.add(Utilities.toArray(field));
        }
        return shipFields;
    }

    private boolean moduleOutsideBoard(Set<int[]> shipFields) {
        for (int[] coordinate : shipFields) {
            if ((coordinate[0] < 0 || coordinate[0] > GameController.getInstance().getBoardSize() - 1) ||
                    (coordinate[1] < 0 || coordinate[1] > GameController.getInstance().getBoardSize() - 1)) {
                return true;
            }
        }
        return false;
    }

    private boolean moduleCollidesWithAnotherShip(Set<int[]> shipFields){
        Board board = GameController.getInstance().getGameState().getCurrentPlayerBoard();
        for (int[] coordinate : shipFields) {
            if (board.getBoardField(coordinate).getFieldContent() == FieldContent.MODULE){
                return true;
            }
        }
        return false;
    }

    private boolean moduleTouchAnotherShip(Set<int[]> shipFields){
        Board board = GameController.getInstance().getGameState().getCurrentPlayerBoard();
        Set<int[]> shipVicinityFields = new HashSet<>();
        for (int[] shipField : shipFields){
            shipVicinityFields.addAll(getAdjacentFields(shipField));
        }
        shipVicinityFields.removeAll(shipFields);
        for (int[] shipVicinityField : shipVicinityFields){
            if(board.getBoardField(shipVicinityField).getFieldContent() == FieldContent.MODULE){
                return true;
            }
        }
        return false;
    }

    private Set<int[]> getAdjacentFields(int[] shipField){
        Set<int[]> adjacentFields = new HashSet<>();
        if(shipField[0]-1 >= 0){
            adjacentFields.add(new int[]{shipField[0]-1, shipField[1]});
        }
        if(shipField[0]+1 < GameController.getInstance().getBoardSize()){
            adjacentFields.add(new int[]{shipField[0]+1, shipField[1]});
        }
        if(shipField[1]-1 >= 0){
            adjacentFields.add(new int[]{shipField[0], shipField[1]-1});
        }
        if(shipField[1]+1 < GameController.getInstance().getBoardSize()){
            adjacentFields.add(new int[]{shipField[0], shipField[1]+1});
        }
        return adjacentFields;
    }

    public void deployShip(ShipType shipType, int[] origin){
        GameState gameState = GameController.getInstance().getGameState();
        Ship deployedShip = new Ship(shipType, origin, GameController.getInstance().getShipOrientation());
        gameState.getCurrentPlayerShips().add(deployedShip);
        decrementPortShipsQuantity(shipType);
        HashMap<String, ShipModule> shipModules = deployedShip.getShipModules();
        for (String coordinate : shipModules.keySet()){
            gameState.getCurrentPlayerShipsModules().put(coordinate, shipModules.get(coordinate));
            gameState.getCurrentPlayerBoard().getBoardField(Utilities.toArray(coordinate)).setFieldContent(FieldContent.MODULE);
        }
    }

    private void decrementPortShipsQuantity(ShipType shipType){
        HashMap<ShipType, Integer> currPlayerPortShips = GameController.getInstance().getGameState().getCurrentPlayerShipsInPort();
        currPlayerPortShips.put(shipType, currPlayerPortShips.get(shipType) - 1);
    }
}
