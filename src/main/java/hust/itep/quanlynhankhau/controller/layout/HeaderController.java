package hust.itep.quanlynhankhau.controller.layout;

import hust.itep.quanlynhankhau.controller.page.household.HouseholdController;
import hust.itep.quanlynhankhau.controller.page.population.*;
import hust.itep.quanlynhankhau.controller.page.HomeController;
import hust.itep.quanlynhankhau.controller.page.LoginController;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    private MenuItem populationMenuItem;

    @FXML
    private MenuItem householdMenuItem;

    public HeaderController() {


    }

    private void initializeMenuBar() {


    }
    private void initializeLogoutButton() {
        logoutButton.setOnMouseClicked(e -> {
            PageManager.clearHeader();
            PageManager.clearFooter();
            PageManager.setPage(LoginController.getKey(), new LoginController());
        });
    }

    private void initializePopulationMenu() {
        registerTemporaryResidenceMenuItem.setOnAction(e -> {
            PageManager.setPage(RegisterTemporaryResidenceController.getKey(), new RegisterTemporaryResidenceController());
        });

        registerTemporaryAbsenceMenuItem.setOnAction(e -> {
            PageManager.setPage(RegisterTemporaryAbsenceController.getKey(), new RegisterTemporaryAbsenceController());
        });

        deathDeclarationMenuItem.setOnAction(e -> {
            PageManager.setPage(DeathDeclarationController.getKey(), new DeathDeclarationController());
        });

        populationMenuItem.setOnAction(e -> {
            PageManager.setPage(PopulationController.getKey(), new PopulationController());
        });
    }


    private void initializeHouseholdMenu() {
        householdMenuItem.setOnAction(e -> {
            PageManager.setPage(HouseholdController.getKey(), new HouseholdController());
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
            PageManager.setPage(HomeController.getKey(), new HomeController());
        });

        initializeMenuBar();
        initializeLogoutButton();
        initializePopulationMenu();
        initializeHouseholdMenu();
        initializeCovidMenu();
    }




}
