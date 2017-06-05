import java.util.Comparator;

/**
 * Created by Tianyi on 4/25/17.
 */
public class recComparator implements Comparator<HiveRecord>{
    @Override
    public int compare(HiveRecord x, HiveRecord y){
        if(x.day < y.day){
            return -1;
        }
        if(x.day > y.day){
            return 1;
        }
        return 0;
    }
}
