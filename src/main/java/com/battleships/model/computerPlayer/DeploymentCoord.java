package com.battleships.model.computerPlayer;

import com.battleships.model.ShipOrientation;

public record DeploymentCoord(int[] coordinates, ShipOrientation orientation) {

    public ShipOrientation getOrientation() {
        return orientation;
    }

    public int[] getCoordinates() {
        return coordinates;
    }
}
