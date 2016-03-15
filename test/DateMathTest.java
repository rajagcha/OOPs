/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zephyr
 */
public class DateMathTest {

    public DateMathTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of daysDifferent method, of class DateMath.
     */
    @Test
    public void testDaysDifferent() {
        System.out.println("daysDifferent");
        GregorianCalendar sdate=new GregorianCalendar(2009,10,30);
        GregorianCalendar edate=new GregorianCalendar(2009, 11, 1);
        Date start = sdate.getTime();
        Date end = edate.getTime();
        long expResult = 1L;
        long result = DateMath.daysDifferent(start, end);
        assertEquals(expResult, result);

        edate.set(2009,11,30);
        end=edate.getTime();

        expResult=30L;
        result=DateMath.daysDifferent(start, end);
        assertEquals(result, expResult);

        edate.set(2009, 11,31,12,59);
        end=edate.getTime();

        expResult=31L;
        result=DateMath.daysDifferent(start, end);
        assertEquals(result, expResult);


    }

    

}