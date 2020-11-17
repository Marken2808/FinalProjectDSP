package main.utils;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
//import org.opencv.face.Face;
//import org.opencv.face.FaceRecognizer;
//import org.opencv.face.FisherFaceRecognizer;
//import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
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

    public Mat resizeImage ;
    public ArrayList<Mat> listRez;
    public ArrayList<Mat> listCrop;
    public Mat croppedImage;
    public String basePath=System.getProperty("user.dir").concat("\\src\\resources\\");
    public String outputSrc = basePath+"images/output/";
    public String inputSrc = basePath+"images/input/";
    public String outputCapt = basePath+"images/dataset/";
    public String haarFace = basePath+"haarcascades/haarcascade_frontalface_alt2.xml";
    public String haarEyes = basePath+"haarcascades/haarcascade_eye_tree_eyeglasses.xml";

    public static OpenCV instance;

//    ----------------------------------------

//    private FisherFaceRecognizer faceRecognizer=FisherFaceRecognizer.create(0,10000);
//    public HashMap<Integer, String> names = new HashMap<Integer, String>();
//    private int label;//label is to identify student id
//    private String name_1;// name_1 is to identify student name

// -----------------------------------------------

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

    public ArrayList<Mat> detectImage (File file, ImageView currentFrame){

        String inputImg = inputSrc + file.getName();
        String outputImg = outputSrc + file.getName().replace(".jpg","_add.jpg");

        Mat src = Imgcodecs.imread(inputImg);
        Image imageToShow = Utils.mat2Image(src);
        this.updateImageView(currentFrame, imageToShow);
        this.faceCascade = new CascadeClassifier(haarFace);
        this.eyesCascade = new CascadeClassifier(haarEyes);
//        this.mouthCascade = new CascadeClassifier(haarMouth);

        this.detectAndDisplay(src);

        Imgcodecs.imwrite( outputImg, src);
        this.updateImageView(currentFrame, Utils.mat2Image(Imgcodecs.imread(outputImg)) );

        return this.listRez;
    }

//     -------------------------------------------------------------------------------

//    private String[] faceRecognition(Mat currentFace,String outfile) {
//        // predict the label
//        int[] predLabel = new int[1];
//        double[] confidence = new double[1];
//        int result = -1;
//        //Read XML file for face detection in OpenCV
//        faceRecognizer.read("./resources/haarcascade_frontalface_alt.xml");
//        faceRecognizer.predict(currentFace,predLabel,confidence);
//        result = faceRecognizer.predict_label(currentFace);
//
//        ArrayList<String> emotions=new ArrayList<>();
//        //save image into base64 format
//        //so that Google Cloud API will be faster
//        outfile=GetImageStr();
//        outfile=GenerateImage(outfile);
//
//        Imgcodecs.imwrite(outfile,currentFace);
//        try {
//            //get emotions
//            emotions = detectFaces(new String[] {outfile});
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new String[] {String.valueOf(result),String.valueOf(confidence[0]),emotions.get(0),emotions.get(1),emotions.get(2)};
//    }
//
//    public static ArrayList<String> detectFaces(String[] filePath) throws Exception, IOException {
//        List<AnnotateImageRequest> requests = new ArrayList<>();
//        ArrayList<String> result = new ArrayList<>();
//        for(String file:filePath) {
//            ByteString imgBytes = ByteString.readFrom(new FileInputStream(file));
//
//            com.google.cloud.vision.v1.Image img = com.google.cloud.vision.v1.Image.newBuilder().setContent(imgBytes).build();
//            Feature feat = Feature.newBuilder().setType(Feature.Type.FACE_DETECTION).build();
//            AnnotateImageRequest request =
//                    AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
//            requests.add(request);
//        }
//
//        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
//            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
//            List<AnnotateImageResponse> responses = response.getResponsesList();
//
//            for (AnnotateImageResponse res : responses) {
//                if (res.hasError()) {
//                    return null;
//                }
//
//                // For full list of available annotations, see http://g.co/cloud/vision/docs
//                for (FaceAnnotation annotation : res.getFaceAnnotationsList()) {
//                    result.add(" joy: " + annotation.getJoyLikelihood());
//                    result.add(" anger: " + annotation.getAngerLikelihood());
//                    result.add(" surprise: " + annotation.getSurpriseLikelihood());
//                }
//            }
//        }
//        return result;
//    }
//
//    public static String GetImageStr()
//    {//Transfer image file into byte array string and use base64 to encode it
//        String imgFile="resources/current.jpg";
//        InputStream in=null;
//        byte[] data=null;
//        try {
//            in=new FileInputStream(imgFile);
//            data=new byte[in.available()];
//            in.read(data);
//            in.close();
//        }
//        catch(IOException e) {
//            e.printStackTrace();
//        }
//        return Base64.getEncoder().encodeToString(data);
//    }
//    /**
//     *
//     * @param imgStr is the path that stores all the file. These file need to be decoded from Base64 to images.
//     * @return
//     */
//    public static String GenerateImage(String imgStr)
//    {
//        if(imgStr==null)
//            return "error";
//        try
//        {
//            byte[]b = Base64.getDecoder().decode(imgStr);
//            for(int i=0;i<b.length;++i) {
//                if(b[i]<0) {
//                    b[i]+=256;
//                }
//            }
//            String imgFilePath="resources/current2.jpg";
//            OutputStream out=new FileOutputStream(imgFilePath);
//            out.write(b);
//            out.flush();
//            out.close();
//            return imgFilePath;
//
//        }catch(Exception e)
//        {
//            return "error";
//        }
//
//    }
//
//    /**
//     *
//     * @param img is the mat that need to be saved as jpg image
//     * @param rect is the size of the mat
//     * @return a path of images
//     */
//    private static String save(Mat img,Rect rect) {
//        String outFile="resources/current.jpg";
//        Imgcodecs.imwrite(outFile, img);
//        return outFile;
//    }


//    ---------------------------------------------------------------------

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
        Rect[] facesArray = faces.toArray();
        for (Rect face : facesArray) {
            Mat org_frame = frame.clone();
//            Point center = new Point(face.x + face.width / 2, face.y + face.height / 2);
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
//            Imgproc.putText(frame,"name", new Point(face.x-10, face.y-10) ,Imgproc.FONT_HERSHEY_PLAIN, 1.5, new Scalar(0, 255, 0, 2.0), 2);

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

//            ------------------------------------------------------
//            Rect rectCrop=new Rect(face.tl(),face.br());
//            Mat croppedImage=new Mat(frame,rectCrop);
//            Mat resizeImage = new Mat();
//            Size size = new Size(150,150);
//            Imgproc.resize(croppedImage, resizeImage, size);
//
//            String[] returnedResults = faceRecognition(resizeImage,save(resizeImage,face));
//            double prediction = Double.parseDouble(returnedResults[0]);
//            double confidence = Double.parseDouble(returnedResults[1]);
//            confidence=Math.round(confidence);
//            String emotion1=returnedResults[2];
////            String emotion2=returnedResults[3];
////            String emotion3=returnedResults[4];
//            System.out.println(emotion1);
//            System.out.println(confidence);
//
//            label = (int) prediction;
//            if (names.containsKey(label) && confidence<1500) {// if confidence below 2000, then mark the person as a new user
//                name_1 = names.get(label);
//            } else {
//                name_1 = "Unknown";
//                label = 0;
//            }
//            // Create the text we will annotate the box with:
////            String box_text = "Prediction = " + name_1 + " Confidence = " + confidence;
////            String box_text2="Emotion= "+emotion1;
////            String box_text3="Emotion= "+emotion2;
////            String box_text4="Emotion= "+emotion3;
//            // Calculate the position for annotated text (make sure we don't
//            // put illegal values in there):
////            double pos_x = Math.max(face.tl().x - 10, 0);
////            double pos_y = Math.max(face.tl().y - 10, 0);
////            double pos_x_1 = Math.max(face.tl().x - 35, 0);
////            double pos_y_1 = Math.max(face.tl().y - 35, 0);
////            double pos_x_2 = Math.max(face.tl().x - 55, 0);
////            double pos_y_2 = Math.max(face.tl().y - 55, 0);
////            double pos_x_3 = Math.max(face.tl().x - 75, 0);
////            double pos_y_3 = Math.max(face.tl().y - 75, 0);
//            // And now put it into the image:
////            Imgproc.putText(frame, box_text, new Point(pos_x, pos_y),
////                    Imgproc.FONT_HERSHEY_PLAIN, 2.0, new Scalar(71, 99, 255, 2.0));
////            Imgproc.putText(frame, box_text2, new Point(pos_x_1, pos_y_1),
////                    Imgproc.FONT_HERSHEY_PLAIN, 2.0, new Scalar(71, 99, 255, 2.0));
////            Imgproc.putText(frame, box_text3, new Point(pos_x_2, pos_y_2),
////                    Imgproc.FONT_HERSHEY_PLAIN, 2.0, new Scalar(71, 99, 255, 2.0));
////            Imgproc.putText(frame, box_text4, new Point(pos_x_3, pos_y_3),
////                    Imgproc.FONT_HERSHEY_PLAIN, 2.0, new Scalar(71, 99, 255, 2.0));
//
//            System.out.println(name_1+" Welcome!");
//            System.out.println(label);

//            ------------------------------------------------

            this.croppedImage = new Mat(org_frame, face);
            this.resizeImage = new Mat();
            this.listCrop.add(this.croppedImage);         //for multi pics
            this.listRez.add(this.resizeImage);


        }
        for(int i=0; i<this.listCrop.size();i++){
            Imgproc.resize(this.listCrop.get(i), this.listRez.get(i), new Size(1000,1000));
            Imgcodecs.imwrite( outputCapt+"new"+i+".jpg", this.listRez.get(i));
        }
//        HighGui.imshow("Capture - Face detection", frame );
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
