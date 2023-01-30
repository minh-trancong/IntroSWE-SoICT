module com.quanlynhankhau.hust.quanlynhankhau {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens hust.itep.quanlynhankhau to javafx.fxml;
    exports hust.itep.quanlynhankhau;
}