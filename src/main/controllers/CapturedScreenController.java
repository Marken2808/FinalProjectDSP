package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.utils.OpenCV;
import main.utils.Utils;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.io.*;
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
    private JFXComboBox<?> comboPic;

    File imgs;

    @FXML
    void saveNew(ActionEvent event) throws IOException {
        ImageIO.write(SwingFXUtils.fromFXImage(this.captImg.getImage(), null), "jpg", new FileOutputStream(callCV.basePath +"images/dataset/"+fieldName.getText()+".jpg"));
        btnSubmit.getScene().getWindow().hide();

    }

    private OpenCV callCV = OpenCV.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(callCV.listRez.size()==1){
            this.comboPic.setPromptText("This Pic");
            comboPic.setDisable(true);
            this.imgs = new File(callCV.basePath +"images/dataset/new0.jpg");
        } else if(callCV.listRez.size()==0){
            this.imgs = null;
        } else{
            for(int i=0; i<callCV.listRez.size(); i++){
                System.out.println(i);
                this.imgs = new File(callCV.basePath +"images/dataset/new"+i+".jpg");
            }
        }
        try {
            this.captImg.setImage(new Image(new FileInputStream(this.imgs)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
