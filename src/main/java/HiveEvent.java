/**
 * Created by Tianyi on 4/25/17.
 */
public class HiveEvent extends Event {
    public static final int UPDATE = 1;

    public static Weather weather;

    HiveEvent(Weather _weather, int delay, EventHandler handler, int eventType){
        super(delay, handler, eventType);
        weather = _weather;
    }

    public Weather getWeather(){
        return weather;
    }
}
