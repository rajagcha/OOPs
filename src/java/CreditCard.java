import java.util.Date;
import javax.persistence.*;
import java.io.*;
import java.text.DateFormat;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.46001F4B-C850-C372-AE51-E34CBFEBD9EA]
// </editor-fold>
@Embeddable
public class CreditCard implements Serializable {

    private long hid;
    /**
     *  <p style="margin-top: 0">
     *        The name of the driver of the rental car
     *      </p>
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.8601FE9E-D3F8-0539-DC4D-6D46EE4F7381]
    // </editor-fold>
    @Column(nullable=false)
    private String name;

    /**
     *  <p style="margin-top: 0">
     *        The identification code on the license
     *      </p>
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.33814C52-ACFD-4DC3-732B-548E367EBDBE]
    // </editor-fold>
    @Column(nullable=false)
    private String number;

    /**
     *  <p style="margin-top: 0">
     *        The expiration date of the license
     *      </p>
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7EC66258-34E1-EBE9-6465-C21221D16271]
    // </editor-fold> 
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    private Date expire;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.41273B55-E043-6A3B-C84B-BEF8A73BBED4]
    // </editor-fold> 
    protected CreditCard () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.89B16797-7DE0-2D54-5C2F-EF3AE39E20D3]
    // </editor-fold> 
    public Date getExpire () {
        return expire;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.A34F88D7-7662-9722-BAA0-C07EA09496E1]
    // </editor-fold> 
    public String getName () {
        return name;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.B0DBA016-ED1A-29A0-E007-9C102F502826]
    // </editor-fold> 
    public String getNumber () {
        return number;
    }

    public long getDbId() {
        return hid;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1C581296-A4FA-DAD8-3DAC-F0F4FD932538]
    // </editor-fold> 
    public CreditCard (String name, String number, Date expire) 
    throws BadInputException {
 
        String tempNumber=validCardNumber(number);
        // ensure that there is a name
        if (name == null || name.length() <2)
            throw new BadInputException ("Customer name need to exist and be longer than 2 characters");


        if (tempNumber==null)
            throw new BadInputException("Card number is not 16 digits");

        this.name = name;
        this.number=tempNumber;
        this.expire=expire;

        this.hid=-1l;
    }

    public CreditCard (String name, String number, Date expire, long dbId)
            throws BadInputException {
        this (name,number,expire);
        this.hid=dbId;
    }

    public void setDbId (long dbId) {
        this.hid=dbId;
    }

    /**
     *  <p style="margin-top: 0">
     *        Determines if the driver license is valid by contacting the outside 
     *        database with all the information in the class
     *      </p>
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.70D81468-D641-1021-3330-2C5DACCE3296]
    // </editor-fold> 
    public Boolean validateCreditCard (double charge) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5056CB74-20C3-FD24-AD05-B8C1D030BF2D]
    // </editor-fold> 
    protected void setExpire (Date val) {
        this.expire = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.6BDEB25B-54E0-C623-ABC0-A63138B136E7]
    // </editor-fold> 
    protected void setName (String val) {
        this.name = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.3C767B16-E926-37C1-21D5-98F0A7F2EF46]
    // </editor-fold> 
    protected void setNumber (String val) {
        this.number = val;
    }


    /**
     * Converts a credit card number to a String of 16 digits
     * if there is not 16 digits or the string contains non blank or digits returns null
     * @param cardNumber  - the string containing the potential credit card number
     * @return - a string of 16 digits (\d{16}) or null if the input is not a valid credit card number
     */
    public static String validCardNumber(String cardNumber) {
        String retCard=null;
        String workString=new String(cardNumber.replaceAll("\\s", ""));

        // remove all blank characters

        // if there are non digit characters fail or the length is not 16 characters
        if (!workString.matches("\\d{16}"))
            return retCard;
            if (workString.length()!=16)
            return retCard;

        retCard=workString;

        return retCard;
    }

    /**
     * expects an input of 16 digits (\d{16}) and outputs it in the human readable format
     * of 4 digits followed by a space
     * for examp 1234567890123456 -> 1234 5678 9012 3456
     * @param cardNumber a string of 16 digit
     * @return the formated output if the input is invalid null
     */
    public static String  formatCreditCardNumber (String cardNumber) {
        String retNumber=validCardNumber(cardNumber);

        if (retNumber==null) return retNumber;

        return retNumber.replaceAll("(\\d{4})(\\d{4})(\\d{4})(\\d{4})", "$1 $2 $3 $4");

    }

    @Override
    public String toString() {
        DateFormat dfmt=DateFormat.getDateInstance(DateFormat.MEDIUM);
        StringBuilder outS= new StringBuilder("Name " + name+ "Number " + formatCreditCardNumber(number)
                + "exp " + dfmt.format(expire));
        return outS.toString();
    }

}

