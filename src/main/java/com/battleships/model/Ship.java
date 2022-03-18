package com.battleships.model;

import java.util.Arrays;
import java.util.HashMap;

public class Ship {
    int shipSize;
    ShipType shipType;
    ShipOrientation shipOrientation;
    HashMap<String, ShipModule> shipModules = new HashMap<>();

    public Ship(ShipType shipType, int[] origin, ShipOrientation orientation){
        this.shipType = shipType;
        this.shipOrientation = orientation;
        switch (shipType){
            case GUN_BOAT -> {createGunBoat(origin); shipSize = 1;}
            case CRUISER -> {createCruiser(origin, orientation); shipSize = 2;}
            case BATTLESHIP -> {createBattleship(origin, orientation); shipSize = 3;}
            case CARRIER -> {createCarrier(origin, orientation); shipSize = 4;}
        }
    }

    public ShipModule getShipModule(int x, int y) {
        int[] coordinates = {x, y};
        return shipModules.get(coordinates);
    }

    private void createGunBoat(int[] origin){
        shipModules.put(Arrays.toString(origin), new ShipModule(origin[0], origin[1], this, true));
    }

    private void createCruiser(int[] origin, ShipOrientation orientation){
        shipModules.put(Arrays.toString(origin), new ShipModule(origin[0], origin[1], this, true));
        if (orientation == ShipOrientation.HORIZONTAL){
            shipModules.put(Arrays.toString(new int[]{origin[0], origin[1]+1}), new ShipModule(origin[0], origin[1]+1, this, false));
        } else {
            shipModules.put(Arrays.toString(new int[]{origin[0]+1, origin[1]}), new ShipModule(origin[0]+1, origin[1], this, false));
        }
    }

    private void createBattleship(int[] origin, ShipOrientation orientation){
        shipModules.put(Arrays.toString(origin), new ShipModule(origin[0], origin[1], this, true));
        if (orientation == ShipOrientation.HORIZONTAL){
            shipModules.put(Arrays.toString(new int[]{origin[0], origin[1]+1}), new ShipModule(origin[0], origin[1]+1, this, false));
            shipModules.put(Arrays.toString(new int[]{origin[0], origin[1]+2}), new ShipModule(origin[0], origin[1]+2, this, false));
        } else {
            shipModules.put(Arrays.toString(new int[]{origin[0]+1, origin[1]}), new ShipModule(origin[0]+1, origin[1], this, false));
            shipModules.put(Arrays.toString(new int[]{origin[0]+2, origin[1]}), new ShipModule(origin[0]+2, origin[1], this, false));
        }
    }

    private void createCarrier(int[] origin, ShipOrientation orientation){
        shipModules.put(Arrays.toString(origin), new ShipModule(origin[0], origin[1], this, true));
        if (orientation == ShipOrientation.HORIZONTAL){
            shipModules.put(Arrays.toString(new int[]{origin[0], origin[1]+1}), new ShipModule(origin[0], origin[1]+1, this, false));
            shipModules.put(Arrays.toString(new int[]{origin[0], origin[1]+2}), new ShipModule(origin[0], origin[1]+2, this, false));
            shipModules.put(Arrays.toString(new int[]{origin[0], origin[1]+3}), new ShipModule(origin[0], origin[1]+3, this, false));
        } else {
            shipModules.put(Arrays.toString(new int[]{origin[0]+1, origin[1]}), new ShipModule(origin[0]+1, origin[1], this, false));
            shipModules.put(Arrays.toString(new int[]{origin[0]+2, origin[1]}), new ShipModule(origin[0]+2, origin[1], this, false));
            shipModules.put(Arrays.toString(new int[]{origin[0]+3, origin[1]}), new ShipModule(origin[0]+3, origin[1], this, false));
        }
    }

    //GETTERS AND SETTERS
    public HashMap<String, ShipModule> getShipModules() {
        return shipModules;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public ShipOrientation getShipOrientation() {
        return shipOrientation;
    }

    public int getShipSize(){
        return shipSize;
    }
}
