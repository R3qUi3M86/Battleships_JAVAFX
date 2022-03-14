package com.battleships.view;
import com.battleships.controller.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class WindowPlacementController {

    @FXML
    public Text playerPhaseInfo;
    public Text portNoGB;
    public Text portNoCR;
    public Text portNoBB;
    public Text portNoCA;
    public ImageView portImgGB;
    public ImageView portImgCR;
    public ImageView portImgBB;
    public ImageView portImgCA;
    public Rectangle selectPortGB;
    public Rectangle selectPortCR;
    public Rectangle selectPortBB;
    public Rectangle selectPortCA;
    public Rectangle selectBoardGB;
    public Rectangle selectBoardCRh;
    public Rectangle selectBoardBBh;
    public Rectangle selectBoardCAh;
    public Rectangle selectBoardCRv;
    public Rectangle selectBoardBBv;
    public Rectangle selectBoardCAv;
    public Button doneBtn;


    public void initialize(){

    }
}
