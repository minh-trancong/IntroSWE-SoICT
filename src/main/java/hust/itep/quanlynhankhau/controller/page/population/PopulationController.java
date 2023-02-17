package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.page.population.popup.*;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.population.DeathDeclaration;
import hust.itep.quanlynhankhau.model.population.Population;
import hust.itep.quanlynhankhau.model.population.TemporaryAbsence;
import hust.itep.quanlynhankhau.model.population.TemporaryResidence;
import hust.itep.quanlynhankhau.service.dao.population.DeathDeclarationDao;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryAbsenceDao;
import hust.itep.quanlynhankhau.service.dao.population.TemporaryResidenceDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class PopulationController {
    private static final String KEY = "/fxml/page/population/population.fxml";
    public static String getKey() {
        return KEY;
    }

    @FXML
    MFXButton addPopulationButton;

    /*
    @FXML
    MFXButton deletePopulationButton;
     */

    @FXML
    MFXButton updatePopulationButton;

    @FXML
    MFXButton absenceButton;

    @FXML
    MFXButton residenceButton;
    @FXML
    MFXButton deathButton;
    @FXML
    TableView<Population> populationTableView;
    @FXML
    RadioButton idRadioButton;
    @FXML
    RadioButton nameRadioButton;
    @FXML
    RadioButton addressRadioButton;
    @FXML
    MFXTextField searchTextField;

    @FXML
    ToggleGroup searchByToggleGroup;
    private FilteredList<Population> populations;

    @FXML
    public void initialize() {
        initializeTableView();
        initializeSearchTextField();
        initializeButton();
    }

    private void initializeButton() {

        addPopulationButton.setOnAction(e -> {
            PopupManager.setPopup(AddPopulationController.getKey(),
                    new AddPopulationController(),
                    StageFactory.buildStage(addPopulationButton.getText()));
        });


        deathButton.setOnAction(e -> {
            ObservableList<Population> populationObservableList = populationTableView.getSelectionModel().getSelectedItems();

            if (populationObservableList.size() != 1) {
                return;
            }

            Population population = populationTableView.getSelectionModel().getSelectedItem();

            if (population.getRelationshipToHead().equals("Là chủ hộ")) {
                InformativeBox.display("Thất bại", "Không thể khai tử chủ hộ");
                return;
            }

            DeathDeclarationDao deathDeclarationDao = new DeathDeclarationDao();
            ArrayList<DeathDeclaration> deathDeclarations =
                    new ArrayList<>(deathDeclarationDao.getAll(DeathDeclaration.class));


            for (DeathDeclaration deathDeclaration : deathDeclarations) {
                if (deathDeclaration.getDeceased().getId() == population.getId()) {
                    InformativeBox.display("Thất bại", "Nhân khẩu đã được khai tử");
                    return;
                }
            }

            PopupManager.setPopup(DeathDeclarationController.getKey(),
                    new DeathDeclarationController(population), StageFactory.buildStage(deathButton.getText()));
        });
        /*
        // Only delete if list contains no house hold head
        deletePopulationButton.setOnAction(e -> {
            ObservableList<Population> populationObservableList = populationTableView.getSelectionModel().getSelectedItems();

            if (populationObservableList.isEmpty()) {
                return;
            }

            boolean confirmation = ConfirmBox.display("Xóa nhân khẩu", "Bạn có chắc không ?");

            if (confirmation) {
                for (Population population : populationObservableList) {
                    if (population.getRelationshipToHead() != null && population.getRelationshipToHead().equals("Là chủ hộ")) {

                        InformativeBox.display("Thất bại", "Không thể xóa chủ hộ");
                        return;
                    }
                }

                PopulationDao populationDao = new PopulationDao();

                populationObservableList.forEach(population -> populationDao.delete(population));
                InformativeBox.display("Thành công", "Xóa nhân khẩu thành công");
                PageManager.refreshCurrentPage();
            }
        });

         */

        updatePopulationButton.setOnAction(e -> {
            ObservableList<Population> populationObservableList = populationTableView.getSelectionModel().getSelectedItems();

            if (populationObservableList.size() != 1) {
                return;
            }

            PopupManager.setPopup(UpdatePopulationController.getKey(),
                    new UpdatePopulationController(populationObservableList.get(0)),
                    StageFactory.buildStage(updatePopulationButton.getText()));
        });

        absenceButton.setOnAction(e -> {
            ObservableList<Population> populationObservableList = populationTableView.getSelectionModel().getSelectedItems();

            if (populationObservableList.size() != 1) {
                return;
            }

            Population population = populationTableView.getSelectionModel().getSelectedItem();
            // Check if population is in a household if not display error
            if (population.getHousehold() == null) {
                InformativeBox.display("Không được phép", "Nhân khẩu không có trong hộ nào");
                return;
            }
            // Check if population is already absence if true display error
            TemporaryAbsenceDao temporaryAbsenceDao = new TemporaryAbsenceDao();
            ArrayList<TemporaryAbsence> temporaryAbsences =
                    new ArrayList<>(temporaryAbsenceDao.getAll(TemporaryAbsence.class));

            temporaryAbsences.removeIf(temporaryAbsence ->
                    temporaryAbsence.getPopulation().getId() != population.getId());

            temporaryAbsences.removeIf(temporaryAbsence -> {
                Date now = Date.valueOf(LocalDate.now());
                return now.after(temporaryAbsence.getToDate());
            });

            if (temporaryAbsences.size() != 0) {
                InformativeBox.display("Không được phép", "Nhân khẩu đang có giấy tạm vắng chưa hoàn thành");
                return;
            }

            // Display form
            PopupManager.setPopup(RegisterTemporaryAbsenceController.getKey(),
                    new RegisterTemporaryAbsenceController(population),
                    StageFactory.buildStage(absenceButton.getText()));
        });

        residenceButton.setOnAction(e -> {
            ObservableList<Population> populationObservableList = populationTableView.getSelectionModel().getSelectedItems();

            if (populationObservableList.size() != 1) {
                return;
            }

            Population population = populationTableView.getSelectionModel().getSelectedItem();
            if (population.getHousehold() != null) {
                InformativeBox.display("Không được phép", "Nhân khẩu đã ở trong hộ");
                return;
            }

            // Check if population is already a residence
            TemporaryResidenceDao temporaryResidenceDao = new TemporaryResidenceDao();
            ArrayList<TemporaryResidence> temporaryResidences =
                    new ArrayList<>(temporaryResidenceDao.getAll(TemporaryResidence.class));

            temporaryResidences.removeIf(temporaryResidence ->
                temporaryResidence.getPopulation().getId() != population.getId());

            temporaryResidences.removeIf(temporaryResidence -> {
               Date now = Date.valueOf(LocalDate.now());
               return now.after(temporaryResidence.getToDate());
            });

            if (temporaryResidences.size() != 0) {
                InformativeBox.display("Không được phép", "Nhân khẩu đang có giấy tạm trú chưa hoàn thành");
                return;
            }


            PopupManager.setPopup(RegisterTemporaryResidenceController.getKey(),
                    new RegisterTemporaryResidenceController(population),
                    StageFactory.buildStage(residenceButton.getText()));
        });

    }

    private void initializeSearchTextField() {
        searchByToggleGroup.selectedToggleProperty().addListener((observable) -> {
            searchTextField.setText(searchTextField.getText());
        });

        searchTextField.textProperty().addListener(e -> {
            String text = searchTextField.getText().toLowerCase();

            if (text.isBlank()) {
                populations.setPredicate(population -> true);
                return;
            }

            if (idRadioButton.isSelected()) {
                populations.setPredicate(population -> {
                  if (population.getCitizenId() == null) {
                      return false;
                  } else {
                      return population.getCitizenId().toLowerCase().contains(text);
                  }
                });
            } else if (addressRadioButton.isSelected()) {
                populations.setPredicate(population -> population.getCurrentAddress().toLowerCase().contains(text));
            } else if (nameRadioButton.isSelected()) {
                populations.setPredicate(population -> population.getName().toLowerCase().contains(text));
            }
        });


    }

    private void initializeTableView() {
        populations = new FilteredList<>(FXCollections.observableList(new PopulationDao().getAll(Population.class)));
        TableViewHelper.initializePopulationTableView(populationTableView, populations);
    }
}
