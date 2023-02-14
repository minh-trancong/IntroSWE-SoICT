package hust.itep.quanlynhankhau.controller.layout;

import hust.itep.quanlynhankhau.controller.page.household.HouseholdController;
import hust.itep.quanlynhankhau.controller.page.population.*;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.page.HomeController;
import hust.itep.quanlynhankhau.controller.page.LoginController;
import hust.itep.quanlynhankhau.controller.page.household.AddHouseholdController;
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
    private MenuItem viewPopulation;
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
            PageManager.setPage(LoginController.getKey());
        });
    }

    private void initializePopulationMenu() {
        addPopulationMenuItem.setOnAction(e -> {
            PageManager.setPageConcurrent(AddPopulationController.getKey());
        });

        registerTemporaryResidenceMenuItem.setOnAction(e -> {
            PageManager.setPageConcurrent(RegisterTemporaryAbsenceController.getKey());
        });

        registerTemporaryAbsenceMenuItem.setOnAction(e -> {
            PageManager.setPageConcurrent(RegisterTemporaryAbsenceController.getKey());
        });

        deathDeclarationMenuItem.setOnAction(e -> {
            PageManager.setPageConcurrent(DeathDeclarationController.getKey());
        });

        viewPopulation.setOnAction(e -> {
            PageManager.setPageConcurrent(ViewPopulationController.getKey());
        });

        populationMenuItem.setOnAction(e -> {
            PageManager.setPageConcurrent(PopulationController.getKey());
        });
    }


    private void initializeHouseholdMenu() {
        addHouseholdMenuItem.setOnAction(e -> {
            PageManager.setPageConcurrent(AddHouseholdController.getKey());
        });

        householdMenuItem.setOnAction(e -> {
            PageManager.setPageConcurrent(HouseholdController.getKey());
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
