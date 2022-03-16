package com.battleships.model;

import java.util.HashMap;

public class Ship {
    ShipType shipType;
    ShipOrientation shipOrientation;
    HashMap<int[], ShipModule> shipModules = new HashMap<>();

    public Ship(ShipType shipType, int[] origin, ShipOrientation orientation){
        this.shipType = shipType;
        this.shipOrientation = orientation;
        switch (shipType){
            case GUN_BOAT -> createGunBoat(origin);
            case CRUISER -> createCruiser(origin, orientation);
            case BATTLESHIP -> createBattleship(origin, orientation);
            case CARRIER -> createCarrier(origin, orientation);
        }
    }

    public ShipModule getShipModule(int x, int y) {
        int[] coordinates = {x, y};
        return shipModules.get(coordinates);
    }

    private void createGunBoat(int[] origin){
        shipModules.put(origin, new ShipModule(origin[0], origin[1], this, true));
    }

    private void createCruiser(int[] origin, ShipOrientation orientation){
        shipModules.put(origin, new ShipModule(origin[0], origin[1], this, true));
        if (orientation == ShipOrientation.HORIZONTAL){
            shipModules.put(new int[]{origin[0], origin[1]+1}, new ShipModule(origin[0], origin[1]+1, this, false));
        } else {
            shipModules.put(new int[]{origin[0]+1, origin[1]}, new ShipModule(origin[0]+1, origin[1], this, false));
        }
    }

    private void createBattleship(int[] origin, ShipOrientation orientation){
        shipModules.put(origin, new ShipModule(origin[0], origin[1], this, true));
        if (orientation == ShipOrientation.HORIZONTAL){
            shipModules.put(new int[]{origin[0], origin[1]+1}, new ShipModule(origin[0], origin[1]+1, this, false));
            shipModules.put(new int[]{origin[0], origin[1]+2}, new ShipModule(origin[0], origin[1]+2, this, false));
        } else {
            shipModules.put(new int[]{origin[0]+1, origin[1]}, new ShipModule(origin[0]+1, origin[1], this, false));
            shipModules.put(new int[]{origin[0]+2, origin[1]}, new ShipModule(origin[0]+2, origin[1], this, false));
        }
    }

    private void createCarrier(int[] origin, ShipOrientation orientation){
        shipModules.put(origin, new ShipModule(origin[0], origin[1], this, true));
        if (orientation == ShipOrientation.HORIZONTAL){
            shipModules.put(new int[]{origin[0], origin[1]+1}, new ShipModule(origin[0], origin[1]+1, this, false));
            shipModules.put(new int[]{origin[0], origin[1]+2}, new ShipModule(origin[0], origin[1]+2, this, false));
            shipModules.put(new int[]{origin[0], origin[1]+3}, new ShipModule(origin[0], origin[1]+3, this, false));
        } else {
            shipModules.put(new int[]{origin[0]+1, origin[1]}, new ShipModule(origin[0]+1, origin[1], this, false));
            shipModules.put(new int[]{origin[0]+2, origin[1]}, new ShipModule(origin[0]+2, origin[1], this, false));
            shipModules.put(new int[]{origin[0]+3, origin[1]}, new ShipModule(origin[0]+3, origin[1], this, false));
        }
    }

    //GETTERS AND SETTERS
    public HashMap<int[], ShipModule> getShipModules() {
        return shipModules;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public ShipOrientation getShipOrientation() {
        return shipOrientation;
    }
}
