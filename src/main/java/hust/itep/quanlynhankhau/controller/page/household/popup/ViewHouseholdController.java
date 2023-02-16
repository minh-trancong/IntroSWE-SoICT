package hust.itep.quanlynhankhau.controller.page.household.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.model.household.Household;
import hust.itep.quanlynhankhau.model.population.Population;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ViewHouseholdController {
    private static final String KEY = "/fxml/page/household/popup/view-household.fxml";

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

    private Household household;

    private ObservableList<Population> populations;

    public ViewHouseholdController(Household household) {
        this.household = household;
    }

    @FXML
    public void initialize() {
        populations = FXCollections.observableArrayList(household.getPopulationList());
        TableViewHelper.initializeNewHouseholdPopulationTableView(populationTable, populations);
        populationTable.setEditable(false);

        headTextField.setText(household.getHeadOfHouseHold().getName());
        areaCodeTextField.setText(household.getAreaCode());
        addressTextField.setText(household.getAddress());
    }
}
