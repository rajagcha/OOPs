/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Date;
/**
 *
 * @author Raj
 */
public class DateMath {
    /**
     * Returns the number of days between two dates rounded to the nearest day.
     * @param start
     * @param end
     * @return
     */
    public static long daysDifferent(Date start,Date end){
        long MILLESECTODAYS= 24L*3600L*1000;
        long out=end.getTime() - start.getTime();

        return Math.round(out/MILLESECTODAYS);
    }

}
