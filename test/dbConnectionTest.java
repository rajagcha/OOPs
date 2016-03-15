/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.mail.Store;
import static org.junit.Assert.*;

/**
 *
 * @author Zephyr
 */
public class dbConnectionTest {


    public dbConnectionTest() {
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
     * Test of dbconnect method, of class dbConnection.
     */
    @Test
    public void testDbconnect() throws SQLException, BadDatabaseInsertException{
        // test a successful connection
        System.out.println("dbconnect");
        dbConnection instance = new dbConnection();
        boolean expResult = true;
        boolean result = instance.dbconnect();
        assertEquals(expResult, result);
        instance.closedb();
    }

    /**
     * Test of closedb method, of class dbConnection.
     */
    @Test
    public void testClosedb() throws SQLException{
        System.out.println("closedb");
        dbConnection instance = new dbConnection();
        boolean result = instance.dbconnect();
        instance.closedb();
    }

    /**
     * Test of getDBConnected method, of class dbConnection.
     */
    @Test
    public void testGetDBConnected() throws SQLException{
        System.out.println("getDBConnected");
        dbConnection instance = new dbConnection();
        boolean result = instance.dbconnect();
        boolean expResult = true;
        result = instance.getDBConnected();
        assertEquals(expResult, result);

        // faii bevause the databse is closed
        instance.closedb();
        expResult = false;
        result = instance.getDBConnected();
        assertEquals(expResult, result);
        instance.closedb();
    }

    /**
     * Test of getFleet method, of class dbConnection.
     */
    @Test
    public void testGetFleet() throws Exception {
        System.out.println("getFleet");
        dbConnection instance = new dbConnection();
        instance.dbconnect();
        Vehicle expResult = null;
        ArrayList<Vehicle> fleet = instance.getFleet();
        String result=null;
        assertTrue(fleet.size() == 11);
        expResult=fleet.get(2);
        result="J73GJ7GH62738GU";
        assertEquals(expResult.getVehicleId(), result);

        instance.closedb();
    }

    /**
     * Test of getCostReport method, of class dbConnection.
     */
    @Test
    public void testGetCostReport() throws Exception {
        System.out.println("getCostReport");
        String type = "";
        long vId = 0L;
        dbConnection instance = new dbConnection();
        ArrayList<CostReport> expResult = null;
        ArrayList<CostReport> result = instance.getCostReport(type, vId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        instance.closedb();
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBillHistory method, of class dbConnection.
     */
    @Test
    public void testGetBillHistory() throws Exception {
        System.out.println("getBillHistory");
        long vId = 0L;
        dbConnection instance = new dbConnection();
        ArrayList<Bill> expResult = null;
        ArrayList<Bill> result = instance.getBillHistory(vId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        instance.closedb();
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRental method, of class dbConnection.
     */
    @Test
    public void testGetRental()throws Exception {
        System.out.println("getRental");
        long vid = 0L;
        dbConnection instance = new dbConnection();
        Rental expResult = null;
//        Rental result = instance.getRental(vid);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        instance.closedb();
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectData method, of class dbConnection.
     */
    @Test
    public void testSelectData() throws SQLException{
        System.out.println("selectData");
        String command = "";
        dbConnection instance = new dbConnection();
        ResultSet expResult = null;
        ResultSet result = instance.selectData(command);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        instance.closedb();
        fail("The test case is a prototype.");
    }

  
    /**
     * Test of getCostReport method, of class dbConnection.
     */
    @Test
    public void testGetCostReport_String_long() throws Exception {
        System.out.println("getCostReport");
        String type = "";
        long vId = 0L;
        dbConnection instance = new dbConnection();
        ArrayList<CostReport> expResult = null;
        ArrayList<CostReport> result = instance.getCostReport(type, vId);
        assertEquals(expResult, result);
        instance.closedb();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBillHistory method, of class dbConnection.
     */
    @Test
    public void testGetBillHistory_long() throws Exception {
        System.out.println("getBillHistory");
        long vId = 0L;
        dbConnection instance = new dbConnection();
        ArrayList<Bill> expResult = null;
        ArrayList<Bill> result = instance.getBillHistory(vId);
        assertEquals(expResult, result);
        instance.closedb();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCorperateCards method, of class dbConnection.
     */
    @Test
    public void testGetCorperateCards() throws Exception {
        System.out.println("getCorperateCards");
        dbConnection db=new dbConnection();
          try {
            db.dbconnect();
        } catch (SQLException e) {
            System.err.print(e);
        }
        int expResult = 12;
        ArrayList<CreditCard> result = db.getCorperateCards();
        assertEquals(expResult, result.size());

        db.closedb();
    }

    /**
     * Test of getVehicleType method, of class dbConnection.
     */
    @Test
    public void testGetVehicleType1() throws Exception {
        System.out.println("getVehicleType");
       dbConnection db=new dbConnection();
          try {
            db.dbconnect();
        } catch (SQLException e) {
            System.err.print(e);
        }
        int expResult = 8;
        ArrayList<VehicleType> result = db.getVehicleType();
        assertEquals(expResult, result.size());

        db.closedb();
    }

    /**
     * Test of getVehicleType method, of class dbConnection.
     */
    @Test
    public void testGetVehicleType_long() throws Exception {
        System.out.println("getVehicleType");
       dbConnection db=new dbConnection();
          try {
            db.dbconnect();
        } catch (SQLException e) {
            System.err.print(e);
        }
        long dbId = 6;
        String expResult = "Without TV";
        VehicleType result = db.getVehicleType(dbId);
        assertEquals(expResult, result.getOption());

        // bad get
        try {
            result = db.getVehicleType(14);
            if (result!=null) {
                fail("Should not be able to get his iteam");
            }
        } catch (SQLException e) {
            
        }
        db.closedb();
    }

    @Test
    public void testStoreMonthlyReport() throws Exception {
               System.out.println("storeMonthlyReport");
        dbConnection db = new dbConnection();
        try {
            db.dbconnect();
        } catch (SQLException e) {
            System.err.print(e);
        }

        int month=12;
        int year=2004;
        String report="this is a test of the report\n     Does this work\n";
        try {
            db.store(year, month, "revenue", report);
            db.store(2004, 12, "A", "This is another Test\nDoes it work\n");

            db.store(2004, 12, "A", "This does not work");
            fail ("Should neve get here\ntestStoreMontlyReport\n");
        } catch (SQLException e) {
            System.out.print("The unique constraint works testStoreMontlyReport\n");
        }
        db.closedb();
    }

    @Test
    public void testGetRevenueAndVehicleReport() throws Exception {
                      System.out.println("storeMonthlyReport");
        dbConnection db = new dbConnection();
        try {
            db.dbconnect();
        } catch (SQLException e) {
            System.err.print(e);
        }

        int month=12;
        int year=2004;
        String report=null;
        String expreport="this is a test of the report\n     Does this work\n";
        try {
            report=db.getRevenueReport(year, month);
            assertEquals(expreport, report);
            report=db.getVehicleReport(2004, 12, "A");
            expreport="This is another Test\nDoes it work\n";
            assertEquals(expreport, report);

            expreport=null;
            report=db.getVehicleReport(2004, 12, "B");
            assertEquals(expreport, report);
            report=db.getVehicleReport(2004, 11, "A");
            assertEquals(expreport, report);
        } catch (SQLException e) {
            fail ("Should neve get here\ntestGetRevenueandVehicleReport\n");
        }
        db.closedb();
    }

    /**
     * Test of getRental method, of class dbConnection.
     */
    @Test
    public void testGetRental_long() throws Exception {
        System.out.println("getRental");
        long vid = 0L;
        dbConnection instance = new dbConnection();
        Rental expResult = null;
        Rental result = instance.getRental(vid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
         instance.closedb();
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectData method, of class dbConnection.
     */
    @Test
    public void testSelectData_String() throws Exception {
        System.out.println("selectData");
        String command = "";
        dbConnection instance = new dbConnection();
        ResultSet expResult = null;
        ResultSet result = instance.selectData(command);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of store method, of class dbConnection.
     */
    @Test
    public void testStore() throws Exception {
//        System.out.println("store");
//        Vehicle v = null;
//        dbConnection instance = new dbConnection();
//        instance.store(v);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of modifyTable method, of class dbConnection.
     */
    @Test
    public void testModifyTable() throws Exception {
        System.out.println("modifyTable");
        String command = "";
        dbConnection instance = new dbConnection();
        boolean expResult = false;
        boolean result = instance.modifyTable(command);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dbExists method, of class dbConnection.
     */
    @Test
    public void testDbExists_Vehicle() throws Exception {
        System.out.println("dbExists");
        dbConnection instance = new dbConnection();
        instance.dbconnect();
        VehicleType vt=instance.getVehicleType(5);
        Vehicle testVehicle = new Vehicle("SHKTE69808G79JE",vt, 1000.1, VehicleStatus.AVAILABLE, -200);

        boolean expResult = true;
        boolean result = instance.dbExists(testVehicle);
        assertEquals(expResult, result);
        testVehicle = new Vehicle("SHKTE69808GHTJE", vt, 1000.1, VehicleStatus.AVAILABLE, -200);
        expResult = false;
        result = instance.dbExists(testVehicle);
    }

    /**
     * Test of dbExists method, of class dbConnection.
     */
    @Test
    public void testDbExists_VehicleType() throws Exception {
        System.out.println("dbExists");
        VehicleType testVehicleType = new VehicleType("Standard/Large/Family size", "with Child Seat", 0);
        dbConnection instance = new dbConnection();
        instance.dbconnect();

        boolean expResult = true;
        boolean result = instance.dbExists(testVehicleType);
        assertEquals(expResult, result);

        expResult=false;
        testVehicleType=new VehicleType("Standard/Large/Family size", "4 door", 0);
        result = instance.dbExists(testVehicleType);
        assertEquals(expResult, result);

        testVehicleType=new VehicleType("Largish", "4 door", 0);
        result = instance.dbExists(testVehicleType);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of dbExists method, of class dbConnection.
     */
    @Test
    public void testDbExists_CreditCard() throws Exception {
        System.out.println("dbExists");
        GregorianCalendar gexpire=new GregorianCalendar(2010,11,30);
        Date expire=gexpire.getTime();
        CreditCard testCreditCard = new CreditCard("John & Wayne Motors Co.", "5747 4848 9490 0838",
                expire);
        dbConnection instance = new dbConnection();
        instance.dbconnect();

        boolean expResult = true;
        boolean result = instance.dbExists(testCreditCard);
        assertEquals(expResult, result);

        testCreditCard=new CreditCard("Wiley Brothers","5747 4848 9490 0838",expire);
        expResult = false;
        result = instance.dbExists(testCreditCard);
        assertEquals(expResult, result);

        testCreditCard = new CreditCard("John & Wayne Motors Co.", "5747484894900838", expire);
        expResult = true;
        result = instance.dbExists(testCreditCard);
        assertEquals(expResult, result);
    }

    /**
     * Test of dbExistsRental method, of class dbConnection.
     */
    @Test
    public void testDbExistsRental() throws Exception {
        System.out.println("dbExistsRental");
        long dbId = 0L;
        dbConnection instance = new dbConnection();
        instance.dbconnect();
        boolean expResult = false;
        boolean result = instance.dbExistsRental(dbId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dbExistsCorperateCard method, of class dbConnection.
     */
    @Test
    public void testDbExistsCorperateCard() throws Exception {
        System.out.println("dbExistsCorperateCard");
        long dbId = 56L;
        dbConnection instance = new dbConnection();
        instance.dbconnect();
        boolean expResult = false;
        boolean result = instance.dbExistsCorperateCard(dbId);
        assertEquals(expResult, result);

        dbId=5L;
        expResult = true;
        result = instance.dbExistsCorperateCard(dbId);
        assertEquals(expResult, result);
    }

    /**
     * Test of dbExistsVehicleType method, of class dbConnection.
     */
    @Test
    public void testDbExistsVehicleType() throws Exception {
        System.out.println("dbExistsVehicleType");
        long dbId = 0L;
        dbConnection instance = new dbConnection();
        instance.dbconnect();
        boolean expResult = false;
        boolean result = instance.dbExistsVehicleType(dbId);
        assertEquals(expResult, result);

        dbId = 5L;
        expResult = true;
        result = instance.dbExistsVehicleType(dbId);
        assertEquals(expResult, result);
    }

    /**
     * Test of dbconnect method, of class dbConnection.
     */


}