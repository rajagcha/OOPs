/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raj
 */
public class BillDateCompare implements java.util.Comparator {

    @Override
    public int compare (Object A, Object  B) {
        return ((Bill)A).getStartDate().compareTo(((Bill)B).getStartDate());
    }
}
