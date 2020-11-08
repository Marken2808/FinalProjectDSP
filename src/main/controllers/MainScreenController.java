package main.controllers;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.utils.OpenCV;
import main.utils.Utils;
import org.opencv.core.*;
import org.opencv.face.Face;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainScreenController implements Initializable
{
    @FXML
    private ImageView currentFrame;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton btnStart;

    @FXML
    private JFXButton btnShot;

    @FXML
    private JFXButton btnInsert;




    // a flag to change the button behavior
    private boolean cameraActive;

    public static Mat resizeImage ;


    private OpenCV callCV = OpenCV.getInstance();



    @FXML
    void takeShot(ActionEvent event) throws IOException {
        callCV.stopAcquisition();
        // update the button content
        this.btnStart.setDisable(false);
        this.btnStart.setText("Continue");
        System.out.println("SHOT: " +Utils.mat2Image(this.resizeImage));

        callCV.updateImageView(currentFrame, Utils.mat2Image(this.resizeImage) );

//        CapturedScreenController toNext = new CapturedScreenController();
//        toNext.getImg();
//        toNext.setImg(Utils.mat2Image(this.resizeImage));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/CapturedScreen.fxml"));

        Parent Root = loader.load();
        Stage Stage = new Stage();
        Scene Scene = new Scene(Root);
        Stage.setScene(Scene);
        Stage.show();
    }

    public static String basePath=System.getProperty("user.dir").concat("\\src\\resources\\");
    public static String inputSrc = basePath+"images/input/";
    public static String outputSrc = basePath+"images/output/";
    public static String haarFace = basePath+"haarcascades/haarcascade_frontalface_alt2.xml";
    public static String haarEyes = basePath+"haarcascades/haarcascade_eye_tree_eyeglasses.xml";

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
        File selectedFile = event.getDragboard().getFiles().get(0);
        currentFrame.setImage(new Image(new FileInputStream(selectedFile)));
        event.consume();
        detectImage(selectedFile);
        this.btnInsert.setText("Insert Image");
    }


    void detectImage (File file){

        String inputImg = inputSrc + file.getName();
        String outputImg = outputSrc + file.getName().replace(".jpg","_add.jpg");

        Mat src = Imgcodecs.imread(inputImg);
        Image imageToShow = Utils.mat2Image(src);
        callCV.updateImageView(currentFrame, imageToShow);
        callCV.faceCascade = new CascadeClassifier(haarFace);
        callCV.eyesCascade = new CascadeClassifier(haarEyes);
//        this.mouthCascade = new CascadeClassifier(haarMouth);

        callCV.detectAndDisplay(src);

        Imgcodecs.imwrite( outputImg, src);
        callCV.updateImageView(currentFrame, Utils.mat2Image(Imgcodecs.imread(outputImg)) );
//        System.out.println("Image Processed");
    }

    @FXML
    void insertImage(ActionEvent event) throws IOException {
        this.btnInsert.setText("Inserting...");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            detectImage(selectedFile);
        }
        this.btnInsert.setText("Insert Image");

    }

    @FXML
    protected void startCamera()
    {
        // set a fixed width for the frame
//        currentFrame.setFitWidth(600);
        // preserve image ratio
        currentFrame.fitWidthProperty().bind(anchorPane.widthProperty());
        currentFrame.fitHeightProperty().bind(anchorPane.heightProperty());
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
                callCV.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

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
            // update again the button content
            this.btnStart.setText("Start Camera");
            this.btnInsert.setDisable(false);
            this.btnShot.setDisable(true);
            // stop the timer
            callCV.stopAcquisition();
        }
    }


//    private double[] faceRecognition(Mat currentFace) {
//
//        // predict the label
//
//        int[] predLabel = new int[1];
//        double[] confidence = new double[1];
//        int result = -1;
//
//        FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
//        faceRecognizer.predict(currentFace,predLabel,confidence);
////        	result = faceRecognizer.predict_label(currentFace);
//        result = predLabel[0];
//
//        return new double[] {result,confidence[0]};
//    }







    /**
     * The action triggered by selecting the Haar Classifier checkbox. It loads
     * the trained set to be used for frontal face detection.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        callCV.init();
        callCV.faceCascade.load(haarFace);
        callCV.eyesCascade.load(haarEyes);
    }
}
