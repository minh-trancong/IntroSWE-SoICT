package hust.itep.quanlynhankhau.controller.utility;

import hust.itep.quanlynhankhau.context.Context;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class PageManager {
    private static HashMap<String, Object> pageMap = new HashMap<>();

    private static BorderPane mainContainer;
    public static BorderPane getMainContainer() {
        return mainContainer;
    }

    public static void setMainContainer(BorderPane container) {
        if (mainContainer != null) {
            System.out.println("Main container has been set");
            return;
        }

        mainContainer = container;
    }

    public static void addPage(String key, Object controller) {
        if (pageMap.containsKey(key)) {
            System.out.println(key  + " is already in the map");
            return;
        }
        pageMap.put(key, controller);
    }
    public static void removePage(String key) {
        pageMap.remove(key);
    }


    public static void setPageDialog(String key, Object controller, Stage stage) {
        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                Context.setLoadingCursor();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(key));
                loader.setController(controller);
                return loader.load();
            }
        };

        loadTask.setOnSucceeded(e -> {
            Context.setDefaultCursor();
            Parent root = loadTask.getValue();
            stage.setScene(new Scene(root));
            stage.show();
        });

        new Thread(loadTask).start();
    }

    public static void setPageDialog(String key, Stage stage) {
        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                Context.setLoadingCursor();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(key));
                loader.setController(pageMap.get(key));
                return loader.load();
            }
        };

        loadTask.setOnSucceeded(e -> {
            Context.setDefaultCursor();
            Parent root = loadTask.getValue();
            stage.setScene(new Scene(root));
            stage.show();
        });

        new Thread(loadTask).start();
    }

    public static void setPageConcurrent(String key) {
        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                Context.setLoadingCursor();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(key));
                loader.setController(pageMap.get(key));
                return loader.load();
            }
        };

        loadTask.setOnSucceeded(e -> {
            Context.setDefaultCursor();
            mainContainer.setCenter(loadTask.getValue());
        });

        new Thread(loadTask).start();
    }

    public static void setPage(String key) {
        if (mainContainer == null) {
            System.out.println("Main container is null");
            return;
        }

        FXMLLoader loader = new FXMLLoader(PageManager.class.getResource(key));
        Object controller = pageMap.get(key);
        loader.setController(controller);

        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainContainer.setCenter(parent);
    }

    public static void clearHeader() {
        mainContainer.setTop(null);
    }

    public static void clearFooter() {
        mainContainer.setBottom(null);
    }


    public static void setHeaderConcurrent(String url, Object controller) {
        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                Context.setLoadingCursor();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
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

    public static void setFooterConcurrent(String url, Object controller) {
        Task<Parent> loadTask = new Task<Parent>() {
            @Override
            protected Parent call() throws Exception {
                Context.setLoadingCursor();
                FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
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

    public static void setHeader(String url, Object controller) {
        FXMLLoader loader = new FXMLLoader(PageManager.class.getResource(url));
        loader.setController(controller);
        try {
            mainContainer.setTop(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setFooter(String url, Object controller) {
        FXMLLoader loader = new FXMLLoader(PageManager.class.getResource(url));
        loader.setController(controller);
        try {
            mainContainer.setBottom(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
