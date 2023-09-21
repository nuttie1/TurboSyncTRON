module com.example.otp1r4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.mariadb.jdbc;


    opens com.example.otp1r4 to javafx.fxml;
    exports com.example.otp1r4;
    exports com.example.otp1r4.dao;
    opens com.example.otp1r4.dao to javafx.fxml;
    exports com.example.otp1r4.model;
    opens com.example.otp1r4.model to javafx.fxml;
    exports com.example.otp1r4.controller;
    opens com.example.otp1r4.controller to javafx.fxml;
}