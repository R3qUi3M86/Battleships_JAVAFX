package com.battleships.controller;

import com.battleships.model.*;
import com.battleships.model.ShotResult;

public final class GameController {
    private static GameController gameController;
    private GameMode gameMode = GameMode.PLAYER_VS_PLAYER;
    private ShootingMode shootingMode = ShootingMode.DYNAMIC;
    private int boardSize = 10;
    private int shotsPerPlayer = 3;
    private GameState gameState;
    private final ShipDeployer shipDeployer = new ShipDeployer();
    private Player currentPlayer;
    private ShipOrientation shipOrientation = ShipOrientation.HORIZONTAL;

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
            case PLAY -> startGame();
            case SETTINGS -> ViewController.getInstance().displaySettingsMenu();
            case QUIT -> System.exit(0);
        }
    }

    private void startGame(){
        gameState = new GameState(boardSize);
        determineCurrentPlayer();
        ViewController.getInstance().displayPlacementPhase();
    }

    public void startNextPlacementPhase(){
        switchPlayers();
        shipOrientation = ShipOrientation.HORIZONTAL;
        ViewController.getInstance().displayPlacementPhase();
    }

    private void determineCurrentPlayer(){
        if ((int)Math.floor(Math.random()*(2)+1) == 1){
            currentPlayer = Player.PLAYER1;
        } else {
            currentPlayer = Player.PLAYER2;
        }
        System.out.println(currentPlayer);
    }

    public void playRound(){
        switchPlayers();
        setShotsForCurrentPlayer();
        ViewController.getInstance().displayShootingPhase();
    }

    private void setShotsForCurrentPlayer(){
        if (shootingMode == ShootingMode.STATIC){
            gameState.setCurrentPlayerShots(shotsPerPlayer);
        } else {
            gameState.setCurrentPlayerShots(gameState.getLargestShipAfloatSize());
        }
    }

    private void playerIsHuman(){
    }

    public ShotResult takeShotInput(int[] coordinate) {
        Shooter shooter = new Shooter(gameState, coordinate);
        shooter.shoot();
        return shooter.getShotResult();
    }

    public void switchShipOrientation(){
        if (shipOrientation == ShipOrientation.HORIZONTAL){
            shipOrientation = ShipOrientation.VERTICAL;
        } else {
            shipOrientation = ShipOrientation.HORIZONTAL;
        }
    }

    private void switchPlayers(){
        if (currentPlayer == Player.PLAYER1){
            currentPlayer = Player.PLAYER2;
        } else {
            currentPlayer = Player.PLAYER1;
        }
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

    public GameState getGameState(){
        return gameState;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getEnemyPlayer() {
        if (currentPlayer == Player.PLAYER1){
            return Player.PLAYER2;
        } else {
            return Player.PLAYER1;
        }
    }

    public ShipOrientation getShipOrientation() {
        return shipOrientation;
    }

    public ShipDeployer getShipDeployer(){
        return shipDeployer;
    }
}
