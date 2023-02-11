package hust.itep.quanlynhankhau.controller.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

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

    public static void cacheAll() {
        pageMap.forEach((key, value) -> {
           FXMLLoader loader = new FXMLLoader(PageManager.class.getResource(key));
           loader.setController(value);
           try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
