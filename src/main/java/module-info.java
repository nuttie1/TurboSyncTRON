module com.example.otp1r4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;


    opens com.example.otp1r4 to javafx.fxml;
    exports com.example.otp1r4;
}