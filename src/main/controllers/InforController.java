package main.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class InforController implements Initializable {
    @FXML
    private JFXButton btnCancel;

    @FXML
    private Label labelTitle;

    @FXML
    private ImageView imgType;

    public static InforController instance;
    public InforController(){
        instance = this;
    }
    public static InforController getInstance() {
        if(instance == null){
            instance = new InforController();
        }
        return instance;
    }

    @FXML
    void clickCancel(MouseEvent event) {
        MainController.dialog.close();
        String CaptureScreen    = "/main/views/CapturedScreen.fxml";
        MainController.getInstance().popUp(CaptureScreen,true);
    }

    public void setDialog(String content, String img){
        labelTitle.setText(content);
        imgType.setImage(new Image(img));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
