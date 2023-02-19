package hust.itep.quanlynhankhau.controller.page.covid.popup;

import hust.itep.quanlynhankhau.model.covid.CovidTest;
import hust.itep.quanlynhankhau.model.covid.MovementDeclaration;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class ViewCovidTestController {
    private static final String KEY = "/fxml/page/covid/popup/view-covid-test.fxml";

    public static String getKey() {
        return KEY;
    }
    @FXML
    MFXTextField nameTextField;

    @FXML
    MFXTextField datePicker;

    @FXML
    MFXTextField locationTextField;
    @FXML
    MFXTextField citizenIdTextField;
    @FXML
    MFXTextField methodTextField;
    @FXML
    MFXTextField quarantineComboBox;
    @FXML
    MFXTextField resultComboBox;
    private CovidTest covidTest;

    public ViewCovidTestController(CovidTest covidTest) {
        this.covidTest = covidTest;
    }
    public void initialize() {
        initializeTextField();
    }
    private void initializeTextField() {
        nameTextField.setText(covidTest.getPopulation().getName());
        citizenIdTextField.setText(covidTest.getPopulation().getCitizenId() == null ?
                "" : covidTest.getPopulation().getCitizenId());
        datePicker.setText(covidTest.getTestDate().toString());
        locationTextField.setText(covidTest.getLocation());
        methodTextField.setText(covidTest.getMethod());
        quarantineComboBox.setText(covidTest.getQuarantine() ? "Có" : "Không");
        resultComboBox.setText(covidTest.getResult() ? "Dương tính" : "Âm tính");
    }
}
