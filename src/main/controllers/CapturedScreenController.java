package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.utils.OpenCV;
import main.utils.Utils;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CapturedScreenController implements Initializable {

    @FXML
    private ImageView captImg;

    @FXML
    private JFXTextField fieldName;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXComboBox<?> comboPic;

    @FXML
    private JFXComboBox<Integer> boxID;

    @FXML
    private JFXComboBox<Integer> boxSet;

    @FXML
    private HBox hboxInfor;

    @FXML
    private Label textInfor;

    @FXML
    private ImageView picInfor;

    File imgs;
    OpenCV callCV = OpenCV.getInstance();
    StringBuilder sb = new StringBuilder();

    @FXML
    void submitNew(ActionEvent event) throws IOException, InterruptedException {
//        ImageIO.write(
//            SwingFXUtils.fromFXImage(this.captImg.getImage(), null),
//            "jpg",
//            new FileOutputStream(
//                callCV.basePath +"images/dataset/"+ boxID.getValue() +"-"+fieldName.getText()+"_"+boxSet.getValue()+".jpg"
//            )
//        );
        isFulfill();
        Timeline timeline = new Timeline(
                new KeyFrame( Duration.millis(200),
                ae -> {if (isFulfill()) btnSubmit.getScene().getWindow().hide();}));
        timeline.play();

    }

    public boolean isEmpty(Object obj){

//        if empty is false, else true;
        if (obj instanceof JFXComboBox){
            return (((JFXComboBox) obj).getValue().equals("")? true: false);
        } else if( obj instanceof JFXTextField){
            return (((JFXTextField) obj).getText().equals("")? true: false);
        }
        return true;
    }

    public void lineInfor(Label text, ImageView pic, String type){
        hboxInfor.setVisible(true);
        if (type.equals("Success")){
            text.setText("Perfect, done");
            text.setStyle("-fx-text-fill: #06dd06");
            pic.setImage(new Image("/resources/images/icon/check-circle_green.png"));
        } else if (type.equals("Fail")){
            text.setText("Please, provide all variable above");
            text.setStyle("-fx-text-fill: #f90606");
            pic.setImage(new Image("/resources/images/icon/alert-circle_red.png"));
        }
    }
    public boolean isFulfill(){

        if((!isEmpty(fieldName) && !isEmpty(boxID) && !isEmpty(boxSet))){    // true: not empty
//            System.out.println("all filled: " + !isEmpty(boxID)+!isEmpty(fieldName) + !isEmpty(boxSet));
            lineInfor(textInfor,picInfor,"Success");
            return true;
        } else {
//            System.out.println("empty: " + !isEmpty(boxID)+!isEmpty(fieldName) + !isEmpty(boxSet));
            lineInfor(textInfor,picInfor,"Fail");
            return false;
        }
    }

    @FXML
    void clearDataset(ActionEvent event) {
        sb.setLength(0);
        clear(boxID);
        clear(fieldName);
        clear(boxSet);
        isFulfill();
    }

    public void clear(Object obj){
        if (obj instanceof JFXComboBox){
            ((JFXComboBox) obj).setValue("");
        } else if( obj instanceof JFXTextField){
            ((JFXTextField) obj).setText("");
        }
    }

    @FXML
    void chooseID(KeyEvent keyEvent) {

        sb.append(keyEvent.getText());
        sb.insert(0,0);

        if(keyEvent.getCode().equals(KeyCode.BACK_SPACE) || keyEvent.getCode().equals(KeyCode.DELETE)){
            sb.deleteCharAt(sb.length()-1);
        }
//        System.out.println(sb);
        fieldName.setText(getListName(Integer.parseInt(String.valueOf(sb))));
        boxSet.setItems(FXCollections.observableList(getListSet(Integer.parseInt(String.valueOf(sb)))));
        sb.deleteCharAt(0);
    }

    public ArrayList<Integer> getListSet(int id){
        ArrayList<Integer> listSet = new ArrayList<>();
//        System.out.println("check: "+callCV.ImageFile().length);
        for (int i = 0; i < callCV.ImageFile().length; i++) {
            if(callCV.namesList[i][0].equals(id)) {
                listSet.add((Integer) callCV.namesList[i][2]);
            }
        }
        return listSet;
    }

    public String getListName(int id){
        for (int i = 0; i < callCV.ImageFile().length; i++) {
            if(callCV.namesList[i][0].equals(id)) {
                return (String) callCV.namesList[i][1];
            }
        }
        return null;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Integer> observableList = FXCollections.observableList(getListSet(callCV.predictionID));
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
//                System.out.println(i);
                imgs = new File(callCV.basePath +"images/test/0-new_"+i+".jpg");
            }
        }
        try {
            captImg.setImage(new Image(new FileInputStream(imgs)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        isFulfill();
        hboxInfor.setVisible(false);

    }
}
