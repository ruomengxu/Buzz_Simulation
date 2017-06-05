import java.util.Comparator;

/**
 * Created by ruomengxu on 5/4/17.
 */
public class recComparator_f implements Comparator<ForagerRecord> {
    @Override
    public int compare(ForagerRecord x, ForagerRecord y){
        if(x.day < y.day){
            return -1;
        }
        else if(x.day > y.day){
            return 1;
        }
        else{
            if (x.born < y.born){
                return -1;
            }
            else if (x.born > y.born){
                return 1;
            }
            else return 0;

        }

    }
}
