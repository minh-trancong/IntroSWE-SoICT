package hust.itep.quanlynhankhau.controller.component.modifier;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

public class ComboBoxHelper {

    private final static ObservableList<String> GENDERS = FXCollections
            .observableList(Arrays.asList("Nam", "Nữ", "Khác"));

    private final static ObservableList<String> GENDERS_CHOICE = FXCollections
            .observableList(Arrays.asList("Tất cả", "Nam", "Nữ"));

    private final static ObservableList<String> STATUS_CHOICE = FXCollections
            .observableList(Arrays.asList("Tất cả", "Tạm vắng", "Tạm trú"));

    public static void gender(MFXComboBox comboBox) {
        comboBox.setItems(GENDERS);
    }

    public static void genderChoice(MFXComboBox comboBox) {
        comboBox.setItems(GENDERS_CHOICE);
    }

    public static void status(MFXComboBox comboBox) {
        comboBox.setItems(STATUS_CHOICE);
    }
}
