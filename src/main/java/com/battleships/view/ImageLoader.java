package com.battleships.view;

import com.battleships.BattleshipsWindowed;
import javafx.scene.image.Image;

import java.net.URL;

public class ImageLoader {
    public static Image loadImage(String fileName){
            URL imageURL = BattleshipsWindowed.class.getResource("pictures/"+fileName);
            assert imageURL != null;
            return new Image(imageURL.toExternalForm());
    }
}
