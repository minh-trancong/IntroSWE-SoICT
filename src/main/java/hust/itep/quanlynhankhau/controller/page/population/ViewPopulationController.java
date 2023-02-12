package hust.itep.quanlynhankhau.controller.page.population;

import hust.itep.quanlynhankhau.model.Population;
import hust.itep.quanlynhankhau.service.dao.population.PopulationDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewPopulationController {
    private static final String KEY = "/fxml/page/population/view-population.fxml";
    public static String getKey() {
        return KEY;
    }

    @FXML
    private TableView<Population> tbPopulation;
    private ObservableList<Population> items;


    public void initialize() {
        items = FXCollections.observableArrayList(new PopulationDao().getAll(Population.class));

        TableColumn<Population, Long> idColumn = new TableColumn<>("ID");
        TableColumn<Population, String> nameColumn = new TableColumn<>("Họ và tên");
        TableColumn<Population, Date> birthColumn = new TableColumn<>("Ngày sinh");
        TableColumn<Population, String> birthPlaceColumn = new TableColumn<>("Nơi sinh");
        TableColumn<Population, String> citizenIdColumn = new TableColumn<>("CCCD");
        TableColumn<Population, String> currentAddressColumn = new TableColumn<>("Địa chỉ hiện tại");
        TableColumn<Population, String> ethnicityColumn = new TableColumn<>("Dân tộc");
        TableColumn<Population, String> genderColumn = new TableColumn<>("Giới tính");
        TableColumn<Population, String> nationalityColumn = new TableColumn<>("Quốc tịch");
        TableColumn<Population, String> nativePlaceColumn = new TableColumn<>("Nguyên quán");
        TableColumn<Population, String> occupationColumn = new TableColumn<>("Nghề nghiệp");
        TableColumn<Population, String> passportColumn = new TableColumn<>("Hộ chiếu");
        TableColumn<Population, String> permanentAddressColumn = new TableColumn<>("Địa chỉ thường trú");
        TableColumn<Population, String> phoneColumn = new TableColumn<>("Số điện thoại");
        TableColumn<Population, String> relationshipToHeadColumn = new TableColumn<>("Quan hệ với chủ hộ");
        TableColumn<Population, Long> householdIdColumn = new TableColumn<>("Mã hộ khẩu");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<Population, Long>("id"));
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("name"));
        birthColumn.setCellValueFactory(new PropertyValueFactory<Population, Date>("birthdate"));

        birthColumn.setCellFactory(column -> {
            TableCell<Population, Date> cell = new TableCell<Population, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        birthPlaceColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("birthPlace"));
        citizenIdColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("citizenId"));
        currentAddressColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("currentAddress"));
        ethnicityColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("ethnicity"));
        genderColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("gender"));
        nationalityColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("nationality"));
        nativePlaceColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("nativePlace"));
        occupationColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("occupation"));
        passportColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("passport"));
        permanentAddressColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("permanentAddress"));
        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("phone"));
        relationshipToHeadColumn.setCellValueFactory(
                new PropertyValueFactory<Population, String>("relationshipToHead"));
        householdIdColumn.setCellValueFactory(
                new PropertyValueFactory<Population, Long>("household"));
        ArrayList<TableColumn<Population, ? extends Object>> columns = new ArrayList<>();
        columns.add(idColumn);
        columns.add(nameColumn);
        columns.add(birthColumn);
        columns.add(birthPlaceColumn);
        columns.add(citizenIdColumn);
        columns.add(currentAddressColumn);
        columns.add(ethnicityColumn);
        columns.add(genderColumn);
        columns.add(nationalityColumn);
        columns.add(nativePlaceColumn);
        columns.add(occupationColumn);
        columns.add(passportColumn);
        columns.add(permanentAddressColumn);
        columns.add(phoneColumn);
        columns.add(relationshipToHeadColumn);
        columns.add(householdIdColumn);

        tbPopulation.getColumns().addAll(columns);
        tbPopulation.setItems(items);
    }
}
