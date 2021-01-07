package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import main.utils.OpenCV;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PreviewController implements Initializable {

    @FXML
    private AnchorPane previewPane;

    public static PreviewController instance;
    public PreviewController(){
        instance = this;
    }
    public static PreviewController getInstance() {
        if(instance == null){
            instance = new PreviewController();
        }
        return instance;
    }

    public void showAbsence(int on, int total){



        Label absence = new Label();
        absence.setFont(new Font(14));

        if (on!=total) {
            ImageView img = new ImageView(new Image("/resources/images/icon/user-x_red.png"));
            absence.setText((total-on)+"");
            img.setFitWidth(15);
            img.setFitHeight(15);
            absence.setGraphic(img);
            absence.setGraphicTextGap(3);
        }

        previewPane.getChildren().addAll(absence);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
