package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CapturedScreenController {

    @FXML
    private ImageView captImg;

    public void sendImage(Image image) {
        //Display the message
        captImg.setImage(image);
    }
}
