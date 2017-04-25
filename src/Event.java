/**
 * Created by ruomengxu on 4/25/17.
 */
import java.lang.Math;
import java.util.List;
import java.util.Random;
public class Event {
    public int potential_eggs_num(int lay_egg_days , int max_number_eggs ){
        double P_t = max_number_eggs -0.0027*Math.pow(lay_egg_days,2) + 0.395*lay_egg_days;
        return (int)P_t;
    }
    public int eggs_num(double temp, double sunlight_length, int forage_pop, int potential_eggs_number ){
        double DD = -0.0006*Math.pow(temp,2)+0.05*temp+0.021;
        double L_t = -0.00743*Math.pow(sunlight_length,3)+0.312*Math.pow(sunlight_length,2)-4.04*sunlight_length+16.58;
        double N_t = 0.673*Math.log(forage_pop*0.001+1);
        double E_t = DD*L_t*N_t*potential_eggs_number;
        return (int)E_t;

    }
    public double unfert_eggs_ratio(int amount_sperm, double sunlight_length, int forage_pop){
        Random rnd = new Random();
        double s_t = rnd.nextDouble()*0.4+0.6;
        double L_t = 0.284*Math.log(sunlight_length*0.1);
        double F_t = 0.797*Math.log(forage_pop*0.0006);
        double B_t = L_t*F_t;
        double S_t = 1-(-6.355*Math.pow(s_t,3)+7.657*Math.pow(s_t,2)-2.3*s_t+1.002);
        return S_t+B_t;
    }
    public int[] production(int eggs_num, double unfert_eggs_ratio ){
        int drone_production = (int)(eggs_num * unfert_eggs_ratio);
        int work_production = eggs_num - drone_production;
        int[] outoput = new int[]{drone_production, work_production};
        return outoput;
    }
    public 




}
