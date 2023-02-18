package hust.itep.quanlynhankhau.controller.page.population.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.DatePickerHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.model.population.TemporaryResidence;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryResidenceDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import java.sql.Date;
import java.time.LocalDate;

public class RegisterTemporaryResidenceController {
    private static final String KEY = "/fxml/page/population/popup/register-temporary-residence.fxml";

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
    private MFXButton submitButton;


    private Population population;

    public RegisterTemporaryResidenceController(Population population) {
        this.population = population;
    }

    @FXML
    public void initialize() {
        DatePickerHelper.setVietnamese(fromDateTextField);
        DatePickerHelper.setVietnamese(toDateTextField);
        initializeTextField();
        initializeButton();

    }

    private void initializeTextField() {
        nameTextField.setText(population.getName());
        citizenIdTextField.setText(population.getCitizenId());
    }

    private void initializeButton() {
        submitButton.setOnAction(e -> {
            if (reasonTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Lý do không được để trống");
                return;
            }

            if (fromDateTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Ngày bắt đầu tạm trú không được để trống");
                return;
            }

            if (toDateTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Ngày kết thúc tạm trú không được để trống");
                return;
            }

            if (fromDateTextField.getValue().isBefore(LocalDate.now())) {
                InformativeBox.display("Thất bại", "Ngày bắt đầu tạm trú không được trước ngày hôm nay");
                return;
            }

            if (fromDateTextField.getValue().isAfter(toDateTextField.getValue())) {
                InformativeBox.display("Thất bại", "Ngày kết thúc tạm trú phải sau ngày bắt đầu tạm vắng");
                return;
            }

            TemporaryResidence temporaryResidence = new TemporaryResidence();
            temporaryResidence.setReason(reasonTextField.getText());
            temporaryResidence.setPopulation(population);
            temporaryResidence.setFromDate(Date.valueOf(fromDateTextField.getValue()));
            temporaryResidence.setToDate(Date.valueOf(toDateTextField.getValue()));

            TemporaryResidenceDao temporaryResidenceDao = new TemporaryResidenceDao();
            temporaryResidenceDao.save(temporaryResidence);

            PopupManager.closeCurrentStage();
            PageManager.refreshCurrentPage();
        });
    }
}