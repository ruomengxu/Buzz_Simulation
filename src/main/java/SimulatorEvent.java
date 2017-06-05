/**
 * Created by Tianyi on 4/25/17.
 */
public class SimulatorEvent extends Event{
    public static final int STOP_EVENT = 0;

    SimulatorEvent(int delay, EventHandler handler, int eventType) {
        super(delay, handler, eventType);
    }
}
