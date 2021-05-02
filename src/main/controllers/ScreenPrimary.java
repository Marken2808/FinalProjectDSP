package controllers;

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
import utils.OpenCV;

import java.io.File;
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

    private String SignInPopup      = "/views/PopupSignIn.fxml";
    private String DrawerMenu       = "/views/DrawerMenu.fxml";
    private String DashboardScreen  = "/views/ScreenDashboard.fxml";
    private String CameraScreen     = "/views/ScreenCamera.fxml";
    private String OverviewScreen   = "/views/ScreenOverview.fxml";
    private String ProfileScreen    = "/views/ScreenProfile.fxml";
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

    public void displayPopup(String screen, boolean canClose){
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
            displayDrawer(0.0, 40.0, 0.0, 0.0);
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

            VBox menuLeft = FXMLLoader.load(getClass().getResource(DrawerMenu));
            drawerPane.setSidePane(menuLeft);
            drawerPane.setDefaultDrawerSize(mainAnchorPane.getWidth()*0.2);
            showElements(menuLeft);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayScreen(String title, String screen){
        mainStackPane.getChildren().clear();
        try {
            StackPane screenPane = FXMLLoader.load(getClass().getResource(screen));
            labelTitle.setText(title);
//            labelTitle.setFont(new Font("Times New Roman",20));
            mainStackPane.getChildren().add(screenPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displaySignIn(){
        header.setVisible(false);
        displayPopup(SignInPopup,false);
    }

    public void showElements(VBox menuLeft) {

        ObservableList<Node> DrawerMenu = menuLeft.getChildren();
        ObservableList<Node> DrawerBoxes = ((VBox) DrawerMenu.get(1)).getChildren();

        for (Node node : DrawerBoxes) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "Profile" :
                            displayScreen("Profile", ProfileScreen);
                            break;
                        case "Home":
                            displayScreen("Overview", OverviewScreen);
                            break;
                        case "Settings":
                            System.out.println("Setting in developing");
                            break;
                        case "Camera":
                            displayScreen("Live Camera", CameraScreen);
                            break;
                        case "Dashboard":
                            displayScreen("Dashboard", DashboardScreen);
                            break;
                    }
                });
            }
        }
    }

    @FXML
    void clickClose(MouseEvent event) throws InterruptedException {
        ScreenCamera.getInstance().isCameraActive();
        ((Stage) btnClose.getScene().getWindow()).close();

        File testClose = new File(OpenCV.getInstance().testPath);
        for(File file : testClose.listFiles()){
            if( !file.getPath().contains("Image_0.jpg")){
                System.gc();
                Thread.sleep(100);
                file.delete();
            }
        }

        File datasetClose = new File(OpenCV.getInstance().datasetPath);
        for (File file : datasetClose.listFiles()){
            file.delete();
        }

        System.out.println("Tidy works");
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
