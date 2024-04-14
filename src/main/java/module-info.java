module com.example.englishdictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;

    opens com.example.englishdictionary to javafx.fxml;
    exports com.example.englishdictionary;
}