package com.battleships.model.computerPlayer;

import com.battleships.controller.GameController;
import com.battleships.model.*;

import java.util.*;

public class AILogicPipeline {
    public void deployAllShips(){
        HashMap<ShipType, Integer> AiShipsInPort = GameController.getInstance().getGameState().getCurrentPlayerShipsInPort();
        Board aiPlayerBoard = GameController.getInstance().getGameState().getCurrentPlayerBoard();
        for (int i = 0; i < AiShipsInPort.size(); i++){
            ShipType shipType = getLargestShipTypeInPort(AiShipsInPort);
            int shipTypeAmount = AiShipsInPort.get(shipType);
            for (int j = 0; j < shipTypeAmount; j++){
                DeploymentCoord deploymentCoord = getDeploymentField(shipType, aiPlayerBoard);
                setDeployedShipOrientation(deploymentCoord);
                GameController.getInstance().getShipDeployer().deployShip(shipType, deploymentCoord.getCoordinates());
            }
        }
    }

    private ShipType getLargestShipTypeInPort(HashMap<ShipType, Integer> AiShipsInPort){
        if (AiShipsInPort.containsKey(ShipType.CARRIER) && AiShipsInPort.get(ShipType.CARRIER) > 0){
            return ShipType.CARRIER;
        } else if (AiShipsInPort.containsKey(ShipType.BATTLESHIP) && AiShipsInPort.get(ShipType.BATTLESHIP) > 0){
            return ShipType.BATTLESHIP;
        } else if (AiShipsInPort.containsKey(ShipType.CRUISER) && AiShipsInPort.get(ShipType.CRUISER) > 0){
            return ShipType.CRUISER;
        } else {
            return ShipType.GUN_BOAT;
        }
    }

    private ShipType getLargestShipTypeInEnemyFleet(){
        ArrayList<Ship> enemyShips = GameController.getInstance().getGameState().getEnemyPlayerShips();
        int largestSize = GameController.getInstance().getGameState().getLargestShipAfloatSize(enemyShips);
        return switch (largestSize) {
            case 4 -> ShipType.CARRIER;
            case 3 -> ShipType.BATTLESHIP;
            case 2 -> ShipType.CRUISER;
            default -> ShipType.GUN_BOAT;
        };
    }

    private void setDeployedShipOrientation(DeploymentCoord deploymentCoord){
        if (GameController.getInstance().getShipOrientation() != deploymentCoord.getOrientation()){
            GameController.getInstance().switchShipOrientation();
        }
    }

    private DeploymentCoord getDeploymentField(ShipType shipType, Board board){
        ArrayList<DeploymentCoord> potDeployCoordinates = getPotentialDeploymentCoordinates(shipType, board);
        int possibilities = potDeployCoordinates.size();
        int choice = (int) Math.floor(Math.random()*possibilities);
        return potDeployCoordinates.get(choice);
    }

    private ArrayList<DeploymentCoord> getPotentialDeploymentCoordinates(ShipType shipType, Board board){
        ArrayList<DeploymentCoord> potDeployCoordinates = new ArrayList<>();
        int boardSize = GameController.getInstance().getBoardSize();
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                if (GameController.getInstance().getShipDeployer().shipDeploymentIsLegal(shipType, new int []{i,j}, board)){
                    potDeployCoordinates.add(new DeploymentCoord(new int []{i,j}, GameController.getInstance().getShipOrientation()));
                }
                GameController.getInstance().switchShipOrientation();
                if (GameController.getInstance().getShipDeployer().shipDeploymentIsLegal(shipType, new int []{i,j}, board)){
                    potDeployCoordinates.add(new DeploymentCoord(new int []{i,j}, GameController.getInstance().getShipOrientation()));
                }
            }
        }
        return potDeployCoordinates;
    }

    public int[] getShot(){
        int[] shootCoordinate;
        ArrayList<int[]> hitFields = getHitFields();
        if (hitFields.size() > 0){
            shootCoordinate = getShotToKill(hitFields);
        } else {
            shootCoordinate = getShotToHuntLargestShip();
        }
        return shootCoordinate;
    }

    private ArrayList<int[]> getHitFields(){
        ArrayList<int[]> hitFields = new ArrayList<>();
        Board enemyFOWBoard = GameController.getInstance().getGameState().getEnemyFogOfWarBoard();
        int boardSize = GameController.getInstance().getBoardSize();
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                if(enemyFOWBoard.getBoardField(new int[]{i, j}).getFieldContent() == FieldContent.HIT){
                    hitFields.add(new int[]{i, j});
                }
            }
        }
        return hitFields;
    }

    private int[] getShotToKill(ArrayList<int[]> hitFields){
        ArrayList<int[]> potentialShots;
        ArrayList<int[]> potentialPromisingShots = new ArrayList<>();
        Board enemyFOWBoard = GameController.getInstance().getGameState().getEnemyFogOfWarBoard();
        if(hitFields.size() == 1){
            potentialShots = new ArrayList<>(GameController.getInstance().getShipDeployer().getAdjacentFields(hitFields.get(0)));
        } else {
            if (hitFields.get(0)[1] == hitFields.get(1)[1]){
                potentialShots = getFarFieldsCoord(hitFields, ShipOrientation.HORIZONTAL);
            } else {
                potentialShots = getFarFieldsCoord(hitFields, ShipOrientation.VERTICAL);
            }
        }
        for (int[] potentialShotField : potentialShots){
            if (enemyFOWBoard.getBoardField(potentialShotField).getFieldContent() == FieldContent.WATER){
                potentialPromisingShots.add(potentialShotField);
            }
        }
        return potentialPromisingShots.get((int) (Math.random() * potentialPromisingShots.size()));
    }

    private ArrayList<int[]> getFarFieldsCoord(ArrayList<int[]> hitFields, ShipOrientation orientation){
        Board fowBoard = GameController.getInstance().getGameState().getEnemyFogOfWarBoard();
        int boardSize = GameController.getInstance().getBoardSize();
        int[] leftMostField;
        int[] rightMostField;
        int rowColSwitch;
        if (orientation == ShipOrientation.HORIZONTAL) {
            rowColSwitch = 0;
            leftMostField = new int[]{50, 0};
            rightMostField = new int[]{-1, 0};
        } else {
            rowColSwitch = 1;
            leftMostField = new int[]{0, 50};
            rightMostField = new int[]{0, -1};
        }
        for (int[] field : hitFields){
            if(field[rowColSwitch] < leftMostField[rowColSwitch]){
                leftMostField = field;
            }
            if(field[rowColSwitch] > rightMostField[rowColSwitch]){
                rightMostField = field;
            }
        }
        leftMostField[rowColSwitch] = leftMostField[rowColSwitch] - 1;
        rightMostField[rowColSwitch] = rightMostField[rowColSwitch] + 1;

        ArrayList<int[]> potShotFields = new ArrayList<>();
        if (leftMostField[rowColSwitch] > 0 && fowBoard.getBoardField(leftMostField).getFieldContent() == FieldContent.WATER){
            potShotFields.add(leftMostField);
        }
        if (rightMostField[rowColSwitch] < boardSize && fowBoard.getBoardField(rightMostField).getFieldContent() == FieldContent.WATER){
            potShotFields.add(rightMostField);
        }
        return potShotFields;
    }

    private int[] getShotToHuntLargestShip(){
        ShipType largestEnemyShipType = getLargestShipTypeInEnemyFleet();
        Board enemyFOWBoard = GameController.getInstance().getGameState().getEnemyFogOfWarBoard();
        int boardSize = GameController.getInstance().getBoardSize();
        Set<int[]> potentialShots = new HashSet<>();
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++) {
                if(GameController.getInstance().getShipDeployer().shipDeploymentIsLegal(largestEnemyShipType, new int[]{i, j}, enemyFOWBoard)){
                    potentialShots.add(new int[]{i,j});
                }
                GameController.getInstance().switchShipOrientation();
                if(GameController.getInstance().getShipDeployer().shipDeploymentIsLegal(largestEnemyShipType, new int[]{i, j}, enemyFOWBoard)){
                    potentialShots.add(new int[]{i,j});
                }
            }
        }
        ArrayList<int[]> potentialShotCoordinates = new ArrayList<>(potentialShots);
        return potentialShotCoordinates.get((int) Math.floor(Math.random()*potentialShotCoordinates.size()));
    }
}
