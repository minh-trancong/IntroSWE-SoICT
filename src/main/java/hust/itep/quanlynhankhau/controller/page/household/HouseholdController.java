package hust.itep.quanlynhankhau.controller.page.household;

import hust.itep.quanlynhankhau.context.Context;
import hust.itep.quanlynhankhau.controller.page.population.AddPopulationController;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.Household;
import hust.itep.quanlynhankhau.service.dao.HouseholdDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class HouseholdController {
    private static final String KEY = "/fxml/page/household/household.fxml";

    public static String getKey() {
        return KEY;
    }


    @FXML
    RadioButton headNameRadioButton;
    @FXML
    RadioButton areaCodeRadioButton;
    @FXML
    RadioButton addressRadioButton;

    @FXML
    MFXTextField searchTextField;

    @FXML
    TableView<Household> householdTableView;
    @FXML
    MFXButton addHouseholdButton;
    @FXML
    MFXButton deleteHouseholdButton;
    @FXML
    MFXButton splitHouseholdButton;
    @FXML
    MFXButton moveHouseholdButton;

    @FXML
    ToggleGroup searchByToggleGroup;

    private FilteredList<Household> households;

    @FXML
    public void initialize() {
        initializeButton();
        initializeTableView();
        initializeTextField();
    }

    private void initializeButton() {
        addHouseholdButton.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle(addHouseholdButton.getText());
            stage.setResizable(false);
            stage.getIcons().add(Context.ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            PopupManager.setPopup(AddHouseholdController.getKey(), new AddPopulationController(), stage);
        });
    }

    private void initializeTextField() {
        searchByToggleGroup.selectedToggleProperty().addListener((observable) -> {
            searchTextField.setText(searchTextField.getText());
        });

        searchTextField.textProperty().addListener(e -> {
            String text = searchTextField.getText().toLowerCase();

            if (text.isBlank()) {
                households.setPredicate(population -> true);
                return;
            }

            if (headNameRadioButton.isSelected()) {
                households.setPredicate(household -> household.getHeadOfHouseHold().getName().toLowerCase().contains(text));
            } else if (addressRadioButton.isSelected()) {
                households.setPredicate(population -> population.getAddress().toLowerCase().contains(text));
            } else if (areaCodeRadioButton.isSelected()) {
                households.setPredicate(household -> household.getAreaCode().toLowerCase().contains(text));
            }
        });
    }

    private void initializeTableView() {
        households = new FilteredList<>(FXCollections.observableList(new HouseholdDao().getAll(Household.class)));

        TableColumn<Household, Long> idColumn = new TableColumn<>("Mã hộ khẩu");
        TableColumn<Household, String> headNameColumn = new TableColumn<>("Tên chủ hộ");
        TableColumn<Household, String> headIdColumn = new TableColumn<>("CCCD chủ hộ");
        TableColumn<Household, String> addressColumn = new TableColumn<>("Địa chỉ");
        TableColumn<Household, String> areaCodeColumn = new TableColumn<>("Mã khu vực");

        idColumn.setCellValueFactory(new PropertyValueFactory<Household, Long>("householdId"));
        headNameColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                return new SimpleStringProperty(e.getValue().getHeadOfHouseHold().getName().toString());
            }
            return null;
        });
        headIdColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                return new SimpleStringProperty(e.getValue().getHeadOfHouseHold().getCitizenId());
            }
            return null;
        });

        addressColumn.setCellValueFactory(new PropertyValueFactory<Household, String>("address"));
        areaCodeColumn.setCellValueFactory(new PropertyValueFactory<Household, String>("areaCode"));

        ArrayList<TableColumn<Household, ? extends Object>> columns = new ArrayList<>();

        columns.add(idColumn);
        columns.add(headNameColumn);
        columns.add(headIdColumn);
        columns.add(addressColumn);
        columns.add(areaCodeColumn);

        householdTableView.getColumns().addAll(columns);
        householdTableView.setItems(households);

        int count = householdTableView.getColumns().size();

        householdTableView.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(householdTableView.prefWidthProperty().divide(count));
            column.setEditable(false);
            column.setReorderable(false);
        });
    }



}
