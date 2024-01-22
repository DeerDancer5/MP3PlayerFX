package org.example.mp3playerfx.event;

import javafx.event.Event;
import javafx.event.EventType;

public class UpdateLibraryEvent extends Event {
    public static final EventType<UpdateLibraryEvent> CUSTOM_EVENT =
            new EventType<>(Event.ANY, "CUSTOM_EVENT");

    public UpdateLibraryEvent() {
        super(CUSTOM_EVENT);
    }
}
