/**
 * Created by ruomengxu on 4/25/17.
 */
public class Hive {
    private int pop;
    private int sperm_amount;
    private int forage_pop;
    private int forage_day_limit = 21;
    private int lay_egg_days;
    private int max_number_eggs;
    private int days_egg2drone = 24;
    private int days_egg2worker = 21;

    public Hive(int Pop, int Sperm_amount, int Forage_day_limit ){
        pop = Pop;
        sperm_amount = Sperm_amount;
        forage_day_limit = Forage_day_limit;
        lay_egg_days = 0;
    }

    public int get_forage_day_limit(){
        return forage_day_limit;
    }
    public int get_sperm_amount(){
        return sperm_amount;
    }
    public int get_forage_pop(){
        return forage_pop;
    }
    public int pop(){
        return pop;
    }


}


