package main.controllers;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.utils.OpenCV;
import main.utils.UtilsOCV;
import org.opencv.core.*;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Primary implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public StackPane stackPane;

    @FXML
    public StackPane header;

    @FXML
    private JFXButton btnRestore;

    @FXML
    private JFXButton btnMinimise;

    @FXML
    private JFXButton btnClose;

    @FXML
    private Label labelTitle;

    @FXML
    private JFXDrawer drawerPane;

    public static JFXDialog dialog;

    private String CaptureScreen    = "/main/views/CapturedScreen.fxml";
    private String SignInScreen     = "/main/views/SignInScreen.fxml";
    private String SignUpScreen     = "/main/views/SignUpScreen.fxml";
    private String DrawerScreen     = "/main/views/DrawerMenu.fxml";
    private String DashboardScreen  = "/main/views/DashboardScreen.fxml";
    private String CameraScreen     = "/main/views/ScreenCamera.fxml";
//----------------------------instance--------------------

    public static Primary instance;
    public Primary(){
        instance = this;
    }
    public static Primary getInstance() {
        if(instance == null){
            instance = new Primary();
        }
        return instance;
    }
//---------------------------------------------------------

    public void popUp(String screen, boolean canClose){
        try {
            FXMLLoader loader = new FXMLLoader(Primary.class.getResource(screen));
            Parent Root = loader.load();
            JFXDialogLayout content = new JFXDialogLayout();
            content.setBody(Root);
            dialog = new JFXDialog(stackPane, content , JFXDialog.DialogTransition.CENTER);
            dialog.setOverlayClose(canClose);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void isMenuClicked(MouseEvent event) throws IOException {
        if(Camera.getInstance().isCameraActive()) {     // grant turn off to use drawer
            displayDrawer(0.0, 30.0, 0.0, 0.0);
            drawerPane.open();
            drawerPane.setVisible(true);

            drawerPane.setOnDrawerClosed(jfxDrawerEvent -> {
                drawerPane.close();
                drawerPane.setVisible(false);
            });
        }
    }

    public void displayDrawer(Double left,Double top,Double right,Double bottom) {
        try {
            anchorPane.setLeftAnchor(drawerPane, left);
            anchorPane.setTopAnchor(drawerPane, top);
            anchorPane.setRightAnchor(drawerPane, right);
            anchorPane.setBottomAnchor(drawerPane,bottom);

            VBox menuLeft = FXMLLoader.load(getClass().getResource(DrawerScreen));
            drawerPane.setSidePane(menuLeft);
            drawerPane.setDefaultDrawerSize(anchorPane.getWidth()*0.25);
            showElements(menuLeft);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displaySignIn(){
        header.setVisible(false);
        popUp(SignInScreen,false);
    }

    public void displaySignUp(){
        header.setVisible(false);
        popUp(SignUpScreen,false);
    }

    public void displayComponent(Node node, StackPane componentPane){

        String title = node.getAccessibleText().toUpperCase();
        labelTitle.setText(title);
        stackPane.getChildren().setAll(componentPane);

    }

    public void showElements(VBox menuLeft) throws IOException {
        StackPane dashboard = FXMLLoader.load(getClass().getResource(DashboardScreen));
        StackPane camera    = FXMLLoader.load(getClass().getResource(CameraScreen));

        ObservableList<Node> DrawerMenu = menuLeft.getChildren();
        ObservableList<Node> DrawerBoxes = ((VBox) DrawerMenu.get(2)).getChildren();

        for (Node node : DrawerBoxes) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "Home":
                            break;
                        case "Settings":
                            break;
                        case "Camera":
                            displayComponent(node,camera);
                            break;
                        case "Dashboard":
                            displayComponent(node, dashboard);
                            break;
                    }
                });
            }
        }
    }




    @FXML
    void clickClose(MouseEvent event) {
//        callCV.setClosed();
        ((Stage) btnClose.getScene().getWindow()).close();
    }

    @FXML
    void clickMinimise(MouseEvent event) {
        ((Stage) btnMinimise.getScene().getWindow()).setIconified(true);
    }

    @FXML
    void clickRestore(MouseEvent event) {
        Stage restore = ((Stage) btnRestore.getScene().getWindow());
        if(restore.isMaximized()){
            restore.setMaximized(false);
        } else {
            restore.setMaximized(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displaySignIn();
    }
}
