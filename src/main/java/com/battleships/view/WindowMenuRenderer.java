package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import com.battleships.controller.GameMode;
import com.battleships.controller.ShootingMode;
import javafx.scene.image.Image ;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WindowMenuRenderer {
    Scene menuScene;
    WindowMenuController windowMenuController = new WindowMenuController();


    public void renderMainMenu(ArrayList<String> menuOptions) {
        Stage window = BattleshipsWindowed.getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(BattleshipsWindowed.class.getResource("menu.fxml"));
        try {
            this.menuScene = new Scene(fxmlLoader.load());
        } catch (IOException e){
            System.err.println("Could not load resource!");
        }

        VBox menuContainer = (VBox) menuScene.lookup("#menuContainer");
        menuContainer.setSpacing(50);

        int i = 1;
        for (String menuOption : menuOptions) {
            Button button = new Button(menuOption.substring(3));
            button.setId(String.valueOf(i));
            button.setPrefWidth(400);
            button.setPrefHeight(50);
            menuContainer.getChildren().add(button);
            button.setOnAction(e -> windowMenuController.onMainMenuBtnClick(button.getId()));
            i++;
        }
        window.setScene(menuScene);
    }

    public void renderSettingsMenu(GameMode gameMode, ShootingMode shootingMode, int boardSize, int shootsPerPlayer){

    }
}
