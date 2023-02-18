package hust.itep.quanlynhankhau.controller.page.covid.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.ComboBoxHelper;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.covid.MovementDeclaration;
import hust.itep.quanlynhankhau.model.population.Population;
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

public class AddMovementDeclarationController {
    private static final String KEY = "/fxml/page/covid/popup/add-movement-declaration.fxml";

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
    MFXComboBox covidComboBox;

    @FXML
    MFXTextField departureTextField;

    @FXML
    MFXDatePicker departureDatePicker;
    @FXML
    MFXTextField destinationTextField;
    @FXML
    MFXTextField symptomsTextField;
    @FXML
    MFXTextField vehicleTextField;
    @FXML
    MFXTextField licensePlateTextField;
    @FXML
    MFXTextField passengerNumberTextField;
    @FXML
    MFXTextField historyTextField;



    ObservableList<Population> populations;

    Population population;

    @FXML
    public void initialize() {
        initializeComboBox();
        initializeTable();
        initializeButton();
    }

    private void initializeComboBox() {
        ComboBoxHelper.simple(covidComboBox);
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
            textFields.add(departureTextField);
            textFields.add(departureDatePicker);
            textFields.add(destinationTextField);
            textFields.add(symptomsTextField);
            textFields.add(vehicleTextField);
            textFields.add(licensePlateTextField);
            textFields.add(passengerNumberTextField);
            textFields.add(historyTextField);

            for (MFXTextField textField : textFields) {
                if (textField.getText().isBlank()) {
                    InformativeBox.display("Thất bại", textField.getFloatingText() + " không được để trống");
                return;
                }
            }


            try {
                Integer passengerNum = Integer.valueOf(passengerNumberTextField.getText());
            } catch (Exception ex) {
                InformativeBox.display("Thất bại", passengerNumberTextField.getFloatingText() + " phải chứa số nguyên");
                return;
            }

            Population persistPopulation = new PopulationDao().get(Population.class, population.getId());

            MovementDeclaration movementDeclaration = new MovementDeclaration();
            movementDeclaration.setPopulation(persistPopulation);
            movementDeclaration.setDepartureLocation(departureTextField.getText());
            movementDeclaration.setNumberOfPassengers(Integer.valueOf(passengerNumberTextField.getText()));
            movementDeclaration.setHasCovid(covidComboBox.getText().equals("Có") ? true : false);
            movementDeclaration.setDestination(destinationTextField.getText());
            movementDeclaration.setDepartureDate(Date.valueOf(departureDatePicker.getValue()));
            movementDeclaration.setLicensePlate(licensePlateTextField.getText());
            movementDeclaration.setSymptoms(symptomsTextField.getText());
            movementDeclaration.setVehicleType(vehicleTextField.getText());
            movementDeclaration.setDeclarationDate(Date.valueOf(LocalDate.now()));
            movementDeclaration.setHistory(historyTextField.getText());

            MovementDeclarationDao movementDeclarationDao = new MovementDeclarationDao();
            movementDeclarationDao.save(movementDeclaration);

            PageManager.refreshCurrentPage();
            PopupManager.refreshCurrentStage();
        });
    }

    private void initializeTable() {
        populations = FXCollections.observableArrayList(new PopulationDao().getAll(Population.class));
        TableViewHelper.initializeHouseholdPopulationTableView(populationTable, populations);
    }
}
