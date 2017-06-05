/**
 * Created by Tianyi on 4/25/17.
 */
public class Weather {
    private double temp;
    private double sunlight;
    private double rainfall;
    private double wind_velocity;

    public Weather(double _temp, double _sunlight, double _rainfall, double _wind_velocity){
        temp = _temp;
        sunlight = _sunlight;
        rainfall = _rainfall;
        wind_velocity = _wind_velocity;
    }

    public double getTemp(){return temp;}
    public double getSunlight(){return sunlight;}
    public double getRainfall(){return rainfall;}
    public double getWindVelocity(){return wind_velocity;}

}
