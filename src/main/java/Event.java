/**
 * Created by ruomengxu on 4/25/17.
 */

public class Event implements Comparable<Event>{
    private int m_time;
    private EventHandler m_handler;
    private int m_eventType;
    private int m_eventId;
    static private int m_nextId = 0;

    Event(){
        m_eventId = m_nextId++;
    }

    Event(int delay, EventHandler handler, int eventType){
        this();
        m_time = delay;
        m_handler = handler;
        m_eventType = eventType;
    }

    public int getId(){return m_eventId;}

    public int getTime(){
        return m_time;
    }

    public void setTime(int time){
        m_time = time;
    }

    public EventHandler getHandler(){
        return m_handler;
    }

    public void setHandler(EventHandler handler){
        m_handler = handler;
    }

    public int getType() { return m_eventType; }

    @Override
    public int compareTo(final Event ev) {
        int timeCmp = Integer.compare(m_time, ev.getTime());
        if(timeCmp != 0) {
            return timeCmp;
        }
        else
            return Integer.compare(m_eventId, ev.getId());
    }



}
