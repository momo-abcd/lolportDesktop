module com.lolport {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.kwhat.jnativehook;

    opens com.lolport to javafx.fxml;
    exports com.lolport;
}
