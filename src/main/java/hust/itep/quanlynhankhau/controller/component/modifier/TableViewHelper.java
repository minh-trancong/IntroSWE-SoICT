package hust.itep.quanlynhankhau.controller.component.modifier;

import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import hust.itep.quanlynhankhau.controller.page.population.ViewPopulationController;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.Population;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class TableViewHelper {

    private static <T> void initializeCommonTableView(TableView<T> tableView) {
        int count = tableView.getColumns().size();

        tableView.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(tableView.prefWidthProperty().divide(count));
            column.setEditable(false);
            column.setReorderable(false);
        });

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    public static void initializePopulationTableView(TableView<Population> populationTableView, FilteredList<Population> populations) {
        TableColumn<Population, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Population, String> nameColumn = new TableColumn<>("Tên");
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
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                final int index = row.getIndex();
                if (index >= 0 && !row.isEmpty() && populationTableView.getSelectionModel().isSelected(index)) {
                    populationTableView.getSelectionModel().clearSelection(index);
                    e.consume();
                }
            });

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


}
