package main.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.controllers.MainScreenController;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class OpenCV {


    // a timer for acquiring the video stream
    public ScheduledExecutorService timer;
    // the OpenCV object that performs the video capture
    public VideoCapture capture;

    public int absoluteFaceSize;

    //   face cascade classifier
    public CascadeClassifier faceCascade;

    //   eyes cascade classifier
    public CascadeClassifier eyesCascade;


    public static OpenCV instance;

    public OpenCV(){
        instance = this;
    }

    public static OpenCV getInstance(){
        if(instance == null){
            instance = new OpenCV();
        }
        return instance;
    }

    /**
     * Init the controller, at start time
     */
    public void init()
    {

        this.capture = new VideoCapture();
        this.faceCascade = new CascadeClassifier();
        this.eyesCascade = new CascadeClassifier();
        this.absoluteFaceSize = 0;
    }

    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Image} to show
     */
    public Mat grabFrame()
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

    /**
     * Method for face detection and tracking
     *
     * @param frame
     *            it looks for faces in this frame
     */
    public void detectAndDisplay(Mat frame)
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

        Rect[] facesArray = faces.toArray();
        for (Rect face : facesArray) {
            Mat org_frame = frame.clone();
//            Point center = new Point(face.x + face.width / 2, face.y + face.height / 2);
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
            Imgproc.putText(frame,"name", new Point(face.x-10, face.y-10) ,Imgproc.FONT_HERSHEY_PLAIN, 1.5, new Scalar(0, 255, 0, 2.0), 2);

            Mat faceROI = grayFrame.submat(face);
            // ------In each face, detect eyes--------------------
            MatOfRect eyes = new MatOfRect();
            this.eyesCascade.detectMultiScale(faceROI, eyes, 1.2, 2);

            List<Rect> listOfEyes = eyes.toList();
            for (Rect eye : listOfEyes) {
                Point eyeCenter = new Point(face.x + eye.x + eye.width / 2, face.y + eye.y + eye.height / 2);
                int radius = (int) Math.round((eye.width + eye.height) * 0.25);
                Imgproc.circle(frame, eyeCenter, radius, new Scalar(255, 0, 0), 2);

            }

//            Mat croppedImage = new Mat(org_frame, face);
//            this.resizeImage = new Mat();
//            Imgproc.resize(croppedImage, this.resizeImage, new Size(400,400));



//            System.out.println("View: " + this.resizeImage);
//            double[] returnedResults = faceRecognition(faceROI);

        }
//        HighGui.imshow("Capture - Face detection", frame );

    }

    /**
     * Method for loading a classifier trained set from disk
     *
     * @param classifierPath
     *            the path on disk where a classifier trained set is located
     */
    public void checkboxSelection(String classifierPath)
    {
        // load the classifier(s)
        this.faceCascade.load(classifierPath);
//        this.eyesCascade.load(haarEyes);

//        // now the video capture can start
//        this.btnStart.setDisable(false);
//        this.btnInsert.setDisable(true);
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    public void stopAcquisition()
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
    public void updateImageView(ImageView view, Image image)
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


}
