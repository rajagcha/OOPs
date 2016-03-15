import java.util.Date;
import javax.persistence.*;
import java.io.*;
import java.text.DateFormat;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.9E2F26BF-A4DB-B5A3-FDF2-498B4DFF7032]
// </editor-fold>
@Embeddable
public class Rental implements Serializable {


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9611AC39-BC68-E644-F138-26EDFB786C2B]
    // </editor-fold> 
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    private Date startDate;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BBEFD378-83EE-77E6-52A5-6A8E752962E5]
    // </editor-fold>
    @Column(nullable=false)
    private Double startMileage;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.936FA55B-81CA-9C2D-BC67-84E355D635B5]
    // </editor-fold>
    @Column(nullable=false)
    private CustomerInformation customer;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7C9BF5DE-3C54-646D-7ABC-D9140A93137E]
    // </editor-fold>
    @Column(nullable=false)
    private Double Deposit;

    private long hId;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.6C9BB892-03E7-76BE-25E9-8E592922A5AD]
    // </editor-fold> 
    protected Rental () {
    }

    /**
     * This is the constructor of rental.
     * Checks that the startMileage and deposit must be greater than zero
     * Checks that there is a customer is attached
     *
     * @param startDate    - the first day of the rental
     * @param startMileage - the mileage of the vehicle before the rental starts
     * @param customer     - the customer who is renting the vehicle
     * @param deposit      - the deposit that ehe customer put down on the vehicle
     * @throws BadInputException - Thrown if an input parameter is bad
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2D449585-71B7-8BCD-AC26-17D7D24750EE]
    // </editor-fold> 
    public Rental (Date startDate, Double startMileage, CustomerInformation customer, Double deposit)
           throws BadInputException {

        // ensure that the deposit is greater than $0
        if (deposit < 0)
            throw new BadInputException("The deposite must be greater than zero :"+deposit);

        // ensure that the startMileage is greater than 0
        if (startMileage < 0 )
            throw new BadInputException("Start Mileage must be greater than zero :"+ startMileage);

        // ensuret that the customer is a legal costumer
        if (customer == null)
            throw new BadInputException("No customer given a customer must be attached to the Rental");

        this.startDate = startDate;
        this.customer = customer;
        this.startMileage=startMileage;
        this.Deposit=deposit;
        this.hId=-1L;
    }

    /**
     * database constructor
     * @param startDate
     * @param startMileage
     * @param customer
     * @param deposit
     * @param hId
     * @throws BadInputException
     */
    public Rental (Date startDate, double startMileage, CustomerInformation customer, Double deposit,
            long hId)   throws BadInputException {
        this(startDate,startMileage, customer, deposit);
        this.hId=hId;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.61EAC97D-6227-9216-BF58-3DF83A758634]
    // </editor-fold> 
    public Double getDeposit () {
        return Deposit;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.98E26793-BDD4-53EF-EEE4-0F81974985FB]
    // </editor-fold> 
    protected void setDeposit (Double val) {
        this.Deposit = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.B6537B9A-BD30-0F13-409A-8878F5A41B2F]
    // </editor-fold> 
    public CustomerInformation getCustomer () {
        return customer;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.53DE60D5-1069-70D6-021D-ACCF6912737D]
    // </editor-fold> 
    protected void setCustomer (CustomerInformation val) {
        this.customer = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.87F07B2F-F42B-42F2-A6B8-6831481F8E87]
    // </editor-fold> 
    public Date getStartDate () {
        return startDate;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.40DC3A92-4C9D-6D28-4C1C-D97E47B05CEB]
    // </editor-fold> 
    protected void setStartDate (Date val) {
        this.startDate = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.B0323036-D1AE-A7C0-247F-344308A5A818]
    // </editor-fold> 
    public Double getStartMileage () {
        return startMileage;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5EDD3DDC-6AE0-B90D-10F8-1222EB1B6B92]
    // </editor-fold> 
    protected void setStartMileage (Double val) {
        this.startMileage = val;
    }

    // meant only for the database to use
    public void setDbId(long id) {
        this.hId=id;
    }
    public long getDbId () {
        return hId;
    }

    public String toString() {
        DateFormat dfmt=DateFormat.getDateInstance(DateFormat.MEDIUM);
        StringBuilder outS=new StringBuilder ("Name : " + customer.getName() +" Phone : "
                + customer.getPhoneNumber() +"\n");
        outS.append("Date : "+ dfmt.format(startDate) +" Miles : " +String.format("%8.2f", startMileage)+"\n");
        outS.append("Credit Card : " + customer.getCreditCard().toString()+"\n");
        outS.append("Drivers License : " + customer.getDriversLicense().toString()+"\n");

        return outS.toString();
    }


}

