package main.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import main.utils.OpenCV;
import main.utils.UtilsOCV;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScreenCamera implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private ImageView currentFrame;

    @FXML
    private HBox boxFooter;

    @FXML
    private JFXButton btnStart;

    @FXML
    private JFXButton btnShot;

    @FXML
    private JFXButton btnInsert;

    private boolean cameraActive = false;

    private OpenCV callCV = OpenCV.getInstance();

    private String CaptureScreen    = "/main/views/PopupCaptured.fxml";
//----------------------------instance--------------------

    public static ScreenCamera instance;
    public ScreenCamera(){
        instance = this;
    }
    public static ScreenCamera getInstance() {
        if(instance == null){
            instance = new ScreenCamera();
        }
        return instance;
    }
    //---------------------------------------------------------

    @FXML
    void takeShot(ActionEvent event) {

        callCV.stopAcquisition();
        // update the button content
        this.btnStart.setDisable(false);
        this.btnStart.setText("Continue");

        ScreenPrimary.getInstance().popUp(CaptureScreen, true);

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
        currentFrame.setVisible(true);
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
        currentFrame.setVisible(true);
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

//        currentFrame.fitWidthProperty().bind(stackPane.widthProperty());
//        currentFrame.fitHeightProperty().bind(stackPane.heightProperty());
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
                        currentFrame.setVisible(true);
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

    public boolean isCameraActive(){    // drawer menu used
        if(!this.cameraActive){
            return true;
        } else {
            turnOffCamera();
            return false;
        }
    }

    public void turnOffCamera(){

            callCV.stopAcquisition();
            // the camera is not active at this point
            this.cameraActive = false;
            this.currentFrame.setImage(null);   // add later
            this.currentFrame.setVisible(false);
            // update again the button content
            this.btnStart.setText("Start Camera");
            this.btnInsert.setDisable(false);
            this.btnShot.setDisable(true);
            this.btnStart.setDisable(false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        callCV.init();
        currentFrame.fitWidthProperty().bind(ScreenPrimary.getInstance().mainStackPane.widthProperty());
        currentFrame.fitHeightProperty().bind(ScreenPrimary.getInstance().mainStackPane.heightProperty());

    }
}
