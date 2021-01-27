package main.utils;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import main.controllers.MainController;

import java.io.IOException;

public class PopUp {

    private String screen;
    private boolean canClose;
    private JFXDialog dialog;
    private StackPane stackPane;

    public PopUp(String screen, boolean canClose, StackPane stackPane) {
        this.screen = screen;
        this.canClose = canClose;
        this.stackPane = stackPane;
    }

    public void showPopUp(){
        try {

            FXMLLoader loader = new FXMLLoader(MainController.class.getResource(screen));
            Parent Root = loader.load();
            JFXDialogLayout content = new JFXDialogLayout();
            content.setBody(Root);
            dialog = new JFXDialog(stackPane, content , JFXDialog.DialogTransition.CENTER);
            dialog.setOverlayClose(canClose);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
