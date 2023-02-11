package hust.itep.quanlynhankhau.controller;

import hust.itep.quanlynhankhau.controller.page.HomeController;
import hust.itep.quanlynhankhau.controller.page.LoginController;
import hust.itep.quanlynhankhau.controller.page.household.AddHouseholdController;
import hust.itep.quanlynhankhau.controller.page.population.AddPopulationController;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MainController {
    @FXML
    private BorderPane borderPane;


    @FXML
    public void initialize() {
        PageManager.setMainContainer(borderPane);
        PageManager.addPage(LoginController.getKey(), new LoginController());
        PageManager.addPage(HomeController.getKey(), new HomeController());
        PageManager.addPage(AddHouseholdController.getKey(), new AddHouseholdController());
        PageManager.addPage(AddPopulationController.getKey(), new AddPopulationController());

        PageManager.setPage(LoginController.getKey());
    }







}