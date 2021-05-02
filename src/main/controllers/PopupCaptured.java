package controllers;

import com.jfoenix.controls.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import models.Face;
import models.Student;
import models.User;
import org.opencv.imgcodecs.Imgcodecs;
import utils.OpenCV;
import utils.UtilsOCV;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PopupCaptured implements Initializable {

    @FXML
    private ImageView captImg;

    @FXML
    private JFXComboBox<String> comboPic;

    @FXML
    private VBox boxData;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    public HBox hboxInfor;

    @FXML
    private Label textInfor;

    @FXML
    private ImageView picInfor;

    private JFXTreeView<?> treeviewSub = new JFXTreeView();
    private Map<String, String> choice = new HashMap<>();

    JFXComboBox boxId = new JFXComboBox();

    JFXTextField fieldName = new JFXTextField();

    JFXComboBox boxSet = new JFXComboBox();

    JFXTextField fieldPhone = new JFXTextField();

    JFXDatePicker fieldDob = new JFXDatePicker();

    JFXTextField username = new JFXTextField();

    JFXTextField password = new JFXTextField();

    String imgPath;
    OpenCV callCV = OpenCV.getInstance();
    StringBuilder sb = new StringBuilder();

    public void submitNew() {
        isFulfill();

        Timeline timeline = new Timeline( new KeyFrame( Duration.millis(200), ae -> {
            if (isFulfill()) {
                //                    write image on file
                int id = Integer.parseInt(String.valueOf(boxId.getValue()));
                String name = fieldName.getText();
                int set = Integer.parseInt(String.valueOf(boxSet.getValue()));
                Student student = new Student(id,name);
                Face face = new Face(imgPath,set,student);

                Imgcodecs.imwrite(
                    callCV.datasetPath + id + "-" + name + "_" + set + ".jpg",
                    UtilsOCV.bufferedImageToMat(
                            SwingFXUtils.fromFXImage(this.captImg.getImage(), null)
                    )
                );

                try {
                    new User().insertStudentData(student, face);
                    handlePopup(
                            "Success",
                            "Face Inserted",
                            "/images/icon/check-circle_green.png",
                            "OK"
                    );
                } catch (SQLException e) {
                    handlePopup(
                            "Fail",
                            "Face Exist",
                            "/images/icon/alert-circle_red.png",
                            "Close"
                    );
                }

                ScreenPrimary.dialog.close();
            }
        }));
        timeline.play();

    }

    public void handlePopup(String title, String content, String img, String type){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String InforScreen = "/views/PopupInfor.fxml";
                ScreenPrimary.getInstance().displayPopup(InforScreen,true);
                PopupInfor.getInstance().setDialog(title,content,img,type);
            }
        });
    }

    public boolean isEmpty(Object obj){

//        if empty is false, else true;
        if (obj instanceof JFXComboBox){
            return (((JFXComboBox) obj).getValue().equals("")? true: false);
        } else if( obj instanceof JFXTextField){
            return (((JFXTextField) obj).getText().equals("")? true: false);
        }
        return true;
    }

    public void lineInfor(Label text, ImageView pic, String type){
        hboxInfor.setVisible(true);
        if (type.equals("Success")){
            text.setText("Perfect, done");
            text.setStyle("-fx-text-fill: #06dd06");
            pic.setImage(new Image("/images/icon/check-circle_green.png"));
        } else if (type.equals("Fail")){
            text.setText("Please, provide all variable above");
            text.setStyle("-fx-text-fill: #f90606");
            pic.setImage(new Image("/images/icon/alert-circle_red.png"));
        }
    }
    public boolean isFulfill() {

        if ((!isEmpty(fieldName) && !isEmpty(boxId) && !isEmpty(boxSet))) {    // true: not empty
//            System.out.println("all filled: " + !isEmpty(boxID)+!isEmpty(fieldName) + !isEmpty(boxSet));
            lineInfor(textInfor, picInfor, "Success");
            return true;
        } else {
//            System.out.println("empty: " + !isEmpty(boxID)+!isEmpty(fieldName) + !isEmpty(boxSet));
            lineInfor(textInfor, picInfor, "Fail");
            return false;
        }
    }

    public void clear(){
        sb.setLength(0);
        boxId.setValue(null);
        fieldName.setText(null);
        boxSet.setValue(null);
        isFulfill();
    }

//    public boolean isDigit(String string) {
//
//        for (char c : string.toCharArray()) {
//            if (!Character.isDigit(c)) {
//                return false;
//            }
//        }
//        return true;
//    }

    public void chooseID(KeyEvent keyEvent) {

        String input = keyEvent.getText();

        sb.append(input);

        try {
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE) || keyEvent.getCode().equals(KeyCode.DELETE)) {
                sb.setLength(sb.length() - 1);
                if (sb.toString().isEmpty()){
                    sb.append(0);
                }
            }

            System.out.println(sb.toString());

            int toIntID = Integer.parseInt(sb.toString().replaceAll("\\s+", ""));
            ArrayList<Integer> toListSet = getListSet(toIntID);

            fieldName.setText(getListName(toIntID));
            boxSet.setItems(FXCollections.observableList(toListSet));
            boxSet.setValue(toListSet.get(toListSet.size() - 1) + 1);

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            fieldName.setText(null);
            boxSet.setValue(null);
        }

    }

    public ArrayList<Integer> getListSet(int id){
        ArrayList<Integer> listSet = new ArrayList<>();
//        System.out.println("check: "+callCV.imageFiles.length);
        for (int i = 0; i < callCV.imageFiles.length; i++) {
            if(callCV.namesList[i][0].equals(id)) {
                listSet.add((int) callCV.namesList[i][2]);
            }
        }
        return listSet;
    }

    public String getListName(int id){
        for (int i = 0; i < callCV.imageFiles.length; i++) {
            if(callCV.namesList[i][0].equals(id)) {
                return (String) callCV.namesList[i][1];
            }
        }
        return null;
    }

    public Node display_mainView() {
        boxId.setPromptText("ID");
        boxId.setLabelFloat(true);
        boxId.setEditable(true);
        boxId.setPrefWidth(70);
        sb.append(boxId.getValue());
        boxId.setOnKeyReleased(e->{ chooseID(e); });

        fieldName.setLabelFloat(true);
        fieldName.setPromptText("Name");
        fieldName.setAlignment(Pos.CENTER);

        boxSet.setPromptText("Set");
        boxSet.setLabelFloat(true);
        boxSet.setEditable(true);
        boxSet.setPrefWidth(50);

        JFXButton btnClear = new JFXButton();
        ImageView img = new ImageView(new Image("/images/icon/x.png"));
        img.setFitWidth(15); img.setFitHeight(15);
        btnClear.setGraphic(img);
        btnClear.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnClear.setOnMouseClicked(event -> { clear(); });

        HBox mainBox = new HBox();
        mainBox.getChildren().addAll(boxId, fieldName, boxSet, btnClear);
        mainBox.setSpacing(5);
//        mainBox.setAlignment(Pos.BOTTOM_CENTER);
        mainBox.setPadding(new Insets(6,0,0,0));

        return mainBox;
    }

    public Node display_detailView() {
        fieldPhone.setPromptText("Phone");
        fieldPhone.setLabelFloat(true);
        fieldPhone.setEditable(true);
        fieldPhone.setAlignment(Pos.CENTER);

        fieldDob.setPromptText("Birthday");
        fieldDob.setEditable(false);
        fieldDob.setPrefWidth(150);

        HBox detailsBox = new HBox();
        detailsBox.getChildren().addAll(fieldPhone, fieldDob);
        detailsBox.setSpacing(5);
        detailsBox.setAlignment(Pos.BOTTOM_CENTER);
//        detailsBox.setPadding(new Insets(5,0,5,0));

        return detailsBox;
    }

    public Node display_accountView() {

        username.setPromptText("Username");
        username.setLabelFloat(true);
        username.setEditable(true);
        username.setAlignment(Pos.CENTER);

        password.setPromptText("Password");
        password.setLabelFloat(true);
        password.setEditable(true);
        password.setAlignment(Pos.CENTER);

        HBox accountBox = new HBox();
        accountBox.getChildren().addAll(username, password);
        accountBox.setSpacing(10);
//        accountBox.setAlignment(Pos.BOTTOM_CENTER);
        accountBox.setPadding(new Insets(6,0,0,0));

        return accountBox;
    }

    public void display_SubTreeView() {




        TreeItem main = new TreeItem("");
        main.getChildren().addAll(new TreeItem(display_mainView()));
        main.setExpanded(true);

        TreeItem sub = new TreeItem("Detail");
        sub.getChildren().addAll(new TreeItem(display_detailView()));
        sub.setExpanded(false);

        TreeItem acc = new TreeItem("Account");
        acc.getChildren().addAll(new TreeItem(display_accountView()));
        acc.setExpanded(false);

        TreeItem rootItem = new TreeItem();
        rootItem.getChildren().addAll(main, sub, acc);

        treeviewSub.setRoot(rootItem);
        treeviewSub.setShowRoot(false);

        treeviewSub.setStyle("-fx-box-border: transparent");


        JFXButton btnSubmit = new JFXButton("Submit");
        btnSubmit.setOnMouseClicked(event -> { submitNew(); });

        boxData.getChildren().addAll(treeviewSub, btnSubmit);

    }

    @FXML
    void onSelectCombo(ActionEvent event) throws FileNotFoundException {
        System.out.println(comboPic.getSelectionModel().getSelectedItem());
        imgPath = choice.get(comboPic.getSelectionModel().getSelectedItem());

        if(imgPath!=null) {
            captImg.setImage(new Image(new FileInputStream(imgPath)));
        }

    }

    public void display_ImageInfoView(){
        String imgName;

        if (callCV.listRez.isEmpty()){
            imgPath = null;
        } else {
            if (callCV.listRez.size() == 1) {
                imgName = "Image_0";

                imgPath = callCV.resPath +"images/test/"+imgName+".jpg";
                choice.put(imgName,imgPath);
                comboPic.setDisable(true);
                comboPic.setPromptText(imgName);

                boxId.setValue(callCV.predictionID);
                fieldName.setText(String.valueOf(callCV.namesMap.get(callCV.predictionID)));
                boxSet.setItems(observableList);
                boxSet.setValue(0);
            } else {
                for(int i=0; i<callCV.listRez.size(); i++){
                    imgName = "Image_"+(i+1) ;
                    imgPath = callCV.resPath +"images/test/"+imgName+".jpg";
                    choice.put(imgName, imgPath);
                    comboPic.setPromptText(imgName);
                }
            }
        }

        comboPic.setItems(FXCollections.observableArrayList(choice.keySet()));

        if(imgPath!=null) {
            try {
                captImg.setImage(new Image(new FileInputStream(imgPath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }



    ObservableList<Integer> observableList = FXCollections.observableList(getListSet(callCV.predictionID));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        display_ImageInfoView();
        isFulfill();
        hboxInfor.setVisible(false);
        display_SubTreeView();


    }
}
