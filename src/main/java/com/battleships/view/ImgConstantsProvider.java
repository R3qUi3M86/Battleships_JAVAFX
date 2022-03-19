package com.battleships.view;

import com.battleships.controller.GameController;

public abstract class ImgConstantsProvider {

    public static int getSelectorBaseSize(){
        return switch (GameController.getInstance().getBoardSize()) {
            case 10 -> 52;
            case 9 -> 56;
            case 8 -> 62;
            case 7 -> 68;
            case 6 -> 77;
            case 5 -> 88;
            default -> 0;
        };
    }

    public static double getShipImgCellSize(){
        return switch (GameController.getInstance().getBoardSize()) {
            case 10 -> 50;
            case 9 -> 54.444;
            case 8 -> 60;
            case 7 -> 66.428;
            case 6 -> 75;
            case 5 -> 85.4;
            default -> 0;
        };
    }

    public static int getPlacementLayoutXStart(){
        return switch (GameController.getInstance().getBoardSize()) {
            case 10 -> 449;
            case 9 -> 454;
            case 8 -> 456;
            case 7 -> 466;
            case 6 -> 474;
            case 5 -> 484;
            default -> 0;
        };
    }

    public static int getShootingLayoutXStart(){
        return switch (GameController.getInstance().getBoardSize()) {
            case 10 -> 779;
            case 9 -> 784;
            case 8 -> 786;
            case 7 -> 796;
            case 6 -> 804;
            case 5 -> 814;
            default -> 0;
        };
    }

    public static int getLayoutYStart(){
        return switch (GameController.getInstance().getBoardSize()) {
            case 10 -> 149;
            case 9 -> 154;
            case 8 -> 157;
            case 7 -> 166;
            case 6 -> 174;
            case 5 -> 184;
            default -> 0;
        };
    }
}
