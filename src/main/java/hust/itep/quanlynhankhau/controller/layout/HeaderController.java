package hust.itep.quanlynhankhau.controller.layout;

import hust.itep.quanlynhankhau.controller.page.covid.CovidStatisticsController;
import hust.itep.quanlynhankhau.controller.page.covid.CovidTestController;
import hust.itep.quanlynhankhau.controller.page.covid.DeclareMovementController;
import hust.itep.quanlynhankhau.controller.page.covid.QuarantineInformationController;
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
    private MenuItem registerTemporaryResidenceMenuItem;

    @FXML
    private Menu householdMenu;

    @FXML
    private MenuItem addHouseholdMenuItem;

    @FXML
    private Menu covidMenu;

    @FXML
    private Button logoutButton;

    @FXML
    private MenuItem populationMenuItem;

    @FXML
    private MenuItem householdMenuItem;

    @FXML
    private MenuItem populationStatisticsMenuItem;

    // Covid stuff
    @FXML
    private MenuItem declareMovementMenuItem;

    @FXML
    private MenuItem declareCovidTestMenuItem;
    @FXML
    private MenuItem declareQuarantineMenuItem;
    @FXML
    private MenuItem covidStatisticsMenuItem;


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

        populationMenuItem.setOnAction(e -> {
            PageManager.setPage(PopulationController.getKey(), new PopulationController());
        });


        populationStatisticsMenuItem.setOnAction(e -> {
            PageManager.setPage(PopulationStatisticsController.getKey(), new PopulationStatisticsController());
        });
    }


    private void initializeHouseholdMenu() {
        householdMenuItem.setOnAction(e -> {
            PageManager.setPage(HouseholdController.getKey(), new HouseholdController());
        });
    }

    private void initializeCovidMenu() {
        declareMovementMenuItem.setOnAction(e -> {
            PageManager.setPage(DeclareMovementController.getKey(), new DeclareMovementController());
        });

        declareCovidTestMenuItem.setOnAction(e -> {
            PageManager.setPage(CovidTestController.getKey(), new CovidTestController());
        });

        declareQuarantineMenuItem.setOnAction(e -> {
            PageManager.setPage(QuarantineInformationController.getKey(), new QuarantineInformationController());
        });

        covidStatisticsMenuItem.setOnAction(e -> {
            PageManager.setPage(CovidStatisticsController.getKey(), new CovidStatisticsController());
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
