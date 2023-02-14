package hust.itep.quanlynhankhau.controller.page;

import hust.itep.quanlynhankhau.model.Household;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.model.TemporaryAbsence;
import hust.itep.quanlynhankhau.model.TemporaryResidence;
import hust.itep.quanlynhankhau.service.dao.HouseholdDao;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryAbsenceDao;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryResidenceDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class HomeController {
    public final static String KEY = "/fxml/page/home.fxml";

    public static String getKey() {
        return KEY;
    }

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button submitButton;

    @FXML
    private Label populationCountLabel;

    @FXML
    private Label householdCountLabel;

    @FXML
    private Label temporaryAbsceneCountLabel;

    @FXML
    private Label temporaryResidenceCountLabel;

    @FXML
    public void initialize() {
        PopulationDao populationDao = new PopulationDao();
        populationCountLabel.setText(String.valueOf(populationDao.getAll(Population.class).size()));

        HouseholdDao householdDao = new HouseholdDao();
        householdCountLabel.setText(String.valueOf(householdDao.getAll(Household.class).size()));

        TemporaryResidenceDao temporaryResidenceDao = new TemporaryResidenceDao();
        ArrayList<TemporaryResidence> temporaryResidences = new ArrayList<>(temporaryResidenceDao.getAll(TemporaryResidence.class));

        temporaryResidences.removeIf(temporaryResidence -> {
            Date now = Date.valueOf(LocalDate.now());

            return now.after(temporaryResidence.getToDate()) || now.before(temporaryResidence.getFromDate());
        });
        temporaryResidenceCountLabel.setText(String.valueOf(temporaryResidences.size()));

        TemporaryAbsenceDao temporaryAbsenceDao = new TemporaryAbsenceDao();
        ArrayList<TemporaryAbsence> temporaryAbsences = new ArrayList<>(temporaryAbsenceDao.getAll(TemporaryAbsence.class));

        temporaryAbsences.removeIf(temporaryAbsence -> {
           Date now = Date.valueOf(LocalDate.now());
           return now.before(temporaryAbsence.getFromDate()) || now.after(temporaryAbsence.getToDate());
        });

        temporaryAbsceneCountLabel.setText(String.valueOf(temporaryAbsences.size()));
    }

}
