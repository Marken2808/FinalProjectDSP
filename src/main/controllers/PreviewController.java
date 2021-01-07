package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

    public void checkAttend(int on, int total){

        ImageView img = new ImageView(new Image("/resources/images/icon/users.png"));
        img.setFitWidth(10);
        img.setFitHeight(10);
        Label student = new Label();

        student.setGraphic(img);
        student.setGraphicTextGap(15);

        if (on==total) {
            student.setText("enough");
        } else {
            student.setText("lack");
        }

        previewPane.getChildren().addAll(student);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
