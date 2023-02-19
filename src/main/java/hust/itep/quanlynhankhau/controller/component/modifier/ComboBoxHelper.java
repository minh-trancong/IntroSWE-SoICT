package hust.itep.quanlynhankhau.controller.component.modifier;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

public class ComboBoxHelper {

    private final static ObservableList<String> GENDERS = FXCollections
            .observableList(Arrays.asList("Nam", "Nữ", "Khác"));

    private final static ObservableList<String> SIMPLE = FXCollections
            .observableList(Arrays.asList("Có", "Không"));

    private final static ObservableList<String> GENDERS_CHOICE = FXCollections
            .observableList(Arrays.asList("Tất cả", "Nam", "Nữ"));

    private final static ObservableList<String> STATUS_CHOICE = FXCollections
            .observableList(Arrays.asList("Tất cả", "Tạm vắng", "Tạm trú"));

    private final static ObservableList<String> COVID_TEST = FXCollections
            .observableList(Arrays.asList("Dương tính", "Âm tính"));

    private final static ObservableList<String> TAG_CHOICE = FXCollections
            .observableList(Arrays.asList("TẤT CẢ", "TEST COVID", "KHAI BÁO DỊCH TỄ", "KHAI BÁO CÁCH LY"));


    public static void simple(MFXComboBox comboBox) {comboBox.setItems(SIMPLE);}
    public static void gender(MFXComboBox comboBox) {
        comboBox.setItems(GENDERS);
    }

    public static void genderChoice(MFXComboBox comboBox) {
        comboBox.setItems(GENDERS_CHOICE);
    }

    public static void covid(MFXComboBox comboBox) {comboBox.setItems(COVID_TEST);}

    public static void status(MFXComboBox comboBox) {
        comboBox.setItems(STATUS_CHOICE);
    }
    public static void tagChoice(MFXComboBox comboBox){ comboBox.setItems(TAG_CHOICE);}
}
