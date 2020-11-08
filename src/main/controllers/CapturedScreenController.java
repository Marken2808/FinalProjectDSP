package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.utils.OpenCV;
import main.utils.Utils;
import org.opencv.core.Mat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class CapturedScreenController implements Initializable {

    @FXML
    private ImageView captImg;

    @FXML
    private JFXTextField fieldName;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    void saveNew(ActionEvent event) {
        System.out.println(fieldName.getText());
    }

//    private OpenCV callCV = OpenCV.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        captImg.setImage(new Image(getClass().getClassLoader().getResourceAsStream("/resources/images/dataset/new.jpg")));
    }
}
