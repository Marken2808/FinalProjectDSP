package main.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.utils.CircleChart;
import main.utils.DBbean;
import main.utils.PopUp;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class DrawerViewStudent implements Initializable {



    @FXML
    private StackPane stackPanehere;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXMasonryPane semiPane;

    @FXML
    private StackPane doughnutPane;

    public double[] moduleLists;

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

    public void drawSemiCircleChart(String name, double achieve){
        StackPane stackPane = new StackPane();
        JFXButton btnPane = new JFXButton();
        VBox vBox = new VBox();

        ObservableList<CircleChart.Data> dataList = FXCollections.observableArrayList(
                new CircleChart.Data(achieve, "Achieve : "+achieve, Color.LIMEGREEN),
                new CircleChart.Data(10-achieve, "Total", Color.LIGHTGRAY)
        );

        CircleChart chart = new CircleChart(180, dataList, 0, 0, 100,90,0);

        Label labelName = new Label(name);
        Label labelAchieve = new Label(achieve+"");

        vBox.getChildren().addAll(labelName, labelAchieve);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        labelName.setFont(new Font(15));
//        stackPane.setMargin(vBox,new Insets(0,0,0,0));
        stackPane.getChildren().addAll(chart, vBox);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        btnPane.setGraphic(stackPane);
        semiPane.getChildren().add(btnPane);

        btnPane.setOnMouseClicked(event -> {
            Platform.runLater(() -> {

                System.out.println("Clicked: "+ name);
                String AttendanceScreen = "/main/views/AttendanceScreen.fxml";
//            MainController.getInstance().popUp(AttendanceScreen,true);

                PopUp test = new PopUp(AttendanceScreen, true, stackPanehere);
                test.showPopUp();
            });
        });
    }

    public void displaySeCiChart(){
        String[] moduleName = new String[]{"MATH","PHYSICS","CHEMISTRY","ENGLISH","HISTORY","BIOLOGY","GEOGRAPHY"};

        int id = TabStudent.id;
        double[] moduleData = DBbean.getModuleData(id);
        moduleLists = Arrays.copyOfRange(moduleData,1,moduleData.length-1);

        for(int i=0; i<moduleLists.length; i++) {
            drawSemiCircleChart(moduleName[i], moduleLists[i]);
        }
//        mathPane.getChildren().add(drawSemiCircleChart(moduleName[0],moduleLists[0]));
//        btnPane.setGraphic(drawSemiCircleChart(moduleName[0],moduleLists[0]));
//        System.out.println(Arrays.toString(moduleLists));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displaySeCiChart();
        drawDoughnutChart();
    }
}
