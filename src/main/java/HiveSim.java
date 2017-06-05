import java.util.Random;

/**
 * Created by Tianyi on 4/25/17.
 */
public class HiveSim {
    public static Hive hive;
    public static int area;
    public static int m_initialPop;
    public static int m_foragePop;
    public static int m_max_number_eggs;
    public static int m_forage_day_limit;
    public static Weather[] weathers = new Weather[360];

    public HiveSim(int area_, int start_month){
        area = area_;

        Random rand = new Random();
        rand.setSeed(1994102400);
        Weather[] tmp = new Weather[360];

        hive = new Hive(m_initialPop, m_foragePop,m_max_number_eggs,m_forage_day_limit); // Initialization, connect with GUI

        if(area == 1){
            for(int i=0;i<360;i++){
                tmp[i] = new Weather(39*Math.pow(Math.sin(3.14159/360*i),1.1), 4.5*Math.pow(Math.sin(3.14159/360*i),1.2)+9.9, rand.nextInt(6)/11,rand.nextInt(12)*1.6);
            }
        }
        else{
            for(int i=0;i<360;i++){
            	tmp[i] = new Weather(31*Math.sin(3.14159/360*i), 6.15*Math.pow(Math.sin(3.14159/360*i),0.8)+9.1, rand.nextInt(8)/11, rand.nextInt(25)*1.6);
            }
        }
        System.arraycopy(tmp,(start_month-1)*30,weathers,0,(12-start_month+1)*30);
        System.arraycopy(tmp,0,weathers,(12-start_month+1)*30,(start_month-1)*30);


    }

    public static void simStart(int inipop, int forage, int Max_number_eggs, int Forager_life){
    	m_initialPop = inipop;
    	m_foragePop = forage;
    	m_max_number_eggs = Max_number_eggs;
    	m_forage_day_limit = Forager_life;
    	int start_month = GUI.getStartMonth();
        HiveSim hiveSim = new HiveSim(GUI.getArea(),start_month);

        HiveEvent firstDay = new HiveEvent(weathers[0], 0, hive, HiveEvent.UPDATE);
        Simulator.schedule(firstDay);

        Simulator.stopAt(GUI.getSimtime()*360);

        Simulator.run();
    }
}
