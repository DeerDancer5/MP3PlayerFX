package org.example.mp3playerfx.event;
import javafx.event.EventHandler;


public class EventDispatcher {

    private static EventHandler<UpdateLibraryEvent> updateLibraryEventEventHandler;

    public static void setCustomEventHandler(EventHandler<UpdateLibraryEvent> handler) {
        updateLibraryEventEventHandler= handler;
    }

    public static void dispatchCustomEvent() {
        if (updateLibraryEventEventHandler!= null) {
            updateLibraryEventEventHandler.handle(new UpdateLibraryEvent());
        }
    }
}
