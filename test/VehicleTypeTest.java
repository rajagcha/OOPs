/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class VehicleTypeTest {

    public VehicleTypeTest() {
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
     * Test of getRate method, of class VehicleType.
     */
    @Test
    public void testGetRate() {
        System.out.println("getRate");
        VehicleType instance = null;
        double expResult = 0.0;
        double result = instance.getRate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class VehicleType.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        VehicleType instance = new VehicleType("car","dog",10.00d);
        String expResult = "car";
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOption method, of class VehicleType.
     */
    @Test
    public void testGetOption() {
        System.out.println("getOption");
        VehicleType instance = null;
        String expResult = "";
        String result = instance.getOption();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class VehicleType.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        VehicleType instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of typeEquals method, of class VehicleType.
     */
    @Test
    public void testTypeEquals() {
        System.out.println("typeEquals");
        String type = "";
        String option = "";
        VehicleType instance = null;
        boolean expResult = false;
        boolean result = instance.typeEquals(type, option);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}