package hust.itep.quanlynhankhau.controller.page;

import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.layout.HeaderController;
import hust.itep.quanlynhankhau.controller.layout.FooterController;
import hust.itep.quanlynhankhau.service.dao.AccountDao;
import hust.itep.quanlynhankhau.model.Account;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public void initializeButton() {
        loginButton.setOnMouseClicked(e -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            AccountDao accountDao = new AccountDao();
            ArrayList<Account> accounts = new ArrayList<>(accountDao.getAll());

            Account current = null;

            for (Account account : accounts) {
                if (account.getUsername().equals(username)
                        && account.getPassword().equals(password)) {
                    current = account;
                }
            }

            if (current == null) {
                return;
            }

            PageManager.setHeader(HeaderController.getKey(), new HeaderController(current.getRole()));
            PageManager.setPage(HomeController.getKey());
            PageManager.setFooter(FooterController.getKey(), new FooterController());
            PageManager.cacheAll();

        });
    }
}
