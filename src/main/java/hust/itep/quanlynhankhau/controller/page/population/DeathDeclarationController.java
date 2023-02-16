package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.controller.component.modifier.DatePickerHelper;
import hust.itep.quanlynhankhau.controller.component.Form;
import hust.itep.quanlynhankhau.controller.component.modifier.ValidationHelper;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.model.population.DeathDeclaration;
import hust.itep.quanlynhankhau.service.dao.population.DeathDeclarationDao;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import java.sql.Date;
import java.util.ArrayList;

public class DeathDeclarationController {
    private static final String KEY = "/fxml/page/population/death-declaration.fxml";
    public static String getKey() {
        return KEY;
    }

    @FXML
    MFXTextField reporterCitizenIdTextField;
    @FXML
    MFXTextField deceasedCitizenIdTextField;
    @FXML
    MFXTextField paperCodeTextField;
    @FXML
    MFXTextField reasonTextField;

    @FXML
    MFXDatePicker deathDatePicker;

    @FXML
    MFXDatePicker reportDatePicker;

    @FXML
    MFXButton submitButton;


    private ArrayList<MFXTextField> nonEmptyTextFields = new ArrayList<>();

    @FXML
    public void initialize() {
        DatePickerHelper.setVietnamese(deathDatePicker);
        DatePickerHelper.setVietnamese(reportDatePicker);
        initializeTextField();
        initializeForm();
    }

    public void initializeForm() {
        Form form = new Form(submitButton);
        form.setOnSubmit(e -> submit());
        form.getTextFields().addAll(nonEmptyTextFields);
    }

    private void submit() {
        DeathDeclaration deathDeclaration = new DeathDeclaration();
        deathDeclaration.setDeathDate(Date.valueOf(deathDatePicker.getValue()));
        deathDeclaration.setReason(reasonTextField.getText());
        deathDeclaration.setReportedAt(Date.valueOf(reportDatePicker.getValue()));

        PopulationDao populationDao = new PopulationDao();
        deathDeclaration.setDeceased(populationDao.getByCitizenId(deceasedCitizenIdTextField.getText()));
        deathDeclaration.setReporter(populationDao.getByCitizenId(reporterCitizenIdTextField.getText()));

        DeathDeclarationDao deathDeclarationDao = new DeathDeclarationDao();
        deathDeclarationDao.save(deathDeclaration);
        PageManager.setPage(DeathDeclarationController.getKey(), new DeathDeclarationController());
    }

    public void initializeTextField() {
        nonEmptyTextFields.add(reasonTextField);
        nonEmptyTextFields.add(paperCodeTextField);
        nonEmptyTextFields.add(reporterCitizenIdTextField);
        nonEmptyTextFields.add(deceasedCitizenIdTextField);
        nonEmptyTextFields.add(deathDatePicker);
        nonEmptyTextFields.add(reportDatePicker);

        for (MFXTextField textField : nonEmptyTextFields) {
            textField.getValidator().constraint(
                    ValidationHelper.constraintBuild(textField, "Thông tin này là bắt buộc",
                            () -> !textField.getText().isBlank())
            );
            ValidationHelper.setValidatorListener(textField);
        }
    }
}
