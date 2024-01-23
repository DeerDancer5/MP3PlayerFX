module org.example.mp3playerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires java.desktop;
    requires mp3agic;
    requires jaudiotagger;
    requires json.simple;
    requires jdk.xml.dom;

    opens org.example.mp3playerfx to javafx.fxml;
    exports org.example.mp3playerfx;
    exports org.example.mp3playerfx.controller;
    opens org.example.mp3playerfx.controller to javafx.fxml;
    exports org.example.mp3playerfx.model;
    opens org.example.mp3playerfx.model to javafx.fxml;
    exports org.example.mp3playerfx.model.player;
    opens org.example.mp3playerfx.model.player to javafx.fxml;
    exports org.example.mp3playerfx.model.playlist;
    opens org.example.mp3playerfx.model.playlist to javafx.fxml;
    exports org.example.mp3playerfx.model.library;
    opens org.example.mp3playerfx.model.library to javafx.fxml;
    exports org.example.mp3playerfx.event;
    opens org.example.mp3playerfx.event to javafx.fxml;
    exports org.example.mp3playerfx.model.player.engine;
    opens org.example.mp3playerfx.model.player.engine to javafx.fxml;
    exports org.example.mp3playerfx.model.player.state;
    opens org.example.mp3playerfx.model.player.state to javafx.fxml;
}