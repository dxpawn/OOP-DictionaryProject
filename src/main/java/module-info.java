module com.sample.engrisk {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web; // for WebView

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.media;


    opens com.sample.engrisk to javafx.fxml;
    exports com.sample.engrisk;
}