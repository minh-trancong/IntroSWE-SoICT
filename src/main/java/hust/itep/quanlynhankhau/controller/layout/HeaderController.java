package hust.itep.quanlynhankhau.controller.layout;

import hust.itep.quanlynhankhau.Main;
import hust.itep.quanlynhankhau.controller.page.population.DeathDeclarationController;
import hust.itep.quanlynhankhau.controller.page.population.RegisterTemporaryAbsenceController;
import hust.itep.quanlynhankhau.controller.page.population.RegisterTemporaryResidenceController;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.page.HomeController;
import hust.itep.quanlynhankhau.controller.page.LoginController;
import hust.itep.quanlynhankhau.controller.page.household.AddHouseholdController;
import hust.itep.quanlynhankhau.controller.page.population.AddPopulationController;
import hust.itep.quanlynhankhau.model.Account;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HeaderController {
    private static final String KEY = "/fxml/layout/header.fxml";

    public static String getKey() {
        return KEY;
    }

    @FXML
    private Label homeLabel;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu populationMenu;

    @FXML
    private MenuItem addPopulationMenuItem;

    @FXML
    private MenuItem registerTemporaryResidenceMenuItem;

    @FXML
    private Menu householdMenu;

    @FXML
    private MenuItem addHouseholdMenuItem;

    @FXML
    private Menu covidMenu;

    @FXML
    private MenuItem declareMedicalMenuItem;

    @FXML
    private MenuItem registerTemporaryAbsenceMenuItem;

    @FXML
    private MenuItem deathDeclarationMenuItem;

    @FXML
    private Button logoutButton;


    public HeaderController() {


    }

    private void initializeMenuBar() {


    }
    private void initializeLogoutButton() {
        logoutButton.setOnMouseClicked(e -> {
            PageManager.clearHeader();
            PageManager.clearFooter();
            PageManager.setPage(LoginController.getKey());
        });
    }

    private void initializePopulationMenu() {
        addPopulationMenuItem.setOnAction(e -> {
            Main.getStage().getScene().setCursor(Cursor.WAIT);
            new Thread(() -> {
                Platform.runLater(() -> {
                    PageManager.setPage(AddPopulationController.getKey());
                    Main.getStage().getScene().setCursor(Cursor.DEFAULT);
                });
            }).start();
        });

        registerTemporaryResidenceMenuItem.setOnAction(e -> {
            Main.getStage().getScene().setCursor(Cursor.WAIT);
            new Thread(() -> {
                Platform.runLater(() -> {
                    PageManager.setPage(RegisterTemporaryResidenceController.getKey());
                    Main.getStage().getScene().setCursor(Cursor.DEFAULT);
                });
            }).start();
        });

        registerTemporaryAbsenceMenuItem.setOnAction(e -> {
            Main.getStage().getScene().setCursor(Cursor.WAIT);
            new Thread(() -> {
                Platform.runLater(() -> {
                    PageManager.setPage(RegisterTemporaryAbsenceController.getKey());
                    Main.getStage().getScene().setCursor(Cursor.DEFAULT);
                });
            }).start();
        });

        deathDeclarationMenuItem.setOnAction(e -> {
            Main.getStage().getScene().setCursor(Cursor.WAIT);
            new Thread(() -> {
                Platform.runLater(() -> {
                    PageManager.setPage(DeathDeclarationController.getKey());
                    Main.getStage().getScene().setCursor(Cursor.DEFAULT);
                });
            }).start();
        });
    }

    private void initializeHouseholdMenu() {


        addHouseholdMenuItem.setOnAction(e -> {
            Main.getStage().getScene().setCursor(Cursor.WAIT);
            new Thread(() -> {
                Platform.runLater(() -> {
                    PageManager.setPage(AddHouseholdController.getKey());
                    Main.getStage().getScene().setCursor(Cursor.DEFAULT);
                });
            }).start();
        });
    }

    private void initializeCovidMenu() {
        declareMedicalMenuItem.setOnAction(e -> {
            System.out.println("Khai báo y tế");
        });
    }

    @FXML
    public void initialize() {
        homeLabel.setOnMouseClicked(e -> {
            PageManager.setPage(HomeController.getKey());
        });

        initializeMenuBar();
        initializeLogoutButton();
        initializePopulationMenu();
        initializeHouseholdMenu();
        initializeCovidMenu();
    }




}
