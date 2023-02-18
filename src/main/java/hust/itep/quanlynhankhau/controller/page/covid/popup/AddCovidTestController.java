package hust.itep.quanlynhankhau.controller.page.covid.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.ComboBoxHelper;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.covid.CovidTest;
import hust.itep.quanlynhankhau.model.covid.MovementDeclaration;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.service.dao.CovidTestDao;
import hust.itep.quanlynhankhau.service.dao.MovementDeclarationDao;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddCovidTestController {
    private static final String KEY = "/fxml/page/covid/popup/add-covid-test.fxml";

    public static String getKey() {
        return KEY;
    }

    @FXML
    TableView<Population> populationTable;

    @FXML
    MFXButton choosePopulationButton;

    @FXML
    MFXButton submitButton;

    @FXML
    MFXTextField nameTextField;
    @FXML
    MFXTextField citizenIdTextField;

    @FXML
    MFXTextField locationTextField;

    @FXML
    MFXTextField methodTextField;

    @FXML
    MFXDatePicker datePicker;

    @FXML
    MFXComboBox resultComboBox;

    @FXML
    MFXComboBox quarantineComboBox;


    ObservableList<Population> populations;

    Population population;

    @FXML
    public void initialize() {
        initializeComboBox();
        initializeButton();
        initializeTable();
    }

    private void initializeComboBox() {
        ComboBoxHelper.covid(resultComboBox);
        ComboBoxHelper.simple(quarantineComboBox);
    }

    private void initializeButton() {
        choosePopulationButton.setOnAction(e -> {
            if (populationTable.getSelectionModel().getSelectedItems().size() != 1) {
                return;
            }

            population = populationTable.getSelectionModel().getSelectedItem();
            nameTextField.setText(population.getName());
            citizenIdTextField.setText(population.getCitizenId() == null ? "" : population.getCitizenId());
        });

        submitButton.setOnAction(e -> {
            ArrayList<MFXTextField> textFields = new ArrayList<>();
            textFields.add(nameTextField);
            textFields.add(locationTextField);
            textFields.add(methodTextField);
            textFields.add(datePicker);
            textFields.add(resultComboBox);
            textFields.add(quarantineComboBox);

            for (MFXTextField textField : textFields) {
                if (textField.getText().isBlank()) {
                    InformativeBox.display("Thất bại", textField.getFloatingText() + " không được để trống");
                    return;
                }
            }

            if (datePicker.getValue().isAfter(LocalDate.now())) {
                InformativeBox.display("Thất bại", datePicker.getFloatingText() + " không được sau ngày hiện tại");
                return;
            }

            CovidTestDao covidTestDao = new CovidTestDao();
            CovidTest covidTest = new CovidTest();
            covidTest.setPopulation(population);
            covidTest.setTestDate(Date.valueOf(datePicker.getValue()));
            covidTest.setLocation(locationTextField.getText());
            covidTest.setResult(resultComboBox.getText() == "Dương tính" ? true : false);
            covidTest.setQuarantine(quarantineComboBox.getText() == "Có" ? true : false);
            covidTest.setMethod(methodTextField.getText());

            covidTestDao.save(covidTest);
            PageManager.refreshCurrentPage();
            PopupManager.refreshCurrentStage();
        });

    }

    private void initializeTable() {
        populations = FXCollections.observableArrayList(new PopulationDao().getAll(Population.class));
        TableViewHelper.initializeHouseholdPopulationTableView(populationTable, populations);
    }
}
