package hust.itep.quanlynhankhau.controller.page.population.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.ComboBoxHelper;
import hust.itep.quanlynhankhau.controller.component.modifier.DatePickerHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import io.github.palexdev.materialfx.controls.*;
import javafx.fxml.FXML;

import java.sql.Date;
import java.util.ArrayList;

public class AddPopulationController {
    private static final String KEY = "/fxml/page/population/popup/add-population.fxml";

    @FXML
    MFXButton submitButton;
    @FXML
    MFXTextField nameTextField;
    @FXML
    MFXTextField phoneTextField;
    @FXML
    MFXComboBox genderComboBox;
    @FXML
    MFXDatePicker birthdateDatePicker;
    @FXML
    MFXTextField nationalityTextField;
    @FXML
    MFXTextField ethnicityTextField;
    @FXML
    MFXTextField citizenIdTextField;
    @FXML
    MFXTextField passportTextField;
    @FXML
    MFXTextField birthPlaceTextField;
    @FXML
    MFXTextField nativePlaceTextField;
    @FXML
    MFXTextField occupationTextField;
    @FXML
    MFXTextField permanentAddressTextField;
    @FXML
    MFXTextField currentAddressTextField;

    private ArrayList<MFXTextField> nonEmptyTextFields = new ArrayList<>();

    public static String getKey() {
        return KEY;
    }

    @FXML
    public void initialize() {
        initializeTextField();
        initializeComboBox();
        initializeDatePicker();
        initializeButton();
    }


    public void initializeDatePicker() {
        DatePickerHelper.setVietnamese(birthdateDatePicker);
    }

    public void initializeComboBox() {
        ComboBoxHelper.gender(genderComboBox);
    }

    public void initializeTextField() {
        nonEmptyTextFields.add(nameTextField);
        nonEmptyTextFields.add(genderComboBox);
        nonEmptyTextFields.add(birthdateDatePicker);
        nonEmptyTextFields.add(nationalityTextField);
        nonEmptyTextFields.add(ethnicityTextField);
        nonEmptyTextFields.add(birthPlaceTextField);
        nonEmptyTextFields.add(nativePlaceTextField);
        nonEmptyTextFields.add(occupationTextField);
        nonEmptyTextFields.add(permanentAddressTextField);
        nonEmptyTextFields.add(currentAddressTextField);
    }

    private void initializeButton() {
        submitButton.setOnAction(e -> {
            for (MFXTextField textField : nonEmptyTextFields) {
                if (textField.getText().isBlank()) {
                    InformativeBox.display("Thất bại", textField.getFloatingText() + " không được để trống");
                    return;
                }
            }

            Population population = new Population();
            population.setName(nameTextField.getText());
            population.setGender(genderComboBox.getText());
            population.setPhone(phoneTextField.getText());
            population.setBirthdate(Date.valueOf(birthdateDatePicker.getValue()));
            population.setEthnicity(ethnicityTextField.getText());
            population.setPassport(passportTextField.getText());
            population.setOccupation(occupationTextField.getText());
            population.setNationality(nationalityTextField.getText());
            population.setCitizenId(citizenIdTextField.getText().isBlank() ? null : citizenIdTextField.getText());
            population.setCurrentAddress(currentAddressTextField.getText());
            population.setNativePlace(nativePlaceTextField.getText());
            population.setBirthPlace(birthPlaceTextField.getText());
            population.setPermanentAddress(permanentAddressTextField.getText());

            PopulationDao populationDao = new PopulationDao();
            populationDao.save(population);

            PageManager.refreshCurrentPage();
            PopupManager.refreshCurrentStage();
        });
    }
    private void submit() {
        Population population = new Population();
        population.setName(nameTextField.getText());
        population.setGender(genderComboBox.getText());
        population.setPhone(phoneTextField.getText());
        population.setBirthdate(Date.valueOf(birthdateDatePicker.getValue()));
        population.setEthnicity(ethnicityTextField.getText());
        population.setPassport(passportTextField.getText());
        population.setOccupation(occupationTextField.getText());
        population.setNationality(nationalityTextField.getText());
        population.setCitizenId(citizenIdTextField.getText().isBlank() ? null : citizenIdTextField.getText());
        population.setCurrentAddress(currentAddressTextField.getText());
        population.setNativePlace(nativePlaceTextField.getText());
        population.setBirthPlace(birthPlaceTextField.getText());
        population.setPermanentAddress(permanentAddressTextField.getText());

        PopulationDao populationDao = new PopulationDao();
        populationDao.save(population);

        PageManager.refreshCurrentPage();
        PopupManager.refreshCurrentStage();
    }
}
