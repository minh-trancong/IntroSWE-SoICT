package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.controller.component.popup.ConfirmBox;
import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.component.popup.InformativeBox;
import hust.itep.quanlynhankhau.controller.page.population.popup.AddPopulationController;
import hust.itep.quanlynhankhau.controller.page.population.popup.UpdatePopulationController;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PopulationController {
    private static final String KEY = "/fxml/page/population/population.fxml";
    public static String getKey() {
        return KEY;
    }

    @FXML
    MFXButton addPopulationButton;
    @FXML
    MFXButton deletePopulationButton;
    @FXML
    MFXButton updatePopulationButton;
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
            // Refresh page to do
        });


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

        updatePopulationButton.setOnAction(e -> {
            ObservableList<Population> populationObservableList = populationTableView.getSelectionModel().getSelectedItems();

            if (populationObservableList.size() > 1 || populationObservableList.size() == 0) {
                return;
            }

            PopupManager.setPopup(UpdatePopulationController.getKey(),
                    new UpdatePopulationController(populationObservableList.get(0)),
                    StageFactory.buildStage(updatePopulationButton.getText()));
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
                populations.setPredicate(population -> population.getCitizenId().toLowerCase().equals(text));
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
