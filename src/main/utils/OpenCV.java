package main.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.*;
import org.opencv.face.Face;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public final class OpenCV {


    // a timer for acquiring the video stream
    public ScheduledExecutorService timer;
    // the OpenCV object that performs the video capture
    public VideoCapture capture;

    public int absoluteFaceSize;

    //   face cascade classifier
    public CascadeClassifier faceCascade;

    //   eyes cascade classifier
    public CascadeClassifier eyesCascade;

    public ArrayList<Mat> listRez;
    public ArrayList<Mat> listCrop;

    public String basePath=System.getProperty("user.dir").concat("\\src\\resources\\");
    public String outputSrc = basePath+"images/output/";
    public String inputSrc = basePath+"images/input/";
    public String outputCapt = basePath+"images/dataset/";
    public String haarFace = basePath+"haarcascades/haarcascade_frontalface_alt2.xml";
    public String haarEyes = basePath+"haarcascades/haarcascade_eye_tree_eyeglasses.xml";

    // Names of the people from the training set
    public HashMap<Integer, String> names = new HashMap<Integer, String>();


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

        trainModel();
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


//                    test.detectAndDisplay(frame,faceCascade,faceRecognizer);

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

    public ArrayList<Mat> detectImage (File file, ImageView currentFrame){

        String inputImg = inputSrc + file.getName();
        String outputImg = outputSrc + file.getName().replace(".jpg","_add.jpg");

        Mat src = Imgcodecs.imread(inputImg);
        Image imageToShow = Utils.mat2Image(src);
        this.updateImageView(currentFrame, imageToShow);
        this.faceCascade = new CascadeClassifier(haarFace);
        this.eyesCascade = new CascadeClassifier(haarEyes);


//        test.detectAndDisplay(src,faceCascade,faceRecognizer);

        this.detectAndDisplay(src);
//        detectAndDisplay();
        Imgcodecs.imwrite( outputImg, src);
        this.updateImageView(currentFrame, Utils.mat2Image(Imgcodecs.imread(outputImg)) );

        return this.listRez;
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
                this.absoluteFaceSize = Math.round(height * 0.01f);
            }
        }
        // detect faces
        this.faceCascade.detectMultiScale(grayFrame, faces, 1.3, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
                new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

        this.listCrop = new ArrayList<>();
        this.listRez = new ArrayList<>();
        Mat resizeImage ;
        Mat croppedImage;
        Rect[] facesArray = faces.toArray();
        for (Rect face : facesArray) {

            Mat org_frame = frame.clone();
//            Point center = new Point(face.x + face.width / 2, face.y + face.height / 2);
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
//            Imgproc.putText(frame,box_text, new Point(face.x-10, face.y-10) ,Imgproc.FONT_HERSHEY_PLAIN, 1.5, new Scalar(0, 255, 0, 2.0), 2);

            Mat faceROI = grayFrame.submat(face);

            // ------In each face, detect eyes--------------------
            MatOfRect eyes = new MatOfRect();
            this.eyesCascade.detectMultiScale(faceROI, eyes, 1.2, 2);

//            this.eyesCascade.detectMultiScale(faceROI, eyes);
            List<Rect> listOfEyes = eyes.toList();
            for (Rect eye : listOfEyes) {
                Point eyeCenter = new Point(face.x + eye.x + eye.width / 2, face.y + eye.y + eye.height / 2);
                int radius = (int) Math.round((eye.width + eye.height) * 0.25);
                Imgproc.circle(frame, eyeCenter, radius, new Scalar(255, 0, 0), 2);

            }


            Rect rectCrop = new Rect(face.tl(), face.br());
            croppedImage = new Mat(org_frame, rectCrop);
            resizeImage = new Mat();
            this.listCrop.add(croppedImage);         //for multi pics
            this.listRez.add(resizeImage);

            for(int i=0; i<this.listCrop.size();i++){
//            Imgproc.resize(.get(i), this.listRez.get(i), new Size(1000,1000));
                Imgcodecs.imwrite( outputCapt+"0-new_"+i+".jpg", this.listCrop.get(i));

//                Imgcodecs.imwrite( outputCapt+"0-new_.jpg", listCrop.get(0));
            }

            Imgproc.cvtColor(croppedImage, croppedImage, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(croppedImage, croppedImage);
            Size size = new Size(250,250);
            Imgproc.resize(croppedImage, resizeImage, size);
//



            // ---------------------------------------------
//            int prediction = faceRecognition(resizeImage);
            double[] returnedResults = faceRecognition(resizeImage);
            double prediction = returnedResults[0];
            double confidence = returnedResults[1];

            System.out.println("PREDICTED LABEL IS: " + prediction);
            int label = (int) prediction;
            String name = "";
            if (names.containsKey(label)) {
                name = names.get(label);
            } else {
                name = "Unknown";
            }
//
            String box_text = name + " : " + confidence + "%";
            double pos_x = face.x - 10;
            double pos_y = face.y - 10;
//            // And now put it into the image:
            Imgproc.putText(frame, box_text, new Point(pos_x, pos_y),
                    Imgproc.FONT_HERSHEY_PLAIN, 1.5, new Scalar(0, 255, 0, 2.0));

            // ---------------------------------------------


        }


//        HighGui.imshow("Capture - Face detection", frame );
    }

    //    -----------------------------------------
    public void trainModel () {
        // Read the data from the training set
        File root = new File(outputCapt);

        System.out.println(root);
        File[] imageFiles = root.listFiles( new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        });

        List<Mat> images = new ArrayList<Mat>();

        System.out.println("THE NUMBER OF IMAGES READ IS: " + imageFiles.length);

        List<Integer> trainingLabels = new ArrayList<>();

        Mat labels = new Mat(imageFiles.length,1,CvType.CV_32SC1);

        int counter = 0;

        for (File image : imageFiles) {
            // Parse the training set folder files
            Mat img = Imgcodecs.imread(image.getAbsolutePath());
            // Change to Grayscale and equalize the histogram
            Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
            Imgproc.equalizeHist(img, img);
            // Extract label from the file name
            int label = Integer.parseInt(image.getName().split("\\-")[0]);
            // Extract name from the file name and add it to names HashMap
            String labnname = image.getName().split("\\_")[0];
            String name = labnname.split("\\-")[1];
            names.put(label, name);
            // Add training set images to images Mat
            images.add(img);

            labels.put(counter, 0, label);
            counter++;
        }
//        FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
//        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create(0,1000);

        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        faceRecognizer.train(images, labels);
        faceRecognizer.save("traineddata");
    }

    public double[] faceRecognition(Mat currentFace) {

        // predict the label

        int[] predLabel = new int[1];
        double[] confidence = new double[1];

        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        faceRecognizer.read("traineddata");
        faceRecognizer.predict(currentFace,predLabel,confidence);
//        result = faceRecognizer.predict_label(currentFace);
//        result = predLabel[0];

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
