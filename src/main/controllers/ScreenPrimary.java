package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ScreenPrimary implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public StackPane mainStackPane;

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

    private String SignInScreen     = "/main/views/PopupSignIn.fxml";
    private String DrawerScreen     = "/main/views/DrawerMenu.fxml";
    private String DashboardScreen  = "/main/views/DashboardScreen.fxml";
    private String CameraScreen     = "/main/views/ScreenCamera.fxml";
//----------------------------instance--------------------

    public static ScreenPrimary instance;
    public ScreenPrimary(){
        instance = this;
    }
    public static ScreenPrimary getInstance() {
        if(instance == null){
            instance = new ScreenPrimary();
        }
        return instance;
    }
//---------------------------------------------------------

    public void popUp(String screen, boolean canClose){
        try {
            FXMLLoader loader = new FXMLLoader(ScreenPrimary.class.getResource(screen));
            Parent Root = loader.load();
            JFXDialogLayout content = new JFXDialogLayout();
            content.setBody(Root);
            dialog = new JFXDialog(mainStackPane, content , JFXDialog.DialogTransition.CENTER);
            dialog.setOverlayClose(canClose);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void isMenuClicked(MouseEvent event) throws IOException {
        if(ScreenCamera.getInstance().isCameraActive()) {     // grant turn off to use drawer
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
            mainAnchorPane.setLeftAnchor(drawerPane, left);
            mainAnchorPane.setTopAnchor(drawerPane, top);
            mainAnchorPane.setRightAnchor(drawerPane, right);
            mainAnchorPane.setBottomAnchor(drawerPane,bottom);

            VBox menuLeft = FXMLLoader.load(getClass().getResource(DrawerScreen));
            drawerPane.setSidePane(menuLeft);
            drawerPane.setDefaultDrawerSize(mainAnchorPane.getWidth()*0.25);
            showElements(menuLeft);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displaySignIn(){
        header.setVisible(false);
        popUp(SignInScreen,false);
    }

    public void displayComponent(Node node, StackPane componentPane){

        String title = node.getAccessibleText().toUpperCase();
        labelTitle.setText(title);
        mainStackPane.getChildren().setAll(componentPane);

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
        ScreenCamera.getInstance().turnOffCamera();
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
