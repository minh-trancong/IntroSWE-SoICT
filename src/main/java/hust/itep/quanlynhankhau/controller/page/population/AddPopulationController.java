package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.controller.component.DatePickerHelper;
import hust.itep.quanlynhankhau.controller.component.Form;
import hust.itep.quanlynhankhau.controller.component.ValidationHelper;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import io.github.palexdev.materialfx.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

public class AddPopulationController {
    private static final String KEY = "/fxml/page/population/add-population.fxml";
    private final static ObservableList<String> GENDERS = FXCollections
            .observableList(Arrays.asList("Nam", "Nữ", "Khác"));
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
        initializeForm();
    }


    public void initializeDatePicker() {
        DatePickerHelper.setVietnamese(birthdateDatePicker);
    }

    public void initializeComboBox() {
        genderComboBox.setItems(GENDERS);
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

        for (MFXTextField textField : nonEmptyTextFields) {
            textField.getValidator().constraint(
                    ValidationHelper.constraintBuild(textField, "Thông tin này là bắt buộc",
                            () -> !textField.getText().isBlank())
            );
            ValidationHelper.setValidatorListener(textField);
        }
    }

    public void initializeForm() {
        Form form = new Form(submitButton);
        form.setOnSubmit(e -> submit());

        form.getTextFields().addAll(nonEmptyTextFields);
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
        population.setCitizenId(citizenIdTextField.getText());
        population.setCurrentAddress(currentAddressTextField.getText());
        population.setNativePlace(nativePlaceTextField.getText());
        population.setBirthPlace(birthPlaceTextField.getText());
        population.setPermanentAddress(permanentAddressTextField.getText());

        PopulationDao populationDao = new PopulationDao();
        populationDao.save(population);
        PopupManager.refreshCurrentStage();
    }
}
