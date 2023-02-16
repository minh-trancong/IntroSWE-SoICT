package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.model.population.PopulationAddressModification;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.sql.Date;

import java.util.HashMap;

public class ViewPopulationController {
    private static final String KEY = "/fxml/page/population/popup/view-population.fxml";
    public static String getKey() {
        return KEY;
    }

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

    @FXML
    TableView<PopulationAddressModification> addressHistoryTableView;

    private Population population;

    public ViewPopulationController(Population population) {
        this.population = population;
    }

    @FXML
    public void initialize() {
        initializeTextField();
        initializeTableView();
    }

    public void initializeTableView() {
        TableColumn<PopulationAddressModification, String> oldAddressColumn = new TableColumn<>("Địa chỉ cũ");
        TableColumn<PopulationAddressModification, String> newAddressColumn = new TableColumn<>("Địa chỉ mới");
        TableColumn<PopulationAddressModification, Date> changedDateColumn = new TableColumn<>("Ngày thay đổi");

        oldAddressColumn.setCellValueFactory(new PropertyValueFactory<>("oldAddress"));
        newAddressColumn.setCellValueFactory(new PropertyValueFactory<>("newAddress"));
        changedDateColumn.setCellValueFactory(new PropertyValueFactory<>("changeDate"));

        addressHistoryTableView.getColumns().add(oldAddressColumn);
        addressHistoryTableView.getColumns().add(newAddressColumn);
        addressHistoryTableView.getColumns().add(changedDateColumn);

        addressHistoryTableView.getColumns().forEach(e -> {
            e.prefWidthProperty().bind(addressHistoryTableView.prefWidthProperty()
                    .divide(addressHistoryTableView.getColumns().size()));
        });

        addressHistoryTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        addressHistoryTableView.setItems(FXCollections.observableList(population.getPopulationAddressModificationsList()));
    }

    public void initializeTextField() {
        HashMap<String, MFXTextField> textFieldHashMap = new HashMap<>();
        textFieldHashMap.put("name", nameTextField);
        textFieldHashMap.put("phone", phoneTextField);
        textFieldHashMap.put("gender", genderComboBox);
        textFieldHashMap.put("birthdate", birthdateDatePicker);
        textFieldHashMap.put("nationality", nationalityTextField);
        textFieldHashMap.put("ethnicity", ethnicityTextField);
        textFieldHashMap.put("citizenId", citizenIdTextField);
        textFieldHashMap.put("passport", passportTextField);
        textFieldHashMap.put("birthPlace", birthPlaceTextField);
        textFieldHashMap.put("nativePlace", nativePlaceTextField);
        textFieldHashMap.put("occupation", occupationTextField);
        textFieldHashMap.put("permanentAddress", permanentAddressTextField);
        textFieldHashMap.put("currentAddress", currentAddressTextField);

        textFieldHashMap.forEach((key, value) -> {
            value.setDisable(true);

            Field field = null;
            try {
                field = population.getClass().getDeclaredField(key);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);

            try {
                if (field.get(population) != null) {
                    value.setText(field.get(population).toString());
                } else {
                    value.setText("");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
