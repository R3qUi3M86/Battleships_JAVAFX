package com.battleships.util;

import java.util.Objects;

public abstract class Utilities {
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static int[] toArray(String string){
        string = string.replace("[", "");
        string = string.replace("]", "");
        string = string.replace(" ", "");
        String[] stringArr = string.split(",");
        return new int[]{Integer.parseInt(stringArr[0]), Integer.parseInt(stringArr[1])};
    }
}
