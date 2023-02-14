package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.context.Context;
import hust.itep.quanlynhankhau.controller.component.ConfirmBox;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

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
            Stage stage = new Stage();
            stage.setTitle(addPopulationButton.getText());
            stage.setResizable(false);
            stage.getIcons().add(Context.ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            PageManager.setPageDialog(AddPopulationController.getKey(), stage);
        });



        deletePopulationButton.setOnAction(e -> {
            boolean confirmation = ConfirmBox.display("Xóa nhân khẩu", "Bạn có chắc không ?");

            if (confirmation) {
                System.out.println("Agree");
            } else {
                System.out.println("NONONON");
            }
        });

        updatePopulationButton.setOnAction(e -> {
            Population selectedPopulation = populationTableView.getSelectionModel().getSelectedItem();

            if (selectedPopulation == null) {
                return;
            }

            Stage stage = new Stage();
            stage.setTitle(updatePopulationButton.getText());
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(Context.ICON);
            PageManager.setPageDialog(AddPopulationController.getKey(),
                    new UpdatePopulationController(selectedPopulation), stage);
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

        TableColumn<Population, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Population, String> nameColumn = new TableColumn<>("Tên");
        TableColumn<Population, String> genderColumn = new TableColumn<>("Giới tính");
        TableColumn<Population, String> birthdateColumn = new TableColumn<>("Ngày sinh");
        TableColumn<Population, String> citizenIdColumn = new TableColumn<>("CCCD");
        TableColumn<Population, String> addressColumn = new TableColumn<>("Địa chỉ");

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

        ArrayList<TableColumn<Population, ? extends Object>> columns = new ArrayList<>();

        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(genderColumn);
        columns.add(birthdateColumn);
        columns.add(citizenIdColumn);
        columns.add(addressColumn);

        populationTableView.getColumns().addAll(columns);
        populationTableView.setItems(populations);

        int count = populationTableView.getColumns().size();

        populationTableView.getColumns().forEach(column -> {
            column.prefWidthProperty().bind(populationTableView.prefWidthProperty().divide(count));
            column.setEditable(false);
            column.setReorderable(false);
        });
    }
}
