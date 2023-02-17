package hust.itep.quanlynhankhau.controller.page.household.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.household.Household;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.service.dao.HouseholdDao;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class UpdateHouseholdController {

    private static final String KEY = "/fxml/page/household/popup/update-household.fxml";

    public static String getKey() {
        return KEY;
    }

    @FXML
    private MFXTextField addressTextField;
    @FXML
    private MFXTextField areaCodeTextField;



    @FXML
    private MFXTextField headTextField;
    @FXML
    private TableView<Population> populationTable;
    @FXML
    private TableView<Population> addPopulationTable;
    @FXML
    private MFXButton setHeadButton;
    @FXML
    private MFXButton clearHeadButton;
    @FXML
    private MFXButton addPopulationButton;
    @FXML
    private MFXButton submitButton;
    @FXML
    private MFXButton removePopulationButton;
    private ObservableList<Population> items;
    private ObservableList<Population> addedItems;
    private Population headPopulation = null;

    private Household household;

    public UpdateHouseholdController(Household household) {
        this.household = household;
        headPopulation = household.getHeadOfHouseHold();
    }

    @FXML
    public void initialize() {
        initializeTextField();
        initializeTable();
        initializeButton();
    }

    public void initializeTextField() {
        headTextField.setText(headPopulation.getName());
        addressTextField.setText(household.getAddress());
        areaCodeTextField.setText(household.getAreaCode());
    }

    public void initializeButton() {
        addPopulationButton.setOnAction(e -> {
            if (populationTable.getSelectionModel().getSelectedItems().isEmpty()) {
                return;
            }

            List<Population> populations = populationTable.getSelectionModel().getSelectedItems();

            addedItems.addAll(populations);
            items.removeAll(populations);
        });

        removePopulationButton.setOnAction(e -> {
            if (addPopulationTable.getSelectionModel().getSelectedItems().isEmpty()) {
                return;
            }

            List<Population> populations = addPopulationTable.getSelectionModel().getSelectedItems();

            populations.forEach(population -> System.out.println(population.getName()));

            items.addAll(populations);
            addedItems.removeAll(populations);
        });

        setHeadButton.setOnAction(e -> {
            if (addPopulationTable.getSelectionModel().getSelectedItems().size() != 1) {
                return;
            }

            Population population = addPopulationTable.getSelectionModel().getSelectedItem();

            if (headPopulation == null) {
                headTextField.setText(population.getName());
                headPopulation = population;
                addedItems.remove(population);
            } else {
                headTextField.setText(population.getName());
                addedItems.add(headPopulation);
                headPopulation = population;
                addedItems.remove(population);
            }
        });

        clearHeadButton.setOnAction(e -> {
            if (headPopulation != null) {
                headTextField.setText("");
                addedItems.add(headPopulation);
                headPopulation = null;
            }
        });

        submitButton.setOnAction(e -> {
            if (headPopulation == null) {
                InformativeBox.display("Thất bại", "Chủ hộ chưa được đặt");
                return;
            }

            if (addressTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Địa chỉ mới không được để trống");
                return;
            }

            if (areaCodeTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Mã khu vực không được để trống");
                return;
            }


            HouseholdDao householdDao = new HouseholdDao();

            household.setAddress(addressTextField.getText());
            household.setAreaCode(areaCodeTextField.getText());
            household.setHeadOfHouseHold(headPopulation);
            householdDao.update(household);

            PopulationDao populationDao = new PopulationDao();

            addedItems.forEach(population -> {
                population.setHousehold(household);
                populationDao.update(population);
            });

            items.forEach(population -> {
                population.setHousehold(null);
                populationDao.update(population);
            });

            headPopulation.setHousehold(household);
            headPopulation.setRelationshipToHead("Là chủ hộ");
            populationDao.update(headPopulation);

            PopupManager.closeCurrentStage();
            PageManager.refreshCurrentPage();
        });
    }

    public void initializeTable() {
        ArrayList<Population> populations = new ArrayList<>(new PopulationDao().getAll(Population.class));
        populations.removeIf(population -> population.getHousehold() != null);

        items = FXCollections.observableArrayList(populations);

        ArrayList<Population> addedPopulations = new ArrayList<>(household.getPopulationList());
        addedPopulations.removeIf(population -> population.getId().equals(headPopulation.getId()));

        addedItems = FXCollections.observableArrayList(addedPopulations);

        TableViewHelper.initializeHouseholdPopulationTableView(populationTable, items);
        TableViewHelper.initializeNewHouseholdPopulationTableView(addPopulationTable, addedItems);
    }
}
