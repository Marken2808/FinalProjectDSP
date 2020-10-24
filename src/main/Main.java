package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.controllers.mainScreenController;
import org.opencv.core.Core;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // load the FXML resource
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/mainScreen.fxml"));
        BorderPane root = (BorderPane) loader.load();
        // set a whitesmoke background
        //root.setStyle("-fx-background-color: whitesmoke;");
        // create and style a scene
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        // create the stage with the given title and the previously created
        // scene
        primaryStage.setTitle("Face Detection and Tracking");
        primaryStage.setScene(scene);
        // show the GUI
        primaryStage.show();

        // init the controller
        mainScreenController controller = loader.getController();
        controller.init();

        // set the proper behavior on closing the application
        primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we)
            {
                controller.setClosed();
            }
        }));
    }

    public static void main(String[] args) {
        // load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }
}
