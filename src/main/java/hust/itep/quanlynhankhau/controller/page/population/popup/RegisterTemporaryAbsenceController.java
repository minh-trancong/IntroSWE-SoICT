package hust.itep.quanlynhankhau.controller.page.population.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.DatePickerHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.model.population.TemporaryAbsence;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryAbsenceDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import java.sql.Date;
import java.time.LocalDate;

public class RegisterTemporaryAbsenceController {
    private static final String KEY = "/fxml/page/population/popup/register-temporary-absence.fxml";
    public static String getKey() {
        return KEY;
    }

    @FXML
    private MFXTextField citizenIdTextField;

    @FXML
    private MFXTextField nameTextField;

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

    private Population population;

    public RegisterTemporaryAbsenceController(Population population) {
        this.population = population;
    }

    @FXML
    public void initialize() {
        DatePickerHelper.setVietnamese(fromDateTextField);
        DatePickerHelper.setVietnamese(toDateTextField);
        initializeButton();
        initializeTextField();

    }

    private void initializeTextField() {
        nameTextField.setText(population.getName());
        citizenIdTextField.setText(population.getCitizenId());
    }


    private void initializeButton() {
        submitButton.setOnAction(e -> {
            if (locationTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Nơi tạm trú không được để trống");
                return;
            }

            if (reasonTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Lý do không được để trống");
                return;
            }

            if (fromDateTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Ngày bắt đầu tạm vắng không được để trống");
                return;
            }

            if (toDateTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Ngày kết thúc tạm vắng không được để trống");
                return;
            }

            if (fromDateTextField.getValue().isBefore(LocalDate.now())) {
                InformativeBox.display("Thất bại", "Ngày bắt đầu tạm vắng không được trước ngày hôm nay");
                return;
            }

            if (fromDateTextField.getValue().isAfter(toDateTextField.getValue())) {
                InformativeBox.display("Thất bại", "Ngày kết thúc tạm vắng phải sau ngày bắt đầu tạm vắng");
                return;
            }

            TemporaryAbsence temporaryAbsence = new TemporaryAbsence();
            temporaryAbsence.setPlace(locationTextField.getText());
            temporaryAbsence.setPopulation(population);
            temporaryAbsence.setToDate(Date.valueOf(toDateTextField.getValue()));
            temporaryAbsence.setFromDate(Date.valueOf(fromDateTextField.getValue()));
            temporaryAbsence.setReason(reasonTextField.getText());

            TemporaryAbsenceDao temporaryAbsenceDao = new TemporaryAbsenceDao();
            temporaryAbsenceDao.save(temporaryAbsence);
            PopupManager.closeCurrentStage();
            PageManager.refreshCurrentPage();
        });
    }

}
