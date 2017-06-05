import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by ruomengxu on 4/25/17.
 */
public class Hive implements EventHandler {
    private int population;
    private int sperm_amount = 5000000;
    private int forage_pop;
    private int lay_egg_days;
    private int max_number_eggs;
    private int days_larve2drone = 24;
    private int days_larve2worker = 21;
    private int days_drone2death = 59;
    private int days_forager2death;
    private int death_total;
    private double sperm_remain;
    private double constant;

    Comparator<HiveRecord> comparator = new recComparator();
    Comparator<ForagerRecord> comparator_f = new recComparator_f();
    private PriorityQueue<HiveRecord> larve2workersRecord = new PriorityQueue<HiveRecord>(comparator);
    private PriorityQueue<HiveRecord> workers2foragersRecord = new PriorityQueue<HiveRecord>(comparator);
    private PriorityQueue<HiveRecord> dronesRecord = new PriorityQueue<HiveRecord>(comparator);
    private PriorityQueue<HiveRecord> larve2dronesRecord = new PriorityQueue<HiveRecord>(comparator);
    private PriorityQueue<ForagerRecord> foragersRecord = new PriorityQueue<ForagerRecord>(comparator_f);


    public Hive(int Pop, int Forage_pop,int Max_number_eggs, int Forage_day_limit ){
        population = Pop;
        forage_pop = Forage_pop;
        lay_egg_days = 0;
        death_total = 0;
        max_number_eggs = Max_number_eggs;
        days_forager2death = Forage_day_limit;
        ForagerRecord ini_forager = new ForagerRecord(forage_pop,0,0);
        foragersRecord.offer(ini_forager);
        HiveRecord ini_drone = new HiveRecord(Pop - Forage_pop,0);
        dronesRecord.offer(ini_drone);
        sperm_remain = 1;
        constant = 1.0/122/600000*sperm_amount;
    }

    // Functions to compute important numbers and ratios
    public int potential_eggs_num(int lay_egg_days , int max_number_eggs ){
        double P_t = max_number_eggs - 0.0027 * Math.pow(lay_egg_days,2) + 0.395 * lay_egg_days;
        return (int)P_t;
    }
    public int eggs_num(double temp, double sunlight_length, int forage_pop, int potential_eggs_number ){
        double DD = Math.min(-0.0006 * Math.pow(temp,2) + 0.05 * temp + 0.021,1);
        double L_t = Math.min(-0.00743 * Math.pow(sunlight_length,3) + 0.312 * Math.pow(sunlight_length,2) - 4.04 * sunlight_length + 16.58,1);
        double N_t = Math.min(0.672 * Math.log(forage_pop*0.001+1),1);
        double E_t = Math.max(DD * L_t * N_t * potential_eggs_number,0);
        return (int)E_t;

    }
    public int unfert_eggs(double sunlight_length, int forage_pop, int egg_num){
        double a = Math.exp(-lay_egg_days * constant);
        double s_t = (sperm_remain - a);
        sperm_remain = a;
        double L_t = 0.284 * Math.log(sunlight_length * 0.1);
        double F_t = 0.797 * Math.log(forage_pop * 0.0006);
        double B_t = Math.max(L_t * F_t,0);
        double S_t = Math.max(1 - (-6.355 * Math.pow(s_t,3) + 7.657 * Math.pow(s_t,2) - 2.3 * s_t + 1.002),0);
        return (int) Math.floor((S_t + B_t)*egg_num);
    }


    public void handle(Event event) {

        GUI.print(event.getTime(), population);

        HiveEvent hiveEvent = (HiveEvent) event;
        int current = hiveEvent.getTime();

        Weather weather = hiveEvent.getWeather();

        // Compute the number of eggs
        int potential_egg = potential_eggs_num(lay_egg_days, max_number_eggs);
        int egg = eggs_num(weather.getTemp(), weather.getSunlight(), forage_pop, potential_egg);
        int unfertilized_egg = unfert_eggs(weather.getSunlight(), forage_pop, egg);
        int amount_egg_larve2drone = unfertilized_egg;

        int amount_egg_larve2worker = Math.max(egg - amount_egg_larve2drone,0);

        // Update the hive
        // 1. Remove the death
        PriorityQueue<ForagerRecord> new_f_temp = new PriorityQueue<ForagerRecord>(comparator_f);
        while(!foragersRecord.isEmpty()){
            ForagerRecord tmp = foragersRecord.poll();
            if(current - tmp.day < days_forager2death && current - tmp.born < days_drone2death){
                new_f_temp.offer(tmp);
            }
            else
            {
                population -= tmp.amount;
                forage_pop -= tmp.amount;
                death_total += tmp.amount;

            }
        }
        foragersRecord = new_f_temp;


        if (!dronesRecord.isEmpty()) {
            if (current - dronesRecord.peek().day > days_drone2death) {
                HiveRecord death_drones_Record = dronesRecord.poll();
                population -= death_drones_Record.amount;
                death_total += death_drones_Record.amount;
            }
        }



        // 2. Larve grow into workers and drones
        if (!larve2workersRecord.isEmpty()) {
            if (current - larve2workersRecord.peek().day > days_larve2worker) {
                int amount_new_workers = larve2workersRecord.poll().amount;
                HiveRecord record_workers = new HiveRecord(amount_new_workers, current);
                workers2foragersRecord.offer(record_workers);
                population += amount_new_workers;
            }
        }

        if (!larve2dronesRecord.isEmpty()) {
            if (current - larve2dronesRecord.peek().day > days_larve2drone) {
                int amount_new_drones = larve2dronesRecord.poll().amount;
                HiveRecord record_drones = new HiveRecord(amount_new_drones, current);
                dronesRecord.offer(record_drones);
                population += amount_new_drones;
            }
        }

        // 3. Eggs grow into larve
        HiveRecord record_egg_larve2worker = new HiveRecord((int) (0.85 * amount_egg_larve2worker), current);
        larve2workersRecord.offer(record_egg_larve2worker);

        HiveRecord record_egg_larve2drone = new HiveRecord((int) (0.85 * amount_egg_larve2drone), current);
        larve2dronesRecord.offer(record_egg_larve2drone);

        // 4. Workers grow into foragers
        if (!workers2foragersRecord.isEmpty()) {
            if (!workers2foragersRecord.isEmpty() && current - workers2foragersRecord.peek().day > 21) {
                int amount_new_foragers = workers2foragersRecord.poll().amount;
                ForagerRecord record_foragers = new ForagerRecord(amount_new_foragers, current,current);
                foragersRecord.offer(record_foragers);
                forage_pop += amount_new_foragers;
            }
        }

        // 5. Update the foragers
        if (weather.getTemp() <= 12 || weather.getWindVelocity() >= 34 || weather.getRainfall() >= 0.5) {
            int length = foragersRecord.size();
            PriorityQueue<ForagerRecord> newpq = new PriorityQueue<ForagerRecord>(comparator_f);
            for (int i = 0; i < length; i++) {
                ForagerRecord old_record = foragersRecord.poll();
                ForagerRecord new_record = new ForagerRecord(old_record.amount, old_record.day + 1,old_record.born);
                newpq.offer(new_record);
            }
            foragersRecord = newpq;
        }






        // 6. Update day of laying egg
        lay_egg_days += 1;

        // 7. Schedule new event of next day

        HiveEvent nextDayEvent = new HiveEvent(HiveSim.weathers[Math.floorMod(current + 1,360)], 1, this, HiveEvent.UPDATE);
        Simulator.schedule(nextDayEvent);




    }



    public int get_forage_day_limit(){
        return days_forager2death;
    }
    public int get_sperm_amount(){
        return sperm_amount;
    }
    public int get_forage_pop(){
        return forage_pop;
    }
    public int pop(){
        return population;
    }


}


