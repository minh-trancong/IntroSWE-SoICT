package hust.itep.quanlynhankhau.controller.page.covid;


import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.component.popup.ConfirmBox;
import hust.itep.quanlynhankhau.controller.page.covid.popup.AddCovidTestController;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.covid.CovidTest;
import hust.itep.quanlynhankhau.model.covid.MovementDeclaration;
import hust.itep.quanlynhankhau.service.dao.CovidTestDao;
import hust.itep.quanlynhankhau.service.dao.MovementDeclarationDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.Table;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Filter;

public class CovidTestController {
    private static final String KEY = "/fxml/page/covid/covid-test.fxml";

    public static String getKey() {
        return KEY;
    }

    @FXML
    MFXButton addDeclarationButton;

    @FXML
    MFXButton deleteDeclarationButton;

    // Radio buttons
    @FXML
    RadioButton idRadioButton;
    @FXML
    RadioButton nameRadioButton;
    @FXML
    RadioButton locationRadioButton;
    @FXML
    MFXTextField searchTextField;

    @FXML
    ToggleGroup searchByToggleGroup;


    @FXML
    TableView<CovidTest> declarationTable;

    FilteredList<CovidTest> covidTests;


    @FXML
    public void initialize() {
        initializeTable();
        initializeButton();
        initializeTextField();
    }

    private void initializeButton() {
        addDeclarationButton.setOnAction(e -> {
            PopupManager.setPopup(AddCovidTestController.getKey(), new AddCovidTestController(),
                    StageFactory.buildStage(addDeclarationButton.getText()));
        });


        deleteDeclarationButton.setOnAction(e -> {
            ArrayList<CovidTest> covidTestArrayList
                    = new ArrayList<>(declarationTable.getSelectionModel().getSelectedItems());

            if (covidTestArrayList.isEmpty()) {
                return;
            }

            if (!ConfirmBox.display("Xóa khai báo", "Bạn có chắc không")) {
                return;
            }

            CovidTestDao covidTestDao = new CovidTestDao();
            covidTestArrayList.forEach(covidTestDao::delete);
            PageManager.refreshCurrentPage();
        });
    }

    private void initializeTextField() {
        searchByToggleGroup.selectedToggleProperty().addListener(e -> {
            searchTextField.setText(searchTextField.getText());
        });

        searchTextField.textProperty().addListener(e -> {
            String text = searchTextField.getText().toLowerCase();

            if (text.isBlank()) {
                covidTests.setPredicate(covidTest -> true);
                return;
            }

            if (idRadioButton.isSelected()) {
                covidTests.setPredicate(covidTest -> {
                    if (covidTest.getPopulation().getCitizenId() == null) {
                        return false;
                    } else {
                        return covidTest.getPopulation().getCitizenId().toLowerCase().contains(text);
                    }
                });
            } else if (locationRadioButton.isSelected()) {
                covidTests.setPredicate(covidTest -> covidTest.getLocation().toLowerCase().contains(text));
            }  else if (nameRadioButton.isSelected()) {
                covidTests.setPredicate(covidTest ->
                        covidTest.getPopulation().getName().toLowerCase().contains(text));
            }
        });


    }


    private void initializeTable() {
        covidTests =
                new FilteredList<>(FXCollections.observableArrayList(new CovidTestDao().getAll(CovidTest.class)));

        TableColumn<CovidTest, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CovidTest, String> nameColumn = new TableColumn<>("Họ và tên");
        nameColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                return new SimpleStringProperty(e.getValue().getPopulation().getName());
            } else {
                return null;
            }
        });

        TableColumn<CovidTest, String> citizenIdColumn = new TableColumn<>("CCCD");
        citizenIdColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                if (e.getValue().getPopulation().getCitizenId() != null) {
                    return new SimpleStringProperty(e.getValue().getPopulation().getCitizenId());
                } else {
                    return null;
                }
            }
            return null;
        });

        TableColumn<CovidTest, String> testLocationColumn = new TableColumn<>("Địa điểm Test");
        testLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<CovidTest, Date> testDateColumn = new TableColumn<>("Ngày test");
        testDateColumn.setCellValueFactory(new PropertyValueFactory<>("testDate"));

        TableColumn<CovidTest, String> methodColumn = new TableColumn<>("Cách test");
        methodColumn.setCellValueFactory(new PropertyValueFactory<>("method"));

        TableColumn<CovidTest, String> resultColumn = new TableColumn<>("Kết quả");
        resultColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                String ret = e.getValue().getResult() ? "Dương tính" : "Âm tính";
                return new SimpleStringProperty(ret);
            }
            return null;
        });

        TableColumn<CovidTest, String> quarantineColumn = new TableColumn<>("Cách ly");
        quarantineColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                String ret = e.getValue().getQuarantine() ? "Có" : "Không";
                return new SimpleStringProperty(ret);
            }
            return null;
        });

        ArrayList<TableColumn<CovidTest, ? extends Object>> columns = new ArrayList<>();
        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(citizenIdColumn);
        columns.add(testLocationColumn);
        columns.add(testDateColumn);
        columns.add(methodColumn);
        columns.add(resultColumn);
        columns.add(quarantineColumn);

        declarationTable.getColumns().addAll(columns);

        declarationTable.setItems(covidTests);

        TableViewHelper.initializeCommonTableView(declarationTable);
    }

}
