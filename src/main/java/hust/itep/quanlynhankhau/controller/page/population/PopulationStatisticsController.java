package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.controller.component.modifier.ComboBoxHelper;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.model.population.TemporaryAbsence;
import hust.itep.quanlynhankhau.model.population.TemporaryResidence;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryAbsenceDao;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryResidenceDao;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.function.Predicate;

public class PopulationStatisticsController {
    private static final String KEY = "/fxml/page/population/population-statistics.fxml";

    public static String getKey() {
        return KEY;
    }
    @FXML
    TableView<Population> populationTable;
    @FXML
    MFXComboBox genderComboBox;

    @FXML
    MFXComboBox statusComboBox;

    @FXML
    MFXTextField ageFromTextField;

    @FXML
    MFXTextField ageToTextField;

    FilteredList<Population> populationFilteredList;
    Predicate<Population> genderPredicate = p -> true;
    Predicate<Population> ageFromPredicate = p -> true;

    Predicate<Population> ageToPredicate = p -> true;

    Predicate<Population> statusPredicate = p -> true;

    @FXML
    public void initialize() {
        initializeTable();
        initializeComboBox();
        initializeTextField();
    }

    private void initializeComboBox() {
        ComboBoxHelper.genderChoice(genderComboBox);
        genderComboBox.setText("Tất cả");

        genderComboBox.textProperty().addListener(observable -> {
            if (!genderComboBox.getText().equals("Tất cả")) {
                genderPredicate = p -> p.getGender().toLowerCase().equals(genderComboBox.getText().toLowerCase());
                populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate).and(statusPredicate));
            } else {
                genderPredicate = p -> true;
                populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate).and(statusPredicate));
            }
        });

        ComboBoxHelper.status(statusComboBox);
        statusComboBox.setText("Tất cả");

        statusComboBox.textProperty().addListener(observable -> {
            if (statusComboBox.getText().equals("Tạm trú")) {
                TemporaryResidenceDao temporaryResidenceDao = new TemporaryResidenceDao();
                ArrayList<TemporaryResidence> temporaryResidences = new ArrayList<>(temporaryResidenceDao.getAll(TemporaryResidence.class));

                temporaryResidences.removeIf(temporaryResidence -> {
                    Date now = Date.valueOf(LocalDate.now());
                    return now.after(temporaryResidence.getToDate()) || now.before(temporaryResidence.getFromDate());
                });

                ArrayList<Long> populations = new ArrayList<>();
                temporaryResidences.forEach(temporaryResidence -> populations.add(temporaryResidence.getPopulation().getId()));

                statusPredicate = p -> populations.contains(p.getId());
                populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate).and(statusPredicate));
            } else if (statusComboBox.getText().equals("Tạm vắng")) {
                TemporaryAbsenceDao temporaryAbsenceDao = new TemporaryAbsenceDao();
                ArrayList<TemporaryAbsence> temporaryAbsences = new ArrayList<>(temporaryAbsenceDao.getAll(TemporaryAbsence.class));

                temporaryAbsences.removeIf(temporaryAbsence -> {
                    Date now = Date.valueOf(LocalDate.now());
                    return now.before(temporaryAbsence.getFromDate()) || now.after(temporaryAbsence.getToDate());
                });

                ArrayList<Long> populations = new ArrayList<>();
                temporaryAbsences.forEach(temporaryAbsence -> populations.add(temporaryAbsence.getPopulation().getId()));

                statusPredicate = p -> populations.contains(p.getId());

                populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate).and(statusPredicate));
            } else {
                statusPredicate = p -> true;
                populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate).and(statusPredicate));
            }
        });

    }

    private void initializeTextField() {
        ageFromTextField.textProperty().addListener(e -> {
            if (ageFromTextField.getText().isBlank()) {
                ageFromPredicate = p -> true;
                populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate));
            } else {
                try {
                    int ageFrom = Integer.valueOf(ageFromTextField.getText());
                    ageFromPredicate = p -> ChronoUnit.YEARS.between(p.getBirthdate().toLocalDate(), LocalDate.now()) >= ageFrom;
                    populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate));
                } catch (Exception ex) {
                    ageFromPredicate = p -> true;
                }
            }
        });

        ageToTextField.textProperty().addListener(e -> {
            if (ageToTextField.getText().isBlank()) {
                ageToPredicate = p -> true;
                populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate));
            } else {
                try {
                    int ageTo = Integer.valueOf(ageToTextField.getText());
                    ageToPredicate = p -> ChronoUnit.YEARS.between(p.getBirthdate().toLocalDate(), LocalDate.now()) <= ageTo;
                    populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate));
                } catch (Exception ex) {
                    ageToPredicate = p -> true;
                }
            }
        });
    }
    private void initializeTable() {
        populationFilteredList =
                new FilteredList<>(FXCollections.observableList(new PopulationDao().getAll(Population.class)));

        populationFilteredList.setPredicate(genderPredicate.and(ageFromPredicate).and(ageToPredicate));

        TableViewHelper.initializePopulationTableView(populationTable, populationFilteredList);
    }

}
