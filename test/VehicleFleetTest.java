/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Calendar;
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
public class VehicleFleetTest {

    private VehicleFleet testFleet;

    public VehicleFleetTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        testFleet=new VehicleFleet();
        VehicleType v1=new VehicleType("Compact4Door","None",20.00);
        VehicleType v2 = new VehicleType("Compact2Door", "None", 15.00);
        VehicleType v3 = new VehicleType("SUV", "None", 35.00);
        VehicleType v4 = new VehicleType("Standard", "ChildSeat", 25.00);
        VehicleType v5 = new VehicleType("Convertible", "None", 45.00);


        try {
            testFleet.addVehicle("TestVin2", v1, 2300.00);
            testFleet.addVehicle("TestVin3", v5,2300.00);
            testFleet.addVehicle("TestVin4", v3, 2300.00);
            testFleet.addVehicle("TestVin5", v4, 2300.00);
            testFleet.addVehicle("TestVin1", v2, 2300.00);
            testFleet.addVehicle("TestVin6", v1, 2300.00);
            testFleet.addVehicle("TestVin7", v3, 2300.00);
            testFleet.addVehicle("TestVin8", v1, 2300.00);
        } catch ( BadInputException e) {
            // this is testing
            fail("making initail fleet died");
        } catch (CanNotChangeStatusException e) {
            // this is testing
            fail ("making initial fleet died 2");
        }
    }

    @After
    public void tearDown() {
    }


    /**
     * Test of addVehicle method, of class VehicleFleet.
     */
    @Test
    public void testAddVehicle() {
        System.out.println("addVehicle");
        String vehicleId = "TestVin1";
        VehicleType type = new VehicleType("Compact2Door","None",15.00);
        Double mileage = 3295.00;
        VehicleFleet instance = new VehicleFleet();
        // add vehicle to empty fleet
        try {
            instance.addVehicle(vehicleId, type, mileage);
        } catch (BadInputException e) {
            fail("Should not get exception");
        } catch (CanNotChangeStatusException e) {
            fail("Should not get expcetion");
        }

        // add vehicel but it is already in fleet
        try {
            instance.addVehicle(vehicleId, type, mileage);
            fail("Added vehicle with id already in fleet");
        } catch (BadInputException e){
            assertTrue(true);
        } catch (CanNotChangeStatusException e) {
            fail("Should not throw this expcetion");
        }

        // add another vehicle to fleet
        try {
            instance.addVehicle("testVin2", type, mileage);
        } catch (BadInputException e) {
            fail("Should not throw this expcetion");
        } catch (CanNotChangeStatusException e) {
            fail("Should not throw this expcetion");
        }

        // add vehicel but it is already in fleet (last possition)
        vehicleId="TestVin1";
        try {
            testFleet.addVehicle(vehicleId, type, mileage);
            fail("Added vehicle with id already in fleet");
        } catch (BadInputException e){
            assertTrue(true);
        } catch (CanNotChangeStatusException e) {
            fail("Should not throw this expcetion");
        }
         // add vehicel but it is already in fleet (first possition)
        vehicleId="TestVin2";
        try {
            testFleet.addVehicle(vehicleId, type, mileage);
            fail("Added vehicle with id already in fleet");
        } catch (BadInputException e){
            assertTrue(true);
        } catch (CanNotChangeStatusException e) {
            fail("Should not throw this expcetion");
        }

        // add vehicel but it is already in fleet (middle possition)
        vehicleId="TestVin3";
        try {
            testFleet.addVehicle(vehicleId, type, mileage);
            fail("Added vehicle with id already in fleet");
        } catch (BadInputException e){
            assertTrue(true);
        } catch (CanNotChangeStatusException e) {
            fail("Should not throw this expcetion");
        }

 
    }

    /**
     * Test of vehicleIdExists method, of class VehicleFleet.
     */
    @Test
    public void testVehicleIdExists() {
        System.out.println("vehicleIdExists");

        // test last vehicle in the fleet
        String vehicleId = "TestVin1";
        Boolean expResult = true;
        Boolean result = testFleet.vehicleIdExists(vehicleId);
        assertEquals(expResult, result);

        // test first vehicle in fleet
        vehicleId="TestVin2";
        result = testFleet.vehicleIdExists(vehicleId);
        assertEquals(expResult, result);

        // test middle vehicle in fleet
        vehicleId="TestVin3";
        result = testFleet.vehicleIdExists(vehicleId);
        assertEquals(expResult, result);

        // test a vehicle not in the fleet
        vehicleId="0295830293493";
        result = testFleet.vehicleIdExists(vehicleId);
        expResult=false;
        assertEquals(expResult, result);

        // test if case of vin matters
        vehicleId="testvin1";
        result = testFleet.vehicleIdExists(vehicleId);
        expResult=true;
        assertEquals(expResult, result);


    }

    /**
     * Test of deleteVehicle method, of class VehicleFleet.
     */
    @Test
    public void testDeleteVehicle() {
        System.out.println("deleteVehicle");

        // try to delete a none existant vehicle
        String vehicleId = "239_-0925n 2389234";
        try {
            testFleet.deleteVehicle(vehicleId);
            fail ("Deleted vehicle which does not exists");
        } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            assertTrue(true);
        }

        // Delete the last vehicle in the fleet
        vehicleId = "TestVin1";
        try {
            testFleet.deleteVehicle(vehicleId);
        } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            fail("Wrong exception caught");
        }

        // change the sataus on the last vehicle to rented
        // then try to delete should fail....
        vehicleId = "TestVin4";
        Vehicle v = testFleet.getVehicle(vehicleId);

        try {
            v.changeStatus(VehicleStatus.RENTED);
            testFleet.deleteVehicle(vehicleId);
            fail("Deleted vehicle with status RENTED");
        } catch (CanNotChangeStatusException e) {
            assertTrue(true);
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }

        // delete middle vehicle
        vehicleId = "TestVin3";
        try {
            testFleet.deleteVehicle(vehicleId);
        } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }
        // delete first vehicle
        vehicleId = "TestVin2";
        try {
            testFleet.deleteVehicle(vehicleId);
        } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }

        // delete only vehicle
        vehicleId = "TestVin3";
        try {
            testFleet.deleteVehicle(vehicleId);
        } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }

      // delete vehicle last vehicles
        vehicleId = "TestVin4";
        try {
            testFleet.getVehicle(vehicleId).changeStatus(VehicleStatus.UNAVAILABLE);
            testFleet.deleteVehicle(vehicleId);
        } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            assertTrue(true);
        }
   
    }

    /**
     * Test of returnVehicleFromService method, of class VehicleFleet.
     */
    @Test
    public void testReturnVehicleFromService() {
        System.out.println("returnVehicleFromService");
        String vehicleId = "TestVin8";
        CostReport serviceReport = null;
        GregorianCalendar gDate= new GregorianCalendar (2009,03,29);
        Date returnDate = gDate.getTime();
        Vehicle testCar=null;
        int reportNumBefore=0;

        // test cases
        // 1. Good vehicleId and status
        // end of fleet
        try {
            testCar=testFleet.getVehicle(vehicleId);
            reportNumBefore=testCar.getServiceReport().size();
            serviceReport = new CostReport(returnDate, "Oil change Seat Belt Check", 50.00);
            testFleet.changeVehicleStatus(vehicleId, VehicleStatus.UNAVAILABLE);
            testFleet.returnVehicleFromService(vehicleId, serviceReport);
        } catch (BadInputException e) {
            fail ("Should be no exception :" +e);
        } catch (CanNotChangeStatusException e) {
           fail ("Should be no exception :" +e);
        }
        // number of reports increased
        assertTrue(reportNumBefore == testCar.getServiceReport().size()-1);
        // car with vehicleId is now available
        assertTrue(testFleet.getVehicle(vehicleId).getStatus() == VehicleStatus.AVAILABLE);

        // beginning of fleet
        try {
            vehicleId="TestVin2";
            testCar=testFleet.getVehicle(vehicleId);
            reportNumBefore=testCar.getServiceReport().size();
           serviceReport = new CostReport(returnDate, "Oil change Seat Belt Check", 50.00);
            testFleet.changeVehicleStatus(vehicleId, VehicleStatus.UNAVAILABLE);
            testFleet.returnVehicleFromService(vehicleId, serviceReport);
        } catch (BadInputException e) {
            fail ("Should be no exception :" +e);
        } catch (CanNotChangeStatusException e) {
           fail ("Should be no exception :" +e);
        }
        // number of reports increased
        assertTrue(reportNumBefore == testCar.getServiceReport().size()-1);
        // car with vehicleId is now available
        assertTrue(testFleet.getVehicle(vehicleId).getStatus() == VehicleStatus.AVAILABLE);

        // middle of fleet
        try {
            vehicleId="TestVin4";
            testCar=testFleet.getVehicle(vehicleId);
            reportNumBefore=testCar.getServiceReport().size();
           serviceReport = new CostReport(returnDate, "Oil change Seat Belt Check", 50.00);
            testFleet.changeVehicleStatus(vehicleId, VehicleStatus.UNAVAILABLE);
            testFleet.returnVehicleFromService(vehicleId, serviceReport);
        } catch (BadInputException e) {
            fail ("Should be no exception :" +e);
        } catch (CanNotChangeStatusException e) {
           fail ("Should be no exception :" +e);
        }
        // number of reports increased
        assertTrue(reportNumBefore == testCar.getServiceReport().size()-1);
        // car with vehicleId is now available
        assertTrue(testFleet.getVehicle(vehicleId).getStatus() == VehicleStatus.AVAILABLE);
        
        // insert a second one
        try {
            vehicleId="TestVin4";
                        testCar=testFleet.getVehicle(vehicleId);
           reportNumBefore=testCar.getServiceReport().size();
           serviceReport = new CostReport(returnDate, "Oil change Seat Belt Check", 50.00);
            testFleet.changeVehicleStatus(vehicleId, VehicleStatus.UNAVAILABLE);
            testFleet.returnVehicleFromService(vehicleId, serviceReport);
        } catch (BadInputException e) {
            fail ("Should be no exception :" +e);
        } catch (CanNotChangeStatusException e) {
           fail ("Should be no exception :" +e);
        }
        // number of reports increased
        assertTrue(reportNumBefore == testCar.getServiceReport().size()-1);
        // car with vehicleId is now available
        assertTrue(testFleet.getVehicle(vehicleId).getStatus() == VehicleStatus.AVAILABLE);

        // 2. bad vehicleId
      try {
          vehicleId = "2395ng kle-23894";
            serviceReport = new CostReport(returnDate, "Oil change Seat Belt Check", 50.00);
            // testFleet.changeVehicleStatus(vehicleId, VehicleStatus.UNAVAILABLE);
            testFleet.returnVehicleFromService(vehicleId, serviceReport);
            fail ("Vehicle has bad id so should not complete");
        } catch (BadInputException e) {
            assertTrue("Caught bad vehicle " +e,true);
        } catch (CanNotChangeStatusException e) {
           fail ("Should be no exception :" +e);
        }

        // 3. Good vehicleId and bad status
      try {
          vehicleId = "TestVin8";
                      testCar=testFleet.getVehicle(vehicleId);
                     reportNumBefore=testCar.getServiceReport().size();
            serviceReport = new CostReport(returnDate, "Oil change Seat Belt Check", 50.00);
            testFleet.changeVehicleStatus(vehicleId, VehicleStatus.RENTED);
            testFleet.returnVehicleFromService(vehicleId, serviceReport);
            fail ("Vehicle has bad status so should not complete");
        } catch (BadInputException e) {
            fail ("Should be no exception :" +e);
        } catch (CanNotChangeStatusException e) {
            assertTrue("Caught bad vehicle " +e,true);
        }
        // number of reports not increased
        assertTrue(reportNumBefore == testCar.getServiceReport().size());
        // car with vehicleId is still rented
        assertTrue(testFleet.getVehicle(vehicleId).getStatus() == VehicleStatus.RENTED);


    }

    /**
     * Test of generateServiceReport method, of class VehicleFleet.
     */
    @Test
    public void testGenerateServiceReport() {
        System.out.println("generateServiceReport");
        GregorianCalendar gDate= new GregorianCalendar (2009,03,29);
        Date returnDate = gDate.getTime();
        String serviceDescription = "Simple Oil Change";
        Double serviceCost = 30.00;
        VehicleFleet instance = new VehicleFleet();
        CostReport expResult = null;
        CostReport result=null;
        
        // test good results
        try {
            result = instance.generateServiceReport(returnDate, serviceDescription, serviceCost);
        } catch (BadInputException e) {
            fail("Exception thrown "+e);
        }
        assertEquals(result.getCost(), serviceCost);
        assertEquals(result.getDate(), returnDate);
        assertEquals(result.getDescription(), serviceDescription);

        // test bad results -cost < 0
        try {
            serviceCost=-10.20;
            result = instance.generateServiceReport(returnDate, serviceDescription, serviceCost);
            fail("Cost must not be less than 0");
        } catch (BadInputException e) {
            assertTrue(true);
        }

        // test bad results description = null
        try {
            serviceCost=50.39;
            serviceDescription=null;
            result = instance.generateServiceReport(returnDate, serviceDescription, serviceCost);
            fail("Require a service report");
        } catch (BadInputException e) {
            assertTrue(true);
        }
        // test bad description < 3 characters long
        try {
            serviceDescription="a1";
            result = instance.generateServiceReport(returnDate, serviceDescription, serviceCost);
            fail("Require a service report of at least 3 characters");
        } catch (BadInputException e) {
            assertTrue(true);
        }

        // test bad date = null
        try {
            returnDate=null;
            serviceDescription="An oil change and tire rotation";
            result = instance.generateServiceReport(returnDate, serviceDescription, serviceCost);
            fail("Require a none null date");
        } catch (BadInputException e) {
            assertTrue(true);
        }
    }

    /**
     * Test of getGasRate method, of class VehicleFleet.
     */
    @Test
    public void testGetGasRate() {
        System.out.println("getGasRate");
        VehicleFleet instance = new VehicleFleet();
        Double expResult = null;
        Double result = instance.getGasRate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMileageAllowance method, of class VehicleFleet.
     */
    @Test
    public void testGetMileageAllowance() {
        System.out.println("getMileageAllowance");
        VehicleFleet instance = new VehicleFleet();
        Double expResult = null;
        Double result = instance.getMileageAllowance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMileageRate method, of class VehicleFleet.
     */
    @Test
    public void testGetMileageRate() {
        System.out.println("getMileageRate");
        VehicleFleet instance = new VehicleFleet();
        Double expResult = null;
        Double result = instance.getMileageRate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicles method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicles() {
        System.out.println("getVehicles");
        VehicleFleet instance = new VehicleFleet();
        ArrayList<Vehicle> expResult = null;
        ArrayList<Vehicle> result = instance.getVehicles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleByType method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicleByType() {
        System.out.println("getVehicleByType");

        // get one with 3 vehicles
        ArrayList<Vehicle> result = testFleet.getVehicleByType("Compact4Door","None");
        int expResult=3;
        assertEquals(expResult, result.size());

        // get one with 0 vehicls
        result = testFleet.getVehicleByType("Stanard","None");
        expResult=0;
        assertEquals(expResult, result.size());

        //get one with 1 vehicle
        result = testFleet.getVehicleByType("Compact2Door","None");
        expResult=1;
        assertEquals(expResult, result.size());

        System.out.println("Passed getVehicleByType");
    }

    /**
     * Test of getVehicleByAvalible method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicleByAvalible() {
        System.out.println("getVehicleByAvalible");

        // all 8 vehicles are available
        int expResult = 8;
        ArrayList<Vehicle> result = testFleet.getVehicleByAvalible();
        assertEquals(expResult, result.size());

        // make 3 vehicles unavailable
       try {
           testFleet.changeVehicleStatus("TestVin2",VehicleStatus.UNAVAILABLE);
           testFleet.changeVehicleStatus("TestVin1",VehicleStatus.UNAVAILABLE);
           testFleet.changeVehicleStatus("TestVin6",VehicleStatus.UNAVAILABLE);
       } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }
        expResult=5;
        result = testFleet.getVehicleByAvalible();
        assertEquals(expResult, result.size());

        // make 3 vehicles unavailable, 2 Rented
       try {
           testFleet.changeVehicleStatus("TestVin4",VehicleStatus.RENTED);
           testFleet.changeVehicleStatus("TestVin8",VehicleStatus.RENTED);
       } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }
        expResult=3;
        result = testFleet.getVehicleByAvalible();
        assertEquals(expResult, result.size());

       // make 3 vehicles unavailable, 2 Rented, 3 Deleted
       try {
           testFleet.changeVehicleStatus("TestVin3",VehicleStatus.DELETED);
           testFleet.changeVehicleStatus("TestVin5",VehicleStatus.DELETED);
           testFleet.changeVehicleStatus("TestVin7",VehicleStatus.DELETED);
       } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }
        expResult=0;
        result = testFleet.getVehicleByAvalible();
        assertEquals(expResult, result.size());

    }

    /**
     * Test of rentVehicle method, of class VehicleFleet.
     */
    @Test
    public void testRentVehicle() throws BadInputException {
        System.out.println("rentVehicle");
        String vehicleId = "TestVin3";

        DriverLicense dLicense = null;
        CreditCard cCard=null;
        CustomerInformation customer = null;
        Date expire= null;


        double startMileage=0;
        Calendar cTime=Calendar.getInstance();
        Calendar cDate=Calendar.getInstance();
        Date startDate= new Date();

        Calendar dlExpire = Calendar.getInstance();
        dlExpire.clear();
        dlExpire.set(2010,11,4);
        expire=dlExpire.getTime();
        dLicense=new DriverLicense("Zephyr Altair", "California", "CA293785320", expire);

        dlExpire.clear();
        dlExpire.set(2010,9,29);
        expire=dlExpire.getTime();
        cCard=new CreditCard("Z Altair","3941 9248 0293 5732",expire);

        customer = new CustomerInformation("Zephyr Altair", 6043924732L, dLicense, cCard);

        // get the current date only for the start date of the rental
        cDate.clear();
        cDate.set(cTime.YEAR,cTime.MONTH,cTime.DAY_OF_MONTH);
        startDate=cDate.getTime();

        // 1. rent an Avaiable vehicle
        try {
            testFleet.rentVehicle(vehicleId, customer, startMileage, startDate);
        } catch (BadInputException e) {
            fail ("this is a good rental");
        } catch (CanNotValidateCreditCardException e) {
            fail ("Can not get here validate always returns true");
        } catch (CanNotChangeStatusException e) {
            fail ("This is a good rental");
        }
        assertEquals(testFleet.getVehicle(vehicleId).getStatus(),VehicleStatus.RENTED);

        // 2. rent a vehicle not in the fleet
        vehicleId="NotInFleet";
        try {
            testFleet.rentVehicle(vehicleId, customer, startMileage, startDate);
            fail("Can not rent a vehicle not in the fleet");
        } catch (BadInputException e) {
           assertTrue(true);
        } catch (CanNotValidateCreditCardException e) {
            fail ("Can not get here validate always returns true");
        } catch (CanNotChangeStatusException e) {
            fail ("This should not throw this exception");
        }
        // assertEquals(testFleet.getVehicle(vehicleId).getStatus(),VehicleStatus.RENTED);

        // 3. rent a vehicle which is NotAvailable, Rented, Deleted
        vehicleId="TestVin1";
        VehicleStatus newStatus=VehicleStatus.UNAVAILABLE;
        try {
            testFleet.changeVehicleStatus(vehicleId, newStatus);
            testFleet.rentVehicle(vehicleId, customer, startMileage, startDate);
            fail("Can not rent a vehicle which is unavailable");
        } catch (BadInputException e) {
            assertTrue(true);
        } catch (CanNotValidateCreditCardException e) {
            fail ("Can not get here validate always returns true " +e);
        } catch (CanNotChangeStatusException e) {
            fail ("This should not throw this exception " + e);
        }
        assertEquals(testFleet.getVehicle(vehicleId).getStatus(),newStatus);

        vehicleId="TestVin3";
        newStatus=VehicleStatus.RENTED;
        try {
            testFleet.rentVehicle(vehicleId, customer, startMileage, startDate);
            fail("Can not rent a vehicle that is rented");
        } catch (BadInputException e) {
           assertTrue(true);
        } catch (CanNotValidateCreditCardException e) {
            fail ("Can not get here validate always returns true " +e);
        } catch (CanNotChangeStatusException e) {
            fail ("This should not throw this exception " +e);
        }
        assertEquals(testFleet.getVehicle(vehicleId).getStatus(), newStatus);

        vehicleId="TestVin7";
        newStatus=VehicleStatus.DELETED;
        try {
            testFleet.changeVehicleStatus(vehicleId, newStatus);
            testFleet.rentVehicle(vehicleId, customer, startMileage, startDate);
            fail("Can not rent a vehicle which is deleted");
        } catch (BadInputException e) {
            assertTrue(true);
        } catch (CanNotValidateCreditCardException e) {
            fail ("Can not get here validate always returns true " +e);
        } catch (CanNotChangeStatusException e) {
            fail ("This should not throw this exception " +e);
        }
        assertEquals(testFleet.getVehicle(vehicleId).getStatus(),newStatus);

        // 5. statrMileage = -1
         vehicleId="TestVin5";
         startMileage=-1.00;
        try {
            testFleet.rentVehicle(vehicleId, customer, startMileage, startDate);
            fail("Bad start Milage input");
        } catch (BadInputException e) {
            assertTrue(true);
        } catch (CanNotValidateCreditCardException e) {
            fail ("Can not get here validate always returns true " +e);
        } catch (CanNotChangeStatusException e) {
            fail ("This is a good rental " + e);
        }
        assertEquals(testFleet.getVehicle(vehicleId).getStatus(),VehicleStatus.AVAILABLE);

        // 6. startMileage = currentMileage, last vehicle in list
         vehicleId="TestVin8";
         startMileage=2300.00;
        try {
            testFleet.rentVehicle(vehicleId, customer, startMileage, startDate);
        } catch (BadInputException e) {
            fail ("Good Rental " +e);
        } catch (CanNotValidateCreditCardException e) {
            fail ("Can not get here validate always returns true " +e);
        } catch (CanNotChangeStatusException e) {
            fail ("This is a good rental " +e);
        }
        assertEquals(testFleet.getVehicle(vehicleId).getStatus(),VehicleStatus.RENTED);
        assertTrue(testFleet.getVehicle(vehicleId).getCurrentMilage()==startMileage);
        assertTrue(testFleet.getVehicle(vehicleId).getCurrentRental().getStartMileage()==startMileage);

        // 7. startMileage > currentMileage, first vehicle in list
         vehicleId="TestVin2";
         startMileage=2350.01;
        try {
            testFleet.rentVehicle(vehicleId, customer, startMileage, startDate);
        } catch (BadInputException e) {
            fail ("Good Rental " +e);
        } catch (CanNotValidateCreditCardException e) {
            fail ("Can not get here validate always returns true " +e);
        } catch (CanNotChangeStatusException e) {
            fail ("This is a good rental " +e);
        }
        assertEquals(testFleet.getVehicle(vehicleId).getStatus(),VehicleStatus.RENTED);
        assertTrue(testFleet.getVehicle(vehicleId).getCurrentMilage()==startMileage);
        assertTrue(testFleet.getVehicle(vehicleId).getCurrentRental().getStartMileage()==startMileage);


        // 8. startMileage < currentMileage
         vehicleId="TestVin5";
         startMileage=2000.48;
        try {
            testFleet.rentVehicle(vehicleId, customer, startMileage, startDate);
            fail("Bad start Milage input ");
        } catch (BadInputException e) {
            assertTrue(true);
        } catch (CanNotValidateCreditCardException e) {
            fail ("Can not get here validate always returns true " +e);
        } catch (CanNotChangeStatusException e) {
            fail ("This is a good rental " +e);
        }
        assertEquals(testFleet.getVehicle(vehicleId).getStatus(),VehicleStatus.AVAILABLE);

        System.out.println("Rental tests succeeded");

    }

    /**
     * Test of returnVehicleWithDamage method, of class VehicleFleet.
     */
    @Test
    public void testReturnVehicleWithDamage() {
        System.out.println("returnVehicleWithDamage");
        String vehicleId = "";
        Double currentMileage = null;
        Double gassAdded = null;
        CreditCard returnCreditCard = null;
        CostReport damageReport = null;
        VehicleFleet instance = new VehicleFleet();
   //     instance.returnVehicleWithDamage(vehicleId, currentMileage, gassAdded, returnCreditCard, damageReport);
        // TODO review the generated test code and remove the default call to fail.
  //     fail("The test case is a prototype.");

        // test cases
        // 1. good everything
        // 2. bad vechicleId
        // 3. vehicle not rented
        // 3a. returndate = null
        // 3b. reutrndate < rental startDate
        // 4. current Mileage < 0
        // 5. current Mileage < rental start Mileage
        // 6. gassAdded < 0
        // 7. no Credit Card supplied
        // 8. no Damage report supplied
        // 9. Good everything same day rental and return
        // 10. Good everything next day return
        // 11. Good everything > 1 month rental
        // 12. Good everything > 1 year rental

    }

    /**
     * Test of generateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testGenerateCreditCard() {
        System.out.println("generateCreditCard");
        VehicleFleet instance = new VehicleFleet();
        CreditCard expResult = null;
        CreditCard result = instance.generateCreditCard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateCustomer method, of class VehicleFleet.
     */
    @Test
    public void testGenerateCustomer() {
        System.out.println("generateCustomer");
        VehicleFleet instance = new VehicleFleet();
        CustomerInformation expResult = null;
        CustomerInformation result = instance.generateCustomer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnVehicle method, of class VehicleFleet.
     */
    @Test
    public void testReturnVehicle() {
        System.out.println("returnVehicle");
        VehicleFleet instance = new VehicleFleet();
        instance.returnVehicle();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicle method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicle() {
        System.out.println("getVehicle");

        // test a bad vehicle id
        String VehicleId = "junker";
        Vehicle expResult = null;
        Vehicle result = testFleet.getVehicle(VehicleId);
        assertEquals(expResult, result);

        // test the first vehicle id
        VehicleId="TestVin2";
        result = testFleet.getVehicle(VehicleId);
        assertEquals(VehicleId, result.getVehicleId());

        // test the last vehicle id
        VehicleId="TestVin1";
        result = testFleet.getVehicle(VehicleId);
        assertEquals(VehicleId, result.getVehicleId());

        // test the middle vehicle id
        VehicleId="TestVin4";
        result = testFleet.getVehicle(VehicleId);
        assertEquals(VehicleId, result.getVehicleId());

        System.out.println("Passed getVehicle Testing");
    }


    /**
     * Test of generateRevenueReport method, of class VehicleFleet.
     */
    @Test
    public void testGenerateRevenueReport() {
        System.out.println("generateRevenueReport");
        Date startDate = null;
        Date endDate = null;
        VehicleFleet instance = new VehicleFleet();
        String expResult = "";
        String result = instance.generateRevenueReport(startDate, endDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testChangeVehicleStatus() {
        System.out.println("changeVehicleStatus");

        // change status properly on first vehicle
        VehicleStatus newStatus=VehicleStatus.UNAVAILABLE;
        String vehicleId="TestVin2";
       try {
           testFleet.changeVehicleStatus(vehicleId,newStatus);
       } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }
        assertEquals(newStatus, testFleet.getVehicle(vehicleId).getStatus());

        // change status properly on last vehicle
        newStatus=VehicleStatus.RENTED;
        vehicleId="TestVin8";
       try {
           testFleet.changeVehicleStatus(vehicleId,newStatus);
       } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }
        assertEquals(newStatus, testFleet.getVehicle(vehicleId).getStatus());

        // change status on vehicle not in fleet
        newStatus=VehicleStatus.DELETED;
        vehicleId="NotInFleet";
       try {
           testFleet.changeVehicleStatus(vehicleId,newStatus);
           fail("changing status of vehicle not in fleet");
       } catch (CanNotChangeStatusException e) {
            fail ("Wrong exception caught");
        } catch (BadInputException e) {
            assertTrue(true);
        }

        // change status improperly on middle vehicle
        newStatus=VehicleStatus.RENTED;
        vehicleId="TestVin2";
       try {
           testFleet.changeVehicleStatus(vehicleId,newStatus);
           fail("Illegal status change");
       } catch (CanNotChangeStatusException e) {
            assertTrue(true);
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }
        assertEquals(VehicleStatus.UNAVAILABLE, testFleet.getVehicle(vehicleId).getStatus());

        // change status improperly on middle vehicle
        newStatus=VehicleStatus.UNAVAILABLE;
        vehicleId="TestVin8";
       try {
           testFleet.changeVehicleStatus(vehicleId, newStatus);
           testFleet.changeVehicleStatus(vehicleId, VehicleStatus.DELETED);
           assertEquals(VehicleStatus.DELETED, testFleet.getVehicle(vehicleId).getStatus());
           testFleet.changeVehicleStatus(vehicleId,newStatus);
           fail("Illegal status change");
       } catch (CanNotChangeStatusException e) {
            assertTrue(true);
        } catch (BadInputException e) {
            fail ("Wrong exception caught");
        }
        assertEquals(VehicleStatus.DELETED, testFleet.getVehicle(vehicleId).getStatus());

    }

    /**
     * Test of addVehicle method, of class VehicleFleet.
     */
    @Test
    public void testAddVehicle_3args() throws Exception {
        System.out.println("addVehicle");
        String vehicleId = "";
        VehicleType type = null;
        Double mileage = null;
        VehicleFleet instance = new VehicleFleet();
        instance.addVehicle(vehicleId, type, mileage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vehicleIdExists method, of class VehicleFleet.
     */
    @Test
    public void testVehicleIdExists_String() {
        System.out.println("vehicleIdExists");
        String vehicleId = "";
        VehicleFleet instance = new VehicleFleet();
        Boolean expResult = null;
        Boolean result = instance.vehicleIdExists(vehicleId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteVehicle method, of class VehicleFleet.
     */
    @Test
    public void testDeleteVehicle_String() throws Exception {
        System.out.println("deleteVehicle");
        String vehicleId = "";
        VehicleFleet instance = new VehicleFleet();
        instance.deleteVehicle(vehicleId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnVehicleFromService method, of class VehicleFleet.
     */
    @Test
    public void testReturnVehicleFromService_String_CostReport() throws Exception {
        System.out.println("returnVehicleFromService");
        String vehicleId = "";
        CostReport serviceReport = null;
        VehicleFleet instance = new VehicleFleet();
        instance.returnVehicleFromService(vehicleId, serviceReport);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateServiceReport method, of class VehicleFleet.
     */
    @Test
    public void testGenerateServiceReport_3args() throws Exception {
        System.out.println("generateServiceReport");
        Date returnDate = null;
        String serviceDescription = "";
        Double serviceCost = null;
        VehicleFleet instance = new VehicleFleet();
        CostReport expResult = null;
        CostReport result = instance.generateServiceReport(returnDate, serviceDescription, serviceCost);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGasRate method, of class VehicleFleet.
     */
    @Test
    public void testGetGasRate1() {
        System.out.println("getGasRate");
        VehicleFleet instance = new VehicleFleet();
        Double expResult = null;
        Double result = instance.getGasRate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMileageAllowance method, of class VehicleFleet.
     */
    @Test
    public void testGetMileageAllowance1() {
        System.out.println("getMileageAllowance");
        VehicleFleet instance = new VehicleFleet();
        Double expResult = null;
        Double result = instance.getMileageAllowance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMileageRate method, of class VehicleFleet.
     */
    @Test
    public void testGetMileageRate1() {
        System.out.println("getMileageRate");
        VehicleFleet instance = new VehicleFleet();
        Double expResult = null;
        Double result = instance.getMileageRate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicles method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicles1() {
        System.out.println("getVehicles");
        VehicleFleet instance = new VehicleFleet();
        ArrayList<Vehicle> expResult = null;
        ArrayList<Vehicle> result = instance.getVehicles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVehicles method, of class VehicleFleet.
     */
    @Test
    public void testSetVehicles() {
        System.out.println("setVehicles");
        ArrayList<Vehicle> val = null;
        VehicleFleet instance = new VehicleFleet();
        instance.setVehicles(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleByType method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicleByType_VehicleType() {
        System.out.println("getVehicleByType");
        String type = "Compact";
        String option="None";
        VehicleFleet instance = new VehicleFleet();
        ArrayList<Vehicle> expResult = null;
        ArrayList<Vehicle> result = instance.getVehicleByType(type,option);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleByAvalible method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicleByAvalible1() {
        System.out.println("getVehicleByAvalible");
        VehicleFleet instance = new VehicleFleet();
        ArrayList<Vehicle> expResult = null;
        ArrayList<Vehicle> result = instance.getVehicleByAvalible();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rentVehicle method, of class VehicleFleet.
     */
    @Test
    public void testRentVehicle_4args() throws Exception {
        System.out.println("rentVehicle");
        String VehicleId = "";
        CustomerInformation customer = null;
        double startMileage = 0.0;
        Date startDate = null;
        VehicleFleet instance = new VehicleFleet();
        instance.rentVehicle(VehicleId, customer, startMileage, startDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testValidateCreditCard() {
        System.out.println("validateCreditCard");
        CreditCard card = null;
        double value = 0.0;
        VehicleFleet instance = new VehicleFleet();
        Boolean expResult = null;
        Boolean result = instance.validateCreditCard(card, value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnVehicleWithDamage method, of class VehicleFleet.
     */
    @Test
    public void testReturnVehicleWithDamage_6args() throws Exception {
        System.out.println("returnVehicleWithDamage");
        String vehicleId = "";
        Date returnDate = null;
        Double currentMileage = null;
        Double gasAdded = null;
        CreditCard returnCreditCard = null;
        CostReport damageReport = null;
        VehicleFleet instance = new VehicleFleet();
        instance.returnVehicleWithDamage(vehicleId, returnDate, currentMileage, gasAdded, returnCreditCard, damageReport);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testGenerateCreditCard1() {
        System.out.println("generateCreditCard");
        VehicleFleet instance = new VehicleFleet();
        CreditCard expResult = null;
        CreditCard result = instance.generateCreditCard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateCustomer method, of class VehicleFleet.
     */
    @Test
    public void testGenerateCustomer1() {
        System.out.println("generateCustomer");
        VehicleFleet instance = new VehicleFleet();
        CustomerInformation expResult = null;
        CustomerInformation result = instance.generateCustomer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnVehicle method, of class VehicleFleet.
     */
    @Test
    public void testReturnVehicle1() {
        System.out.println("returnVehicle");
        VehicleFleet instance = new VehicleFleet();
        instance.returnVehicle();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicle method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicle_String() {
        System.out.println("getVehicle");
        String vehicleId = "";
        VehicleFleet instance = new VehicleFleet();
        Vehicle expResult = null;
        Vehicle result = instance.getVehicle(vehicleId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

  
    /**
     * Test of generateRevenueReport method, of class VehicleFleet.
     */
    @Test
    public void testGenerateRevenueReport_Date_Date() {
        System.out.println("generateRevenueReport");
        Date startDate = null;
        Date endDate = null;
        VehicleFleet instance = new VehicleFleet();
        String expResult = "";
        String result = instance.generateRevenueReport(startDate, endDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeVehicleStatus method, of class VehicleFleet.
     */
    @Test
    public void testChangeVehicleStatus_String_VehicleStatus() throws Exception {
        System.out.println("changeVehicleStatus");
        String vehicleId = "";
        VehicleStatus newStatus = null;
        VehicleFleet instance = new VehicleFleet();
        instance.changeVehicleStatus(vehicleId, newStatus);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDamageReport method, of class VehicleFleet.
     */
    @Test
    public void testAddDamageReport() throws Exception {
        System.out.println("addDamageReport");
        String vehicleId = "";
        CostReport damageReport = null;
        VehicleFleet instance = new VehicleFleet();
        instance.addDamageReport(vehicleId, damageReport);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class VehicleFleet.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        VehicleFleet.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unique method, of class VehicleFleet.
     */
    @Test
    public void testUnique() {
        System.out.println("unique");
        CustomerInformation testCustomer = null;
        VehicleFleet instance = new VehicleFleet();
        boolean expResult = false;
        boolean result = instance.unique(testCustomer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of corperateCardExists method, of class VehicleFleet.
     */
    @Test
    public void testCorperateCardExists() {
        System.out.println("corperateCardExists");
        String corperateName = "";
        String number = "";
        VehicleFleet instance = new VehicleFleet();
        boolean expResult = false;
        boolean result = instance.corperateCardExists(corperateName, number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vehicleTypeExists method, of class VehicleFleet.
     */
    @Test
    public void testVehicleTypeExists() {
        System.out.println("vehicleTypeExists");
        String vehicleType = "";
        String vehicleOption = "";
        VehicleFleet instance = new VehicleFleet();
        boolean expResult = false;
        boolean result = instance.vehicleTypeExists(vehicleType, vehicleOption);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCorperateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testAddCorperateCreditCard() throws Exception {
        System.out.println("addCorperateCreditCard");
        String corperateName = "Todo Corperate";
        String cardNumber = "1234 1234 1234 1234";
        Date expire = new GregorianCalendar(2010,03,01).getTime();
        VehicleFleet fleet = new VehicleFleet();
        // tests to be run
        // insert good card
        fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
        assertTrue(fleet.getCorperateCardList().size()==1);

        // reinsert the card (fails already present)
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==1);

        // insert 2 more good card
        cardNumber="2345 2345 2345 2345";
        corperateName="Todo Corperate";
        fleet.addCorperateCreditCard(corperateName, cardNumber, expire);

        cardNumber="3456 2345 2345 2345";
        corperateName="Todo Corperate";
        fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
        assertTrue(fleet.getCorperateCardList().size()==3);

        // reinsert the 3 already inserted card (fail each time)
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==3);

        cardNumber="2345 2345 2345 2345";
        corperateName="Todo Corperate";
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==3);


        // insert bad card - bad card number (not 16 digits when spaces are removed)
        // too few digits
        cardNumber="16";
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==3);

        // testing Credit cards for bad number of digits (too many)
        cardNumber="12345678901234567";
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==3);

        // 15 digits
        cardNumber = "1234 1234 2345 234 ";
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==3);
        // 16 digits but an extra non digit
        cardNumber = "1234 1234 2345 1234 a";
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==3);

        // 15 digits but an extra non digit
        cardNumber = "1234 1234 2345 234a";
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==3);

                // 15 digits but an extra non digit
        cardNumber = "1234 12*34 2345 234";
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==3);

                // 15 digits but an extra non digit
        cardNumber = "$1234 1234 2345 234";
        try {
            fleet.addCorperateCreditCard(corperateName, cardNumber, expire);
            fail("Readding first card should fail");
        } catch (BadInputException errorString) {
            // should hit here
        }
        assertTrue(fleet.getCorperateCardList().size()==3);

    }

    /**
     * Test of deleteCorperateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testDeleteCorperateCreditCard() throws Exception {
        System.out.println("deleteCorperateCreditCard");
        String corperateName = "";
        String cardNumber = "";
        VehicleFleet instance = new VehicleFleet();
        instance.deleteCorperateCreditCard(corperateName, cardNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCorperateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testGetCorperateCreditCard() throws Exception {
        System.out.println("getCorperateCreditCard");
        String corperateName = "";
        String cardNumber = "";
        String customerName = "";
        VehicleFleet instance = new VehicleFleet();
        CreditCard expResult = null;
        CreditCard result = instance.getCorperateCreditCard(corperateName, cardNumber, customerName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addVehicle method, of class VehicleFleet.
     */
    @Test
    public void testAddVehicle_3args_1() throws Exception {
        System.out.println("addVehicle");
        String vehicleId = "";
        VehicleType type = null;
        Double mileage = null;
        VehicleFleet instance = new VehicleFleet();
        instance.addVehicle(vehicleId, type, mileage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vehicleIdExists method, of class VehicleFleet.
     */
    @Test
    public void testVehicleIdExists_String_1args() {
        System.out.println("vehicleIdExists");
        String vehicleId = "";
        VehicleFleet instance = new VehicleFleet();
        Boolean expResult = null;
        Boolean result = instance.vehicleIdExists(vehicleId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteVehicle method, of class VehicleFleet.
     */
    @Test
    public void testDeleteVehicle_String_1args() throws Exception {
        System.out.println("deleteVehicle");
        String vehicleId = "";
        VehicleFleet instance = new VehicleFleet();
        instance.deleteVehicle(vehicleId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnVehicleFromService method, of class VehicleFleet.
     */
    @Test
    public void testReturnVehicleFromService_String_CostReport_2args() throws Exception {
        System.out.println("returnVehicleFromService");
        String vehicleId = "";
        CostReport serviceReport = null;
        VehicleFleet instance = new VehicleFleet();
        instance.returnVehicleFromService(vehicleId, serviceReport);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateServiceReport method, of class VehicleFleet.
     */
    @Test
    public void testGenerateServiceReport_3args_1() throws Exception {
        System.out.println("generateServiceReport");
        Date returnDate = null;
        String serviceDescription = "";
        Double serviceCost = null;
        VehicleFleet instance = new VehicleFleet();
        CostReport expResult = null;
        CostReport result = instance.generateServiceReport(returnDate, serviceDescription, serviceCost);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGasRate method, of class VehicleFleet.
     */
    @Test
    public void testGetGasRate2() {
        System.out.println("getGasRate");
        VehicleFleet instance = new VehicleFleet();
        Double expResult = null;
        Double result = instance.getGasRate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMileageAllowance method, of class VehicleFleet.
     */
    @Test
    public void testGetMileageAllowance2() {
        System.out.println("getMileageAllowance");
        VehicleFleet instance = new VehicleFleet();
        Double expResult = null;
        Double result = instance.getMileageAllowance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMileageRate method, of class VehicleFleet.
     */
    @Test
    public void testGetMileageRate2() {
        System.out.println("getMileageRate");
        VehicleFleet instance = new VehicleFleet();
        Double expResult = null;
        Double result = instance.getMileageRate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicles method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicles2() {
        System.out.println("getVehicles");
        VehicleFleet instance = new VehicleFleet();
        ArrayList<Vehicle> expResult = null;
        ArrayList<Vehicle> result = instance.getVehicles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setVehicles method, of class VehicleFleet.
     */
    @Test
    public void testSetVehicles_ArrayList() {
        System.out.println("setVehicles");
        ArrayList<Vehicle> val = null;
        VehicleFleet instance = new VehicleFleet();
        instance.setVehicles(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCorperateCardList method, of class VehicleFleet.
     */
    @Test
    public void testGetCorperateCardList() {
        System.out.println("getCorperateCardList");
        VehicleFleet instance = new VehicleFleet();
        ArrayList<CreditCard> expResult = null;
        ArrayList<CreditCard> result = instance.getCorperateCardList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleByType method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicleByType_VehicleType_1args() {
        System.out.println("getVehicleByType");
        String type = "Compact";
        String option = "None";
        VehicleFleet instance = new VehicleFleet();
        ArrayList<Vehicle> expResult = null;
        ArrayList<Vehicle> result = instance.getVehicleByType(type,option);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicleByAvalible method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicleByAvalible2() {
        System.out.println("getVehicleByAvalible");
        VehicleFleet instance = new VehicleFleet();
        ArrayList<Vehicle> expResult = null;
        ArrayList<Vehicle> result = instance.getVehicleByAvalible();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rentVehicle method, of class VehicleFleet.
     */
    @Test
    public void testRentVehicle_4args_1() throws Exception {
        System.out.println("rentVehicle");
        String VehicleId = "";
        CustomerInformation customer = null;
        double startMileage = 0.0;
        Date startDate = null;
        VehicleFleet instance = new VehicleFleet();
        instance.rentVehicle(VehicleId, customer, startMileage, startDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testValidateCreditCard_CreditCard_double() {
        System.out.println("validateCreditCard");
        CreditCard card = null;
        double value = 0.0;
        VehicleFleet instance = new VehicleFleet();
        Boolean expResult = null;
        Boolean result = instance.validateCreditCard(card, value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnVehicleWithDamage method, of class VehicleFleet.
     */
    @Test
    public void testReturnVehicleWithDamage_6args_1() throws Exception {
        System.out.println("returnVehicleWithDamage");
        String vehicleId = "";
        Date returnDate = null;
        Double currentMileage = null;
        Double gasAdded = null;
        CreditCard returnCreditCard = null;
        CostReport damageReport = null;
        VehicleFleet instance = new VehicleFleet();
        instance.returnVehicleWithDamage(vehicleId, returnDate, currentMileage, gasAdded, returnCreditCard, damageReport);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testGenerateCreditCard2() {
        System.out.println("generateCreditCard");
        VehicleFleet instance = new VehicleFleet();
        CreditCard expResult = null;
        CreditCard result = instance.generateCreditCard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateCustomer method, of class VehicleFleet.
     */
    @Test
    public void testGenerateCustomer2() {
        System.out.println("generateCustomer");
        VehicleFleet instance = new VehicleFleet();
        CustomerInformation expResult = null;
        CustomerInformation result = instance.generateCustomer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnVehicle method, of class VehicleFleet.
     */
    @Test
    public void testReturnVehicle2() {
        System.out.println("returnVehicle");
        VehicleFleet instance = new VehicleFleet();
        instance.returnVehicle();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVehicle method, of class VehicleFleet.
     */
    @Test
    public void testGetVehicle_String_1args() {
        System.out.println("getVehicle");
        String vehicleId = "";
        VehicleFleet instance = new VehicleFleet();
        Vehicle expResult = null;
        Vehicle result = instance.getVehicle(vehicleId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of generateRevenueReport method, of class VehicleFleet.
     */
    @Test
    public void testGenerateRevenueReport_Date_Date_2args() {
        System.out.println("generateRevenueReport");
        Date startDate = null;
        Date endDate = null;
        VehicleFleet instance = new VehicleFleet();
        String expResult = "";
        String result = instance.generateRevenueReport(startDate, endDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeVehicleStatus method, of class VehicleFleet.
     */
    @Test
    public void testChangeVehicleStatus_String_VehicleStatus_2args() throws Exception {
        System.out.println("changeVehicleStatus");
        String vehicleId = "";
        VehicleStatus newStatus = null;
        VehicleFleet instance = new VehicleFleet();
        instance.changeVehicleStatus(vehicleId, newStatus);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDamageReport method, of class VehicleFleet.
     */
    @Test
    public void testAddDamageReport_String_CostReport() throws Exception {
        System.out.println("addDamageReport");
        String vehicleId = "";
        CostReport damageReport = null;
        VehicleFleet instance = new VehicleFleet();
        instance.addDamageReport(vehicleId, damageReport);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class VehicleFleet.
     */
    @Test
    public void testMain_StringArr() throws Exception {
        System.out.println("main");
        String[] args = null;
        VehicleFleet.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unique method, of class VehicleFleet.
     */
    @Test
    public void testUnique_CustomerInformation() {
        System.out.println("unique");
        CustomerInformation testCustomer = null;
        VehicleFleet instance = new VehicleFleet();
        boolean expResult = false;
        boolean result = instance.unique(testCustomer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of corperateCardExists method, of class VehicleFleet.
     */
    @Test
    public void testCorperateCardExists_String_String() {
        System.out.println("corperateCardExists");
        String corperateName = "";
        String number = "";
        VehicleFleet instance = new VehicleFleet();
        boolean expResult = false;
        boolean result = instance.corperateCardExists(corperateName, number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vehicleTypeExists method, of class VehicleFleet.
     */
    @Test
    public void testVehicleTypeExists_String_String() {
        System.out.println("vehicleTypeExists");
        String vehicleType = "";
        String vehicleOption = "";
        VehicleFleet instance = new VehicleFleet();
        boolean expResult = false;
        boolean result = instance.vehicleTypeExists(vehicleType, vehicleOption);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCorperateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testAddCorperateCreditCard_3args() throws Exception {
        System.out.println("addCorperateCreditCard");
        String corperateName = "";
        String cardNumber = "";
        Date expire = null;
        VehicleFleet instance = new VehicleFleet();
        instance.addCorperateCreditCard(corperateName, cardNumber, expire);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of corperateCardNumberExists method, of class VehicleFleet.
     */
    @Test
    public void testCorperateCardNumberExists() {
        System.out.println("corperateCardNumberExists");
        String cardNumber = "";
        VehicleFleet instance = new VehicleFleet();
        boolean expResult = false;
        boolean result = instance.corperateCardNumberExists(cardNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCorperateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testDeleteCorperateCreditCard_String_String() throws Exception {
        System.out.println("deleteCorperateCreditCard");
        String corperateName = "";
        String cardNumber = "";
        VehicleFleet instance = new VehicleFleet();
        instance.deleteCorperateCreditCard(corperateName, cardNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCorperateCreditCard method, of class VehicleFleet.
     */
    @Test
    public void testGetCorperateCreditCard_3args() throws Exception {
        System.out.println("getCorperateCreditCard");
        String corperateName = "";
        String cardNumber = "";
        String customerName = "";
        VehicleFleet instance = new VehicleFleet();
        CreditCard expResult = null;
        CreditCard result = instance.getCorperateCreditCard(corperateName, cardNumber, customerName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addVehicleType method, of class VehicleFleet.
     */
    @Test
    public void testAddVehicleType() throws Exception {
        System.out.println("addVehicleType");
        String type = "";
        String option = "";
        double rate = 0.0;
        VehicleFleet instance = new VehicleFleet();
        instance.addVehicleType(type, option, rate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testMonthlyReports() throws Exception {
        System.out.println("Testing getMonthlyVehicle, getMonthlyRevenue and the save methods");

        int month=1;
        int year=2009;
        String vin="";

    }
}