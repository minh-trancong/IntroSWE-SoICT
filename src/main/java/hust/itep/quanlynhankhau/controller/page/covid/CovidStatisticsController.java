package hust.itep.quanlynhankhau.controller.page.covid;

import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;

public class CovidStatisticsController {
    private static final String KEY = "/fxml/page/covid/covid-statistics.fxml";

    public static String getKey() {
        return KEY;
    }

    public void initialize() {
        initializeTable();
    }

    FilteredList<CovidTest> covidTests;
    FilteredList<MovementDeclaration> movementDeclarations;
    FilteredList<QuarantineInformation> quarantineInformations;
    @FXML
    TableView<CovidInfo> covidStatisticsTable;
    ArrayList<CovidInfo> covidInfos;
    @FXML
    MFXComboBox statusComboBox;
    Predicate<Population> statusPredicate = p -> true;
    private void initializeTable() {
        initializeCovidInfo();
        FilteredList<CovidInfo> covidInfoFilteredList = new FilteredList<>(FXCollections.observableArrayList(covidInfos));

        TableColumn<CovidInfo, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<CovidInfo, String> nameColumn = new TableColumn<>("Họ và tên");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<CovidInfo, String> tagColumn = new TableColumn<>("Tag");
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));

        TableColumn<CovidInfo, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        covidStatisticsTable.getColumns().addAll(idColumn, nameColumn, tagColumn, dateColumn);
        covidStatisticsTable.setItems(FXCollections.observableArrayList(covidInfos));

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
//                        case "KHAI BÁO CÁCH LY":
//                            QuarantineInformation selectedQuarantineInformation = new QuarantineInformation();
//                            for (QuarantineInformation quarantineInformation : quarantineInformations){
//                                if (Objects.equals(quarantineInformation.getId(), row.getItem().getId())){
//                                    selectedQuarantineInformation = quarantineInformation;
//                                    break;
//                                }
//                            }
//                            PopupManager.setPopup(View....getKey(),
//                                    new ViewMovementDeclarationController(selectedQuarantineInformation),
//                                    StageFactory.buildStage("Thông tin khai báo dịch tễ"));
//                            break;
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
        statusComboBox.getItems().addAll("TẤT CẢ", "TEST COVID", "KHAI BÁO DỊCH TỄ", "KHAI BÁO CÁCH LY");
        statusComboBox.textProperty().addListener(observable -> {
            if (statusComboBox.getText().equals("TẤT CẢ")){
                covidInfoFilteredList.setPredicate(p -> true);
            } else covidInfoFilteredList.setPredicate(i -> i.getTag().equals(statusComboBox.getText()));
            covidStatisticsTable.setItems(FXCollections.observableArrayList(covidInfoFilteredList));
        });
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
}

