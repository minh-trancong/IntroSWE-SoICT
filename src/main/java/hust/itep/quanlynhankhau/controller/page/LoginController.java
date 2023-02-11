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
            /*
            Optional<Account> account = accountDao.get(username);
            if (!account.isPresent()) {
                messageLabelError();
                return;
            }

            if (!account.get().getPassword().equals(password)) {
                messageLabelError();
                return;
            }

            PageManager.setHeader(HeaderController.getKey(), new HeaderController(account.get().getRole()));
            PageManager.setPage(HomeController.getKey());
            PageManager.setFooter(FooterController.getKey(), new FooterController());
            PageManager.cacheAll();
             */

        });
    }

    public void messageLabelError() {
        messageLabel.setText("Wrong info");
        messageLabel.setTextFill(Color.RED);
        messageLabel.setVisible(true);
    }
}
