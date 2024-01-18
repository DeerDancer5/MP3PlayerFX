module org.example.mp3playerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;

    opens org.example.mp3playerfx to javafx.fxml;
    exports org.example.mp3playerfx;
    exports org.example.mp3playerfx.controller;
    opens org.example.mp3playerfx.controller to javafx.fxml;
    exports org.example.mp3playerfx.model;
    opens org.example.mp3playerfx.model to javafx.fxml;
}