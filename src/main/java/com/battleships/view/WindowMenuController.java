package com.battleships.view;

import com.battleships.controller.GameController;
import com.battleships.controller.GameMode;
import com.battleships.controller.ShootingMode;
import com.battleships.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class WindowMenuController {

    @FXML
    public Button gameModeBtn;
    public Slider boardSizeSlider;
    public Button shootingModeBtn;
    public Slider shotsSlider;
    public HBox shotAmtGroup;
    public Button backBtn;
    public Text brdSizeNumber;
    public Text shotsAmtNumber;

    public void onMenuBtnClick(String option){
        GameController.getInstance().initSelectedMenuOption(Integer.parseInt(option));
    }

    private void onGameModeBtnClick(){
        if (GameController.getInstance().getGameMode() == GameMode.PLAYER_VS_PLAYER) {
            GameController.getInstance().setGameMode(GameMode.PLAYER_VS_COMPUTER);
        } else {
            GameController.getInstance().setGameMode(GameMode.PLAYER_VS_PLAYER);
        }
        setGameModeBtn(GameController.getInstance().getGameMode());
    }

    private void onShootModeBtnClick(){
        if (GameController.getInstance().getShootingMode() == ShootingMode.DYNAMIC) {
            GameController.getInstance().setShootingMode(ShootingMode.STATIC);
        } else {
            GameController.getInstance().setShootingMode(ShootingMode.DYNAMIC);
        }
        setShootModeBtn(GameController.getInstance().getShootingMode());
    }

    private void onBoardSizeSliderChange(){
        GameController.getInstance().setBoardSize((int) boardSizeSlider.getValue());
        setBoardSizeSlider(GameController.getInstance().getBoardSize());
    }

    private void onShotsSliderChange(){
        GameController.getInstance().setShotsPerPlayer((int) shotsSlider.getValue());
        setShotsSlider(GameController.getInstance().getShotsPerPlayer());
    }

    public void setGameModeBtn(GameMode mode){
        if (mode == GameMode.PLAYER_VS_PLAYER){
            gameModeBtn.setText("Player vs Player");
        } else {
            gameModeBtn.setText("Player vs Computer");
        }
    }

    public void setShootModeBtn(ShootingMode mode){
        if (mode == ShootingMode.DYNAMIC){
            shootingModeBtn.setText("Dynamic");
            shotAmtGroup.setStyle("visibility: hidden");
        } else {
            shootingModeBtn.setText("Static");
            shotAmtGroup.setStyle("visibility: visible");
        }
    }

    public void setBoardSizeSlider(int boardSize){
        boardSizeSlider.setValue(boardSize);
        brdSizeNumber.setText(String.valueOf(boardSize));
    }

    public void setShotsSlider(int shootsAmt){
        shotsSlider.setValue(shootsAmt);
        shotsAmtNumber.setText(String.valueOf(shootsAmt));
    }

    public void addActionListeners(){
        gameModeBtn.setOnAction(e -> onGameModeBtnClick());
        shootingModeBtn.setOnAction(e -> onShootModeBtnClick());
        boardSizeSlider.setOnMouseReleased(e -> onBoardSizeSliderChange());
        shotsSlider.setOnMouseReleased(e -> onShotsSliderChange());
        backBtn.setOnAction(e -> ViewController.getInstance().displayMainMenu());
    }
}