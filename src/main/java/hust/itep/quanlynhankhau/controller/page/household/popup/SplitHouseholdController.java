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

import java.util.List;

public class SplitHouseholdController {
    private static String KEY = "/fxml/page/household/popup/split-household.fxml";

    public static String getKey() {
        return KEY;
    }
    @FXML
    MFXButton submitButton;

    @FXML
    MFXButton addPopulationButton;

    @FXML
    MFXButton removePopulationButton;

    @FXML
    MFXButton setHeadButton;

    @FXML
    MFXButton clearHeadButton;

    @FXML
    MFXTextField oldHeadTextField;

    @FXML
    MFXTextField newHeadTextField;

    @FXML
    MFXTextField newAddressTextField;

    @FXML
    MFXTextField newAreaCodeTextField;

    @FXML
    TableView<Population> oldPopulationTable;

    @FXML
    TableView<Population> newPopulationTable;

    private Household household;

    private ObservableList<Population> oldPopulations;

    private ObservableList<Population> newPopulations = FXCollections.observableArrayList();

    public SplitHouseholdController(Household household) {
        this.household = household;

    }

    private Population newHead = null;

    @FXML
    public void initialize() {
        initializeTextField();
        initializeTable();
        initializeButton();
    }

    private void initializeButton() {
        addPopulationButton.setOnAction(e -> {
            if (oldPopulationTable.getSelectionModel().getSelectedItems().isEmpty()) {
               return;
            }

            List<Population> populations = oldPopulationTable.getSelectionModel().getSelectedItems();

            newPopulations.addAll(populations);
            oldPopulations.removeAll(populations);
        });

        removePopulationButton.setOnAction(e -> {
           if (newPopulationTable.getSelectionModel().getSelectedItems().isEmpty()) {
               return;
           }

           List<Population> populations = newPopulationTable.getSelectionModel().getSelectedItems();

           oldPopulations.addAll(populations);
           newPopulations.removeAll(populations);
        });

        setHeadButton.setOnAction(e -> {
            if (newPopulationTable.getSelectionModel().getSelectedItems().size() != 1) {
                return;
            }

            Population population = newPopulationTable.getSelectionModel().getSelectedItem();

            if (newHead == null) {
                newHeadTextField.setText(population.getName());
                newHead = population;
                newPopulations.remove(population);
            } else {
                newHeadTextField.setText(population.getName());
                newPopulations.add(newHead);
                newHead = population;
                newPopulations.remove(population);
            }
        });

        clearHeadButton.setOnAction(e -> {
            if (newHead != null) {
                newHeadTextField.setText("");
                newPopulations.add(newHead);
                newHead = null;
            }
        });

        submitButton.setOnAction(e -> {
            if (newHead == null) {
                InformativeBox.display("Thất bại", "Chủ hộ chưa được đặt");
                return;
            }

            if (newAddressTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Địa chỉ mới không được để trống");
                return;
            }

            if (newAreaCodeTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Mã khu vực không được để trống");
                return;
            }

            // 1. Create new household
            Household newHousehold = new Household();
            newHousehold.setAddress(newAddressTextField.getText());
            newHousehold.setAreaCode(newAreaCodeTextField.getText());
            newHousehold.setHeadOfHouseHold(newHead);

            HouseholdDao householdDao = new HouseholdDao();
            householdDao.save(newHousehold);

            // 2. Update the population list
            PopulationDao populationDao = new PopulationDao();
            newPopulations.forEach(population -> {
                population.setHousehold(newHousehold);
                populationDao.update(population);
            });

            newHead.setHousehold(newHousehold);
            newHead.setRelationshipToHead("Là chủ hộ");
            populationDao.update(newHead);

            PopupManager.closeCurrentStage();
            PageManager.refreshCurrentPage();
        });
    }

    private void initializeTextField() {
        oldHeadTextField.setText(household.getHeadOfHouseHold().getName());
    }


    private void initializeTable() {
        List<Population> populations = household.getPopulationList();
        populations.removeIf(population -> population.getId() == household.getHeadOfHouseHold().getId());

        oldPopulations = FXCollections.observableArrayList(populations);

        TableViewHelper.initializeHouseholdPopulationTableView(oldPopulationTable, oldPopulations);
        TableViewHelper.initializeNewHouseholdPopulationTableView(newPopulationTable, newPopulations);
    }

}
