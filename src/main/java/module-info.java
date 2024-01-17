module org.example.mp3playerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.mp3playerfx to javafx.fxml;
    exports org.example.mp3playerfx;
}