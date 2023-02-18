package hust.itep.quanlynhankhau.controller.page.covid;

import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.component.popup.ConfirmBox;
import hust.itep.quanlynhankhau.controller.page.covid.popup.AddCovidTestController;
import hust.itep.quanlynhankhau.controller.page.covid.popup.AddMovementDeclarationController;
import hust.itep.quanlynhankhau.controller.page.covid.popup.AddQuarantineInformationController;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.covid.CovidTest;
import hust.itep.quanlynhankhau.model.covid.QuarantineInformation;
import hust.itep.quanlynhankhau.service.dao.CovidTestDao;
import hust.itep.quanlynhankhau.service.dao.QuarantineInformationDao;
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

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;

public class QuarantineInformationController {
    private static final String KEY = "/fxml/page/covid/quarantine-information.fxml";

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
    TableView<QuarantineInformation> declarationTable;

    FilteredList<QuarantineInformation> quarantineInformations;

    @FXML
    public void initialize() {
        initializeButton();
        initializeTextField();
        initializeTable();
    }

    private void initializeTable() {
        quarantineInformations =
                new FilteredList<>(FXCollections.observableArrayList(
                        new QuarantineInformationDao().getAll(QuarantineInformation.class)));

        TableColumn<QuarantineInformation, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<QuarantineInformation, String> nameColumn = new TableColumn<>("Họ và tên");
        nameColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                return new SimpleStringProperty(e.getValue().getPopulation().getName());
            } else {
                return null;
            }
        });

        TableColumn<QuarantineInformation, String> citizenIdColumn = new TableColumn<>("CCCD");
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

        TableColumn<QuarantineInformation, String> locationColumn = new TableColumn<>("Địa điểm cách ly");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<QuarantineInformation, String> contactLevelColumn = new TableColumn<>("Mức độ tiếp xúc");
        contactLevelColumn.setCellValueFactory(new PropertyValueFactory<>("contactLevel"));

        TableColumn<QuarantineInformation, String> covidTestColumn = new TableColumn<>("Test COVID");
        covidTestColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                return new SimpleStringProperty(e.getValue().getTestCovid() ? "Có" : "Không");
            }
            return null;
        });

        TableColumn<QuarantineInformation, Date> startDateColumn = new TableColumn<>("Ngày bắt đầu");
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<QuarantineInformation, Date> endDateColumn = new TableColumn<>("Ngày kết thúc");
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        ArrayList<TableColumn<QuarantineInformation, ? extends Object>> columns = new ArrayList<>();
        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(citizenIdColumn);
        columns.add(locationColumn);
        columns.add(contactLevelColumn);
        columns.add(covidTestColumn);
        columns.add(startDateColumn);
        columns.add(endDateColumn);


        declarationTable.getColumns().addAll(columns);

        declarationTable.setItems(quarantineInformations);

        TableViewHelper.initializeCommonTableView(declarationTable);
    }


    private void initializeButton() {
        addDeclarationButton.setOnAction(e -> {
            PopupManager.setPopup(AddQuarantineInformationController.getKey(),
                    new AddQuarantineInformationController(), StageFactory.buildStage(addDeclarationButton.getText()));
        });

        deleteDeclarationButton.setOnAction(e -> {
            ArrayList<QuarantineInformation> quarantineInformationArrayList
                    = new ArrayList<>(declarationTable.getSelectionModel().getSelectedItems());

            if (quarantineInformationArrayList.isEmpty()) {
                return;
            }

            if (!ConfirmBox.display("Xóa khai báo", "Bạn có chắc không")) {
                return;
            }

            QuarantineInformationDao quarantineInformationDao = new QuarantineInformationDao();
            quarantineInformationArrayList.forEach(quarantineInformationDao::delete);
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
                quarantineInformations.setPredicate(covidTest -> true);
                return;
            }

            if (idRadioButton.isSelected()) {
                quarantineInformations.setPredicate(covidTest -> {
                    if (covidTest.getPopulation().getCitizenId() == null) {
                        return false;
                    } else {
                        return covidTest.getPopulation().getCitizenId().toLowerCase().contains(text);
                    }
                });
            } else if (locationRadioButton.isSelected()) {
                quarantineInformations.setPredicate(covidTest -> covidTest.getLocation().toLowerCase().contains(text));
            } else if (nameRadioButton.isSelected()) {
                quarantineInformations.setPredicate(covidTest ->
                        covidTest.getPopulation().getName().toLowerCase().contains(text));
            }
        });


    }
}
