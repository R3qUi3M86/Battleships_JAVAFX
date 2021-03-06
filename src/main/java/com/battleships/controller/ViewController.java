package com.battleships.controller;

import com.battleships.model.MainMenu;
import com.battleships.model.ShotResult;
import com.battleships.view.WindowPlacementRenderer;
import com.battleships.view.WindowMenuRenderer;
import com.battleships.view.WindowRoundRenderer;

import java.util.ArrayList;

public final class ViewController {
    private static ViewController viewController;
    private final WindowMenuRenderer menuRenderer = new WindowMenuRenderer();
    private final WindowPlacementRenderer placementRenderer = new WindowPlacementRenderer();
    private final WindowRoundRenderer roundRenderer = new WindowRoundRenderer();

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

    public void displayShootingPhase(){
        roundRenderer.renderPlayerRoundPhase();
    }

    public void displayComputerShootingPhase() { roundRenderer.renderComputerRoundPhase();}

    public void showAIShotResult(ShotResult shotResult) {roundRenderer.showAIShotResult(shotResult);}

    public void setInfoElementsUI() {roundRenderer.setInfoElementsUI();}
}
