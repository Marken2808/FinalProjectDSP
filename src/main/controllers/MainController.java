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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

//import static org.bytedeco.opencv.global.opencv_videoio.CAP_PROP_FPS;

public class MainController implements Initializable
{
    @FXML
    private ImageView currentFrame;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton btnStart;

    @FXML
    private JFXButton btnShot;

    @FXML
    private JFXButton btnInsert;

    @FXML
    private HBox boxHeader;

    @FXML
    private HBox boxFooter;

    @FXML
    private AnchorPane home;

    @FXML
    private JFXButton btnMenu;

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

    private boolean cameraActive;

    public static JFXDialog dialog;

    private String CaptureScreen    = "/main/views/CapturedScreen.fxml";
    private String SignInScreen     = "/main/views/SignInScreen.fxml";
    private String SignUpScreen     = "/main/views/SignUpScreen.fxml";
    private String DrawerScreen     = "/main/views/DrawerMenu.fxml";
    private String CalendarScreen   = "/main/views/CalendarScreen.fxml";
    private String DashboardScreen  = "/main/views/DashboardScreen.fxml";
//----------------------------instance--------------------
    private OpenCV callCV = OpenCV.getInstance();
    public static MainController instance;
    public MainController(){
        instance = this;
    }
    public static MainController getInstance() {
        if(instance == null){
            instance = new MainController();
        }
        return instance;
    }
//---------------------------------------------------------

    public void popUp(String screen, boolean canClose){
        try {
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource(screen));
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

    public void utility(boolean show){
        boxFooter.setVisible(show);
        boxHeader.setVisible(show);
        drawerPane.setVisible(show);
    }

    public void displayDrawer() throws IOException {
        anchorPane.setTopAnchor(drawerPane,40.0);
        anchorPane.setBottomAnchor(drawerPane,0.0);
        if ( drawerPane.isClosing() ) { anchorPane.setLeftAnchor(drawerPane, -200.0); }
        else { anchorPane.setLeftAnchor(drawerPane, 0.0); }


        VBox menuLeft = FXMLLoader.load(getClass().getResource(DrawerScreen));
        drawerPane.setSidePane(menuLeft);
        showElements(menuLeft);
    }

    public void displaySignIn(){
        utility(false);
        popUp(SignInScreen,false);
    }

    public void displaySignUp(){
        utility(false);
        popUp(SignUpScreen,false);
    }

    public void displayComponent(Node node, StackPane componentPane){

        String title = node.getAccessibleText().toUpperCase();
        labelTitle.setText(title);
        if(title.equals("HOME")){
            stackPane.getChildren().clear();
            stackPane.getChildren().setAll(home);
        } else {
            stackPane.getChildren().setAll(componentPane);
        }
    }

    public void showElements(VBox menuLeft) throws IOException {
//        AnchorPane home = FXMLLoader.load(getClass().getResource("/main/views/MainScreen.fxml"));
//        StackPane setting = FXMLLoader.load(getClass().getResource(CalendarScreen));
        StackPane dashboard = FXMLLoader.load(getClass().getResource(DashboardScreen));

        for (Node node : menuLeft.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "Home":
                            displayComponent(node, null);
                            break;
                        case "Setting":
                            displayComponent(node,null);
                            break;
                        case "Dashboard":
                            displayComponent(node, dashboard);
                            break;
                    }
                });
            }
        }
    }

    public void turnOffCamera(){
        callCV.stopAcquisition();
        // the camera is not active at this point
        this.cameraActive = false;
        this.currentFrame.setImage(null);
        // update again the button content
        this.btnStart.setText("Start Camera");
        this.btnInsert.setDisable(false);
        this.btnShot.setDisable(true);
        this.btnStart.setDisable(false);
        // stop the timer
    }

    @FXML
    void drawerExit(MouseEvent event) throws IOException {
        if(drawerPane.isOpened() || drawerPane.isOpening()) {
            drawerPane.close();
            btnMenu.setGraphic(new ImageView(new Image("/resources/images/icon/menu.png")));
        }
        // set Drawer always hide away from main scene;
        stackPane.clearConstraints(drawerPane);
        displayDrawer();
    }

    @FXML
    void isMenuClicked(MouseEvent event) throws IOException {

        displayDrawer();
        if (!this.cameraActive) {

            if (drawerPane.isClosed() || drawerPane.isClosing()) {
                drawerPane.open();
                btnMenu.setGraphic(new ImageView(new Image("/resources/images/icon/x.png")));
            } else {
                drawerPane.close();
                btnMenu.setGraphic(new ImageView(new Image("/resources/images/icon/menu.png")));
            }
        } else {
            System.out.println("Need close camera" );
            turnOffCamera();
        }
    }

    @FXML
    void takeShot(ActionEvent event) {

        callCV.stopAcquisition();
        // update the button content
        this.btnStart.setDisable(false);
        this.btnStart.setText("Continue");

        popUp(CaptureScreen, true);

    }

    @FXML
    void dragImage(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
        this.btnInsert.setText("Dragging...");

    }

    @FXML
    void dropImage(DragEvent event) throws FileNotFoundException {
//        System.out.println("Dropping.............");
        this.btnInsert.setText("Dropping...");
        File selectedFile = event.getDragboard().getFiles().get(0);
        currentFrame.setImage(new Image(new FileInputStream(selectedFile)));
        event.consume();

        callCV.detectImage(selectedFile, currentFrame);




//        System.out.println(test);
        this.btnInsert.setText("Insert Image");
        this.btnShot.setDisable(false);
        this.btnShot.setText("Save new");


    }

    @FXML
    void insertImage(ActionEvent event) {
        this.btnInsert.setText("Inserting...");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            callCV.detectImage(selectedFile, currentFrame);
            this.btnShot.setDisable(false);
            this.btnShot.setText("Save new");
        }
        this.btnInsert.setText("Insert Image");

    }

    @FXML
    void startCamera(ActionEvent event) {
        currentFrame.fitWidthProperty().bind(stackPane.widthProperty());
        currentFrame.fitHeightProperty().bind(stackPane.heightProperty());
        if (!this.cameraActive) {
            this.btnShot.setDisable(false);
            this.btnInsert.setDisable(true);
            // start the video capture
            this.callCV.capture.open(0);

            // is the video stream available?
            if (callCV.capture.isOpened()) {
                this.cameraActive = true;
                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = new Runnable() {
                    @Override
                    public void run() {
                        // effectively grab and process a single frame
                        Mat frame = callCV.grabFrame();
                        // convert and show the frame
                        Image imageToShow = UtilsOCV.mat2Image(frame);
                        callCV.updateImageView(currentFrame, imageToShow);
                    }
                };

                callCV.timer = Executors.newSingleThreadScheduledExecutor();
                callCV.timer.scheduleAtFixedRate(frameGrabber, 0, 60, TimeUnit.MILLISECONDS);

                // update the button content
                this.btnStart.setDisable(true);
                this.btnStart.setText("Opening...");
            }
            else { System.err.println("Failed to open the camera connection..."); }
        }
        else {
            turnOffCamera();
        }

    }

    @FXML
    void clickClose(MouseEvent event) {
        callCV.setClosed();
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

        callCV.init();
        displaySignIn();
    }
}
