module com.quanlynhankhau.hust.quanlynhankhau {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires MaterialFX;
    requires org.hibernate.orm.core;
    requires hibernate.jpa;
    requires hibernate.commons.annotations;
    requires jakarta.persistence;

    opens hust.itep.quanlynhankhau to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.page to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.layout to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.page.population to javafx.fxml;

    exports hust.itep.quanlynhankhau;
    opens hust.itep.quanlynhankhau.controller.utility to javafx.fxml;
}