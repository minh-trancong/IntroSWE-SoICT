package hust.itep.quanlynhankhau.controller.component;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.application.Application;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.util.Locale;

public class DatePickerHelper {

    public static void setVietnamese(MFXDatePicker datePicker) {
        datePicker.setLocale(Locale.of("vi", "VN"));
        datePicker.setConverterSupplier(() ->
                new DateStringConverter("dd/MM/yyyy", datePicker.getLocale())
        );
    }

}
