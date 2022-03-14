package com.battleships.controller;

import com.battleships.model.MainMenu;
import com.battleships.view.WindowPlacementRenderer;
import com.battleships.view.WindowMenuRenderer;

import java.util.ArrayList;

public final class ViewController {
    private static ViewController viewController;
    private final WindowMenuRenderer menuRenderer = new WindowMenuRenderer();
    private final WindowPlacementRenderer placementRenderer = new WindowPlacementRenderer();

    private ViewController(){}

    public static ViewController getInstance(){
        if (viewController == null){
            viewController = new ViewController();
        }
        return viewController;
    }

    public void displayMainMenu(){
        ArrayList<String> menuOptions = MainMenu.getMenuOptions();
        menuRenderer.renderMainMenu(menuOptions);
    }

    public void displaySettingsMenu(){
        GameMode gameMode = GameController.getInstance().getGameMode();
        ShootingMode shootingMode = GameController.getInstance().getShootingMode();
        int boardSize = GameController.getInstance().getBoardSize();
        int shootsPerPlayer = GameController.getInstance().getShotsPerPlayer();
        menuRenderer.renderSettingsMenu(gameMode, shootingMode, boardSize, shootsPerPlayer);
    }

    public void displayPlacementPhase(){
        placementRenderer.renderPlacementPhase();
    }

    public void displayGameState(){

    }
}
