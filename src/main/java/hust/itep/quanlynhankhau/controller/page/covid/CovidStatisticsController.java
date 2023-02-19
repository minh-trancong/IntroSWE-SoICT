package hust.itep.quanlynhankhau.controller.page.covid;

import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import hust.itep.quanlynhankhau.controller.component.modifier.ComboBoxHelper;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.page.covid.popup.ViewCovidTestController;
import hust.itep.quanlynhankhau.controller.page.covid.popup.ViewMovementDeclarationController;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.covid.CovidInfo;
import hust.itep.quanlynhankhau.model.covid.CovidTest;
import hust.itep.quanlynhankhau.model.covid.MovementDeclaration;
import hust.itep.quanlynhankhau.model.covid.QuarantineInformation;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.model.population.TemporaryAbsence;
import hust.itep.quanlynhankhau.model.population.TemporaryResidence;
import hust.itep.quanlynhankhau.service.dao.CovidTestDao;
import hust.itep.quanlynhankhau.service.dao.MovementDeclarationDao;
import hust.itep.quanlynhankhau.service.dao.QuarantineInformationDao;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryAbsenceDao;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryResidenceDao;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;
import java.time.*;

public class CovidStatisticsController {
    private static final String KEY = "/fxml/page/covid/covid-statistics.fxml";

    public static String getKey() {
        return KEY;
    }

    FilteredList<CovidTest> covidTests;
    FilteredList<MovementDeclaration> movementDeclarations;
    FilteredList<QuarantineInformation> quarantineInformations;
    @FXML
    TableView<CovidInfo> covidStatisticsTable;
    ArrayList<CovidInfo> covidInfos;
    @FXML
    MFXComboBox tagComboBox;
    Predicate<Population> statusPredicate = p -> true;
    FilteredList<CovidInfo> covidInfoFilteredList;
    @FXML
    MFXDatePicker fromDate;
    @FXML
    MFXDatePicker toDate;
    Predicate<CovidInfo> fromDatePredicate = p -> true;
    Predicate<CovidInfo> toDatePredicate = p -> true;
    Predicate<CovidInfo> tagPredicate = p -> true;

    public void initialize() {
        initializeCovidInfo();
        initializeTable();
        initializeComboBox();
        initializeDatePickers();
    }

    private void initializeTable() {
        covidInfoFilteredList = new FilteredList<>(FXCollections.observableArrayList(covidInfos));

        TableColumn<CovidInfo, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CovidInfo, String> nameColumn = new TableColumn<>("Họ và tên");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<CovidInfo, String> tagColumn = new TableColumn<>("Tag");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));

        TableColumn<CovidInfo, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        covidStatisticsTable.getColumns().addAll(idColumn, nameColumn, tagColumn, dateColumn);

        covidStatisticsTable.setRowFactory(e -> {
            TableRow<CovidInfo> row = new TableRow<>();
            row.addEventHandler(MouseEvent.MOUSE_CLICKED, ex -> {
                if (ex.getClickCount() == 2 && !row.isEmpty()) {
                    switch (row.getItem().getTag()){
                        case "KHAI BÁO DỊCH TỄ":
                            MovementDeclaration selectedMovementDeclaration = new MovementDeclaration();
                            for (MovementDeclaration movementDeclaration : movementDeclarations){
                                if (Objects.equals(movementDeclaration.getId(), row.getItem().getId())){
                                    selectedMovementDeclaration = movementDeclaration;
                                    break;
                                }
                            }
                                 PopupManager.setPopup(ViewMovementDeclarationController.getKey(),
                                    new ViewMovementDeclarationController(selectedMovementDeclaration),
                                    StageFactory.buildStage("Thông tin khai báo dịch tễ"));
                            break;
                        case "KHAI BÁO CÁCH LY":
                            //TODO
                            break;
                        case "TEST COVID":
                            CovidTest selectedCovidTest = new CovidTest();
                            for (CovidTest covidTest : covidTests){
                                if (Objects.equals(covidTest.getId(), row.getItem().getId())){
                                    selectedCovidTest = covidTest;
                                    break;
                                }
                            }
                            PopupManager.setPopup(ViewCovidTestController.getKey(),
                                    new ViewCovidTestController(selectedCovidTest),
                                    StageFactory.buildStage("THÔNG TIN TEST COVID"));
                            break;
                    }

                }
            });
            return row;
        });
        covidInfoFilteredList.setPredicate(tagPredicate.and(fromDatePredicate).and(toDatePredicate));
        covidStatisticsTable.setItems(FXCollections.observableArrayList(covidInfoFilteredList));
    }

    private void initializeCovidInfo(){
        covidTests = new FilteredList<>(
                FXCollections.observableArrayList(new CovidTestDao().getAll(CovidTest.class))
        );
        movementDeclarations =
                new FilteredList<>(FXCollections.observableArrayList(new MovementDeclarationDao().getAll(MovementDeclaration.class)));
        quarantineInformations =
                new FilteredList<>(FXCollections.observableArrayList(
                        new QuarantineInformationDao().getAll(QuarantineInformation.class)));
        this.covidInfos = new ArrayList<>();
        for (CovidTest covidTest : covidTests) {
            CovidInfo covidInfo = new CovidInfo(
                    covidTest.getId(),
                    covidTest.getPopulation().getName(),
                    "TEST COVID",
                    covidTest.getTestDate()
            );
            this.covidInfos.add(covidInfo);
        }

        for (MovementDeclaration movementDeclaration : movementDeclarations){
            CovidInfo covidInfo = new CovidInfo(
                    movementDeclaration.getId(),
                    movementDeclaration.getPopulation().getName(),
                    "KHAI BÁO DỊCH TỄ",
                    movementDeclaration.getDeclarationDate()
            );
            this.covidInfos.add(covidInfo);
        }

        for (QuarantineInformation quarantineInformation : quarantineInformations){
            CovidInfo covidInfo = new CovidInfo(
                    quarantineInformation.getId(),
                    quarantineInformation.getPopulation().getName(),
                    "KHAI BÁO CÁCH LY",
                    quarantineInformation.getStartTime()
            );
            this.covidInfos.add(covidInfo);
        }
    }
    private void initializeComboBox(){
        ComboBoxHelper.tagChoice(tagComboBox);
        tagComboBox.setText("TẤT CẢ");
        tagComboBox.textProperty().addListener(event -> {
            String selectedTag = tagComboBox.getText();
            tagPredicate = p -> selectedTag.equals("TẤT CẢ") || p.getTag().equals(selectedTag);
            covidInfoFilteredList.setPredicate(fromDatePredicate.and(toDatePredicate).and(tagPredicate));
            covidStatisticsTable.setItems(covidInfoFilteredList);
        });
    }

    private void initializeDatePickers() {
        fromDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate fromDateValue = newValue;
            LocalDate toDateValue = toDate.getValue();
            if (fromDateValue != null && toDateValue != null) {
                fromDatePredicate = p -> p.getDate().compareTo(Date.from(fromDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant())) >= 0;
                toDatePredicate = p -> p.getDate().compareTo(Date.from(toDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant())) <= 0;
                covidInfoFilteredList.setPredicate(tagPredicate.and(fromDatePredicate).and(toDatePredicate));
            } else if (fromDateValue != null) {
                fromDatePredicate = p -> p.getDate().compareTo(Date.from(fromDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant())) >= 0;
                covidInfoFilteredList.setPredicate(tagPredicate.and(fromDatePredicate));
            }
        });

        toDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate fromDateValue = fromDate.getValue();
            LocalDate toDateValue = newValue;
            if (fromDateValue != null && toDateValue != null) {
                fromDatePredicate = p -> p.getDate().compareTo(Date.from(fromDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant())) >= 0;
                toDatePredicate = p -> p.getDate().compareTo(Date.from(toDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant())) <= 0;
                covidInfoFilteredList.setPredicate(tagPredicate.and(fromDatePredicate).and(toDatePredicate));
            } else if (toDateValue != null) {
                toDatePredicate = p -> p.getDate().compareTo(Date.from(toDateValue.atStartOfDay(ZoneId.systemDefault()).toInstant())) <= 0;
                covidInfoFilteredList.setPredicate(tagPredicate.and(toDatePredicate));
            }
        });
        covidStatisticsTable.setItems(covidInfoFilteredList);
    }

}

