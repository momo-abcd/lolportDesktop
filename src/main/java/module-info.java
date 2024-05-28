module com.lolport {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.lolport to javafx.fxml;
    exports com.lolport;
}
