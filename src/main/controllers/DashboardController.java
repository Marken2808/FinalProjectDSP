package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private TabPane tab;

    @FXML
    private StackPane userPane;

    @FXML
    private StackPane playerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        try {
//            AnchorPane userTab = FXMLLoader.load(getClass().getResource("/resources/filesFXML/UserTab.fxml"));
//            AnchorPane playerTab = FXMLLoader.load(getClass().getResource("/resources/filesFXML/PlayerTab.fxml"));
//
//            userPane.getChildren().add(userTab);
//            playerPane.getChildren().setAll(playerTab);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
