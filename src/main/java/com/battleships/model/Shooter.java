package com.battleships.model;

import java.util.Arrays;
import java.util.HashMap;

public class Shooter {
    private ShotResult shootResult = ShotResult.MISSED;
    private GameState gameState;
    private int[] coordinate;

    public Shooter(GameState gameState, int[] coordinate){
        this.gameState = gameState;
        this.coordinate = coordinate;
    }

    public void shoot(){
        Board enemyBoard = gameState.getEnemyPlayerBoard();
        Board FOWBoard = gameState.getEnemyFogOfWarBoard();
        FieldContent targetFieldContent = enemyBoard.getBoardField(coordinate).getFieldContent();
        decrementCurrentPlayerShots();
        if(FOWBoard.getBoardField(coordinate).getFieldContent() != FieldContent.WATER){
            setRepeatingShot();
        } else if (targetFieldContent == FieldContent.WATER){
            setMissedShot(FOWBoard.getBoardField(coordinate));
        } else if (targetFieldContent == FieldContent.MODULE){
            setHitShot(FOWBoard.getBoardField(coordinate));
            damageModule();
        }
    }

    private void decrementCurrentPlayerShots(){
        gameState.setCurrentPlayerShots(gameState.getCurrentPlayerShots()-1);
    }

    private void setRepeatingShot(){
        shootResult = ShotResult.REPEATING;
    }

    private void setMissedShot(BoardField FOWBoardField){
        FOWBoardField.setFieldContent(FieldContent.MISS);
        shootResult = ShotResult.MISSED;
    }

    private void setHitShot(BoardField FOWBoardField){
        FOWBoardField.setFieldContent(FieldContent.HIT);
        shootResult = ShotResult.HIT_SOMETHING;
    }

    private void setSunkShot(ShipType shipType){
        switch (shipType){
            case GUN_BOAT -> shootResult = ShotResult.SUNK_GB;
            case CRUISER -> shootResult = ShotResult.SUNK_CR;
            case BATTLESHIP -> shootResult = ShotResult.SUNK_BB;
            case CARRIER -> shootResult = ShotResult.SUNK_CA;
        }
    }

    private void damageModule(){
        ShipModule module = gameState.getEnemyPlayerShipsModules().get(Arrays.toString(coordinate));
        Ship ship = module.getShip();
        module.setModuleStatus(ModuleStatus.DAMAGED);
        HashMap<String, ShipModule> shipModules = ship.getShipModules();
        for (String key : shipModules.keySet()){
            if(shipModules.get(key).getModuleStatus() == ModuleStatus.HEALTHY){
                return;
            }
        }
        setSunkShot(ship.getShipType());
        sinkShip(ship);
    }

    private void sinkShip(Ship ship){
        HashMap<String, ShipModule> shipModules = ship.getShipModules();
        Board FOWBoard = gameState.getEnemyFogOfWarBoard();
        for (String key : shipModules.keySet()){
            int[] modCoordinate = shipModules.get(key).getModuleCoord();
            FOWBoard.getBoardField(modCoordinate).setFieldContent(FieldContent.SUNK);
        }
        gameState.getEnemyPlayerShips().remove(ship);
    }

    //GETTERS AND SETTERS
    public ShotResult getShotResult(){
        return shootResult;
    }
}
