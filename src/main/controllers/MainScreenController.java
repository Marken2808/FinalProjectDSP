package main.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.utils.Utils;
import org.opencv.core.*;
import org.opencv.face.Face;
import org.opencv.face.FaceRecognizer;
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
    private JFXButton btnStart;

    @FXML
    private JFXButton btnShot;

    @FXML
    private JFXButton btnInsert;

    @FXML
    private JFXCheckBox haarClassifier;

    @FXML
    private JFXCheckBox lbpClassifier;

    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;
    // the OpenCV object that performs the video capture
    private VideoCapture capture;
    // a flag to change the button behavior
    private boolean cameraActive;

    // face cascade classifier
    private CascadeClassifier faceCascade;

    // eyes cascade classifier
    private CascadeClassifier eyesCascade;

    //mouth
    private CascadeClassifier mouthCascade;

    private int absoluteFaceSize;

    /**
     * Init the controller, at start time
     */
    public void init()
    {
        this.capture = new VideoCapture();
        this.faceCascade = new CascadeClassifier();
        this.eyesCascade = new CascadeClassifier();
//        this.mouthCascade = new CascadeClassifier();
        this.absoluteFaceSize = 0;


    }

    @FXML
    void takeShot(ActionEvent event) throws IOException {
        this.stopAcquisition();
        // update the button content
        this.btnStart.setDisable(false);
        this.btnStart.setText("Continue");

        Parent Root = FXMLLoader.load(getClass().getResource("/resources/CapturedScreen.fxml"));
        Stage Stage = new Stage();
        Scene Scene = new Scene(Root);
        Stage.setScene(Scene);
        Stage.show();
    }

    public static String basePath=System.getProperty("user.dir").concat("\\src\\resources\\");
    public static String inputSrc = basePath+"images/input/";
    public static String outputSrc = basePath+"images/output/";
    public static String haarFace = basePath+"haarcascades/haarcascade_frontalface_alt2.xml";
    public static String haarMouth = basePath+"haarcascades/haarcascade_mcs_mouth.xml";
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
        updateImageView(currentFrame, imageToShow);
        this.faceCascade = new CascadeClassifier(haarFace);
        this.eyesCascade = new CascadeClassifier(haarEyes);
//        this.mouthCascade = new CascadeClassifier(haarMouth);

        this.detectAndDisplay(src);

        Imgcodecs.imwrite( outputImg, src);
        updateImageView(currentFrame, Utils.mat2Image(Imgcodecs.imread(outputImg)) );
//        System.out.println("Image Processed");
    }

    @FXML
    void insertImage(ActionEvent event) throws IOException {
        this.btnInsert.setText("Inserting...");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        detectImage(selectedFile);
        this.btnInsert.setText("Insert Image");
    }

    @FXML
    protected void startCamera()
    {
        // set a fixed width for the frame
//        currentFrame.setFitWidth(600);
        // preserve image ratio
        currentFrame.setPreserveRatio(true);


        if (!this.cameraActive)
        {
            // disable setting checkboxes
            this.haarClassifier.setDisable(true);
            this.lbpClassifier.setDisable(true);
            this.btnShot.setDisable(false);
            this.btnInsert.setDisable(true);
            // start the video capture
            this.capture.open(0);

            // is the video stream available?
            if (this.capture.isOpened())
            {
                this.cameraActive = true;

                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = new Runnable() {

                    @Override
                    public void run()
                    {
                        // effectively grab and process a single frame
                        Mat frame = grabFrame();
                        // convert and show the frame
                        Image imageToShow = Utils.mat2Image(frame);
                        updateImageView(currentFrame, imageToShow);
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                // update the button content
                this.btnStart.setDisable(true);
                this.btnStart.setText("Opening...");
            }
            else
            {
                // log the error
                System.err.println("Failed to open the camera connection...");
            }
        }
        else
        {
            // the camera is not active at this point
            this.cameraActive = false;
            // update again the button content
            this.btnStart.setText("Start Camera");
            // enable classifiers checkboxes
            this.haarClassifier.setDisable(false);
            this.lbpClassifier.setDisable(false);
            this.btnInsert.setDisable(false);
            this.btnShot.setDisable(true);
            // stop the timer
            this.stopAcquisition();
        }
    }

    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Image} to show
     */
    private Mat grabFrame()
    {
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened())
        {
            try
            {
                // read the current frame
                this.capture.read(frame);

                // if the frame is not empty, process it
                if (!frame.empty())
                {
                    // face detection
                    this.detectAndDisplay(frame);
                }

            }
            catch (Exception e)
            {
                // log the (full) error
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    private double[] faceRecognition(Mat currentFace) {

        // predict the label

        int[] predLabel = new int[1];
        double[] confidence = new double[1];
        int result = -1;

        FaceRecognizer faceRecognizer = Face.createLBPHFaceRecognizer();
        faceRecognizer.load("traineddata");
        faceRecognizer.predict(currentFace,predLabel,confidence);
//        	result = faceRecognizer.predict_label(currentFace);
        result = predLabel[0];

        return new double[] {result,confidence[0]};
    }

    /**
     * Method for face detection and tracking
     *
     * @param frame
     *            it looks for faces in this frame
     */
    private void detectAndDisplay(Mat frame)
    {
        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        // convert the frame in gray scale
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        // equalize the frame histogram to improve the result
        Imgproc.equalizeHist(grayFrame, grayFrame);

        // compute minimum face size (20% of the frame height, in our case)
        if (this.absoluteFaceSize == 0)
        {
            int height = grayFrame.rows();
            if (Math.round(height * 0.2f) > 0)
            {
                this.absoluteFaceSize = Math.round(height * 0.2f);
            }
        }
        // detect faces
        this.faceCascade.detectMultiScale(grayFrame, faces, 1.3, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
                new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

//        Mat binary = frame.clone();
//        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
//        Mat hierarchey = new Mat();
//        Imgproc.findContours(frame, contours, hierarchey, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//        //Drawing the Contours
//        Scalar color = new Scalar(0, 0, 255);
//        Imgproc.drawContours(binary, contours, -1, color, 2) ;

//        List<Rect> listOfFaces = faces.toList();

        Rect[] facesArray = faces.toArray();
        for (Rect face : facesArray) {
//            Point center = new Point(face.x + face.width / 2, face.y + face.height / 2);
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
            Imgproc.putText(frame,"name", new Point(face.x-10, face.y-10) ,Core.FONT_HERSHEY_PLAIN, 1.5, new Scalar(0, 255, 0, 2.0), 2);
            Mat faceROI = grayFrame.submat(face);

            // Crop the detected faces
            Rect rectCrop = new Rect(face.tl(), face.br());
            Mat croppedImage = new Mat(frame, rectCrop);
            // Change to gray scale
            Imgproc.cvtColor(croppedImage, croppedImage, Imgproc.COLOR_BGR2GRAY);
            // Equalize histogram
            Imgproc.equalizeHist(croppedImage, croppedImage);
            // Resize the image to a default size
            Mat resizeImage = new Mat();
            Size size = new Size(250,250);
            Imgproc.resize(croppedImage, resizeImage, size);

            // ------In each face, detect eyes--------------------
            MatOfRect eyes = new MatOfRect();
            this.eyesCascade.detectMultiScale(faceROI, eyes);
            List<Rect> listOfEyes = eyes.toList();
            for (Rect eye : listOfEyes) {
                Point eyeCenter = new Point(face.x + eye.x + eye.width / 2, face.y + eye.y + eye.height / 2);
                int radius = (int) Math.round((eye.width + eye.height) * 0.25);
                Imgproc.circle(frame, eyeCenter, radius, new Scalar(255, 0, 0), 2);
            }
            //----------------------------------------------------
            // mouth
//            MatOfRect mouthes = new MatOfRect();
//            this.mouthCascade.detectMultiScale(faceROI, mouthes);
//            List<Rect> listOfMouth = mouthes.toList();
//            for (Rect mouth : listOfMouth) {
//                Point mouthCenter = new Point(face.x + mouth.x + mouth.width/2, face.y + mouth.y + mouth.height/2);
//                int radius = (int) Math.round((mouth.width + mouth.height) * 0.2);
//                Imgproc.circle(frame, mouthCenter, radius, new Scalar(255, 0, 255), 1);
//
//            }



        }
//        HighGui.imshow("Capture - Face detection", frame );

    }

    /**
     * The action triggered by selecting the Haar Classifier checkbox. It loads
     * the trained set to be used for frontal face detection.
     */


    @FXML
    protected void haarSelected(Event event)
    {
        // check whether the lpb checkbox is selected and deselect it
        if (this.lbpClassifier.isSelected())
            this.lbpClassifier.setSelected(false);

        this.checkboxSelection(basePath + "haarcascades/haarcascade_frontalface_alt2.xml");
    }

    /**
     * The action triggered by selecting the LBP Classifier checkbox. It loads
     * the trained set to be used for frontal face detection.
     */
    @FXML
    protected void lbpSelected(Event event)
    {
        // check whether the haar checkbox is selected and deselect it
        if (this.haarClassifier.isSelected())
            this.haarClassifier.setSelected(false);

        this.checkboxSelection(basePath+ "lbpcascades/lbpcascade_frontalface.xml");
    }

    /**
     * Method for loading a classifier trained set from disk
     *
     * @param classifierPath
     *            the path on disk where a classifier trained set is located
     */
    private void checkboxSelection(String classifierPath)
    {
        // load the classifier(s)
        this.faceCascade.load(classifierPath);
        this.eyesCascade.load(haarEyes);
//        this.mouthCascade.load(haarMouth);

        // now the video capture can start
        this.btnStart.setDisable(false);
        this.btnInsert.setDisable(true);
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition()
    {
        if (this.timer!=null && !this.timer.isShutdown())
        {
            try
            {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened())
        {
            // release the camera
            this.capture.release();
        }
    }

    /**
     * Update the {@link ImageView} in the JavaFX main thread
     *
     * @param view
     *            the {@link ImageView} to update
     * @param image
     *            the {@link Image} to show
     */
    private void updateImageView(ImageView view, Image image)
    {
        Utils.onFXThread(view.imageProperty(), image);
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    public void setClosed()
    {
        this.stopAcquisition();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init the controller
        init();
        System.out.println(basePath);
    }
}
