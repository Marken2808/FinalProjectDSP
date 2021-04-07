import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;


class MainTest extends ApplicationTest {

    @BeforeEach
    void setUp() throws Exception {
        ApplicationTest.launch(Main.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
    }

    @AfterEach
    void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }



}