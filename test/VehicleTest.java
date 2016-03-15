/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Date;
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
public class VehicleTest {

    public VehicleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
/*
    @Before
    public void setUp(){
        try {
            Vehicle testCar1 = new Vehicle("TestVin1", VehicleType.COMPACT2DOOR, 2300.00);
            Vehicle testCar2 = new Vehicle("TestVin2", VehicleType.COMPACT4DOOR, 2300.00);
            Vehicle testCar3 = new Vehicle("TestVin3", VehicleType.CONVERTIBLE, 2300.00);
            Vehicle testCar4 = new Vehicle("TestVin4", VehicleType.SUVNOTV, 2300.00);
            Vehicle testCar5 = new Vehicle("TestVin5", VehicleType.STANARDWITHCHILD, 2300.00);
        } catch (BadInputException errorString){
            System.out.println(errorString);
        }

    }
*/
    @After
    public void tearDown() {
    }

    /**
     * Test of changeStatus method, of class Vehicle.
     */
 /* RAJ   @Test
    public void testChangeStatus() throws Exception {
        System.out.println("changeStatus");
        VehicleStatus newStatus = VehicleStatus.RENTED;
       Vehicle testCar1=new Vehicle("TestVin1", VehicleType.COMPACT2DOOR, 2300.00);
        Vehicle instance = testCar1;
        try {
            instance.changeStatus(newStatus);
        } catch (CanNotChangeStatusException e) {
            assertTrue(true);
        }
        assertFalse(instance.checkStatus(newStatus));
        newStatus=VehicleStatus.AVAILABLE;
        instance.changeStatus(newStatus);
        assertTrue(instance.checkStatus(newStatus));
        newStatus = VehicleStatus.RENTED;
        instance.changeStatus(newStatus);
        assertTrue(instance.checkStatus(newStatus));
        newStatus=VehicleStatus.UNAVAILABLE;
        instance.changeStatus(newStatus);
        assertTrue(instance.checkStatus(newStatus));
        newStatus=VehicleStatus.DELETED;
        instance.changeStatus(newStatus);
        assertTrue(instance.checkStatus(newStatus));

    }


    /**
     * Test of getBillHistory method, of class Vehicle.
     */
    @Test
    public void testGetBillHistory() {
        System.out.println("getBillHistory");
        Vehicle instance = null;
    //    ArrayList<Bill> expResult = null;
    //    ArrayList<Bill> result = instance.getBillHistory();
    //    assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBillHistory method, of class Vehicle.
     */
    @Test
    public void testSetBillHistory() {
        System.out.println("setBillHistory");
   //     ArrayList<Bill> val = null;
        Vehicle instance = null;
   //     instance.setBillHistory(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentMilage method, of class Vehicle.
     */
    @Test
    public void testGetCurrentMilage() {
        System.out.println("getCurrentMilage");
        Vehicle instance = null;
        Double expResult = null;
        Double result = instance.getCurrentMilage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentMilage method, of class Vehicle.
     */
    @Test
    public void testSetCurrentMilage() {
        System.out.println("setCurrentMilage");
        Double val = null;
        Vehicle instance = null;
        instance.setCurrentMilage(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentRental method, of class Vehicle.
     */
    @Test
    public void testGetCurrentRental() {
        System.out.println("getCurrentRental");
        Vehicle instance = null;
        Rental expResult = null;
        Rental result = instance.getCurrentRental();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCurrentRental method, of class Vehicle.
     */
    @Test
    public void testSetCurrentRental() {
        System.out.println("setCurrentRental");
        Rental newRental = null;
        Vehicle instance = null;
        instance.setCurrentRental(newRental);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDamageReport method, of class Vehicle.
     */
    @Test
    public void testGetDamageReport() {
        System.out.println("getDamageReport");
        Vehicle instance = null;
        ArrayList<CostReport> expResult = null;
        ArrayList<CostReport> result = instance.getDamageReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDamageReport method, of class Vehicle.
     */
    @Test
    public void testSetDamageReport() {
        System.out.println("setDamageReport");
        ArrayList<CostReport> val = null;
        Vehicle instance = null;
        instance.setDamageReport(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getServiceReport method, of class Vehicle.
     */
    @Test
    public void testGetServiceReport() {
        System.out.println("getServiceReport");
        Vehicle instance = null;
        ArrayList<CostReport> expResult = null;
        ArrayList<CostReport> result = instance.getServiceReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setServiceReport method, of class Vehicle.
     */
    @Test
    public void testSetServiceReport() {
        System.out.println("setServiceReport");
        ArrayList<CostReport> val = null;
        Vehicle instance = null;
        instance.setServiceReport(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatus method, of class Vehicle.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Vehicle instance = null;
        VehicleStatus expResult = null;
        VehicleStatus result = instance.getStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleId method, of class Vehicle.
     */
    @Test
    public void testGetVehicleId() {
        System.out.println("getVehicleId");
        Vehicle instance = null;
        String expResult = "";
        String result = instance.getVehicleId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleTypeId method, of class Vehicle.
     */
    @Test
    public void testGetVehicleTypeId() {
        System.out.println("getVehicleTypeId");
        Vehicle instance = null;
        VehicleType expResult = null;
  //      VehicleType result = instance.getVehicleTypeId();
 //       assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
  //      fail("The test case is a prototype.");
    }

    /**
     * Test of addService method, of class Vehicle.
     */
    @Test
    public void testAddService() {
        System.out.println("addService");
        CostReport serviceReport = null;
        Vehicle instance = null;
        instance.addService(serviceReport);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createRental method, of class Vehicle.
     */
    @Test
    public void testCreateRental() {
        System.out.println("createRental");
        CustomerInformation customer = null;
        Double currentMileage = null;
        Vehicle instance = null;
        instance.createRental(customer, currentMileage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDamageReport method, of class Vehicle.
     */
    @Test
    public void testAddDamageReport() {
        System.out.println("addDamageReport");
        CostReport newDamage = null;
        Vehicle instance = null;
        instance.addDamageReport(newDamage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBill method, of class Vehicle.
     */
    @Test
    public void testCreateBill() {
        System.out.println("createBill");
        Date returnDate = null;
        Double currentMileage = null;
        Double addGas = null;
        Double damageCost = null;
        Vehicle instance = null;
   //     Bill expResult = null;
   //     Bill result = instance.createBill(returnDate, currentMileage, addGas, damageCost);
  //      assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateRentalFee method, of class Vehicle.
     */
    @Test
    public void testCalculateRentalFee() {
        System.out.println("calculateRentalFee");
   //     Bill newBill = null;
        Double mileageAllowance = null;
        Double mileageRate = null;
        Double gasRate = null;
        Vehicle instance = null;
        Double expResult = null;
    //    Double result = instance.calculateRentalFee(newBill, mileageAllowance, mileageRate, gasRate);
    //    assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addBill method, of class Vehicle.
     */
    @Test
    public void testAddBill() {
        System.out.println("addBill");
    //    Bill newBill = null;
        Vehicle instance = null;
   //     instance.addBill(newBill);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateVehicleReport method, of class Vehicle.
     */
    @Test
    public void testGenerateVehicleReport() {
        System.out.println("generateVehicleReport");
        Vehicle instance = null;
        String expResult = "";
        String result = instance.generateVehicleReport();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastServiced method, of class Vehicle.
     */
    @Test
    public void testGetLastServiced() {
        System.out.println("getLastServiced");
        Vehicle instance = null;
        CostReport expResult = null;
        CostReport result = instance.getLastServiced();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateRevenue method, of class Vehicle.
     */
    @Test
    public void testGenerateRevenue() {
        System.out.println("generateRevenue");
        Date startDate = null;
        Date endDate = null;
        Vehicle instance = null;
        Double expResult = null;
//        Double result = instance.generateRevenue(startDate, endDate);
  //      assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}