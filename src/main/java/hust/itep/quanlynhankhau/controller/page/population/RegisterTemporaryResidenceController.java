package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.controller.component.Form;
import hust.itep.quanlynhankhau.controller.component.ValidationHelper;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.model.TemporaryResidence;
import hust.itep.quanlynhankhau.service.dao.PopulationDao;
import hust.itep.quanlynhankhau.service.dao.TemporaryResidenceDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import java.sql.Date;
import java.util.ArrayList;

public class RegisterTemporaryResidenceController {
    private static final String KEY = "/fxml/page/population/register-temporary-residence.fxml";

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
    private MFXButton submitButton;

    private ArrayList<MFXTextField> nonEmptyTextFields = new ArrayList<MFXTextField>();

    @FXML
    public void initialize() {
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

        citizenIdTextField.getValidator().constraint(
                ValidationHelper.constraintBuild(citizenIdTextField,"Chứng minh thư không hợp lệ",
                        () -> {
                            PopulationDao populationDao = new PopulationDao();
                            Population population = populationDao.getByCitizenId(citizenIdTextField.getText());
                            return population == null ? false : true;
                        }));
        ValidationHelper.setValidatorListener(citizenIdTextField);
        System.out.println(nonEmptyTextFields.size());
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

        TemporaryResidence temporaryResidence = new TemporaryResidence();
        temporaryResidence.setPaperCode(paperIdTextField.getText());
        temporaryResidence.setFromDate(Date.valueOf(fromDateTextField.getValue()));
        temporaryResidence.setToDate(Date.valueOf(toDateTextField.getValue()));
        temporaryResidence.setReason(reasonTextField.getText());
        temporaryResidence.setPopulation(population);

        TemporaryResidenceDao temporaryResidenceDao = new TemporaryResidenceDao();
        temporaryResidenceDao.save(temporaryResidence);
    }





}
