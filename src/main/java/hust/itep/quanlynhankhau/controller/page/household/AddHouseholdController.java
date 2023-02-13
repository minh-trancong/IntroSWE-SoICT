package hust.itep.quanlynhankhau.controller.page.household;

import hust.itep.quanlynhankhau.controller.page.LoginController;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.model.Household;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.dao.HouseholdDao;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import io.github.palexdev.materialfx.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddHouseholdController {
    private static final String KEY = "/fxml/page/household/add-household.fxml";

    public static String getKey() {
        return KEY;
    }

    @FXML
    private MFXTextField currentAddressTextField;
    @FXML
    private MFXTextField areaCodeTextField;
    @FXML
    private MFXTextField headOfHouseholdTextField;
    @FXML
    private TableView<Population> populationTable;
    @FXML
    private TableView<Population> addPopulationTable;
    @FXML
    private MFXButton householdHeadButton;
    @FXML
    private MFXButton addPopulationButton;
    @FXML
    private MFXButton submitButton;
    @FXML
    private MFXButton removePopulationButton;
    private ObservableList<Population> items;
    private ObservableList<Population> addedItems;
    private Population headPopulation = null;

    @FXML
    public void initialize() {
        addedItems = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList(new PopulationDao().getAll(Population.class));
        items.removeIf(p -> p.getHousehold() != null);
        initializeTable();
        initializeAddTable();
        initializeButton();
    }

    private void initializeButton() {
        submitButton.setOnMouseClicked(e -> {
            Household household = new Household();
            household.setAddress(currentAddressTextField.getText());
            household.setAccount(LoginController.currentAccount);
            household.setCreatedAt(Date.valueOf(LocalDate.now()));
            household.setHeadOfHouseHold(headPopulation);
            household.setAreaCode(areaCodeTextField.getText());

            HouseholdDao householdDao = new HouseholdDao();
            householdDao.save(household);

            PopulationDao populationDao = new PopulationDao();

            for (Population population : addedItems) {
                population.setHousehold(household);
                populationDao.update(population);
            }
            headPopulation.setRelationshipToHead("Là chủ hộ");
            headPopulation.setHousehold(household);
            populationDao.update(headPopulation);
            PageManager.setPage(AddHouseholdController.getKey());
        });
    }

    private void initializeAddTable() {
        TableColumn<Population, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Population, String> nameColumn = new TableColumn<>("Họ và tên");
        TableColumn<Population, String> relationToHeadColumn = new TableColumn<>("Quan hệ với chủ hộ");
        addPopulationTable.setEditable(true);
        relationToHeadColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        relationToHeadColumn.setOnEditCommit(t -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setRelationshipToHead(t.getNewValue());
        });
        relationToHeadColumn.setEditable(true);
        relationToHeadColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("relationshipToHead")
        );

        idColumn.setCellValueFactory(
            new PropertyValueFactory<Population, Long>("id"));
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("name"));

        ArrayList<TableColumn<Population, ? extends Object>> columns = new ArrayList<>();
        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(relationToHeadColumn);

        for (TableColumn<Population, ? extends Object> column : columns) {
            column.prefWidthProperty().bind(addPopulationTable.widthProperty().multiply(1d / columns.size()));
        }

        addPopulationTable.getColumns().addAll(columns);
        addPopulationTable.setItems(addedItems);
    }

    private void initializeTable() {
        TableColumn<Population, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Population, String> nameColumn = new TableColumn<>("Họ và tên");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<Population, Long>("id"));
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("name"));

        ArrayList<TableColumn<Population, ? extends Object>> columns = new ArrayList<>();
        columns.add(idColumn);
        columns.add(nameColumn);

        for (TableColumn<Population, ? extends Object> column : columns) {
            column.prefWidthProperty().bind(populationTable.widthProperty().multiply(1d / columns.size()));
        }

        populationTable.getColumns().addAll(columns);
        populationTable.setItems(items);

        populationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                householdHeadButton.setOnMouseClicked(e -> {
                    addedItems.remove(newValue);
                    newValue.setRelationshipToHead(null);
                    headOfHouseholdTextField.setText(newValue.getName());
                    headPopulation = newValue;
                });

                addPopulationButton.setOnMouseClicked(e -> {
                    if (newValue == headPopulation) {
                        return;
                    }

                    if (!addedItems.contains(newValue)) {
                        addedItems.add(newValue);
                    }
                });

                removePopulationButton.setOnMouseClicked(e -> {
                    addedItems.remove(newValue);
                    newValue.setRelationshipToHead(null);
                });
            }
        });
    }


}
