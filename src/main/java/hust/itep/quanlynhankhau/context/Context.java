package hust.itep.quanlynhankhau.context;

import hust.itep.quanlynhankhau.model.Account;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Context {
    private static Account account;

    private static Stage mainStage;

    public static void setCurrentAccount(Account account) {
        Context.account = account;
    }

    public static Account getAccount() {
        return account;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }
    public static final Image ICON = new Image(Objects.requireNonNull(Context.class.getResourceAsStream("/img/main-icon.png")));

    public static void setLoadingCursor() {
        mainStage.getScene().setCursor(Cursor.WAIT);
    }

    public static void setDefaultCursor() {
        mainStage.getScene().setCursor(Cursor.DEFAULT);
    }
}
