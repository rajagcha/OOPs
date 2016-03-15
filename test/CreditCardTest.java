/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class CreditCardTest {

    public CreditCardTest() {
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
     * Test of getExpire method, of class CreditCard.
     */
    @Test
    public void testGetExpire() {
        System.out.println("getExpire");
        CreditCard instance = new CreditCard();
        Date expResult = null;
        Date result = instance.getExpire();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class CreditCard.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        CreditCard instance = new CreditCard();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumber method, of class CreditCard.
     */
    @Test
    public void testGetNumber() {
        System.out.println("getNumber");
        CreditCard instance = new CreditCard();
        String expResult = "";
        String result = instance.getNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateCreditCard method, of class CreditCard.
     */
    @Test
    public void testValidateCreditCard() {
        System.out.println("validateCreditCard");
        double charge = 0.0;
        CreditCard instance = new CreditCard();
        Boolean expResult = null;
        Boolean result = instance.validateCreditCard(charge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExpire method, of class CreditCard.
     */
    @Test
    public void testSetExpire() {
        System.out.println("setExpire");
        Date val = null;
        CreditCard instance = new CreditCard();
        instance.setExpire(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class CreditCard.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String val = "";
        CreditCard instance = new CreditCard();
        instance.setName(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumber method, of class CreditCard.
     */
    @Test
    public void testSetNumber() {
        System.out.println("setNumber");
        String val = "1234567890123456";
        CreditCard instance = new CreditCard();
        instance.setNumber(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class CreditCard.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        CreditCard instance = new CreditCard();
        String expResult = "1234 5678 9012 3456";
        String result = instance.toString();
        assertEquals(expResult, result);

    }

    /**
     * Test of getExpire method, of class CreditCard.
     */
    @Test
    public void testGetExpire1() {
        System.out.println("getExpire");
        CreditCard instance = new CreditCard();
        Date expResult = null;
        Date result = instance.getExpire();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class CreditCard.
     */
    @Test
    public void testGetName1() {
        System.out.println("getName");
        CreditCard instance = new CreditCard();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumber method, of class CreditCard.
     */
    @Test
    public void testGetNumber1() {
        System.out.println("getNumber");
        CreditCard instance = new CreditCard();
        String expResult = "";
        String result = instance.getNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validateCreditCard method, of class CreditCard.
     */
    @Test
    public void testValidateCreditCard_double() {
        System.out.println("validateCreditCard");
        double charge = 0.0;
        CreditCard instance = new CreditCard();
        Boolean expResult = null;
        Boolean result = instance.validateCreditCard(charge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setExpire method, of class CreditCard.
     */
    @Test
    public void testSetExpire_Date() {
        System.out.println("setExpire");
        Date val = null;
        CreditCard instance = new CreditCard();
        instance.setExpire(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class CreditCard.
     */
    @Test
    public void testSetName_String() {
        System.out.println("setName");
        String val = "";
        CreditCard instance = new CreditCard();
        instance.setName(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumber method, of class CreditCard.
     */
    @Test
    public void testSetNumber_String() {
        System.out.println("setNumber");
        String val = "";
        CreditCard instance = new CreditCard();
        instance.setNumber(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of validCardBumber method, of class CreditCard.
     */
    @Test
    public void testValidCardBumber() {
        System.out.println("validCardBumber");
        String cardNumber = "";
        String expResult = "";
        String result = CreditCard.validCardNumber(cardNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of formatCreditCardNumber method, of class CreditCard.
     */
    @Test
    public void testFormatCreditCardNumber() {
        System.out.println("formatCreditCardNumber");
        String cardNumber = "1234567890123456";
        CreditCard instance = new CreditCard();
        String expResult = "1234 5678 9012 3456";
        String result = instance.formatCreditCardNumber(cardNumber);
        assertEquals(expResult, result);

        // test bad cards
        cardNumber="";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "3";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "a";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "12345678901234567";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "#234567890123456";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "1234,67890123456";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "123456789012345a";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "1234567890123456a";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = ";234 1234 1234 1234";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "1234 1234 1234 12345";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "1234 1234 12r4 1234";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "1234 1234 123. 1234";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "1234 1324 1234 123f";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        cardNumber = "1234 1234 1234 1234|";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(null, result);

        // a last good one
        cardNumber = "1234 1234 1234 1234";
        result=instance.formatCreditCardNumber(cardNumber);
        assertEquals(cardNumber, result);
    }

    /**
     * Test of toString method, of class CreditCard.
     */
    @Test
    public void testToString1() {
        System.out.println("toString");
        CreditCard instance = new CreditCard();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}