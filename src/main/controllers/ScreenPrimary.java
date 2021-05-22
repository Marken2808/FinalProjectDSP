package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.*;
import utils.OpenCV;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ScreenPrimary implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    public StackPane mainStackPane;

    public static StackPane mainStackPaneClone;

    @FXML
    public StackPane header;

    public static StackPane headerClone;

    @FXML
    private JFXButton btnRestore;

    @FXML
    private JFXButton btnMinimise;

    @FXML
    private JFXButton btnClose;

    @FXML
    private Label labelTitle;

    private static Label labelTitleClone;

    @FXML
    private JFXDrawer drawerPane;

    public static JFXDialog dialog;

    private static String SignInPopup      = "/views/PopupSignIn.fxml";
    private static String DrawerMenu       = "/views/DrawerMenu.fxml";
    private static String DashboardScreen  = "/views/ScreenDashboard.fxml";
    private static String CameraScreen     = "/views/ScreenCamera.fxml";
    private static String OverviewScreen   = "/views/ScreenOverview.fxml";
    private static String ProfileScreen    = "/views/ScreenProfile.fxml";
//----------------------------instance--------------------

//    public static ScreenPrimary instance;
//    public ScreenPrimary(){
//        instance = this;
//    }
//    public static ScreenPrimary getInstance() {
//        if(instance == null){
//            instance = new ScreenPrimary();
//        }
//        return instance;
//    }
//---------------------------------------------------------

    public static void displayPopup(String screen, boolean canClose){
        try {
            FXMLLoader loader = new FXMLLoader(ScreenPrimary.class.getResource(screen));
            Parent Root = loader.load();
            JFXDialogLayout content = new JFXDialogLayout();
            content.setBody(Root);
            dialog = new JFXDialog(mainStackPaneClone, content , JFXDialog.DialogTransition.CENTER);
            dialog.setOverlayClose(canClose);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void isMenuClicked(MouseEvent event) throws IOException {
        if(ScreenCamera.getInstance().isCameraActive()) {     // grant turn off to use drawer
            displayDrawer(0.0, 40.0, 0.0, 0.0);
            drawerPane.open();
            drawerPane.setVisible(true);

            drawerPane.setOnDrawerClosed(jfxDrawerEvent -> {
                drawerPane.close();
                drawerPane.setVisible(false);
            });
        }
    }

    public void displayDrawer(Double left,Double top,Double right,Double bottom) {
        try {
            mainAnchorPane.setLeftAnchor(drawerPane, left);
            mainAnchorPane.setTopAnchor(drawerPane, top);
            mainAnchorPane.setRightAnchor(drawerPane, right);
            mainAnchorPane.setBottomAnchor(drawerPane,bottom);

            VBox menuLeft = FXMLLoader.load(getClass().getResource(DrawerMenu));
            drawerPane.setSidePane(menuLeft);
            drawerPane.setDefaultDrawerSize(mainAnchorPane.getWidth()*0.2);
            showElements(menuLeft);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayScreen(String title, String screen, boolean isAllow){
        if(isAllow) {
            mainStackPaneClone.getChildren().clear();
            try {
                StackPane screenPane = FXMLLoader.load(ScreenPrimary.class.getResource(screen));
                labelTitleClone.setText(title);
//            labelTitle.setFont(new Font("Times New Roman",20));
                mainStackPaneClone.getChildren().add(screenPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mainStackPaneClone.getChildren().clear();
        }
    }

    public static boolean check (String role, boolean isRequired) {

        if (role.equals("Student") && isRequired){
            System.out.println("Sorry, Student in developing. Cannot access!");
            return false;
        } else {
            return true;
        }
    }

//    --------------------------------------------
    public static void displaySignIn(){
        headerClone.setVisible(false);
        displayPopup(SignInPopup,false);
    }

    public static void displayProfile() {
        displayScreen("Profile", ProfileScreen, check(PopupSignIn.userData().getRole(), false));
    }

    public static void displayOverview() {
        displayScreen("Overview", OverviewScreen, check(PopupSignIn.userData().getRole(), true));
    }

    public static void displaySettings() {

    }

    public static void displayCamera() {
        displayScreen("Live Camera", CameraScreen, check(PopupSignIn.userData().getRole(), true));
    }

    public static void displayDashboard() {
        displayScreen("Dashboard", DashboardScreen, check(PopupSignIn.userData().getRole(), true));
    }
//    -------------------------------------------------

    public void showElements(VBox menuLeft) {

        ObservableList<Node> DrawerMenu = menuLeft.getChildren();
        ObservableList<Node> DrawerBoxes = ((VBox) DrawerMenu.get(1)).getChildren();

        for (Node node : DrawerBoxes) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "Profile" :
                            displayProfile();
                            break;
                        case "Home":
                            displayOverview();
                            break;
                        case "Settings":
                            System.out.println("Setting in developing");
                            break;
                        case "Camera":
                            displayCamera();
                            break;
                        case "Dashboard":
                            displayDashboard();
                            break;
                    }

                });
            }
        }


    }

    @FXML
    void clickClose(MouseEvent event) throws InterruptedException {
        ScreenCamera.getInstance().isCameraActive();
        ((Stage) btnClose.getScene().getWindow()).close();

        File testClose = new File(OpenCV.getInstance().testPath);
        for(File file : testClose.listFiles()){
            if( !file.getPath().contains("Image_0.jpg")){
                System.gc();
                Thread.sleep(100);
                file.delete();
            }
        }

        File datasetClose = new File(OpenCV.getInstance().datasetPath);
        for (File file : datasetClose.listFiles()){
            file.delete();
        }

        System.out.println("Tidy works");
    }

    @FXML
    void clickMinimise(MouseEvent event) {
        ((Stage) btnMinimise.getScene().getWindow()).setIconified(true);
    }

    @FXML
    void clickRestore(MouseEvent event) {
        Stage restore = ((Stage) btnRestore.getScene().getWindow());
        if(restore.isMaximized()){
            restore.setMaximized(false);
        } else {
            restore.setMaximized(true);
        }
    }

//    public static User userData() {
//
//        User authUser = PopupSignIn.authUser();
//
//        switch (authUser.getRole()){
//            case "Teacher" :
//                Teacher imTeacher = new TeacherDAO().retrieve(authUser.getUserID());
//                authUser.setTeacher(imTeacher);
//                break;
//            case "Student" :
//                Student imStudent = new StudentDAO().retrieve(authUser.getUserID());
//                authUser.setStudent(imStudent);
//                break;
//            case "Admin" :
//                authUser.setRole("Admin");
//                break;
//        }
//        return authUser;
//    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainStackPaneClone = mainStackPane;
        headerClone = header;
        labelTitleClone = labelTitle;
        displaySignIn();


    }


}
