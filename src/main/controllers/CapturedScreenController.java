package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.UUID;

public class CapturedScreenController implements Initializable {

    @FXML
    private ImageView captImg;

    @FXML
    private JFXTextField fieldName;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXComboBox<?> comboPic;

    @FXML
    private JFXComboBox<Integer> boxID;

    @FXML
    private JFXComboBox<Integer> boxSet;

    File imgs;

    @FXML
    void saveNew(ActionEvent event) throws IOException {
        ImageIO.write(
            SwingFXUtils.fromFXImage(this.captImg.getImage(), null),
            "jpg",
            new FileOutputStream(
                    callCV.basePath +"images/dataset/"+ boxID.getValue() +"-"+fieldName.getText()+"_"+boxSet.getValue()+".jpg"
            )
        );
        btnSubmit.getScene().getWindow().hide();


    }

    OpenCV callCV = OpenCV.getInstance();
    ObservableList<Integer> observableList = FXCollections.observableList(callCV.getListSet());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        if(callCV.listRez.size()==1){
            comboPic.setPromptText("This Image");
            comboPic.setDisable(true);
            boxID.setValue(callCV.predictionID);
            fieldName.setText(String.valueOf(callCV.namesMap.get(callCV.predictionID)));

            boxSet.setItems(observableList);
            boxSet.setValue(0);
            imgs = new File(callCV.basePath +"images/test/0-new_0.jpg");
        } else if(callCV.listRez.size()==0){
            imgs = null;
        } else{
            for(int i=0; i<callCV.listRez.size(); i++){
                System.out.println(i);
                imgs = new File(callCV.basePath +"images/test/0-new_"+i+".jpg");
            }
        }
        try {
            captImg.setImage(new Image(new FileInputStream(imgs)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
