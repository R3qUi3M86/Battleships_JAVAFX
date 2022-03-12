package com.battleships.view;
import com.battleships.controller.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class WindowGameController {
    @FXML
    public Button backToMenu;
    @FXML
    public Button tieButton;
    @FXML
    private Label infoPanel;

    private double[] dragDelta;
    private String startFieldNo;
    private String endFieldNo = "";

    @FXML
    public void endGameAsTie(ActionEvent actionEvent) {
        GameController.getInstance().takeGameInput();
    }

    public void setInfoPanelText(String text){
        infoPanel.setText(text);
    }

    public void makeDraggable(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            dragDelta = new double[]{mouseEvent.getSceneX(), mouseEvent.getSceneY()};
            startFieldNo = node.getId();
            node.toFront();
            node.setMouseTransparent(true);
            mouseEvent.consume();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setTranslateX(mouseEvent.getSceneX() - this.dragDelta[0]);
            node.setTranslateY(mouseEvent.getSceneY() - this.dragDelta[1]);
            mouseEvent.consume();
        });

        node.setOnDragDetected(evt -> {
            evt.consume();
            node.startFullDrag();
        });

        node.setOnMouseReleased(event -> {
            node.setMouseTransparent(false);
            event.consume();
            GameController.getInstance().takeGameInput();
        });
    }

    public void addDropzoneEnterListener(Node node){
        node.setOnMouseDragEntered(event -> {
            endFieldNo = node.getId();
        });
    }

    public void setPreviousMove() {

    }

    public void initialize(){
        DropShadow ds = new DropShadow();
        ds.setOffsetY(4.0f);
        ds.setColor(Color.color(0.1f, 0.1f, 0.1f));
        infoPanel.setEffect(ds);

        backToMenu.setDisable(true);
        backToMenu.setVisible(false);
    }
}
