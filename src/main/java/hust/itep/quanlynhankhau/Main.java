package hust.itep.quanlynhankhau;

import hust.itep.quanlynhankhau.controller.MainController;
import hust.itep.quanlynhankhau.model.Account;
import hust.itep.quanlynhankhau.service.dao.AccountDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        loader.setController(new MainController());
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = primaryStage;
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Quản Lý Nhân Khẩu");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/main-icon.png")));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
