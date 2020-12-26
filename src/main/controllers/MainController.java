package main.controllers;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import main.utils.OpenCV;
import main.utils.Utils;
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
    private StackPane stackPane;

    @FXML
    private JFXButton btnStart;

    @FXML
    private JFXButton btnShot;

    @FXML
    private JFXButton btnInsert;

    // a flag to change the button behavior
    private boolean cameraActive;

    private OpenCV callCV = OpenCV.getInstance();

    public static JFXDialog dialog;

    private String CaptureScreen = "/resources/CapturedScreen.fxml";
    private String SignInScreen  = "/resources/SignInScreen.fxml";
    private String SignUpScreen  = "/resources/SignUpScreen.fxml";

//----------------------------instance--------------------
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
        btnStart.setVisible(show);
        btnInsert.setVisible(show);
        btnShot.setVisible(show);
    }

    public void displaySignIn(){
        utility(false);
        popUp(SignInScreen,false);
    }

    public void displaySignUp(){
        utility(false);
        popUp(SignUpScreen,false);
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
        }
        this.btnInsert.setText("Insert Image");
        this.btnShot.setDisable(false);
        this.btnShot.setText("Save new");

    }

    @FXML
    protected void startCamera(ActionEvent event)
    {
        // set a fixed width for the frame
//        currentFrame.setFitWidth(600);
        // preserve image ratio
        currentFrame.fitWidthProperty().bind(stackPane.widthProperty());
        currentFrame.fitHeightProperty().bind(stackPane.heightProperty());
        if (!this.cameraActive)
        {
            this.btnShot.setDisable(false);
            this.btnInsert.setDisable(true);
            // start the video capture
            this.callCV.capture.open(0);

            // is the video stream available?
            if (callCV.capture.isOpened())
            {
                this.cameraActive = true;
                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = new Runnable() {
                    @Override
                    public void run()
                    {
                        // effectively grab and process a single frame
                        Mat frame = callCV.grabFrame();
                        // convert and show the frame
                        Image imageToShow = Utils.mat2Image(frame);
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
        else
        {
            // the camera is not active at this point
            this.cameraActive = false;
            this.currentFrame.setImage(null);
            // update again the button content
            this.btnStart.setText("Start Camera");
            this.btnInsert.setDisable(false);
            this.btnShot.setDisable(true);
            // stop the timer
            callCV.stopAcquisition();
        }
    }








    /**
     * The action triggered by selecting the Haar Classifier checkbox. It loads
     * the trained set to be used for frontal face detection.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        callCV.init();
        displaySignIn();


    }
}
