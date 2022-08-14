import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class CustomTextSearchInputEvent extends Event {

    public CustomTextSearchInputEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public CustomTextSearchInputEvent(Object source, EventTarget target, EventType<? extends Event> eventType) {
        super(source, target, eventType);
    }
}
