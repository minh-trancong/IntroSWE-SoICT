package hust.itep.quanlynhankhau.controller.component.popup;

import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class InformativeBox {
    private static final String KEY = "/fxml/component/informative-box.fxml";

    @FXML
    MFXButton yesButton;

    @FXML
    Label messageLabel;

    private String message;

    public InformativeBox(String message) {
        this.message = message;
    }

    @FXML
    public void initialize() {
        yesButton.setOnAction(e -> {
            stage.close();
        });
        messageLabel.setText(message);
    }

    public static void display(String title, String message) {
        stage = StageFactory.buildStage(title);

        FXMLLoader loader = new FXMLLoader(ConfirmBox.class.getResource(KEY));
        loader.setController(new InformativeBox(message));

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.showAndWait();
    }

    static Stage stage;
}
