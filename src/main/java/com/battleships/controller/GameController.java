package com.battleships.controller;

import com.battleships.model.*;

public final class GameController {
    private static GameController gameController;
    private GameMode gameMode = GameMode.PLAYER_VS_PLAYER;
    private ShootingMode shootingMode = ShootingMode.DYNAMIC;
    private int boardSize = 5;
    private int shotsPerPlayer = 3;

    private GameController(){}

    public static GameController getInstance(){
        if (gameController == null){
            gameController = new GameController();
        }
        return gameController;
    }

    public void initMainMenuLabels(){
        MainMenu.setMenuOptions();
    }

    public void initSelectedMenuOption(int number){
        MainMenu.MenuOptions menuOption = MainMenu.MenuOptions.values()[number-1];
        switch (menuOption){
            case PLAY -> initCheckersGame();
            case SETTINGS -> ViewController.getInstance().displaySettingsMenu();
            case QUIT -> System.exit(0);
        }
    }

    private void initCheckersGame(){
        // prepare game
        startGame();
    }

    private void startGame() {
        //start game
    }

    private void play(){
        ViewController.getInstance().displayGameState();
        //ask player to move
    }

    private void askCurrentPlayerToMove(){
    }

    private void playerIsHuman(){
    }

    public void takeGameInput() {
    }

    public void playMove() {
    }

    //GETTERS AND SETTERS
    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public ShootingMode getShootingMode() {
        return shootingMode;
    }

    public void setShootingMode(ShootingMode shootingMode) {
        this.shootingMode = shootingMode;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getShotsPerPlayer() {
        return shotsPerPlayer;
    }

    public void setShotsPerPlayer(int shotsPerPlayer) {
        this.shotsPerPlayer = shotsPerPlayer;
    }
}
