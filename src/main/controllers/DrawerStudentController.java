package main.controllers;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.utils.CircleChart;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class DrawerStudentController implements Initializable {


    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXMasonryPane semiPane;

    @FXML
    private StackPane doughnutPane;


    public void drawDoughnutChart(){
        StackPane stackPane = new StackPane();
        ObservableList<CircleChart.Data> dataList = FXCollections.observableArrayList(
                new CircleChart.Data(7, "MATH", Color.LIMEGREEN),
                new CircleChart.Data(4, "PHYSICS", Color.LIGHTBLUE),
                new CircleChart.Data(4, "CHEMISTRY", Color.BISQUE),
                new CircleChart.Data(8, "ENGLISH", Color.LIGHTPINK),
                new CircleChart.Data(5, "HISTORY", Color.LAVENDER),
                new CircleChart.Data(6, "BIOLOGY", Color.HONEYDEW),
                new CircleChart.Data(3, "GEOGRAPHY", Color.TOMATO)


        );

        CircleChart chart = new CircleChart(360, dataList, 100, 100, 100,70,2);

        Label label = new Label("Student\nOverall");
        label.setFont(new Font(20));
        stackPane.getChildren().addAll(chart, label);
        doughnutPane.getChildren().add(stackPane);
    }

    public void drawSemiCircleChart(String name, int achieve){
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox();

        ObservableList<CircleChart.Data> dataList = FXCollections.observableArrayList(
                new CircleChart.Data(achieve, "Achieve : "+achieve, Color.LIMEGREEN),
                new CircleChart.Data(10-achieve, "Total", Color.LIGHTGRAY)
        );

        CircleChart chart = new CircleChart(180, dataList, 0, 0, 100,85,0);

        Label labelName = new Label(name);
        Label labelAchieve = new Label(achieve+"");

        vBox.getChildren().addAll(labelName, labelAchieve);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
//        vBox.setStyle("-fx-border-styleCSS.txt: black; -fx-border-width: 1");
        labelName.setFont(new Font(15));
//        labelAchieve.setPadding(new Insets(0,5,0,5));
//        labelAchieve.setStyle("-fx-background-radius: 100; -fx-background-styleCSS.txt: lawngreen");
//        stackPane.setStyle("-fx-border-styleCSS.txt: black; -fx-border-width: 1");
        stackPane.setMargin(vBox,new Insets(60,60,0,60));
        stackPane.getChildren().addAll(chart, vBox);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        semiPane.getChildren().add(stackPane);
    }


    @FXML
    void closeDrawer(MouseEvent event) {
//        System.out.println("click image");
//        TabStudentController.getInstance().displayDrawer();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] subjects = new String[]{"MATH","PHYSICS","CHEMISTRY","ENGLISH","HISTORY","BIOLOGY","GEOGRAPHY"};
        for(String subject : subjects) {
            int rand = new Random().nextInt(10);
            drawSemiCircleChart(subject, rand);
        }
        drawDoughnutChart();
    }
}
