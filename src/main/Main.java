package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import main.utils.OpenCV;
import org.opencv.core.Core;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/MainScreen.fxml"));
        Parent root = loader.load();
        //root.setStyle("-fx-background-color: whitesmoke;");
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setTitle("Face Detection and Tracking");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
//        primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we)
//            {
//                OpenCV.getInstance().setClosed();
//            }
//        }));

    }

    public static void main(String[] args) {
        // load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }
}
