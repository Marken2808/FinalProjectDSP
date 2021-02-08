package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class Preview implements Initializable {

    @FXML
    public AnchorPane previewPane;


    public static Preview instance;
    public Preview(){
        instance = this;
    }
    public static Preview getInstance() {
        if(instance == null){
            instance = new Preview();
        }
        return instance;
    }


    public void showAbsence(int on, int total){

        Label absence = new Label();
        if (on!=total) {

            absence.setFont(new Font(8));
            ImageView img = new ImageView(new Image("/resources/images/icon/user-x_red.png"));
            absence.setText((total-on)+"");
            img.setFitWidth(8);
            img.setFitHeight(8);
            absence.setGraphic(img);
            absence.setGraphicTextGap(3);
            previewPane.getChildren().add(absence);

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
