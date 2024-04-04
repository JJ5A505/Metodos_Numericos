module com.example.metods {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires exp4j;

    opens com.example.metods to javafx.fxml;
    exports com.example.metods;
}