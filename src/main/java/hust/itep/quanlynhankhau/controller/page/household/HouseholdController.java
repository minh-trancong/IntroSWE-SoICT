package hust.itep.quanlynhankhau.controller.page.household;

import hust.itep.quanlynhankhau.context.Context;
import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.page.household.popup.AddHouseholdController;
import hust.itep.quanlynhankhau.controller.page.household.popup.SplitHouseholdController;
import hust.itep.quanlynhankhau.controller.page.household.popup.UpdateHouseholdController;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.household.Household;
import hust.itep.quanlynhankhau.service.dao.HouseholdDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HouseholdController {
    private static final String KEY = "/fxml/page/household/household.fxml";

    public static String getKey() {
        return KEY;
    }

    @FXML
    RadioButton headNameRadioButton;
    @FXML
    RadioButton areaCodeRadioButton;
    @FXML
    RadioButton addressRadioButton;

    @FXML
    MFXTextField searchTextField;

    @FXML
    TableView<Household> householdTableView;
    @FXML
    MFXButton addHouseholdButton;
    @FXML
    MFXButton deleteHouseholdButton;
    @FXML
    MFXButton splitHouseholdButton;
    @FXML
    MFXButton updateHouseholdButton;

    @FXML
    ToggleGroup searchByToggleGroup;

    private FilteredList<Household> households;

    @FXML
    public void initialize() {
        initializeButton();
        initializeTableView();
        initializeTextField();
    }

    private void initializeButton() {
        addHouseholdButton.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle(addHouseholdButton.getText());
            stage.setResizable(false);
            stage.getIcons().add(Context.ICON);
            stage.initModality(Modality.APPLICATION_MODAL);

            PopupManager.setPopup(AddHouseholdController.getKey(), new AddHouseholdController(), stage);
        });

        splitHouseholdButton.setOnAction(e -> {
            int size = householdTableView.getSelectionModel().getSelectedItems().size();
            if (size != 1) {
                return;
            }

            PopupManager.setPopup(SplitHouseholdController.getKey(),
                    new SplitHouseholdController(householdTableView.getSelectionModel().getSelectedItem()),
                    StageFactory.buildStage(splitHouseholdButton.getText()));
        });

        updateHouseholdButton.setOnAction(e -> {
            int size = householdTableView.getSelectionModel().getSelectedItems().size();
            if (size != 1) {
                return;
            }

            PopupManager.setPopup(UpdateHouseholdController.getKey(),
                    new UpdateHouseholdController(householdTableView.getSelectionModel().getSelectedItem()),
                    StageFactory.buildStage(updateHouseholdButton.getText()));
        });
    }

    private void initializeTextField() {
        searchByToggleGroup.selectedToggleProperty().addListener((observable) -> {
            searchTextField.setText(searchTextField.getText());
        });

        searchTextField.textProperty().addListener(e -> {
            String text = searchTextField.getText().toLowerCase();

            if (text.isBlank()) {
                households.setPredicate(population -> true);
                return;
            }

            if (headNameRadioButton.isSelected()) {
                households.setPredicate(household -> household.getHeadOfHouseHold().getName().toLowerCase().contains(text));
            } else if (addressRadioButton.isSelected()) {
                households.setPredicate(population -> population.getAddress().toLowerCase().contains(text));
            } else if (areaCodeRadioButton.isSelected()) {
                households.setPredicate(household -> household.getAreaCode().toLowerCase().contains(text));
            }
        });
    }

    private void initializeTableView() {
        households = new FilteredList<>(FXCollections.observableList(new HouseholdDao().getAll(Household.class)));

        TableViewHelper.initializeHouseholdTableView(householdTableView, households);
    }

}
