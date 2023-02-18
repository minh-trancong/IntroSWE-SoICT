package hust.itep.quanlynhankhau.controller.page.population.popup;

import hust.itep.quanlynhankhau.controller.component.modifier.DatePickerHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.population.DeathDeclaration;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.service.dao.population.DeathDeclarationDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import java.sql.Date;
import java.time.LocalDate;

public class DeathDeclarationController {
    private static final String KEY = "/fxml/page/population/popup/death-declaration.fxml";
    public static String getKey() {
        return KEY;
    }

    @FXML
    MFXTextField nameTextField;
    @FXML
    MFXTextField citizenIdTextField;

    @FXML
    MFXTextField reasonTextField;

    @FXML
    MFXDatePicker deathDatePicker;

    @FXML
    MFXButton submitButton;

    private Population population;

    public DeathDeclarationController(Population population) {
        this.population = population;
    }

    @FXML
    public void initialize() {
        DatePickerHelper.setVietnamese(deathDatePicker);
        initializeTextField();
        initializeButton();
    }

    private void initializeTextField() {
        nameTextField.setText(population.getName());
        citizenIdTextField.setText(population.getCitizenId());
    }

    private void initializeButton() {
        submitButton.setOnAction(e -> {
            if (deathDatePicker.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Ngày chết không được để trống");
                return;
            }

            if (deathDatePicker.getValue().isAfter(LocalDate.now())) {
                InformativeBox.display("Thất bại", "Ngày chết không được sau ngày hôm nay");
                return;
            }

            if (reasonTextField.getText().isBlank()) {
                InformativeBox.display("Thất bại", "Lý do không được để trống");
                return;
            }

            DeathDeclaration deathDeclaration = new DeathDeclaration();
            deathDeclaration.setDeceased(population);
            deathDeclaration.setReason(reasonTextField.getText());
            deathDeclaration.setDeathDate(Date.valueOf(deathDatePicker.getValue()));

            DeathDeclarationDao deathDeclarationDao = new DeathDeclarationDao();
            deathDeclarationDao.save(deathDeclaration);

            PopupManager.closeCurrentStage();
            PageManager.refreshCurrentPage();
        });



    }
}
