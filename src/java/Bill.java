import java.util.Date;
import javax.persistence.*;
import java.io.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Currency;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.29BD9BCB-C638-46E6-7DC4-D867D8518D61]
// </editor-fold>
@Entity
public class Bill implements Serializable {
    
    @Id
    @GeneratedValue
    private Long hId;                     // the object id from the database
    @ManyToOne
    @JoinColumn(name="vehicleHid")
    private Vehicle vehicle;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E320CBD1-C8B9-285B-3705-D013569A95DA]
    // </editor-fold>
    @Column(nullable=false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4AEFE902-7E26-38C5-A26B-B4D63F338B99]
    // </editor-fold>
    @Column(nullable=false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.50F60B6B-750B-8AA7-480A-CFE2CA4ABEA5]
    // </editor-fold>
    @Column(nullable=false)
    private Double startMileage;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3E88788E-973E-BEE5-D59D-D2397D626862]
    // </editor-fold>
    @Column(nullable=false)
    private Double endMileage;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.27149722-9087-8BBA-DDAF-711FC8913F62]
    // </editor-fold>
    @Column(nullable=false)
    private Double gasAdded;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2649CEF6-8903-98B9-C442-8FB910901F82]
    // </editor-fold>
    @Column(nullable=false)
    private Double deposit;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.EA678A2E-BE62-9ADB-C4BB-2EBEC919817F]
    // </editor-fold>
    @Column(nullable=false)
    private Double damageCost;

    @Column(nullable=false)
    private Double dailyRate;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CE0D4FDE-8E11-B3E9-AA46-B8ED5B4D3664]
    // </editor-fold>
    @Column(nullable=false)
    private Double totalFee;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7E8C7F13-7ED9-D8B0-19EC-5B9B2DFE6846]
    // </editor-fold> 
    protected Bill () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.53BB29A3-7284-72B8-EFFE-020DC07C0124]
    // </editor-fold> 
    public Bill (Rental currentRental, Date endDate, Double endMileage, Double gasAdded,
            Double damageCharge, double dailyRate) throws BadInputException {

        // ensure that the currentRental is there
        if (currentRental == null)
            throw new BadInputException("Need a current Rental record");

        // confirm start date from bill is before or equal to the end date
        if (endDate.before(currentRental.getStartDate()))
            throw new BadInputException("Rental end Date must be same as or before start Date");

        // confirm that the start mileage is less than the end mileage
        if (currentRental.getStartMileage() > endMileage)
            throw new BadInputException("Start Mileage must be less than the end Mileage");

        // confirm that the gasAdded is 0 or greater
        if (gasAdded <0) throw new BadInputException("Gas Added must be 0 or greater");

        // confirm damageCharges is 0 or greater
        if (damageCharge < 0) throw new BadInputException("Damage Cost muust be 0 or greater");

        // make sure dailyRate is > 0
        if (dailyRate <= 0) throw new BadInputException("Daily Rate needs to be greater than 0 ");

       // fill in the values
        this.startDate = currentRental.getStartDate();
        this.startMileage = currentRental.getStartMileage();
        this.deposit = currentRental.getDeposit();
        this.damageCost=damageCharge;
        this.endDate=endDate;
        this.endMileage=endMileage;
        this.gasAdded=gasAdded;
        this.dailyRate=dailyRate;
        this.totalFee=0.0;
        this.hId=-1L;
    }
    
 /**
  * Database constructor
  * @param startDate
  * @param endDate
  * @param startMileage
  * @param endMileage
  * @param gasAdded
  * @param deposit
  * @param damageCharge
  * @param totalCost
  * @param hId
  */
    public  Bill (Date startDate, Date endDate, Double startMileage, Double endMileage, Double gasAdded,
            double deposit,Double damageCharge, double totalCost, long hId)  {
        this.startDate = startDate;
        this.startMileage = startMileage;
        this.deposit = deposit;
        this.damageCost=damageCharge;
        this.endDate=endDate;
        this.endMileage=endMileage;
        this.gasAdded=gasAdded;
        this.dailyRate=-1.0;
        this.totalFee=totalCost;
        this.hId=hId;
    }
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.D1A439AE-0507-5708-A128-A4571FCC7B2F]
    // </editor-fold> 
    public Double getDamageCost () {
        return damageCost;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5C52DACD-2F70-F859-20D2-93B4B70DAEC9]
    // </editor-fold> 
    protected void setDamageCost (Double val) {
        this.damageCost = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.2CFCE5E5-8122-12F0-B952-68E941484232]
    // </editor-fold> 
    public Double getDeposit () {
        return deposit;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.D5CE6250-08F5-3530-9663-2D8C8C41C369]
    // </editor-fold> 
    protected void setDeposit (Double val) {
        this.deposit = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.9A6FDDE9-3E90-1F10-F265-0E3110C28F10]
    // </editor-fold> 
    public Date getEndDate () {
        return endDate;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7357F3A5-EEBF-9F87-52CB-18954AAF82AC]
    // </editor-fold> 
    protected void setEndDate (Date val) {
        this.endDate = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.F57AEE70-25E6-0C6B-7D14-701D946ED987]
    // </editor-fold> 
    public Double getEndMileage () {
        return endMileage;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BD17DC37-3B39-BF91-ED4F-1A0B0D1D94E4]
    // </editor-fold> 
    protected void setEndMileage (Double val) {
        this.endMileage = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.D48EB76E-689E-5C3F-E18C-1126AEA3DBB6]
    // </editor-fold> 
    public Double getGasAdded () {
        return gasAdded;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DDC5400A-4D3B-0757-718A-00468BA52DA9]
    // </editor-fold> 
    protected void setGasAdded (Double val) {
        this.gasAdded = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.4960F5AE-904D-8C6D-C6B1-9000220BB6CB]
    // </editor-fold> 
    public Date getStartDate () {
        return startDate;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5ED79C5E-DA72-0E94-0C08-3AE77A0757C9]
    // </editor-fold> 
    protected void setStartDate (Date val) {
        this.startDate = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.6B8518F4-D5A2-0B39-7ACC-030C66E6BF58]
    // </editor-fold> 
    public Double getStartMileage () {
        return startMileage;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C65D35C0-DEEA-9C57-B8DD-0BBC519B47BB]
    // </editor-fold> 
    protected void setStartMileage (Double val) {
        this.startMileage = val;
    }

    public Double getDailyRate () {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate=dailyRate;
    }

    // meant only for the database to use
    public void setDbId(long id) {
        this.hId=id;
    }
    public long getDbId () {
        return hId;
    }


    /**
     * Calculates the total charge for a rental.
     * (number of rental days +1 ) *rental rate = rental charge
     * (miles traveled - (number of rental day * daily miles allowed )) * mileage rate = mileage charge
     * gas to fill tank * gasCost = gas charge
     *
     * total fee = gas charge + mileage charge + rental charge
     *
     * @param daileyMilesAllowed   The number of free miles before being charged
     * @param mileageSurcharge     The rate miles are charged at after daileyMilesAllowed
     * @param gasCost              Cost of gas per gallon which filled the gas tank upon return
     * @return the total fee for the rental
     */
    private double calculateFee(Double daileyMilesAllowed, Double mileageSurcharge, Double gasCost) {

        // the rate is chraged for the first day so need the plus one
        long daysRented=DateMath.daysDifferent(startDate, endDate)+1L;
        System.out.println("days rented " + daysRented);
        double rentalCharge = (daysRented) * dailyRate;
        System.out.println("rental " + rentalCharge);
        double milesTravelled=endMileage-startMileage;
        System.out.println("miles traveled " +milesTravelled);
        double mileageCharge=(milesTravelled- daileyMilesAllowed*daysRented)* mileageSurcharge;
        System.out.println("mileage charge " + mileageCharge);
        double gasCharge=gasAdded*gasCost;
        System.out.println("gas charge " + gasCharge);

        if (mileageCharge <0 ) mileageCharge=0;
        System.out.println("mileage charge " + mileageCharge);

        totalFee=rentalCharge+mileageCharge+gasCharge+damageCost;

        return totalFee;
    }

        /**
     * Calculates the amount owed for a rental.
         * owed=totalFee-deposit
     *
     * @param daileyMilesAllowed   The number of free miles before being charged
     * @param mileageSurcharge     The rate miles are charged at after daileyMilesAllowed
     * @param gasCost              Cost of gas per gallon which filled the gas tank upon return
     * @return the total fee for the rental
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.08730A62-853F-7D5F-F157-3BE064168DDF]
    // </editor-fold> 
    public Double calculateChargeOwed (Double daileyMilesAllowed, Double mileageSurcharge, Double gasCost) {
        double owed=calculateFee(daileyMilesAllowed, mileageSurcharge, gasCost)- deposit;

        return owed;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.AC63AD56-3093-9F74-4C58-A992DA498A4F]
    // </editor-fold> 
    public Double calculateRevenue (Double dailyMilesAllowed, Double mileageSurcharge, Double gasCost) {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.CCF8AFEE-A284-BB5B-9FC1-74DBDD68436B]
    // </editor-fold> 
    public Double getTotalFee () {
        return totalFee;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.9D058996-76F6-5482-B2FA-BAE55F24B2D7]
    // </editor-fold> 
    public void setTotalFee (Double val) {
        this.totalFee = val;
    }

    public double getTotalMiles() {
        return this.endMileage-this.startMileage;
    }

    public double getDaysRented() {
        return DateMath.daysDifferent(startDate, endDate);
    }

    @Override
    public String toString () {
        NumberFormat cfmt=NumberFormat.getCurrencyInstance();
        DateFormat dfmt = DateFormat.getDateInstance(DateFormat.MEDIUM);

        StringBuilder outS=new StringBuilder("Begin : " + dfmt.format(startDate) +" # OF Days :" );
        outS.append(String.format("%3.0f",getDaysRented()) + " # of Miles :" );
        outS.append(String.format("%8.2f",getTotalMiles()) + " Fee :" + cfmt.format(totalFee));

        return outS.toString();
    }

}

