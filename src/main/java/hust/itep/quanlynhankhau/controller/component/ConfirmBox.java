package hust.itep.quanlynhankhau.controller.component;

import hust.itep.quanlynhankhau.context.Context;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmBox {
    @FXML
    MFXButton yesButton;

    @FXML
    MFXButton noButton;

    @FXML
    Label messageLabel;

    private String message;

    public ConfirmBox(String message) {
        this.message = message;
    }

    @FXML
    public void initialize() {
        yesButton.setOnAction(e -> {
            answer = true;
            stage.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            stage.close();
        });
        messageLabel.setText(message);
    }

    static boolean answer;

    static Stage stage;

    private static final String KEY = "/fxml/component/confirm-box.fxml";
    public static boolean display(String title, String message) {
        stage = new Stage();
        stage.getIcons().add(Context.ICON);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader loader = new FXMLLoader(ConfirmBox.class.getResource(KEY));
        loader.setController(new ConfirmBox(message));

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.showAndWait();

        return ConfirmBox.answer;
    }



}
