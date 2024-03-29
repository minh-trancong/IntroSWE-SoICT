package hust.itep.quanlynhankhau.controller.page.population.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.DatePickerHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

public class UpdatePopulationController {
    private static final String KEY = "/fxml/page/population/popup/update-population.fxml";

    public static String getKey() {
        return KEY;
    }
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

    private Population population;

    public UpdatePopulationController(Population population) {
        this.population = population;
    }



    @FXML
    public void initialize() {
        initializeDatePicker();
        initializeComboBox();
        initializeInfo();
        initializeTextField();
        initializeButton();
    }


    @FXML
    public void initializeInfo() {
        nameTextField.setText(population.getName());
        genderComboBox.setText(population.getGender());
        phoneTextField.setText(population.getPhone());
        birthdateDatePicker.setValue(population.getBirthdate().toLocalDate());
        citizenIdTextField.setText(population.getCitizenId());
        ethnicityTextField.setText(population.getEthnicity());
        nationalityTextField.setText(population.getNationality());
        passportTextField.setText(population.getNationality());
        birthPlaceTextField.setText(population.getBirthPlace());
        nativePlaceTextField.setText(population.getNativePlace());
        occupationTextField.setText(population.getOccupation());
        currentAddressTextField.setText(population.getCurrentAddress());
        permanentAddressTextField.setText(population.getPermanentAddress());
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
    }

    public void initializeButton() {
        submitButton.setOnAction(e -> {
            for (MFXTextField textField : nonEmptyTextFields) {
                if (textField.getText().isBlank()) {
                    InformativeBox.display("Thất bại", textField.getFloatingText() + " không được để trống");
                    return;
                }
            }

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

            populationDao.update(population);
            PopupManager.closeCurrentStage();
            PageManager.refreshCurrentPage();
        });
    }
}
