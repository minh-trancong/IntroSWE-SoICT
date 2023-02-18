package hust.itep.quanlynhankhau.controller.page.covid;

import hust.itep.quanlynhankhau.controller.component.factory.StageFactory;
import hust.itep.quanlynhankhau.controller.component.modifier.TableViewHelper;
import hust.itep.quanlynhankhau.controller.component.popup.ConfirmBox;
import hust.itep.quanlynhankhau.controller.page.covid.popup.AddMovementDeclarationController;
import hust.itep.quanlynhankhau.controller.page.covid.popup.ViewMovementDeclarationController;
import hust.itep.quanlynhankhau.controller.page.population.ViewPopulationController;
import hust.itep.quanlynhankhau.controller.utility.PageManager;
import hust.itep.quanlynhankhau.controller.utility.PopupManager;
import hust.itep.quanlynhankhau.model.covid.MovementDeclaration;
import hust.itep.quanlynhankhau.service.dao.MovementDeclarationDao;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;

public class DeclareMovementController {
    private static final String KEY = "/fxml/page/covid/declare-movement.fxml";
    public static String getKey() {
        return KEY;
    }

    @FXML
    MFXButton addDeclarationButton;

    @FXML
    MFXButton deleteDeclarationButton;

    @FXML
    TableView<MovementDeclaration> declarationTable;

    // Radio buttons
    @FXML
    RadioButton idRadioButton;
    @FXML
    RadioButton nameRadioButton;
    @FXML
    RadioButton departureRadioButton;
    @FXML
    RadioButton destinationRadioButton;
    @FXML
    MFXTextField searchTextField;

    @FXML
    ToggleGroup searchByToggleGroup;

    FilteredList<MovementDeclaration> movementDeclarations;

    @FXML
    public void initialize() {
        initializeTable();
        initializeTextField();
        initializeButton();
    }


    private void initializeButton() {
        addDeclarationButton.setOnAction(e -> {
            PopupManager.setPopup(AddMovementDeclarationController.getKey(),
                    new AddMovementDeclarationController(), StageFactory.buildStage(addDeclarationButton.getText()));
        });

        deleteDeclarationButton.setOnAction(e -> {
            ArrayList<MovementDeclaration> movementDeclarationArrayList
                    = new ArrayList<>(declarationTable.getSelectionModel().getSelectedItems());

            if (movementDeclarationArrayList.isEmpty()) {
                return;
            }

            if (!ConfirmBox.display("Xóa khai báo", "Bạn có chắc không")) {
                return;
            }

            MovementDeclarationDao movementDeclarationDao = new MovementDeclarationDao();
            movementDeclarationArrayList.forEach(movementDeclarationDao::delete);
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
                movementDeclarations.setPredicate(movementDeclaration -> true);
                return;
            }

            if (idRadioButton.isSelected()) {
                movementDeclarations.setPredicate(movementDeclaration -> {
                    if (movementDeclaration.getPopulation().getCitizenId() == null) {
                        return false;
                    } else {
                        return movementDeclaration.getPopulation().getCitizenId().toLowerCase().contains(text);
                    }
                });
            } else if (departureRadioButton.isSelected()) {
                movementDeclarations.setPredicate(movementDeclaration -> movementDeclaration.getDepartureLocation().toLowerCase().contains(text));
            } else if (destinationRadioButton.isSelected()) {
                movementDeclarations.setPredicate(movementDeclaration -> movementDeclaration.getDestination().toLowerCase().contains(text));
            } else if (nameRadioButton.isSelected()) {
                movementDeclarations.setPredicate(movementDeclaration ->
                        movementDeclaration.getPopulation().getName().toLowerCase().contains(text));
            }
        });


    }

    private void initializeTable() {
        movementDeclarations =
                new FilteredList<>(FXCollections.observableArrayList(new MovementDeclarationDao().getAll(MovementDeclaration.class)));

        TableColumn<MovementDeclaration, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MovementDeclaration, String> nameColumn = new TableColumn<>("Họ và tên");
        nameColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                return new SimpleStringProperty(e.getValue().getPopulation().getName());
            } else {
                return null;
            }
        });

        TableColumn<MovementDeclaration, String> citizenIdColumn = new TableColumn<>("CCCD");
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

        TableColumn<MovementDeclaration, Date> departureDateColumn = new TableColumn<>("Ngày khởi hành");
        departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));

        TableColumn<MovementDeclaration, String> departureLocationColumn = new TableColumn<>("Điểm khởi hành");
        departureLocationColumn.setCellValueFactory(new PropertyValueFactory<>("departureLocation"));

        TableColumn<MovementDeclaration, String> destinationColumn = new TableColumn<>("Điểm đến");
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));

        TableColumn<MovementDeclaration, String> hasCovidColumn = new TableColumn<>("Bị COVID");
        hasCovidColumn.setCellValueFactory(e -> {
            if (e.getValue() != null) {
                String ret = e.getValue().getHasCovid() ? "Có" : "Không";
                return new SimpleStringProperty(ret);
            }

            return null;
        });

        ArrayList<TableColumn<MovementDeclaration, ? extends Object>> columns = new ArrayList<>();
        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(citizenIdColumn);
        columns.add(departureDateColumn);
        columns.add(departureLocationColumn);
        columns.add(destinationColumn);
        columns.add(hasCovidColumn);

        declarationTable.getColumns().addAll(columns);
        declarationTable.setItems(movementDeclarations);
        
        declarationTable.setRowFactory(e -> {
            TableRow<MovementDeclaration> row = new TableRow<>();

            row.addEventHandler(MouseEvent.MOUSE_CLICKED, ex -> {
                if (ex.getClickCount() == 2 && !row.isEmpty()) {
                    PopupManager.setPopup(ViewMovementDeclarationController.getKey(),
                            new ViewMovementDeclarationController(row.getItem()),
                            StageFactory.buildStage("Thông tin khai báo dịch tễ"));
                }
            });
            
            return row;
        });
        
        TableViewHelper.initializeCommonTableView(declarationTable);
    }



}
