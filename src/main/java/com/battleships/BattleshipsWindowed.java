package com.battleships;

import com.battleships.controller.GameController;
import com.battleships.controller.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class BattleshipsWindowed extends Application {
    private static Stage window;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        window.setTitle("Battleships");
        window.setResizable(false);

        final ViewController viewController = ViewController.getInstance();
        final GameController gameController = GameController.getInstance();

        gameController.initMainMenuLabels();
        viewController.displayMainMenu();
        window.show();
    }

    public static Stage getWindow() {
        return window;
    }
}