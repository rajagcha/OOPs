import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;
import java.io.*;
import java.util.Collections;
import java.util.Calendar;
import java.util.GregorianCalendar;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.63955DCA-A09A-7D10-DAAB-537BE0CAE989]
// </editor-fold>
@Entity
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue
    private Long hId;                     // the object id from the database

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D494EAD5-A286-58FF-73ED-6CA9582655F6]
    // </editor-fold>
    private String id;                              // vehechile Identifier (VIN)


    private VehicleType type;                       // type of vehicle (compact with tv)

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.24422537-337F-5D4F-D19A-5C420B3AB4E7]
    // </editor-fold>
    @Column(nullable=false)
    private VehicleStatus status;                   // current state of the vehcile (Rented, Available)

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C860C320-C7CB-8A73-F967-643B960DE531]
    // </editor-fold>
    @Column(nullable=false)
    private Double currentMilage;                   // current Mileage on the odometer

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.DDED0D8E-7DA8-6874-8ED5-EFB2E4CFBF88]
    // </editor-fold>
    @OneToMany(mappedBy="vehicle")
    @OrderBy("date")
    private ArrayList<CostReport> serviceReport;    // whern the vehicle was serviced and what was done

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5B2A2FFF-91ED-A699-A5FC-6B05CEEB0ED5]
    // </editor-fold>
    @OneToMany(mappedBy="vehcile")
    @OrderBy("date")
    private ArrayList<CostReport> damageReport;     // what damage was done to vehicle and when

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.61C6F925-FE32-82FE-EDF0-F5B9552B8725]
    // </editor-fold>
    private Rental currentRental;                   // Information about the current rental

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C412A64E-F2A5-FB91-53D6-51B771395E69]
    // </editor-fold>
    @OneToMany(mappedBy="vehicle")
    @OrderBy("startDate")
    private ArrayList<Bill> billHistory;            // history of past rentals

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9619B280-4813-C194-9171-AEFFB5948212]
    // </editor-fold>
    public Vehicle (String vehicleId, VehicleType type, Double mileage) throws BadInputException {
        if (mileage <0) throw new BadInputException ("Mileage for a vehicle must be greater than 0 not" +
                ": "+mileage);
        this.id=vehicleId;
        this.type=type;
        this.currentMilage=mileage;
        this.status=VehicleStatus.UNAVAILABLE;
        this.hId=-1L;

        // Initialize the ArrayLists and currentRental
        this.damageReport= new ArrayList<CostReport> ();
        this.serviceReport=new ArrayList<CostReport> ();
        this.currentRental=null;
        this.billHistory=new ArrayList<Bill>();
    }

    /**
     *  constructor for the database to use
     * @param vehicleId
     * @param type
     * @param mileage
     * @param vs
     * @param hId
     */
    public Vehicle(String vehicleId, VehicleType type, Double mileage, VehicleStatus vs, long hId) 
            throws BadInputException {
        this(vehicleId,type,mileage);
        this.hId=hId;
        this.status=vs;
    }

    protected Vehicle() {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D6BBB19B-6609-0B0A-679E-FAC72635ACF3]
    // </editor-fold> 
    public void changeStatus(VehicleStatus newStatus)
            throws CanNotChangeStatusException {
        // make sure that the status change is allowed
        // changing a status to itself is allowed
        if (newStatus != this.status) {
            switch (this.status) {
                // case Available allowed to go to any status
                case DELETED:
                    // Can not change status from deleted
                    throw new CanNotChangeStatusException();
                case UNAVAILABLE:
                    // Can not go to Rented
                    switch (newStatus) {
                        case RENTED:
                            throw new CanNotChangeStatusException();
                    }
                    break;
                case RENTED:
                    switch (newStatus) {
                        case DELETED:
                            throw new CanNotChangeStatusException();
                    }
                    break;
            }
        }

        this.status = newStatus;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E2409EE2-EFEB-632D-F9A0-2BD08433F5DD]
    // </editor-fold> 
    public Boolean checkStatus (VehicleStatus status) {
               Boolean outValue=false;
        if (this.status == status)
               outValue=true;
        return outValue;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.F5136AA8-D6AC-1035-78B0-9E60793E8211]
    // </editor-fold> 
    public ArrayList<Bill> getBillHistory () {
        return billHistory;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.F90856CD-36CB-2445-C112-12D1F48D36B5]
    // </editor-fold> 
    public void setBillHistory (ArrayList<Bill> val) {
        this.billHistory = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.9084AFE8-E6DC-74ED-F45E-3935C751E3C7]
    // </editor-fold> 
    public Double getCurrentMilage () {
        return currentMilage;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.478F7604-C361-C1DD-6BEA-111991874D7F]
    // </editor-fold> 
    public void setCurrentMilage (Double val) {
        this.currentMilage = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.1C7E7FF9-A835-B06D-0866-F99A2765704F]
    // </editor-fold> 
    public Rental getCurrentRental () {
        return currentRental;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.FA00FE01-1BF5-4B92-5043-EF5FED07A969]
    // </editor-fold> 
    public void setCurrentRental (Rental newRental) {
        this.currentRental = newRental;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C802422A-4F59-D0C5-7068-A13913718721]
    // </editor-fold> 
    public ArrayList<CostReport> getDamageReport () {
        return damageReport;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.3D1EA031-002C-2D8E-63BA-4EA6AA8C9A84]
    // </editor-fold> 
    public void setDamageReport (ArrayList<CostReport> val) {
        this.damageReport = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.776F25BA-43EF-4884-C720-8D9FAAC64F7E]
    // </editor-fold> 
    public ArrayList<CostReport> getServiceReport () {
        return serviceReport;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.F1C0EC7E-04EF-4B6E-2E4A-AE79A7457382]
    // </editor-fold> 
    public void setServiceReport (ArrayList<CostReport> val) {
        this.serviceReport = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BB4839EC-DA25-78D9-3E34-0BEFC9C75A11]
    // </editor-fold> 
    public VehicleStatus getStatus () {
        return status;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.973BAEAE-8F0A-2220-92BB-D6B283AF346D]
    // </editor-fold> 
    protected void setStatus (VehicleStatus val) {
        this.status = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.EF7B0707-CC90-34A6-7C09-23A3227E46E9]
    // </editor-fold> 
    public String getVehicleId () {
        return id;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.152CB05D-BD25-89B7-6FED-2E4097BDA7F0]
    // </editor-fold> 
    private void setVehicleId (String val) {
        this.id = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.3B75FB18-8365-1A17-4742-D59474934B7A]
    // </editor-fold> 
    public VehicleType getVehicleType () {
        return type;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.793DE7B6-D533-7F99-CA23-23B6156EB86D]
    // </editor-fold> 
    protected void setType (VehicleType val) {
        this.type = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E250B42C-8319-FC71-A6BA-F6F57B31B7BE]
    // </editor-fold> 
    public void addService (CostReport serviceReport) {
        this.serviceReport.add(serviceReport);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2BB910D6-05DC-6E7E-784F-BE069EFA696E]
    // </editor-fold> 
    public void createRental (CustomerInformation customer, Double currentMileage) {
    }

    // for database use only
    public void setDbId(long id) {
        this.hId=id;
    }
    public long getDbId() {
        return hId;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CCC391F0-EAB3-092D-8A5E-6853E813BE64]
    // </editor-fold> 
    public void addDamageReport (CostReport newDamage) {
        this.damageReport.add(newDamage);
    }

    /**
     * Creates a bill from the current Rental and the input information
     *
     * Procedure
     * 1. validate the inputs for existance
     * 2. ensure the inputs have a "reasonable" value
     * 3. calls the Bill constructor with the appropriate values
     * 4. returns the newBill
     *
     * Local variable -
     *  newBill the bill which is created and returned
     *
     * @param returnDate           the date the vehicle was returned
     * @param currentMileage       the mileage on the odometer when the vehicle was returned
     * @param gasAdded             the amount of gas need to fill tank upon return
     * @param damageCost           the cose of repairing the damage to the vehicle caused during rental
     * @return                     bill for the returned vehicle
     * @throws BadInputException one of the inputs was bad
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5AB02AEA-5C27-7BD3-3C7C-333F7299C39B]
    // </editor-fold> 
    public Bill createBill (Date returnDate, Double currentMileage, Double gasAdded, Double damageCost)
    throws BadInputException {
        Bill newBill = null;

         // test input parameter esixt
        if (returnDate == null)
            throw new BadInputException("Need leagal return date");
        if (currentMileage <0)
            throw new BadInputException("Mileage must be >= 0 :" + currentMileage);
        if (gasAdded<0)
            throw new BadInputException("Gas Added must be >=- 0 :" + gasAdded);
        if (damageCost <0)
            throw new BadInputException("Damage cost must be 0 or greater :"+damageCost);

        // ensure that the vehicle has a currentRental
        if (currentRental == null)
            throw new BadInputException("Vehicle has no current Rental record. Can not generate bill");

        newBill=new Bill(currentRental, returnDate, currentMileage, gasAdded, damageCost, type.getRate());

        return newBill;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.215F382F-9316-4D4F-08B6-91D08C05F23B]
    // </editor-fold> 
    public Double calculateRentalFee (Bill newBill, Double mileageAllowance, Double mileageRate,
            Double gasRate) {
        return newBill.calculateChargeOwed(mileageAllowance, mileageRate, gasRate);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.662D48A6-ED20-4F45-AE2B-BCC512B9C836]
    // </editor-fold> 
    public void addBill (Bill newBill) {
        this.billHistory.add(newBill);
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.529C1E20-5CE1-EB12-5C2E-AA1EB57998BB]
    // </editor-fold> 
    public String generateVehicleReport () {
        return null;
    }
/**
 * Searchs the service history of the vehicle and gets the last service record (cost report).
 * Since the service history is in the order that it was entered with no fixed ordering
 * just iterate through the list and find the costRecord with the latest date.
 * Special case if there is no service record will return null
 * Internal vairables are sr - the iterator for the list or CortReport
 *    lastService - the current latest service record and the return value
 *
 * Note: If the serviceReport was a LinkList which is ordered this would not be needed
 *
 * @return the last service record
 */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3172872B-1D8D-CF27-F501-0EBEE8609120]
    // </editor-fold> 
    public CostReport getLastServiced () {
        CostReport lastService=null;

        // if serviceReport is null or has no enteries exit and return null
        if (serviceReport == null || serviceReport.isEmpty()) return lastService;

        // put the first service record in lastService report
        lastService=serviceReport.get(0);

        // iterate over the list (starting with first )
        // replace lastService record if and only if the date is later than the one in lastService
        for (CostReport sr: serviceReport) {
            if (lastService.getDate().before(sr.getDate())) lastService=sr;
        }
        return lastService;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C63E4EE4-DB06-D8CD-5069-A49C26B79160]
    // </editor-fold>
    /**
     * Returns the total revenue for the vehicle during the time period startDate, endDate
     * Note - this only includes the revenue collected during the period of interest (poi)
     *        so no amount owing is caluclated for vehicles out to rent at the end of poi
     *
     * @param startDate begining of the period of interest
     * @param endDate end of the period of interest
     * @return total of all revenue recieved for the vehicle during the poi
     */
    public double getRevenue (Date startDate, Date endDate) {
        // initial revenue to 0
        double revenue=0;
        
        // loop over all Bills
        for (Bill b: billHistory) {
            // emsire that the end date is within the time period of interest
            if (b.getEndDate().after(startDate) && b.getEndDate().before(endDate) ||
                    b.getEndDate().equals(endDate)|| b.getEndDate().equals(startDate)) {
                // add total fee to revenue
                revenue+=b.getTotalFee();
            }
        }

        return revenue;
    }

    public int getDaysRented (Date startDate, Date endDate) {
        // initialize daysRented to 0
        int daysRented=0;
        Date today=new Date();

        // if the vehicle is out for rent at this time, check that it is in the period of interest
        if (currentRental!= null) {
            if (currentRental.getStartDate().before(endDate)) {
                // calculate number of days this rental happened during the poi
                // 2 cases A: current start date in the poi,
                //         B: current start date before poi
                if (startDate.after(currentRental.getStartDate())) {
                    daysRented=(int) (DateMath.daysDifferent(startDate, endDate) + 1L);
                } else {
                    daysRented=(int)(DateMath.daysDifferent(currentRental.getStartDate(),endDate))+1;
                }
            }

        }

        // loop over all Bills
        for (Bill b: billHistory) {
            // test that the end date is within the time period of interest
            if (b.getEndDate().after(startDate) && b.getEndDate().before(endDate)) {
                // add total fee to revenue
                if (b.getStartDate().before(startDate)) {
                    daysRented+=(int)(DateMath.daysDifferent(startDate, b.getEndDate()) + 1L);
                }
                daysRented+=b.getDaysRented();
                // test if the bill end date is after the poi end date and
                // the bill start date is before the poi end date
                // add the overlap
            } else if (b.getEndDate().after(endDate) && b.getStartDate().before(endDate)) {
                // bill start date befoer poi
                if (b.getStartDate().before(startDate)) {
                    daysRented += (int) (DateMath.daysDifferent(b.getStartDate(), endDate))+1 ;
                // bill startdate in the poi
                } else {
                    daysRented += (int) (DateMath.daysDifferent(b.getStartDate(), endDate));
                }
            }
        }

        return daysRented;
    }

    /**
     * Cleans up a vehicle rental after the credit card has been validated for the rental charge
     * removes the currentRental
     * sets current Mileage to the end Mileage of the newBill
     * adds newBill to the bill history
     * if damageReport is not null - set vehicle status to unavailable and adds damage report
     * else sets vehicle available
     *
     * @param newBill - bill from the current rental
     * @param damageReport - possible damage report can be null
     */
    public void finishRental(Bill newBill, CostReport damageReport) throws CanNotChangeStatusException {
        // set the vehicles current mileage
        currentMilage=newBill.getEndMileage();

        // add newBill to billHistory
        billHistory.add(newBill);
        currentRental=null;

        if (damageReport != null) {
            changeStatus(VehicleStatus.UNAVAILABLE);
            addDamageReport(damageReport);
        } else {
            changeStatus(VehicleStatus.AVAILABLE);
        }

    }

    public double getMileageOn(Date day){
        double returnMileage=-1.0;
        boolean found=false;
        boolean first=true;
        // find last bill before day - the endmileage on the vehicle on the day
        // if no bill before day - take startmileage from bill after day
        // if no bill for vehicle use currentMileage
        if (billHistory.size()<1) {
            return currentMilage;
        }

        // sort the Nill list
        Collections.sort(billHistory, new BillDateCompare());

        // assuming the list is in earliest to latest order now (it could be the reverse)
        for (Bill b:billHistory) {
            if (first) {
                first=false;
                returnMileage=b.getStartMileage();
            }

            // currentMileage is the last recorded mileage
            if (found == false) {
                if (b.getStartDate().after(day)) {
                    found = true;
                } else if (b.getEndDate().after(day)) {
                    returnMileage = b.getStartMileage();
                    found = true;
                } else {
                    returnMileage=b.getEndMileage();
                }
            }
        }

        return returnMileage;
    }

    public ArrayList<CostReport> getService(Date endDate){
        ArrayList<CostReport> retList = new ArrayList<CostReport>();
        for (CostReport cr : this.getServiceReport()) {
               if (cr.getDate().before(endDate)) {
                   retList.add(cr);
               }
        }
        Collections.sort(retList,new CostReportDateCompare());
        return retList;
    }

    public ArrayList<CostReport> getDamageTo(Date endDate){
        ArrayList<CostReport> retList = new ArrayList<CostReport>();
        for (CostReport cr : this.getDamageReport()) {
            if (cr.getDate().before(endDate)) {
                retList.add(cr);
            }
        }
        Collections.sort(retList, new CostReportDateCompare());
        return retList;
    }

    @Override
    public String toString () {
        StringBuilder outS= new StringBuilder("vin: " +getVehicleId() + " Status: " + status+"\n");

        outS.append("Type " + type.getName() +" Miles " + String.format("%8.2f", currentMilage)+"\n");
        outS.append("Service Report: ");
        if (serviceReport.size()==0) {
            outS.append(" None "+"\n");
        }
        for (CostReport sR : serviceReport) {
            outS.append(sR.toString()+"\n");
        }

        outS.append("Damage Report: ");
        if (damageReport.size() == 0) {
            outS.append(" None \n");
        }

        for (CostReport sR : damageReport) {
            outS.append(sR.toString()+"\n");
        }

        outS.append("Current Rental: ");
        if (currentRental == null) {
            outS.append(" None \n");
        } else {
            outS.append(currentRental.toString()+"\n");
        }

        outS.append("Past bills: ");
        if (billHistory.size()==0) {
            outS.append("None \n");
        }
        for (Bill b :billHistory) {
            outS.append(b.toString()+"\n");
        }

        return outS.toString();

    }

}

