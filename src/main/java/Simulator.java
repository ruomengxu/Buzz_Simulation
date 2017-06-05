/**
 * Created by Tianyi on 4/25/17.
 */
public class Simulator {
    //singleton
    private static SimulatorEngine instance = null;

    public static SimulatorEngine getSim() {
        if(instance == null) {
            instance = new SimulatorEngine();
        }
        return instance;
    }

    public static void stopAt(int time) {
        Event stopEvent = new SimulatorEvent(time, getSim(), SimulatorEvent.STOP_EVENT);
        schedule(stopEvent);
    }

    public static void run() {
        getSim().run();
    }

    public static double getCurrentTime() {
        return getSim().getCurrentTime();
    }

    public static void schedule(Event event) {
        event.setTime((int)(event.getTime() + getSim().getCurrentTime()));
        getSim().schedule(event);
    }
    
    public static void voidinstance(){
    	instance = null;
    }
}
