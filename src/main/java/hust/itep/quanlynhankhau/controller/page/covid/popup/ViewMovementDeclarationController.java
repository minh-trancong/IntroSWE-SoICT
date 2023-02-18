package hust.itep.quanlynhankhau.controller.page.covid.popup;

import hust.itep.quanlynhankhau.model.covid.MovementDeclaration;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class ViewMovementDeclarationController {
    private static final String KEY = "/fxml/page/covid/popup/view-movement-declaration.fxml";

    public static String getKey() {
        return KEY;
    }
    @FXML
    MFXTextField nameTextField;
    @FXML
    MFXTextField citizenIdTextField;
    @FXML
    MFXTextField covidTextField;
    @FXML
    MFXTextField departureTextField;
    @FXML
    MFXTextField departureDateTextField;
    @FXML
    MFXTextField destinationTextField;
    @FXML
    MFXTextField symptomsTextField;
    @FXML
    MFXTextField vehicleTextField;
    @FXML
    MFXTextField licensePlateTextField;
    @FXML
    MFXTextField passengerNumberTextField;
    @FXML
    MFXTextField historyTextField;

    @FXML
    MFXTextField declarationDateTextField;

    private MovementDeclaration movementDeclaration;

    public ViewMovementDeclarationController(MovementDeclaration movementDeclaration) {
        this.movementDeclaration = movementDeclaration;
    }


    @FXML
    public void initialize() {
        initializeTextField();
    }

    private void initializeTextField() {
        nameTextField.setText(movementDeclaration.getPopulation().getName());
        citizenIdTextField.setText(movementDeclaration.getPopulation().getCitizenId() == null ?
                "" : movementDeclaration.getPopulation().getCitizenId());
        covidTextField.setText(movementDeclaration.getHasCovid() ? "Có" : "Không");
        departureTextField.setText(movementDeclaration.getDepartureLocation());
        departureDateTextField.setText(movementDeclaration.getDepartureDate().toString());
        destinationTextField.setText(movementDeclaration.getDestination());
        symptomsTextField.setText(movementDeclaration.getSymptoms());
        vehicleTextField.setText(movementDeclaration.getVehicleType());
        licensePlateTextField.setText(movementDeclaration.getLicensePlate());
        passengerNumberTextField.setText(movementDeclaration.getNumberOfPassengers().toString());
        historyTextField.setText(movementDeclaration.getHistory());
        declarationDateTextField.setText(movementDeclaration.getDeclarationDate().toString());

    }

}
