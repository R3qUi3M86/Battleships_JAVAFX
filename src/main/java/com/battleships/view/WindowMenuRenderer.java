package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import com.battleships.controller.GameMode;
import com.battleships.controller.ShootingMode;
import javafx.scene.Cursor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class WindowMenuRenderer {
    Scene menuScene;
    Stage window = BattleshipsWindowed.getWindow();
    WindowMenuController controller = new WindowMenuController();


    public void renderMainMenu(ArrayList<String> menuOptions) {
        FXMLLoader fxmlLoader = new FXMLLoader(BattleshipsWindowed.class.getResource("mainMenu.fxml"));
        loadScene(fxmlLoader);

        VBox menuContainer = (VBox) menuScene.lookup("#menuContainer");
        menuContainer.setSpacing(50);

        int i = 1;
        for (String menuOption : menuOptions) {
            Button button = new Button(menuOption);
            button.setId(String.valueOf(i));
            button.setPrefWidth(400);
            button.setPrefHeight(50);
            button.getStyleClass().add("dark-button");
            button.setStyle("-fx-font-size: " + 18 + "px;");
            button.setCursor(Cursor.HAND);
            menuContainer.getChildren().add(button);
            button.setOnAction(e -> controller.onMenuBtnClick(button.getId()));
            i++;
        }
        window.setScene(menuScene);
    }

    public void renderSettingsMenu(GameMode gameMode, ShootingMode shootingMode, int boardSize, int shootsPerPlayer){
        FXMLLoader fxmlLoader = new FXMLLoader(BattleshipsWindowed.class.getResource("settingsMenu.fxml"));
        loadScene(fxmlLoader);
        WindowMenuController controller = fxmlLoader.getController();
        controller.setGameModeBtn(gameMode);
        controller.setShootModeBtn(shootingMode);
        controller.setBoardSizeSlider(boardSize);
        controller.setShotsSlider(shootsPerPlayer);
        controller.addActionListeners();
        window.setScene(menuScene);
    }

    private void loadScene(FXMLLoader fxmlLoader){
        try {
            this.menuScene = new Scene(fxmlLoader.load());
        } catch (IOException e){
            System.err.println("Could not load resource!");
        }
    }
}
