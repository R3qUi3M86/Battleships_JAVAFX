package com.battleships.model;

import java.util.ArrayList;

public class MainMenu {
    public enum MenuOptions {
        PLAY, SETTINGS, QUIT
    }

    private static final ArrayList<String> menuOptions = new ArrayList<>();

    public static void setMenuOptions(){
        for (MenuOptions menuOption : MenuOptions.values()){
            switch (menuOption) {
                case PLAY -> MainMenu.menuOptions.add("Play Battle");
                case SETTINGS -> MainMenu.menuOptions.add("Settings");
                case QUIT -> MainMenu.menuOptions.add("Quit");
            }
        }
    }

    public static ArrayList<String> getMenuOptions(){
        return MainMenu.menuOptions;
    }
}
