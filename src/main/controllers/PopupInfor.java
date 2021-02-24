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

public class PopupInfor implements Initializable {
    @FXML
    private JFXButton btnType;

    @FXML
    private Label labelTitle;

    @FXML
    private Label labelContent;

    @FXML
    private ImageView imgType;

    public static PopupInfor instance;
    public PopupInfor(){
        instance = this;
    }
    public static PopupInfor getInstance() {
        if(instance == null){
            instance = new PopupInfor();
        }
        return instance;
    }

    @FXML
    void clickBtn(MouseEvent event) {
        ScreenPrimary.dialog.close();
        String CaptureScreen    = "/main/views/PopupCaptured.fxml";
        ScreenPrimary.getInstance().displayPopup(CaptureScreen,true);
    }

    public void setDialog(String title, String content, String img, String type){
        labelTitle.setText(title);
        labelContent.setText(content);
        imgType.setImage(new Image(img));
        btnType.setText(type);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
