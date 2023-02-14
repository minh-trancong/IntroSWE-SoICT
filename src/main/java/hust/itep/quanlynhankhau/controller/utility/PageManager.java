package hust.itep.quanlynhankhau.controller.utility;

import hust.itep.quanlynhankhau.context.Context;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class PageManager {

    private static Object currentController;
    private static String currentFXML;
    private static BorderPane mainContainer;

    public static void setMainContainer(BorderPane container) {
        if (mainContainer != null) {
            System.out.println("Main container has been set");
            return;
        }

        mainContainer = container;
    }

    // Only call on first page
    public static void setPageCurrentThread(String fxml, Object controller) {
        FXMLLoader loader = new FXMLLoader(PageManager.class.getResource(fxml));
        loader.setController(controller);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainContainer.setCenter(parent);
    }

    public static void setPage(String fxml, Object controller) {
        currentFXML = fxml;
        currentController = controller;

        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                Context.setLoadingCursor();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(currentFXML));
                loader.setController(currentController);
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return parent;
            }
        };

        loadTask.setOnSucceeded(e -> {
            Context.setDefaultCursor();
            mainContainer.setCenter(loadTask.getValue());
        });

        new Thread(loadTask).start();
    }

    public static void setHeader(String fxml, Object controller) {
        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                Context.setLoadingCursor();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                loader.setController(controller);
                return loader.load();
            }
        };

        loadTask.setOnSucceeded(e -> {
            Context.setDefaultCursor();
            mainContainer.setTop(loadTask.getValue());
        });

        new Thread(loadTask).start();
    }

    public static void setFooter(String fxml, Object controller) {
        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                Context.setLoadingCursor();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                loader.setController(controller);
                return loader.load();
            }
        };

        loadTask.setOnSucceeded(e -> {
            Context.setDefaultCursor();
            mainContainer.setBottom(loadTask.getValue());
        });

        new Thread(loadTask).start();
    }

    public static void clearFooter() {
        mainContainer.setBottom(null);
    }

    public static void clearHeader() {
        mainContainer.setTop(null);
    }

    public static void refreshCurrentPage() {
        setPage(currentFXML, currentController);
    }
}
