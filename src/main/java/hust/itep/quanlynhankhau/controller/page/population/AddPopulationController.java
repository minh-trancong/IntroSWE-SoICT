package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.controller.utility.Form;
import hust.itep.quanlynhankhau.controller.utility.ModelMapper;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.dao.PopulationDao;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

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
    MFXTextField phoneNumberTextField;
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
    MFXTextField passportNumberTextField;
    @FXML
    MFXTextField birthplaceTextField;
    @FXML
    MFXTextField nativePlaceTextField;
    @FXML
    MFXTextField occupationWorkplaceTextField;
    @FXML
    MFXTextField permanentAddressTextField;
    @FXML
    MFXTextField addressTextField;

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
        form.addTextField(phoneNumberTextField);
        form.addTextField(genderComboBox, Form.NonEmptyConstraint(genderComboBox));
        form.addTextField(birthdateDatePicker, Form.NonEmptyConstraint(birthdateDatePicker));
        form.addTextField(nationalityTextField, Form.NonEmptyConstraint(nationalityTextField));
        form.addTextField(ethnicityTextField, Form.NonEmptyConstraint(ethnicityTextField));
        form.addTextField(citizenIdTextField);
        form.addTextField(passportNumberTextField);
        form.addTextField(birthplaceTextField, Form.NonEmptyConstraint(birthplaceTextField));
        form.addTextField(nativePlaceTextField, Form.NonEmptyConstraint(nativePlaceTextField));
        form.addTextField(occupationWorkplaceTextField);
        form.addTextField(permanentAddressTextField, Form.NonEmptyConstraint(permanentAddressTextField));
        form.addTextField(addressTextField, Form.NonEmptyConstraint(addressTextField));
    }

    private void submit() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addField("name", nameTextField);
        modelMapper.addField("phoneNumber", phoneNumberTextField);
        modelMapper.addField("birthdate", birthdateDatePicker);
        modelMapper.addField("gender", genderComboBox);
        modelMapper.addField("nationality", nationalityTextField);
        modelMapper.addField("ethnicity", ethnicityTextField);
        modelMapper.addField("citizenId", citizenIdTextField);
        modelMapper.addField("birthplace", birthplaceTextField);
        modelMapper.addField("nativePlace", nativePlaceTextField);
        modelMapper.addField("occupationWorkplace", occupationWorkplaceTextField);
        modelMapper.addField("permanentAddress", permanentAddressTextField);
        modelMapper.addField("address", addressTextField);

        Population population = modelMapper.map(Population.class);

        PopulationDao populationDao = new PopulationDao();
        populationDao.save(population);
    }
}
