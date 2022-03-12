package com.battleships.view;

import com.battleships.controller.GameController;

public class WindowMenuController {
    public void onMainMenuBtnClick(String option){
        GameController.getInstance().initSelectedMenuOption(Integer.parseInt(option));
    }
}