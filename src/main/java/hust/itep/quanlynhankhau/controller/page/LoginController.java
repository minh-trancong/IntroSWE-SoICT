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
import javafx.scene.control.Label;

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
        loginButton.setOnAction(e -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            AccountDao accountDao = new AccountDao();
            Account account = accountDao.getByUsername(username);

            if (account == null) {
                return;
            }

            if (!password.equals(account.getPassword())) {
                return;
            }

            PageManager.setPageConcurrent(HomeController.getKey());
            PageManager.setHeaderConcurrent(HeaderController.getKey(), new HeaderController());
            PageManager.setFooterConcurrent(FooterController.getKey(), new FooterController());
        });
    }
}
