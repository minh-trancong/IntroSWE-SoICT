package hust.itep.quanlynhankhau.controller.layout;

import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.page.HomeController;
import hust.itep.quanlynhankhau.controller.page.LoginController;
import hust.itep.quanlynhankhau.controller.page.household.AddHouseholdController;
import hust.itep.quanlynhankhau.controller.page.population.AddPopulationController;
import hust.itep.quanlynhankhau.model.Account;
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
    private Menu householdMenu;

    @FXML
    private MenuItem addHouseholdMenuItem;

    @FXML
    private Menu covidMenu;

    @FXML
    private MenuItem declareMedicalMenuItem;

    @FXML
    private Button logoutButton;



    /*
    private Account.Role role;

    public HeaderController(Account.Role role) {
        this.role = role;

    }
     */


    private void initializeMenuBar() {
        /*
        switch (role) {
            case First: {
                menuBar.getMenus().remove(covidMenu);
                break;
            }
            case Second: {
                menuBar.getMenus().remove(populationMenu);
                menuBar.getMenus().remove(householdMenu);
                break;
            }
            default: break;
        }
         */

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
            PageManager.setPage(AddPopulationController.getKey());
        });
    }

    private void initializeHouseholdMenu() {
        addHouseholdMenuItem.setOnAction(e -> {
            PageManager.setPage(AddHouseholdController.getKey());
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
