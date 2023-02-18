package hust.itep.quanlynhankhau.controller.component.modifier;

import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import hust.itep.quanlynhankhau.controller.page.household.popup.ViewHouseholdController;
import hust.itep.quanlynhankhau.controller.page.population.ViewPopulationController;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.household.Household;
import hust.itep.quanlynhankhau.model.population.Population;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class TableViewHelper {

    public static <T> void initializeCommonTableView(TableView<T> tableView) {
        int count = tableView.getColumns().size();

        tableView.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(tableView.prefWidthProperty().divide(count));
            column.setReorderable(false);
        });

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public static void initializePopulationTableView(TableView<Population> populationTableView, FilteredList<Population> populations) {
        TableColumn<Population, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Population, String> nameColumn = new TableColumn<>("Họ và tên");
        TableColumn<Population, String> genderColumn = new TableColumn<>("Giới tính");
        TableColumn<Population, String> birthdateColumn = new TableColumn<>("Ngày sinh");
        TableColumn<Population, String> citizenIdColumn = new TableColumn<>("CCCD");
        TableColumn<Population, String> addressColumn = new TableColumn<>("Nơi ở hiện tại");
        TableColumn<Population, String> permanentAddressColumn = new TableColumn<>("Nơi thường chú");

        idColumn.setCellValueFactory(new PropertyValueFactory<Population, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Population, String>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Population, String>("gender"));
        birthdateColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                if (e.getValue().getBirthdate() != null) {
                    return new SimpleStringProperty(e.getValue().getBirthdate().toString());
                }
            }
            return new SimpleStringProperty("");
        });
        citizenIdColumn.setCellValueFactory(new PropertyValueFactory("citizenId"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("currentAddress"));
        permanentAddressColumn.setCellValueFactory(new PropertyValueFactory<>("permanentAddress"));

        ArrayList<TableColumn<Population, ? extends Object>> columns = new ArrayList<>();

        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(genderColumn);
        columns.add(birthdateColumn);
        columns.add(citizenIdColumn);
        columns.add(addressColumn);
        columns.add(permanentAddressColumn);

        populationTableView.getColumns().addAll(columns);
        populationTableView.setItems(populations);

        populationTableView.setRowFactory(value -> {
            TableRow<Population> row = new TableRow<>();

            row.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    PopupManager.setPopup(ViewPopulationController.getKey(),
                            new ViewPopulationController(row.getItem()),
                            StageFactory.buildStage("Thông tin nhân khẩu"));
                }
            });

            return row;
        });



        initializeCommonTableView(populationTableView);
    }


    public static void initializeHouseholdTableView(TableView<Household> householdTableView,
                                                    FilteredList<Household> households) {
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

        householdTableView.setRowFactory(e -> {
            TableRow<Household> row = new TableRow<>();

            row.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Household household = row.getItem();
                    PopupManager.setPopup(ViewHouseholdController.getKey(), new ViewHouseholdController(household),
                            StageFactory.buildStage("Thông tin hộ khẩu"));
                }
            });
            return row;
        });


        householdTableView.getColumns().addAll(columns);
        householdTableView.setItems(households);

        initializeCommonTableView(householdTableView);
    }

    public static void initializeHouseholdPopulationTableView(TableView<Population> populationTableView, ObservableList<Population> populations) {
        TableColumn<Population, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Population, String> nameColumn = new TableColumn<>("Họ và tên");
        TableColumn<Population, String> citizenIdColumn = new TableColumn<>("CCCD");

        idColumn.setCellValueFactory(new PropertyValueFactory<Population, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Population, String>("name"));
        citizenIdColumn.setCellValueFactory(new PropertyValueFactory("citizenId"));

        ArrayList<TableColumn<Population, ? extends Object>> columns = new ArrayList<>();

        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(citizenIdColumn);

        populationTableView.getColumns().addAll(columns);
        populationTableView.setItems(populations);

        initializeCommonTableView(populationTableView);
    }

    public static void initializeNewHouseholdPopulationTableView(TableView<Population> newPopulationTable, ObservableList<Population> newPopulations) {
        newPopulationTable.setEditable(true);
        TableColumn<Population, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Population, String> nameColumn = new TableColumn<>("Họ và tên");
        TableColumn<Population, String> citizenIdColumn = new TableColumn<>("CCCD");
        TableColumn<Population, String> relationToHeadColumn = new TableColumn<>("Quan hệ với chủ");

        idColumn.setCellValueFactory(new PropertyValueFactory<Population, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Population, String>("name"));
        citizenIdColumn.setCellValueFactory(new PropertyValueFactory("citizenId"));

        relationToHeadColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        relationToHeadColumn.setOnEditCommit(t -> {
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setRelationshipToHead(t.getNewValue());
        });
        relationToHeadColumn.setEditable(true);
        relationToHeadColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("relationshipToHead")
        );

        ArrayList<TableColumn<Population, ? extends Object>> columns = new ArrayList<>();

        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(citizenIdColumn);
        columns.add(relationToHeadColumn);

        newPopulationTable.getColumns().addAll(columns);
        newPopulationTable.setItems(newPopulations);

        initializeCommonTableView(newPopulationTable);
    }
}
