package hust.itep.quanlynhankhau.controller.page.covid.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.ComboBoxHelper;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.covid.CovidTest;
import hust.itep.quanlynhankhau.model.covid.QuarantineInformation;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.service.dao.CovidTestDao;
import hust.itep.quanlynhankhau.service.dao.QuarantineInformationDao;
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

public class AddQuarantineInformationController {
    private static final String KEY = "/fxml/page/covid/popup/add-quarantine-information.fxml";

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
    MFXTextField contactTextField;

    @FXML
    MFXDatePicker startDate;

    @FXML
    MFXDatePicker endDate;


    @FXML
    MFXComboBox testComboBox;


    ObservableList<Population> populations;

    Population population;

    @FXML
    public void initialize() {
        initializeComboBox();
        initializeTable();
        initializeButton();
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
            textFields.add(contactTextField);
            textFields.add(startDate);
            textFields.add(endDate);
            textFields.add(testComboBox);

            for (MFXTextField textField : textFields) {
                if (textField.getText().isBlank()) {
                    InformativeBox.display("Thất bại", textField.getFloatingText() + " không được để trống");
                    return;
                }
            }

            if (endDate.getValue().isAfter(LocalDate.now())) {
                InformativeBox.display("Thất bại", endDate.getFloatingText() + " không được sau ngày hiện tại");
                return;
            }

            if (startDate.getValue().isAfter(endDate.getValue())) {
                InformativeBox.display("Thất bại", startDate.getFloatingText() + " không được sau " + endDate.getFloatingText());
                return;
            }


            QuarantineInformationDao quarantineInformationDao = new QuarantineInformationDao();
            QuarantineInformation quarantineInformation = new QuarantineInformation();
            quarantineInformation.setPopulation(population);
            quarantineInformation.setContactLevel(contactTextField.getText());
            quarantineInformation.setTestCovid(testComboBox.getText() == "Có" ? true : false);
            quarantineInformation.setLocation(locationTextField.getText());
            quarantineInformation.setEndTime(Date.valueOf(endDate.getValue()));
            quarantineInformation.setStartTime(Date.valueOf(startDate.getValue()));
            quarantineInformationDao.save(quarantineInformation);


            PageManager.refreshCurrentPage();
            PopupManager.refreshCurrentStage();
        });
    }

    private void initializeComboBox() {
        ComboBoxHelper.simple(testComboBox);
    }

    private void initializeTable() {
        populations = FXCollections.observableArrayList(new PopulationDao().getAll(Population.class));
        TableViewHelper.initializeHouseholdPopulationTableView(populationTable, populations);
    }

}
