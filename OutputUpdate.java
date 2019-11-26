package convert17;
import java.awt.Event;
import java.awt.AWTEvent;
import java.util.EventObject;
public class OutputUpdate extends EventObject {
    public OutputUpdate (String source) {
        super(source);
    }
}