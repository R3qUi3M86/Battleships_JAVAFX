package com.battleships.model;

public class ShipModule extends BoardField{
    private final int[] coordinates = new int[2];
    private final Ship ship;
    ModuleStatus moduleStatus = ModuleStatus.HEALTHY;

    public ShipModule(int x, int y, Ship ship){
        this.ship = ship;
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }

    public int[] getModuleCoord() {
        return coordinates;
    }

    public void setModuleCoord(int x, int y) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }

    public Ship getShip() {
        return ship;
    }
}
