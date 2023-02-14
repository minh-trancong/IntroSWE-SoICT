package hust.itep.quanlynhankhau.controller.utility;

import hust.itep.quanlynhankhau.context.Context;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class PopupManager {
    private static Stage currentStage;
    private static String currentFXML;

    private static Object currentController;
    private static HashMap<String, Object> popupMap = new HashMap<>();

    public static void addPopup(String key, Object controller) {
        popupMap.putIfAbsent(key, controller);
    }

    public static void setPopup(String fxml, Object controller, Stage stage) {
        currentStage = stage;
        currentFXML = fxml;
        currentController = controller;
        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                Context.setLoadingCursor();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(currentFXML));
                loader.setController(controller);
                return loader.load();
            }
        };

        loadTask.setOnSucceeded(e -> {
            Context.setDefaultCursor();
            Parent root = loadTask.getValue();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        });

        new Thread(loadTask).start();
    }

    public static void refreshCurrentStage() {
        setPopup(currentFXML, currentController, currentStage);
    }

    public static void closeCurrentStage() {
        if (currentStage.isShowing()) {
            currentStage.close();
        }
    }
}
