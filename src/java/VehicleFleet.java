import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.*;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.NumberFormat;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.357C975F-DD68-9D4D-C035-1F5DDF313112]
// </editor-fold>
//@Entity
public class VehicleFleet implements Serializable {

  //  @Id
    //@GeneratedValue
    private Long hId;                     // the object id from the database


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5F42C843-A757-4E52-65FB-BEDF6F5CE772]
    // </editor-fold>
   // @OneToMany
    private ArrayList<Vehicle> vehicles;    // the fleet of vehicles in all states

    private ArrayList<CreditCard> corperateCard; // the credit cards registered by corperations

    private ArrayList<VehicleType> vehicleType; // list of possible vehicle types

    // private ArrayList<Report> monthlyReport;    // the monthly reports for revenue and vehicles


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.9C611719-61EC-50CC-8490-C6A2D5389339]
    // </editor-fold> 
    // private VehicleType vehicleTypeTable;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B7CE8553-425E-060B-8F61-2FCD6879C499]
    // </editor-fold> 
    private static Double mileageAllowance= 200.00;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.00E78011-F3D7-5139-6E0E-B1C1F8CB9BD8]
    // </editor-fold> 
    private static Double mileageRate=0.30;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.461FCE9B-E8B5-BD49-3AC8-FD2B848A8F84]
    // </editor-fold> 
    private static Double gasRate=2.00;

    private static Double deposit=100.00;

    private dbConnection db=null;


    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.35AE5958-BC50-2E9C-F9F3-936EF3DF1A19]
    // </editor-fold> 
    public VehicleFleet () {
        // initialize the fleet
        vehicles=new ArrayList<Vehicle>();
        corperateCard=new ArrayList<CreditCard>();
        vehicleType=new ArrayList<VehicleType> ();
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.951A59BE-1936-E9DE-EB23-0D1AD8F216CE]
    // </editor-fold> 
    public void addVehicle (String vehicleId, VehicleType type, Double mileage) 
            throws BadInputException, CanNotChangeStatusException {
        Vehicle newVehicle=null;

        // ensure that the vehicle Id does not exist in the fleet already
        if (vehicleIdExists(vehicleId))  throw new BadInputException("Vehicle Id already exists");

        // ensure that the mileage is valid
        if ( mileage < 0 ) throw new BadInputException("Mileage input needs to be greater than 0");

        // make the new vehicle
        newVehicle=new Vehicle(vehicleId,type,mileage);

        // change status to Availible - currently leave ANAVAILABLE then add cose report for initial chekcin
        // newVehicle.changeStatus(VehicleStatus.AVAILABLE);

        // add the new vehicle to the fleet
        vehicles.add(newVehicle);

    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.26AB4BA6-1A61-33D2-CC20-BB7475BAA89E]
    // </editor-fold> 
    public Boolean vehicleIdExists (String vehicleId) {
        Boolean out=false;

        for (Vehicle v: vehicles) {
            if (v.getVehicleId().trim().equalsIgnoreCase(vehicleId)) out=true;
        }

        return out;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.592B5AE4-C6AE-63B8-7DFF-FC09560AF639]
    // </editor-fold> 
    public void deleteVehicle (String vehicleId)
     throws BadInputException, CanNotChangeStatusException {
        Vehicle deleteVehicle=null;

         // ensure that the vehicle Id does not exist in the fleet already
        if (!vehicleIdExists(vehicleId))  throw new BadInputException();
        System.out.println("Vehicle id exists " +vehicleId);

        // get the vehicle from the fleet
        deleteVehicle = getVehicle(vehicleId);
        
        // check status is not currently RENTED
        if (deleteVehicle.checkStatus(VehicleStatus.RENTED)) throw new CanNotChangeStatusException ();
        
    // change vehicle Status
        deleteVehicle.changeStatus(VehicleStatus.DELETED);

    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1558C5DF-3A8B-479A-687B-C343A0204168]
    // </editor-fold> 
    public void returnVehicleFromService (String vehicleId, CostReport serviceReport) 
            throws BadInputException, CanNotChangeStatusException {
        Vehicle servicedVehicle=null;

        // ensure that the vehicle exists
        if (!vehicleIdExists(vehicleId))
            throw new BadInputException("Input Vehicle Id not in Fleet " +vehicleId);

        // get the vehicle from the fleet
        servicedVehicle = getVehicle(vehicleId);

        // ensure that the vehicle is out for service
        if (!servicedVehicle.checkStatus(VehicleStatus.UNAVAILABLE))
            throw new CanNotChangeStatusException("Vehicle "+ vehicleId +" status not Unavailable is " +
                    servicedVehicle.getStatus());

        // change vehicle status to available
        servicedVehicle.changeStatus(VehicleStatus.AVAILABLE);

        // add the service report
        servicedVehicle.addService(serviceReport);

    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.47A5E611-0A47-4ADC-2ABD-39073D745240]
    // </editor-fold> 
    public CostReport generateServiceReport (Date returnDate, String serviceDescription, 
            Double serviceCost)  throws BadInputException {
        CostReport outReport = new CostReport(returnDate, serviceDescription, serviceCost);
        return outReport;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.50CCB3CA-E82A-6409-9A63-32A0D196F381]
    // </editor-fold> 
    public Double getGasRate () {
        return gasRate;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.087154E2-E297-D000-436A-22B7EF8C4AEF]
    // </editor-fold> 
    private void setGasRate (Double val) {
        this.gasRate = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.174D9167-6F01-44EA-B25D-D2145B3C9560]
    // </editor-fold> 
    public Double getMileageAllowance () {
        return mileageAllowance;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.2B9007AE-A0C6-1785-86B3-4C1A2CF12966]
    // </editor-fold> 
    private void setMileageAllowance (Double val) {
        this.mileageAllowance = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.35D15287-350E-E6F7-8C97-C8555C1DF8E3]
    // </editor-fold> 
    public Double getMileageRate () {
        return mileageRate;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7D43F4EC-8562-011D-0D44-3FCA1529FEFB]
    // </editor-fold> 
    private void setMileageRate (Double val) {
        this.mileageRate = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.D9D7C3C8-E314-E254-39D7-0EB7830C57B0]
    // </editor-fold> 
    public ArrayList<Vehicle> getVehicles () {
        return vehicles;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.3204CC93-EB3C-9B13-3126-77B10CB57491]
    // </editor-fold> 
    public void setVehicles (ArrayList<Vehicle> val) {
        this.vehicles = val;
    }

    public ArrayList<CreditCard> getCorperateCardList() {
        return corperateCard;
    }

    public ArrayList<VehicleType> getVehicleType() {
        return vehicleType;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B8376829-B2B4-2B31-DA63-E09384B474DE]
    // </editor-fold> 
    public ArrayList<Vehicle> getVehicleByType (String type, String option) {
        ArrayList<Vehicle> outVehicle=new ArrayList<Vehicle>();

        for (Vehicle v: vehicles) {
            if (v.getVehicleType().typeEquals(type, option))
                outVehicle.add(v);
             }
        return outVehicle;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2C359F9A-5434-0AF8-B8A2-BFBB3053AF7D]
    // </editor-fold> 
    public ArrayList<Vehicle> getVehicleByAvalible () {
        ArrayList<Vehicle> outVehicle=new ArrayList<Vehicle>();

        for (Vehicle v: vehicles) {
            if (v.getStatus()== VehicleStatus.AVAILABLE)
                outVehicle.add(v);
             }
        return outVehicle;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.6D2D3E76-60A1-941F-0B6C-E359FFC7F1A5]
    // </editor-fold> 
    public void rentVehicle (String VehicleId, CustomerInformation customer,
            double startMileage , Date startDate)
            throws BadInputException, CanNotValidateCreditCardException, CanNotChangeStatusException {
        Vehicle rentVehicle = null;
        Rental newRental= null;
        double sMileage=startMileage;
        // ensure that the vehicles exists in the fleet
        if (!vehicleIdExists(VehicleId)) throw new BadInputException ("Vehicle Id Not in Fleet");

        // get the vehicle from the fleet
        rentVehicle=getVehicle(VehicleId);

        // ensure that the status of the vehicle is AVAILABLE
        if (!rentVehicle.checkStatus(VehicleStatus.AVAILABLE)) 
            throw new BadInputException ("Status not AVAILABLE");

        // check if only 1 rental per customer
        if (! unique(customer))
            throw new BadInputException("Non-Unique Customer "+customer.getName());
        // validate the customer credit card with the deposit card
        if (!validateCreditCard(customer.getCreditCard(),deposit))
            throw new CanNotValidateCreditCardException();

        // if no milage was input
        if (sMileage==0) {
            sMileage=rentVehicle.getCurrentMilage();
        } else {
            // make sure  sMileage input is greater than or equal to current Vehicle Mileage
            if (sMileage < rentVehicle.getCurrentMilage()) {
                throw new BadInputException("Bad Mileage Entered");
            } else if (sMileage > rentVehicle.getCurrentMilage()) {
                // if the input mileage is greater than the currentMileage update currentMileage
                rentVehicle.setCurrentMilage(sMileage);
            }
        }
        // change the vehicles status
        rentVehicle.changeStatus(VehicleStatus.RENTED);

        // create a new Rental record
        newRental=new Rental(startDate, sMileage, customer, deposit);

        // store the current rental in the Vehicle
        rentVehicle.setCurrentRental(newRental);

    }

    public Boolean validateCreditCard(CreditCard card, double value) {
        return true;
    }

    /**
     * This code implements the functionality of returning a vehicle from a rental which has been
     * damaged.  All the input parameters are required.  If the returnCreditCard is the same as the
     * one that the renter used for the deposit the CreditCard is expected to have been retrieved and
     * copied before this method is called.
     *
     * What is done:
     * The order is such that if an action will fail that the vehicle will still be in its intial state
     * when the exception is thrown
     * 1. the input parameters are checked to make sure that they are there
     * 2. the vehicle with vehicleId is checked to ensure it is in the fleet and status is rented
     * 3. A new bill is created from the input data, the currentRental and damageReport
     * 4. TotalCharge is calculated for the Bill
     * 5. the credit card is charge is validated
     * 5a. the currentRental is removed from the vehicle
     * 6. the damage report is addeed to the vehicle
     * 7. the currentMileage is update on the vehiclle
     * 8. the currentRental is removed from the vehicle
     * 9. the bill is added to the rental history of the vehicle
     * 10. the status of the vehicle is set to unavailable
     *
     * local variables
     * returnedVehicle - the returned vehicle which was damaged
     * newBill - the bill for the current rental
     *
     * @param vehicleId - identifier for the vehicle being returned
     * @param returnDate - the date the vehicle was returned
     * @param currentMileage - mileage on the odometer as it is being returned
     * @param gasAdded - number of gallons need to fill tank at the time of the return
     * @param returnCreditCard - credit card to charge
     * @param damageReport - the CostReport of the damage which occured to the vehicle
     * @throws CanNotChangeStatusException -
     * @throws BadInputException
     * @throws CanNotValidateCreditCardException
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CC7CB917-0019-73C9-CE0F-0B0BF3289E3D]
    // </editor-fold> 
    public void returnVehicleWithDamage (String vehicleId, Date returnDate, Double currentMileage, Double gasAdded,
            CreditCard returnCreditCard, CostReport damageReport)  throws CanNotChangeStatusException,
             BadInputException, CanNotValidateCreditCardException {
        
        Vehicle returnedVehicle = null;    // the returned damaged vehicle
        Bill newBill = null;               // the bill for the rental
        double totalFee = 0;               // the cost of the rental
        double amountOwed = 0;             // Amount owed after the deposit is removed

        // test input parameter
        if (returnDate == null)
            throw new BadInputException("Need leagal return date");
        if (currentMileage <0) 
            throw new BadInputException("Mileage must be >= 0 :" + currentMileage);
        if (gasAdded<0) 
            throw new BadInputException("Gas Added must be >=- 0 :" + gasAdded);
        if (returnCreditCard == null)
            throw new BadInputException ("A credit card must be entered to charged for the rental");
        if (damageReport == null)
            throw new BadInputException("If vehicle was damaged a Cost Report must" +
                    " be supplied with the damage information");
        
        // make sure the vehicleId is in the fleet
        if (!vehicleIdExists(vehicleId)) 
            throw new BadInputException("Vehicle Id not in the fleet :" + vehicleId);
        
        // get the vehicle fromt the fleet
        returnedVehicle=getVehicle(vehicleId);

        // ensure that the current status is rented
        if (!returnedVehicle.checkStatus(VehicleStatus.RENTED))
            throw new BadInputException ("Vehicle not currently \"RENTED\" : " + vehicleId);

           // create a new bill for the rental
        newBill=new Bill(returnedVehicle.getCurrentRental(),returnDate, currentMileage,
                gasAdded, damageReport.getCost(), returnedVehicle.getVehicleType().getRate());

        // Calculate total fee
        totalFee=newBill.calculateChargeOwed (mileageAllowance, mileageRate, gasRate);

        // Validate rental fee on the credit card
        if (!validateCreditCard(returnCreditCard, totalFee))
            throw new CanNotValidateCreditCardException("Credit Card Declined for "+totalFee);

        // the damage report is addeed to the vehicle
        returnedVehicle.addDamageReport(damageReport);

        // the currentMileage is update on the vehiclle
        returnedVehicle.setCurrentMilage(currentMileage);

    // the currentRental is removed from the vehicle
        returnedVehicle.setCurrentRental(null);

     // the bill is added to the rental history of the vehicle
        returnedVehicle.addBill(newBill);

     // the status of the vehicle is set to unavailable
        // it is assumed the vehicle needs repair in this case
        // otherwise the staff can manually set the value to AVAILABLE
        returnedVehicle.changeStatus(VehicleStatus.UNAVAILABLE);

    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.938A9824-D878-5146-76C1-FA857775CCEB]
    // </editor-fold> 
    public CreditCard generateCreditCard () {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C78F3B0C-F5A9-C70D-CE61-E85329695A46]
    // </editor-fold> 
    public CustomerInformation generateCustomer () {
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E5FABA1C-8113-62B5-7C1B-A07252E968A2]
    // </editor-fold> 
    public void returnVehicle () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B33FB06E-1BE7-F8BF-8B92-0ED746F94F5C]
    // </editor-fold> 
    public Vehicle getVehicle (String vehicleId) {
        // no existance test because will return null if not found
        Vehicle found=null;

        // be dumb loop throught the list and when the id is found
        // fill in found with the vehicle
        for(Vehicle v: vehicles) {
            if (vehicleId.equalsIgnoreCase(v.getVehicleId().trim())) found=v;
        }

        return found;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3F64B09B-6400-66C9-3442-93D08C54C2A1]
    // </editor-fold> 
    public String generateVehicleReport(String vehicleId, Date startDate, Date endDate) {
        DateFormat fmt = DateFormat.getDateInstance(DateFormat.MEDIUM);
        NumberFormat nFmt = NumberFormat.getCurrencyInstance();
        String lastService = null;
        StringBuilder tempLine = new StringBuilder();

        Vehicle vehicle=null;

        vehicle=getVehicle(vehicleId);

        if (vehicle.getLastServiced() == null) {
            lastService = "None";
        } else {
            lastService = fmt.format(vehicle.getLastServiced().getDate());
        }

        tempLine.append(fmt.format(endDate)+" --- " +fmt.format(startDate)+"\n" );
        tempLine.append(String.format("%1$-16s %2$-44s %3$-8s %4$-16s\n", "Vehicle ID", "Vehicle Type", "Mileage",
                "Last Service Date"));
        tempLine.append(String.format("%1$-16s %2$-44s %3$-8s %4$-16s\n", vehicle.getVehicleId(),
                vehicle.getVehicleType().getName(),String.format("%8.2f", vehicle.getMileageOn(endDate)),
                lastService));

        tempLine.append("Damage Reports\n");
        // deal with the possibility of no damage
        // otherwise fill them in
        ArrayList<CostReport> relaventReport=vehicle.getDamageTo(endDate);
        if (relaventReport.size() < 1) {
            tempLine.append("None\n");
        } else {
            for (CostReport d : relaventReport)
                tempLine.append(d.toString()+"\n");
        }
        tempLine.append("Service Reports\n");
        
        // deal with the possibility of no Service
        // otherwise fill them in
        relaventReport=vehicle.getService(endDate);
        if (relaventReport.size() < 1) {
            tempLine.append("None\n");
        } else {
            for (CostReport d : relaventReport)
                tempLine.append(d.toString()+"\n");
        }

        return tempLine.toString();
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.356221B4-17E7-A29E-08F8-8D92591014C8]
    // </editor-fold> 
    public String generateRevenueReport (Date startDate, Date endDate) {

        DateFormat fmt = DateFormat.getDateInstance(DateFormat.MEDIUM);
        NumberFormat nFmt = NumberFormat.getCurrencyInstance();
        double inRevenue = 0;
        double totalRevenue = 0;
        int inDaysRented = 0;
        int totalDaysRented = 0;
        boolean vehicleRented=false;

        StringBuilder tempLine = new StringBuilder("Revenue Report for " + fmt.format(startDate)+
                " to "+fmt.format(endDate)+"\n\n");

        tempLine.append(String.format("%1$25s%2$12s%3$10s\n", "VIN","Days Rented","Revenue"));
           // fill in the vehicle information skipping vehicles with no value
        for (Vehicle v : vehicles) {
            inDaysRented = v.getDaysRented(startDate, endDate);
            inRevenue = v.getRevenue(startDate, endDate);

            if (inRevenue != 0 || inDaysRented != 0) {
                tempLine.append(String.format("%25s",v.getVehicleId().trim()));
                tempLine.append(String.format("%12d",inDaysRented));
                tempLine.append(String.format("%10s\n",nFmt.format(inRevenue)));
                totalRevenue += inRevenue;
                totalDaysRented += inDaysRented;
                vehicleRented=true;
            }
        }

        if (vehicleRented==false)
            tempLine.append("No Vehicles rented for this time period\n");

        tempLine.append(String.format("\n%25s%12d%10s\n","Total",totalDaysRented,nFmt.format(totalRevenue)));

        return tempLine.toString();
    }

    public void changeVehicleStatus  (String vehicleId, VehicleStatus newStatus)
            throws BadInputException, CanNotChangeStatusException {
        Vehicle workVehicle=null;
        // ensure the vehicle exists
        if (! vehicleIdExists(vehicleId))
            throw new BadInputException("Vehicle with VIN: "+vehicleId +" does not exists in Fleet");

        if (newStatus == null)
            throw new BadInputException("Need to enter a status");

        // get the vehicle
        workVehicle=getVehicle(vehicleId);

        // change the status
        workVehicle.changeStatus(newStatus);
    }
    public void addDamageReport(String vehicleId, CostReport damageReport) throws BadInputException {
        Vehicle workVehicle=null;
        // ensure the vehicle exists
        if (! vehicleIdExists(vehicleId))
            throw new BadInputException("Vehicle with VIN: "+vehicleId +" does not exists in Fleet");

        if (damageReport == null)
            throw new BadInputException("Need to enter a status");

        // get the vehicle
        workVehicle=getVehicle(vehicleId);

        workVehicle.addDamageReport(damageReport);

    }

//    public void dbSaveFlett() {
//        for (Vehicle v: vehicles) {
//            if (v.getDbId() ==-1) {
//                dbConnection.store(v);
//            }
//        }
//    }

    public static void main(String[] args) throws BadInputException, CanNotChangeStatusException {
   //     EntityManagerFactory emf=Persistence.createEntityManagerFactory("vehicle2PU");

  //     EntityManager em = emf.createEntityManager();
  //      EntityTransaction tx = em.getTransaction();
//        Session session=null;
//
//        SessionFactory sessionFactory=NewHibernateUtil.getSessionFactory();
//
//        session=sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//
//        tx.begin();
//        GregorianCalendar gC=new GregorianCalendar(2009, 1, 12);
//
//        VehicleFleet testFleet= new VehicleFleet();
//        testFleet.addVehicle("J4YT527GH6283LD", VehicleType.COMPACT2DOOR,3005.00);
//        Vehicle car1=testFleet.getVehicle("J4YT527GH6283LD");
//        CostReport c1=new CostReport(gC.getTime(), "Oil Change", 25.49);
//        car1.addService(c1);
//        testFleet.addVehicle("MHJ689HDG683JG7",VehicleType.COMPACT2DOOR,1284.00);
//        car1=testFleet.getVehicle("MHJ689HDG683JG7");
//        gC.set(2998, 0, 22);
//        CostReport c2=new CostReport(gC.getTime(), "Oil Change1", 26.49);
//        car1.addService(c2);
//        testFleet.addVehicle("J73GJ7GH62738GU",VehicleType.COMPACT2DOOR,7865.00);
//        car1 = testFleet.getVehicle("J73GJ7GH62738GU");
//        gC.set(2009,2,2);
//        CostReport c3 = new CostReport(gC.getTime(), "Oil Change2", 24.49);
//        car1.addService(c3);
//        gC.set(2008,9,14);
//        CostReport d1= new CostReport (gC.getTime(), "Scartch under right rear-view mirror", 3.00);
//        car1.addDamageReport(d1);
//
//        session.persist(d1);
//        tx.commit();
//        session.close();

    }


    /**
     * Tests if the testCustomer is unique in the fleet by comparing the drives licence numbers
     *
     * @param testCustomer the customer to test
     * @return true if no match is found false otherwise
     */
    public boolean unique(CustomerInformation testCustomer) {
        boolean test=true;
        String licenseNumber=testCustomer.getDriversLicense().getLicenseCode();

        // loop over each vehicle and see it the driver license code is the same
        // this is the text because of the variations possible for names
        for (Vehicle v: vehicles) {
            if (v.getCurrentRental()!=null) {
                if (v.getCurrentRental().getCustomer().getDriversLicense().getLicenseCode().
                        equalsIgnoreCase(licenseNumber)) test=false;
            }
        }

        return test;

    }

    public boolean corperateCardExists(String corperateName, String number) {
        boolean found=false;

        String cardNumber = CreditCard.validCardNumber(number);

        if (corperateCard==null) return found;

        for (CreditCard c : corperateCard) {
            String tName=c.getName();
            String tNumber=c.getNumber();
            if (tName.equalsIgnoreCase(corperateName) && tNumber.equalsIgnoreCase(cardNumber)) {
                found = true;
            }

        }

        return found;
    }

    public boolean vehicleTypeExists(String type, String option) {
        boolean found=false;

        for (VehicleType vt : vehicleType) {
            if (vt.typeEquals(type,option)) found=true;
        }
        return found;
    }
    
 /**
  * adds a new credit card to the corperate credit card list
  * first ensures that the card is not already in the list
  * second build a new card
  * third adds the new card to the corperateCard List
  * @param corperateName - name of the company with the card
  * @param cardNumber    - 16 digit number for the card
  * @param expire        - the expirey date for the card
  * @throws BadInputException - thrown if the card already exists in the list
  */
 public void addCorperateCreditCard(String corperateName, String number, Date expire)
 throws BadInputException {
     CreditCard newCard=null;
     String cardNumber=CreditCard.validCardNumber(number);

// ensure that the card is not already in the system
     if (corperateCard==null) {
         corperateCard=new ArrayList<CreditCard>();
     } else {
         if (corperateCardExists(corperateName, cardNumber))
             throw new BadInputException("Corperate Card already exists in the system " + corperateName +
                     "card number " + CreditCard.formatCreditCardNumber(cardNumber) +"\n");

         // ensure that the card number does not already exist in the system for a different company
         if (corperateCardNumberExists(cardNumber))
             throw new BadInputException("Card Number already exists in the system with different company " +
                     CreditCard.formatCreditCardNumber(cardNumber) +"\n");
         // ensure that the inputs are valid?
     }

     newCard=new CreditCard(corperateName, cardNumber, expire);

     corperateCard.add(newCard);
 }

 public boolean corperateCardNumberExists (String number) {
     String cardNumber=CreditCard.validCardNumber(number);
     boolean found=false;

     if (corperateCard==null) return found;

     for (CreditCard c : corperateCard) {
         if (c.getNumber().equalsIgnoreCase(CreditCard.validCardNumber(cardNumber)))
             found = true;
     }
     return found;
 }

 /**
  * remove the corperate card from the corperate card list
  * ensures that the card is in the corperate card list
  * remove the existing card from the corperate card list
  * @param corperateName   - name of the company holding the card
  * @param cardNumber      - the number on the credit card
  * @throws BadInputException - thrown if the card is not found
  */
 public void deleteCorperateCreditCard(String corperateName, String number) throws BadInputException {
     CreditCard removeEl = null;
          String cardNumber=CreditCard.validCardNumber(number);
     // if the card is not in memory throw an exception
     if (!corperateCardExists(corperateName, cardNumber))
         throw new BadInputException("Corperate Card does not exist in the system " + corperateName +
                 "card number "+CreditCard.formatCreditCardNumber(cardNumber));

     if (corperateCard==null) throw new BadInputException("No corperate cards in the corperate list");

     for (CreditCard c : corperateCard) {
         // find the matching card and remove it
         if (c.getName().equalsIgnoreCase(corperateName) &&
                 c.getNumber().equalsIgnoreCase(CreditCard.validCardNumber(cardNumber))) {
             removeEl=c;
         }
     }

     corperateCard.remove(removeEl);
 }

 /**
  * retrieves a corperate credit card from the corperate credit card list
  * first ensures that the card is in the list
  * second builds a new credit card with the curstomer name, card number and the retrieved exipery date
  * @param corperateName - the name of the company with the card
  * @param cardNumber    - the credit card number (16 digits)
  * @param customerName  - the name of the customer present to use the card
  * @return              - null if the card number and corperate name are not found otherwise a
  *                        CreditCard
  * @throws BadInputException
  */
 public CreditCard getCorperateCreditCard(String corperateName , String number,String customerName)
 throws BadInputException {
     CreditCard retCard = null;                           // the return credit card defaults to null
                                                          // if the card is not found

     String cardNumber = CreditCard.validCardNumber(number);
     if (corperateCard==null) throw new BadInputException("No cards in the coperate card list");
     // if the card is found
     if (corperateCardExists(corperateName, cardNumber)) {
         for (CreditCard c : corperateCard) {
             // get the corperate card and make a new one using the customer name for the name field
            if (c.getName().equalsIgnoreCase(corperateName) && c.getNumber().equalsIgnoreCase(cardNumber)) {
                retCard=new CreditCard(customerName, cardNumber, c.getExpire());
            }
         }
     }

     // return the found card
     return retCard;
}

 /**
  * Change the expiry date on a Credit Card
  * @param corperateName - name of company holding the card
  * @param cardNumber    - number 16 digits on the card
  * @param newExpire     - expirey date
  * @throws BadInputException
  */
 public void modifyCorperateCreditCard(String corperateName, String number, Date newExpire)
            throws BadInputException {
     String cardNumber = CreditCard.validCardNumber(number);
     if (!corperateCardExists(corperateName, cardNumber))
         throw new BadInputException("Corperate Card does not exist in the system " + corperateName +
                 "card number "+CreditCard.formatCreditCardNumber(cardNumber)+"\n");
     for (CreditCard c : corperateCard) {
         // find the matching card and remove it
         if (c.getName().equalsIgnoreCase(corperateName) &&
                 c.getNumber().equalsIgnoreCase(CreditCard.validCardNumber(cardNumber))) {
             c.setExpire(newExpire);
         }
     }
 }

public void addVehicleType (String type, String option, double rate)
        throws BadInputException {
    // build new vehicle type
    VehicleType newType= null;
    newType=new VehicleType(type,option,rate);

    // ensure that type option does not already exist
    for (VehicleType vt: vehicleType) {
        if (vehicleTypeExists(type, option))
            throw new BadInputException("Vehicle Type type: "+type+" option: "+option+ " already exists\n");
    }

    // insert into the list
    vehicleType.add(newType);

    }

    public String getMonthlyRevenue(int year, int month)
            throws BadInputException,SQLException, BadDatabaseInsertException  {
        String retReport=null;
        Date today=new Date();
        Date rDate=null;
        Date startDate=null;
        GregorianCalendar gdate=null;
        // 1. try the database for the report - return it if there
        // 2. if it is not there create it and the vehicle reports for the month
        // 3. save all the monthly reports
        // 4. return the revenue report

        // ensure that the inputs are valid
        if (year<1990 || month <1 ||month >12 )
            throw new BadInputException ("Input must be a valid year and month Year= "+year+
                    " Month="+month+"\n");
        // ensure the request is not in the future

        //find the Date for the start of the request and Date for the end of the request
        gdate=new GregorianCalendar(year,month-1,1);
        startDate = gdate.getTime();
        gdate = new GregorianCalendar(year, month-1, 1);
        gdate.add(GregorianCalendar.MONTH, 1);
        gdate.add(GregorianCalendar.DATE, -1);
        rDate = gdate.getTime();

        if (rDate.after(today))
            throw new BadInputException ("The requested month is in the future. Year= "+year+
                  " Month="+month+"\n");

        // get the report from the database
        retReport=getRevenueReport(year,month);

        // if not null then return it and exit
        if (retReport != null)
            return retReport;

        // make and save all the reports for the given month
        generateMonthlyReport(year,month);

        retReport=getRevenueReport(year,month);

        return retReport;
    }
   public String getMonthlyVehicle(String vin, int year, int month)
            throws BadInputException,SQLException, BadDatabaseInsertException  {
        String retReport=null;
        Date today=new Date();
        Date rDate=null;
        Date startDate=null;
        GregorianCalendar gdate=null;
        // 1. try the database for the report - return it if there
        // 2. if it is not there create it and the vehicle reports for the month
        // 3. save all the monthly reports
        // 4. return the revenue report

        // ensure that the inputs are valid
        if (year<1990 || month <1 ||month >12 )
            throw new BadInputException ("Input must be a valid year and month Year= "+year+
                    " Month="+month+"\n");
        // ensure the request is not in the future

        //find the Date for the start of the request and Date for the end of the request
        gdate=new GregorianCalendar(year,month-1,1);
        startDate = gdate.getTime();
        gdate = new GregorianCalendar(year, month-1, 1);
        gdate.add(GregorianCalendar.MONTH, 1);
        gdate.add(GregorianCalendar.DATE, -1);
        rDate = gdate.getTime();

        if (rDate.after(today))
            throw new BadInputException ("The requested month is in the future. Year= "+year+
                  " Month="+month+"\n");

        // get the report from the database
        retReport=getVehicleReport(year, month, vin);

        // if not null then return it and exit
        if (retReport != null)
            return retReport;

        // make and save all the reports for the given month
        generateMonthlyReport(year,month);

        retReport=getVehicleReport(year, month, vin);

        return retReport;
    }

    private void generateMonthlyReport(int year, int month) 
            throws BadInputException, SQLException, BadDatabaseInsertException {
        Date today = new Date();
        Date rDate=null;
        Date startDate=null;
        GregorianCalendar gdate=null;
        String currentReport=null;
        Boolean skip=false;

        // ensure that the inputs are valid
        if (year<1990 || month <1 ||month >12 )
            throw new BadInputException ("Input must be a valid year and month Year= "+year+
                    " Month="+month+"\n");
        // ensure the request is not in the future

        //find the Date for the start of the request and Date for the end of the request
        gdate=new GregorianCalendar(year,month-1,1);
        startDate = gdate.getTime();
        gdate = new GregorianCalendar(year, month-1, 1);
        gdate.add(GregorianCalendar.MONTH, 1);
        gdate.add(GregorianCalendar.DATE, -1);
        rDate = gdate.getTime();

        if (rDate.after(today))
            throw new BadInputException ("The requested month is in the future. Year= "+year+
                  " Month="+month+"\n");

        // generate the Revenue Report
        currentReport=generateRevenueReport(startDate, rDate);
        saveRevenueReport(year,month,currentReport);
        // save the report to the database
        // generate the Vehicle Reports
        // 1. do it for all vehicles in the fleet
        for (Vehicle v:this.getVehicles()) {
        // 2. if the vehicle is deleted and does not have activity (revenue service or damage)
        //    in the month do not generate the report
            skip = false;
            if (v.getStatus()==VehicleStatus.DELETED) {
                if (v.getRevenue(startDate, rDate)==0) {
                    skip=true;
                }
            }

            if (!skip) {
                currentReport=generateVehicleReport(v.getVehicleId(),startDate,rDate);
                saveVehicleReport(v.getVehicleId(),year,month,currentReport);
            }
        }

    }

    public String getRevenueReport(int year, int month) throws SQLException, BadInputException {
        String retReport=null;

        // connect to the database if not already attached
        if (db==null)
            connectdb();

        retReport=db.getRevenueReport(year, month);

        return retReport;
    }

    public String getVehicleReport(int year, int month,String vin) throws SQLException, BadInputException {
        String retReport=null;

        // connect to the database if not already attached
        if (db==null)
            connectdb();

        retReport=db.getVehicleReport(year, month,vin);

        return retReport;
    }

    public void saveVehicleReport(String vin, int year, int month, String report)
    throws SQLException, BadDatabaseInsertException {
        // connect to the database if not already attached
        if (db==null)
            connectdb();

        db.store(year, month, vin, report);
    }

    public void saveRevenueReport(int year, int month, String report)
    throws SQLException, BadDatabaseInsertException {
        // connect to the database if not already attached
        if (db==null)
            connectdb();

        db.store(year, month, "revenue", report);
    }

    public void connectdb() throws SQLException {
        db=new dbConnection();
        db.dbconnect();
    }

    public void fillFleet(dbConnection db) throws BadInputException,SQLException {
        // get the vehicles
        vehicles=db.getFleet();

        // get the vehicle types
        vehicleType=db.getVehicleType();

        // get the corperate credit cards
   // RAJ     corperateCard=db.getCorperateCards();

        // get the monthly reports

    }

}

