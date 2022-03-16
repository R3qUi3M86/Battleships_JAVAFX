package com.battleships.model;

import com.battleships.controller.GameController;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    private final int boardSize;
    private final HashMap<ShipType, Integer> player1shipsInPort = new HashMap<>();
    private final HashMap<ShipType, Integer> player2shipsInPort = new HashMap<>();
    private final Board player1Board;
    private final Board player2Board;
    private final ArrayList<Ship> player1ships = new ArrayList<>();
    private final ArrayList<Ship> player2ships = new ArrayList<>();
    private final HashMap<String, ShipModule> player1shipsModules = new HashMap<>();
    private final HashMap<String, ShipModule> player2shipsModules = new HashMap<>();

    public GameState(int boardSize){
        this.boardSize = boardSize;
        player1Board = new Board(boardSize);
        player2Board = new Board(boardSize);
        setShipsInPort(player1shipsInPort);
        setShipsInPort(player2shipsInPort);
    }

    private void setShipsInPort(HashMap<ShipType, Integer> shipsInPort){
        switch (boardSize){
            case 5 -> {
                shipsInPort.put(ShipType.GUN_BOAT, 2);
                shipsInPort.put(ShipType.CRUISER, 3);
            }
            case 6 -> {
                shipsInPort.put(ShipType.GUN_BOAT, 3);
                shipsInPort.put(ShipType.CRUISER, 2);
                shipsInPort.put(ShipType.BATTLESHIP, 1);
            }
            case 7 -> {
                shipsInPort.put(ShipType.GUN_BOAT, 3);
                shipsInPort.put(ShipType.CRUISER, 2);
                shipsInPort.put(ShipType.BATTLESHIP, 2);
            }
            case 8 -> {
                shipsInPort.put(ShipType.GUN_BOAT, 3);
                shipsInPort.put(ShipType.CRUISER, 3);
                shipsInPort.put(ShipType.BATTLESHIP, 2);
            }
            case 9 -> {
                shipsInPort.put(ShipType.GUN_BOAT, 4);
                shipsInPort.put(ShipType.CRUISER, 3);
                shipsInPort.put(ShipType.BATTLESHIP, 1);
                shipsInPort.put(ShipType.CARRIER, 1);
            }
            case 10 -> {
                shipsInPort.put(ShipType.GUN_BOAT, 4);
                shipsInPort.put(ShipType.CRUISER, 3);
                shipsInPort.put(ShipType.BATTLESHIP, 2);
                shipsInPort.put(ShipType.CARRIER, 1);
            }
        }
    }

    public boolean allShipsAreAfloat(){
        HashMap<ShipType, Integer> shipsInPort = getCurrentPlayerShipsInPort();
        for (ShipType shipType : shipsInPort.keySet()){
            if (shipsInPort.get(shipType) != 0){
                return false;
            }
        }
        return true;
    }

    //GETTERS AND SETTERS
    public int getBoardSize() {
        return boardSize;
    }

    public Board getCurrentPlayerBoard() {
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1){
            return player1Board;
        } else {
            return player2Board;
        }
    }

    public Board getEnemyPlayerBoard() {
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1){
            return player2Board;
        } else {
            return player1Board;
        }
    }

    public HashMap<ShipType, Integer> getCurrentPlayerShipsInPort() {
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1){
            return player1shipsInPort;
        } else {
            return player2shipsInPort;
        }
    }

    public ArrayList<Ship> getCurrentPlayerShips(){
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1){
            return player1ships;
        } else {
            return player2ships;
        }
    }

    public ArrayList<Ship> getEnemyPlayerShips(){
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1){
            return player2ships;
        } else {
            return player1ships;
        }
    }

    public HashMap<String, ShipModule> getCurrentPlayerShipsModules(){
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1){
            return player1shipsModules;
        } else {
            return player2shipsModules;
        }
    }

    public HashMap<String, ShipModule> getEnemyPlayerShipsModules(){
        if (GameController.getInstance().getCurrentPlayer() == Player.PLAYER1){
            return player2shipsModules;
        } else {
            return player1shipsModules;
        }
    }
}
