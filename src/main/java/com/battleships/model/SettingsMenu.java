package com.battleships.model;

import java.util.ArrayList;

public class SettingsMenu {
    public enum MenuOptions {
        PLAY, SETTINGS, QUIT
    }

    private static final ArrayList<String> menuOptions = new ArrayList<>();

    public static void setMenuOptions(){
        for (MenuOptions menuOption : MenuOptions.values()){
            switch (menuOption) {
                case PLAY -> SettingsMenu.menuOptions.add("Play Battle");
                case SETTINGS -> SettingsMenu.menuOptions.add("Settings");
                case QUIT -> SettingsMenu.menuOptions.add("Quit");
            }
        }
    }

    public static ArrayList<String> getMenuOptions(){
        return SettingsMenu.menuOptions;
    }
}
