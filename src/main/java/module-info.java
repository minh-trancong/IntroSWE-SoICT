module com.quanlynhankhau.hust.quanlynhankhau {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;
    requires MaterialFX;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires jakarta.persistence;
    requires org.slf4j;
    requires slf4j.reload4j;

    opens hust.itep.quanlynhankhau.model to org.hibernate.orm.core;

    opens hust.itep.quanlynhankhau to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.page to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.layout to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.page.population to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.page.household to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.page.covid to javafx.fxml;


    exports hust.itep.quanlynhankhau;
    exports hust.itep.quanlynhankhau.model;
    opens hust.itep.quanlynhankhau.controller.utility to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.component to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.page.population.popup to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.component.popup to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.component.modifier to javafx.fxml;
    opens hust.itep.quanlynhankhau.controller.component.factory to javafx.fxml;
}