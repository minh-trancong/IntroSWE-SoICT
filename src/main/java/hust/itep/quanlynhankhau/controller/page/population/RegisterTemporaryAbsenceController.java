package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.controller.component.modifier.DatePickerHelper;
import hust.itep.quanlynhankhau.controller.component.Form;
import hust.itep.quanlynhankhau.controller.component.modifier.ValidationHelper;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.model.TemporaryAbsence;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryAbsenceDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import java.sql.Date;
import java.util.ArrayList;

public class RegisterTemporaryAbsenceController {
    private static final String KEY = "/fxml/page/population/register-temporary-absence.fxml";
    public static String getKey() {
        return KEY;
    }

    @FXML
    private MFXTextField citizenIdTextField;

    @FXML
    private MFXTextField paperIdTextField;

    @FXML
    private MFXDatePicker fromDateTextField;

    @FXML
    private MFXDatePicker toDateTextField;

    @FXML
    private MFXTextField reasonTextField;

    @FXML
    private MFXTextField locationTextField;

    @FXML
    private MFXButton submitButton;

    private ArrayList<MFXTextField> nonEmptyTextFields = new ArrayList<MFXTextField>();

    @FXML
    public void initialize() {
        DatePickerHelper.setVietnamese(fromDateTextField);
        DatePickerHelper.setVietnamese(toDateTextField);
        initializeTextField();
        initializeForm();
    }

    private void initializeForm() {
        Form form = new Form(submitButton);
        form.setOnSubmit(e -> {
            submit();
        });
        form.getTextFields().add(citizenIdTextField);
        form.getTextFields().addAll(nonEmptyTextFields);
    }

    private void initializeTextField() {
        nonEmptyTextFields.add(paperIdTextField);
        nonEmptyTextFields.add(fromDateTextField);
        nonEmptyTextFields.add(toDateTextField);
        nonEmptyTextFields.add(locationTextField);
        nonEmptyTextFields.add(reasonTextField);

        citizenIdTextField.getValidator().constraint(
                ValidationHelper.constraintBuild(citizenIdTextField,"Chứng minh thư không hợp lệ",
                        () -> {
                            PopulationDao populationDao = new PopulationDao();
                            Population population = populationDao.getByCitizenId(citizenIdTextField.getText());
                            return population == null ? false : true;
                        }));
        ValidationHelper.setValidatorListener(citizenIdTextField);
        for (MFXTextField textField : nonEmptyTextFields) {
            textField.getValidator().constraint(
                    ValidationHelper.constraintBuild(textField, "Thông tin này là bắt buộc",
                            () -> !textField.getText().isBlank())
            );
            ValidationHelper.setValidatorListener(textField);
        }
    }

    private void submit() {
        PopulationDao populationDao = new PopulationDao();
        Population population = populationDao.getByCitizenId(citizenIdTextField.getId());

        TemporaryAbsence temporaryAbsence = new TemporaryAbsence();
        temporaryAbsence.setPaperCode(paperIdTextField.getText());
        temporaryAbsence.setFromDate(Date.valueOf(fromDateTextField.getValue()));
        temporaryAbsence.setToDate(Date.valueOf(toDateTextField.getValue()));
        temporaryAbsence.setReason(reasonTextField.getText());
        temporaryAbsence.setPopulation(population);

        TemporaryAbsenceDao temporaryAbsenceDao = new TemporaryAbsenceDao();
        temporaryAbsenceDao.save(temporaryAbsence);
        PageManager.refreshCurrentPage();
    }
}
