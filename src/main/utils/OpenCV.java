package utils;

import controllers.ScreenCamera;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.opencv.imgproc.Imgproc.INTER_AREA;


public class OpenCV {


    // a timer for acquiring the video stream
    public ScheduledExecutorService timer;
    // the OpenCV object that performs the video capture
    public VideoCapture capture;
    //   face cascade classifier
    public CascadeClassifier faceCascade = new CascadeClassifier();
    //   eyes cascade classifier
    public CascadeClassifier eyesCascade = new CascadeClassifier();

    public ArrayList<Mat> listRez;
//    public ArrayList<Mat> listCrop;
    public Rect[] facesArray;

    public String resPath      = System.getProperty("user.dir").concat("\\src\\resources\\");
    public String testPath      = resPath + "images/test/";
    public String datasetPath   = resPath + "images/dataset/";
    public String haarFace      = resPath + "haarcascades/haarcascade_frontalface_alt2.xml";

    // Names of the people from the training set
    public HashMap<Integer, String> namesMap = new HashMap<>();
    public File[] imageFiles = null;
    public Object[][] namesList;
    public int predictionID = 0;
    public File root = new File(datasetPath);
    public FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();




    public static OpenCV instance;


    public OpenCV(){
        instance = this;
    }

    public static OpenCV getInstance() {
        if(instance == null){
            instance = new OpenCV();
        }
        return instance;
    }

    /**
     * Init the controller, at start time
     */
    public void init() {
        this.capture = new VideoCapture();
        this.faceCascade.load(haarFace);
        trainModel();
    }

    /**
     * Get a frame from the opened video stream (if any)
     * @return the {@link Image} to show
     */
    public Mat grabFrame() {
        Mat frame = new Mat();
        // check if the capture is open
        if (this.capture.isOpened()) {
            try {
                // read the current frame
                this.capture.read(frame);
                // if the frame is not empty, process it
                if (!frame.empty()) {
                    // face detection
                    this.detectAndDisplay(frame);
                }
            }
            catch (Exception e) {
                // log the (full) error
                System.err.println("Exception during the image elaboration: " + e);
            }
        }
        return frame;
    }

    public Image detectImage (File file){

        String inImg = file.getPath();

        Mat src = Imgcodecs.imread(inImg);
        this.detectAndDisplay(src);

        Image imageAfter = UtilsOCV.mat2Image(src);
        return imageAfter;
    }



    /**
     * Method for face detection and tracking
     * @param frame
     * it looks for faces in this frame
     */
    public void detectAndDisplay(Mat frame)
    {
        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        // convert the frame in gray scale
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        // equalize the frame histogram to improve the result
        Imgproc.equalizeHist(grayFrame, grayFrame);

        // detect faces
        this.faceCascade.detectMultiScale(
                grayFrame,
                faces,
                ScreenCamera.scales,
                ScreenCamera.neighbours,
                0 | Objdetect.CASCADE_SCALE_IMAGE,
                new Size(ScreenCamera.sizes,ScreenCamera.sizes),
                new Size()
        );

        this.listRez = new ArrayList<>();
        Mat resizeImage ;
        Mat croppedImage ;
        facesArray = faces.toArray();
        for (Rect face : facesArray) {

//            Mat org_frame = frame.clone();
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 1);

            Rect rectCrop = new Rect(face.tl(), face.br());
            croppedImage = new Mat(frame, rectCrop);
//            resizeImage = new Mat();

            Imgproc.cvtColor(croppedImage, croppedImage, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(croppedImage, croppedImage);
            Size size = new Size(150,150);
            Imgproc.resize(croppedImage, croppedImage, size, 0,0, INTER_AREA);

            double[] returnedResults = faceRecognition(croppedImage);
            predictionID = ((int) returnedResults[0]);
            double confidence = returnedResults[1];
            String name;
//            System.out.println("PREDICTED LABEL IS: " + predictionID);
            if (namesMap.containsKey(predictionID)) {
                name = namesMap.get(predictionID);
            } else {
                name = "Unknown";
            }

            this.listRez.add(croppedImage);

            String box_text = name + " : " + confidence + "%";
            double pos_x = face.x - 10;
            double pos_y = face.y - 10;
            // And now put it into the image:
            Imgproc.putText(frame, box_text, new Point(pos_x, pos_y),
                    Imgproc.FONT_HERSHEY_TRIPLEX, 1, new Scalar(0, 255, 0),1);
//            FONT_HERSHEY_COMPLEX_SMALL
        }
    }

    public void trainModel () {


        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

        imageFiles = root.listFiles(File::isFile);
        namesList = new Object[imageFiles.length][3];
        int counter = 0;
        int id = 0;
        int set = 0;
        String name = null;
        // Read the data from the training set
        List<Mat> images = new ArrayList<Mat>();
        Mat labels = new Mat(imageFiles.length,1,CvType.CV_32SC1);
        if (imageFiles != null) {
            for (File image : imageFiles) {
                // Parse the training set folder files
                Mat img = Imgcodecs.imread(image.getAbsolutePath());
                // Change to Grayscale and equalize the histogram
                Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
                Imgproc.equalizeHist(img, img);
                // Extract label from the file name
                id = Integer.parseInt(image.getName().split("\\-")[0]);
                // Extract name from the file name and add it to names HashMap
                name = image.getName().split("\\-")[1].split("\\_")[0];
                // Extract set from the file name
                set = Integer.parseInt(image.getName().split("\\-")[1].split("\\_")[1].split("\\.jpg")[0]);


                // add id,name,set into array[][] nameList

                namesList[counter][0] = id;
                namesList[counter][1] = name;
                namesList[counter][2] = set;

                // add id,name into Hashmap nameNap
                namesMap.put(id, name);

                // Add training set images to images Mat
                images.add(img);

                labels.put(counter, 0, id);
                counter++;
            }

            faceRecognizer.train(images, labels);
            faceRecognizer.save("traineddata.json");
        }


    }

    public double[] faceRecognition(Mat currentFace) {

        // predict the label

        int[] predLabel = new int[1];
        double[] confidence = new double[1];


        faceRecognizer.read("traineddata.json");
        faceRecognizer.predict(currentFace,predLabel,confidence);

        return new double[] {predLabel[0],Math.round(confidence[0])};
    }
//    -----------------------------------------

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
//                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
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
        UtilsOCV.onFXThread(view.imageProperty(), image);
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    public void setClosed()
    {
        this.stopAcquisition();
    }


}
