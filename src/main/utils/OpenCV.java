package main.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.controllers.MainScreenController;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.io.File;
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

    public Mat resizeImage ;

    public static String basePath=System.getProperty("user.dir").concat("\\src\\resources\\");
    public static String outputSrc = basePath+"images/output/";
    public static String inputSrc = basePath+"images/input/";
    public static String outputCapt = basePath+"images/dataset/";

    public static String haarFace = basePath+"haarcascades/haarcascade_frontalface_alt2.xml";
    public static String haarEyes = basePath+"haarcascades/haarcascade_eye_tree_eyeglasses.xml";

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
        this.faceCascade.load(haarFace);
        this.eyesCascade.load(haarEyes);
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

    public void detectImage (File file, ImageView currentFrame){

        String inputImg = inputSrc + file.getName();
        String outputImg = outputSrc + file.getName().replace(".jpg","_add.jpg");

        Mat src = Imgcodecs.imread(inputImg);
        Image imageToShow = Utils.mat2Image(src);
        this.updateImageView(currentFrame, imageToShow);
//        this.faceCascade = new CascadeClassifier(haarFace);
//        this.eyesCascade = new CascadeClassifier(haarEyes);
//        this.mouthCascade = new CascadeClassifier(haarMouth);

        this.detectAndDisplay(src);

        Imgcodecs.imwrite( outputImg, src);
        this.updateImageView(currentFrame, Utils.mat2Image(Imgcodecs.imread(outputImg)) );
//        System.out.println("Image Processed");
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

            Mat croppedImage = new Mat(org_frame, face);
            this.resizeImage = new Mat();
            Imgproc.resize(croppedImage, this.resizeImage, new Size(400,400));

            Imgcodecs.imwrite( outputCapt+"new.jpg", this.resizeImage);
//            this.updateImageView(currentFrame, Utils.mat2Image(Imgcodecs.imread(outputImg)) );


//            double[] returnedResults = faceRecognition(faceROI);

        }
//        HighGui.imshow("Capture - Face detection", frame );

    }

    public Mat getResizeImage() {
        return resizeImage;
    }

    public void setResizeImage(Mat resizeImage) {
        this.resizeImage = resizeImage;
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
