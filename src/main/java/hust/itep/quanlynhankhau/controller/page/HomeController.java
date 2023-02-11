package hust.itep.quanlynhankhau.controller.page;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class HomeController {
    public final static String KEY = "/fxml/page/home.fxml";

    public static String getKey() {
        return KEY;
    }

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {

    }

    private void initializeButton() {

    }
}
