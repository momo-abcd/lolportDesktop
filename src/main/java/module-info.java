module com.lolport {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.kwhat.jnativehook;
    requires org.controlsfx.controls;
    requires com.jfoenix;

    opens com.lolport to javafx.fxml;
    opens com.lolport.controller to javafx.fxml;

    exports com.lolport;
    exports com.lolport.custom;
    opens com.lolport.custom to javafx.fxml;
}
