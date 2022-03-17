package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import com.battleships.controller.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;

public class WindowRoundController {
    private int LAYOUT_X_START;
    private int LAYOUT_Y_START;
    private int BASE_SIZE;

    @FXML
    public Text playerPhaseInfo;
    public Text playerTurnCornerInfo;
    public Text shotsInfo;
    public Text shotPrompter;
    public ImageView seaImgView;
    public Rectangle boardFldSelector;
    public Button endTurnBtn;
    public GridPane enemyShipGrid;
    public GridPane enemyBoardGrid;
    public GridPane playerShipGrid;
    public Region boardRegion;



    public void initialize(){
        setSelectorConstants();
        addBoardGridUIActionListener();
        endTurnBtn.setVisible(false);
        endTurnBtn.setOnAction(e -> endTurnHandler());
        boardRegion.setOnMouseEntered(e -> hideBoardFieldSelector());
    }

    private void setSelectorConstants(){
        LAYOUT_X_START = 779;
        LAYOUT_Y_START = 149;
        BASE_SIZE = 52;
    }

    private void addBoardGridUIActionListener(){
        for (int i = 0; i < GameController.getInstance().getBoardSize(); i++){
            for (int j = 0; j < GameController.getInstance().getBoardSize(); j++){
                Region region = new Region();
                enemyShipGrid.add(region, i, j);
//                region.setOnMouseEntered(this::onGridMouseEnter);
//                region.setOnMouseClicked(this::onGridMouseClick);
            }
        }
    }

    public void initRound(){

    }
}
