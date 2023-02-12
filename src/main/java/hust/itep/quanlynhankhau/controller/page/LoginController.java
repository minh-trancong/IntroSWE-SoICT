package hust.itep.quanlynhankhau.controller.page;

import hust.itep.quanlynhankhau.Main;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.layout.FooterController;
import hust.itep.quanlynhankhau.service.dao.AccountDao;
import hust.itep.quanlynhankhau.model.Account;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class LoginController {
    private static final String KEY = "/fxml/page/login.fxml";
    public static String getKey() {
        return KEY;
    }
    @FXML
    private MFXTextField usernameTextField;
    @FXML
    private MFXPasswordField passwordField;
    @FXML
    private MFXButton loginButton;
    @FXML
    private Label messageLabel;
    @FXML
    public void initialize() {
        initializeButton();
    }

    public static Account currentAccount = null;

    public void initializeButton() {
        loginButton.setOnMouseClicked(e -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            Main.getStage().getScene().setCursor(Cursor.WAIT);

            new Thread(() -> {
                AccountDao accountDao = new AccountDao();
                ArrayList<Account> accounts = new ArrayList<>(accountDao.getAll(Account.class));

                Account current = null;

                for (Account account : accounts) {
                    if (account.getUsername().equals(username)
                            && account.getPassword().equals(password)) {
                        current = account;
                    }
                }

                currentAccount = current;

                Platform.runLater(() -> {
                    Main.getStage().getScene().setCursor(Cursor.DEFAULT);
                });

                if (current == null) {
                    return;
                }

                Platform.runLater(() -> {
                    PageManager.setHeader(HeaderController.getKey(), new HeaderController());
                    PageManager.setPage(HomeController.getKey());
                    PageManager.setFooter(FooterController.getKey(), new FooterController());
                });
            }).start();
        });
    }
}
