/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raj
 */
public class CostReportDateCompare implements java.util.Comparator {

    @Override
    public int compare (Object A, Object  B) {
        return ((CostReport)A).getDate().compareTo(((CostReport)B).getDate());
    }

}
