package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.controller.component.Form;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.dao.PopulationDao;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Date;
import java.util.Arrays;
import java.util.Locale;

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

    public static String getKey() {
        return KEY;
    }

    @FXML
    public void initialize() {
        initializeComboBox();
        initializeDatePicker();
        initializeForm();
    }


    public void initializeDatePicker() {
        birthdateDatePicker.setLocale(Locale.of("vi", "VN"));
        birthdateDatePicker.setConverterSupplier(() ->
                new DateStringConverter("dd/MM/yyyy", birthdateDatePicker.getLocale())
        );
    }

    public void initializeComboBox() {
        genderComboBox.setItems(GENDERS);
    }

    public void initializeForm() {
        Form form = new Form(submitButton, e -> submit());

        form.addTextField(nameTextField, Form.NonEmptyConstraint(nameTextField));
        form.addTextField(phoneTextField);
        form.addTextField(genderComboBox, Form.NonEmptyConstraint(genderComboBox));
        form.addTextField(birthdateDatePicker, Form.NonEmptyConstraint(birthdateDatePicker));
        form.addTextField(nationalityTextField, Form.NonEmptyConstraint(nationalityTextField));
        form.addTextField(ethnicityTextField, Form.NonEmptyConstraint(ethnicityTextField));
        form.addTextField(citizenIdTextField);
        form.addTextField(passportTextField);
        form.addTextField(birthPlaceTextField, Form.NonEmptyConstraint(birthPlaceTextField));
        form.addTextField(nativePlaceTextField, Form.NonEmptyConstraint(nativePlaceTextField));
        form.addTextField(occupationTextField);
        form.addTextField(permanentAddressTextField, Form.NonEmptyConstraint(permanentAddressTextField));
        form.addTextField(currentAddressTextField, Form.NonEmptyConstraint(currentAddressTextField));
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

        Platform.runLater(() -> PageManager.setPage(AddPopulationController.getKey()));
    }
}