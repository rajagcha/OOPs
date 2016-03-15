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
import sun.util.calendar.Gregorian;
import static org.junit.Assert.*;

/**
 *
 * @author Zephyr
 */
public class BillTest {
    GregorianCalendar sDate = null;
    GregorianCalendar eDate = null;
    double sMileage = 100;
    double eMileage = 200;
    double deposit = 200;
    double dailyRate = 20.00;
    double gasAdded = 0;
    double damageCharge = 0;
    Date startDate;
    Date endDate;
    CustomerInformation customer = null;
    Rental testRental = null;
    Bill testBill1 = null;
    Bill testBill2 = null;


    public BillTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws BadInputException {
        sDate=new GregorianCalendar(2009,03,10);
        eDate=new GregorianCalendar(2009,03,11);
        sMileage=100;
        eMileage=200;
        deposit=200;
        dailyRate=20.00;
        gasAdded=0;
        damageCharge=0;
        startDate=sDate.getTime();
        endDate=eDate.getTime();
        CreditCard cc = new CreditCard("Joe Blow", "9328 2934 0293 8419", endDate);
        DriverLicense dl= new DriverLicense("Joe Blow", "Wisconsin", "2nd f0203iednfa", endDate);
        customer=new CustomerInformation("Joseph Blow", 608349239L, dl, cc);
        testRental =new Rental(startDate, sMileage, customer, deposit);
        testBill1=new Bill(testRental, startDate, eMileage, gasAdded, damageCharge, dailyRate);
        testBill2=new Bill(testRental, endDate, eMileage, gasAdded, damageCharge, dailyRate);

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor () throws BadInputException {
        System.out.println("Testing the constructor");
        sDate=new GregorianCalendar(2009,03,10);
        eDate=new GregorianCalendar(2009,03,11);
        sMileage=100;
        eMileage=200;
        deposit=200;
        dailyRate=20.00;
        gasAdded=0;
        damageCharge=0;
        startDate=sDate.getTime();
        endDate=eDate.getTime();
        testRental =new Rental(startDate, sMileage, customer, deposit);
        Bill testBill=new Bill(testRental, startDate, eMileage, gasAdded, damageCharge, dailyRate);
        testBill=new Bill(testRental, endDate, eMileage, gasAdded, damageCharge, dailyRate);

        // Bad Bills
        eMileage=-1;
        try {
        testBill = new Bill(testRental, startDate, eMileage, gasAdded, damageCharge, dailyRate);
        fail ("bad bill construction ");
        } catch (BadInputException e) {
            assertTrue(true);
        }
        eMileage=99;
        try {
            testBill = new Bill(testRental, startDate, eMileage, gasAdded, damageCharge, dailyRate);
        fail ("bad bill construction ");
        } catch (BadInputException e) {
            assertTrue(true);
        }

        eMileage=200;
        gasAdded=-10;

        try {
            testBill = new Bill(testRental, startDate, eMileage, gasAdded, damageCharge, dailyRate);
            fail("bad bill construction ");
        } catch (BadInputException e) {
            assertTrue(true);
        }
        gasAdded=10;
        damageCharge=-1;
        try {
        testBill=new Bill(testRental, startDate, eMileage, gasAdded, damageCharge, dailyRate);
        fail ("bad bill construction ");
        } catch (BadInputException e) {
            assertTrue(true);
        }

        // end date before start date
        eDate.set(2009,3,1);
        endDate = eDate.getTime();
         try {
        testBill=new Bill(testRental, startDate, eMileage, gasAdded, damageCharge, dailyRate);
        fail ("bad bill construction ");
        } catch (BadInputException e) {
            assertTrue(true);
        }
 



        }
    /**
     * Test of getDamageCost method, of class Bill.
     */
    @Test
    public void testGetDamageCost() {
        System.out.println("getDamageCost");
        Bill instance = null;
        Double expResult = null;
        Double result = instance.getDamageCost();
        assertEquals(expResult, result);

    }

    /**
     * Test of getDeposit method, of class Bill.
     */
    @Test
    public void testGetDeposit() {
        System.out.println("getDeposit");
        Bill instance = null;
        Double expResult = null;
        Double result = instance.getDeposit();
        assertEquals(expResult, result);
   
    }

    /**
     * Test of getEndDate method, of class Bill.
     */
    @Test
    public void testGetEndDate() {
        System.out.println("getEndDate");
        Bill instance = null;
        Date expResult = null;
        Date result = instance.getEndDate();
        assertEquals(expResult, result);
 
    }

    /**
     * Test of getEndMileage method, of class Bill.
     */
    @Test
    public void testGetEndMileage() {
        System.out.println("getEndMileage");
        Bill instance = null;
        Double expResult = null;
        Double result = instance.getEndMileage();
        assertEquals(expResult, result);
 
    }

    /**
     * Test of getGasAdded method, of class Bill.
     */
    @Test
    public void testGetGasAdded() {
        System.out.println("getGasAdded");
        Bill instance = null;
        Double expResult = null;
        Double result = instance.getGasAdded();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStartDate method, of class Bill.
     */
    @Test
    public void testGetStartDate() {
        System.out.println("getStartDate");
        Bill instance = null;
        Date expResult = null;
        Date result = instance.getStartDate();
        assertEquals(expResult, result);
 
    }

    /**
     * Test of getStartMileage method, of class Bill.
     */
    @Test
    public void testGetStartMileage() {
        System.out.println("getStartMileage");
        Bill instance = null;
        Double expResult = null;
        Double result = instance.getStartMileage();
        assertEquals(expResult, result);
   
    }

    /**
     * Test of calculateChargeOwed method, of class Bill.
     */
    @Test
    public void testCalculateChargeOwed() {
        System.out.println("calculateChargeOwed");

        double daileyMilesAllowed=200;
        double mileageSurcharge=0.20;
        double gasCost=2.00;
        double testMileage=2349;
        Double expResult = -180.00;
        Bill testBill3=null;
        Rental newRental = null;

        Double result = testBill1.calculateChargeOwed(daileyMilesAllowed, mileageSurcharge, gasCost);
        assertEquals(expResult, result);

        expResult=-160.00;
        result=testBill2.calculateChargeOwed(daileyMilesAllowed, mileageSurcharge, gasCost);
        assertEquals(expResult, result);

       // make a bill with no customer should be in the constructor tests
        try {
            newRental = new Rental(startDate, testMileage, null, 200.00);
            testBill3 = new Bill(newRental, endDate, testMileage, gasAdded, damageCharge, dailyRate);
            fail("there is no customer information included");
        } catch (BadInputException e) {
            assertTrue(true);
        }
        assertTrue(testBill3 == null);
        assertTrue(newRental == null);

        // tset the calculations
        // 3 day rental, 700 miles travelled - 3*20+100*.2= $80.00
//        sDate=new GregorianCalendar(2009,03,10);
        eDate=new GregorianCalendar(2009,03,12);
//        sMileage=100;
        eMileage=800;
        deposit=200;
        dailyRate=20.00;
        gasAdded=0;
        damageCharge=0;
//        startDate=sDate.getTime();
        endDate=eDate.getTime();

        expResult=-120.00;
        try {
            testBill3= new Bill (testRental, endDate, eMileage, gasAdded, damageCharge, dailyRate);
            result=testBill3.calculateChargeOwed(daileyMilesAllowed, mileageSurcharge, gasCost);
        } catch (BadInputException e) {
            fail ("This is a good tset");
        }
        assertEquals(expResult, result);


        // 3 days rental, 700 miles travelled, 10 gallons of gas - $80.00+10*2.00= $100.00
        expResult=-100.00;
        gasAdded=10;
        try {
            testBill3= new Bill (testRental, endDate, eMileage, gasAdded, damageCharge, dailyRate);
            result=testBill3.calculateChargeOwed(daileyMilesAllowed, mileageSurcharge, gasCost);
        } catch (BadInputException e) {
            fail ("This is a good tset");
        }
        assertEquals(expResult, result);

        // same + 24.93 damage charge = $100.00 + 24.93=124.93
        damageCharge=24.93;
        expResult=-75.07;
        try {
            testBill3= new Bill (testRental, endDate, eMileage, gasAdded, damageCharge, dailyRate);
            result=testBill3.calculateChargeOwed(daileyMilesAllowed, mileageSurcharge, gasCost);
        } catch (BadInputException e) {
            fail ("This is a good tset");
        }
        assertEquals(expResult, result);


    }

    /**
     * Test of calculateRevenue method, of class Bill.
     */
    @Test
    public void testCalculateRevenue() {
        System.out.println("calculateRevenue");
        Double dailyMilesAllowed = null;
        Double mileageSurcharge = null;
        Double gasCost = null;
        Bill instance = null;
        Double expResult = null;
        Double result = instance.calculateRevenue(dailyMilesAllowed, mileageSurcharge, gasCost);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalFee method, of class Bill.
     */
    @Test
    public void testGetTotalFee() {
        System.out.println("getTotalFee");
        Bill instance = null;
        Double expResult = null;
        Double result = instance.getTotalFee();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTotalFee method, of class Bill.
     */
    @Test
    public void testSetTotalFee() {
        System.out.println("setTotalFee");
        Double val = null;
        Bill instance = null;
        instance.setTotalFee(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalMiles method, of class Bill.
     */
    @Test
    public void testGetTotalMiles() {
        System.out.println("getTotalMiles");
        Bill instance = null;
        double expResult = 0.0;
        double result = instance.getTotalMiles();
        assertEquals(expResult, result);
  
    }

    /**
     * Test of getDaysRented method, of class Bill.
     */
    @Test
    public void testGetDaysRented() {
        System.out.println("getDaysRented");
        Bill instance = null;
        double expResult = 0.0;
        double result = instance.getDaysRented();
        assertEquals(expResult, result);
   
    }

}