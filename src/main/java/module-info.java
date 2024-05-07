module com.sample.engrisk {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web; // webview
    requires javafx.graphics; // icon

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.media;
    requires jlayer;
    requires json.simple;


    opens com.sample.engrisk to javafx.graphics, javafx.fxml;
    exports com.sample.engrisk;
}