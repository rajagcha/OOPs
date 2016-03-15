/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Raj
 */

 import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import java.lang.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

public class dbConnection {

    private boolean connected = false;             // state variable of whether already connected
    private Statement dbconn = null;               // the connectino to the database
    private ResultSet current_resultset = null;    // the result set for the querries


    public dbConnection() {
        connected = false;
        dbconn = null;
        //System.out.println("Got here " + this.connected);
    }

    /**
     * A simple routine to connect to the mysql database
     * Currently no parameters because this is only test code
     * @return true if connected false otherwise
     */
    public boolean dbconnect() throws SQLException {
        try {

            //Register the JDBC driver for MySQL.
            //     Class.forName("com.mysql.jdbc.Driver");

            //Define URL of database server for
            // database named mysql on the localhost
            // with the default port number 3306.
            String database = "jdbc:mysql://localhost:3306/fleet3";

        //String url = "jdbc:mysql://localhost:3306/";
        //String db = "komal";
        String driver = "com.mysql.jdbc.Driver";
        String user = "root";
        String pass = "root";

            //Get a connection to the database for a
            // user named root with a password of mysql.
            // This user is the default administrator
            // having full privileges to do anything.
            Connection con = DriverManager.getConnection(database, user, pass);

            dbconn = con.createStatement();
            connected = true;

        } catch (SQLException e) {
            this.closedb();
            // handle any errors
            System.err.print("Can not open database connection");
            // displaySQLError(e);
            throw new SQLException("Problem with dbconnect", e);
        } 

        return true;
    }
      /*
     * Closes the connection to the database
     * rest the instance variable to default values
     **/
    public void closedb() throws SQLException {
        if (dbconn != null) {
            try {
                dbconn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection in closedb");
                //displaySQLError(e);
                throw new SQLException("Problem with closedb", e);
            }
        }
        connected = false;
        dbconn = null;
    }

    public boolean getDBConnected() {
        return this.connected;
    }

/**
 * Load the complete vehicle fleet from the database
 * 1. read in each vehcile
 * 2. foreach vehicle read in billhistory
 * 3. foreach vehicle read in serviceReports
 * 4. foeach vehicle read in damageReports
 * 5. foreach vehicle read in currentRentals
 * @return
 */
     public ArrayList<Vehicle> getFleet() throws BadInputException, SQLException {
         ArrayList<Vehicle> addFleet=new ArrayList<Vehicle>();
         long dbId=0;
         ResultSet rsVehicle;
         CostReport inReport;
         VehicleType vType;

         // the query to run
        String query = "Select * from vehicle";

        // get each vehicle
        try {
            rsVehicle = selectData(query);

            while (rsVehicle.next()) {
                addFleet.add(convertRStoVehicle(rsVehicle));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in " + query);
            // displaySQLError(e);
            throw new SQLException("Problem with getFleet query "+ query, e);
        }

        // loop over the vehicles and fill in the list entries.
        for(Vehicle v: addFleet) {
            // fill in the real vehicle type
            vType=getVehicleType(v.getVehicleType().getDbId());
            v.setType(vType);

            // user the database id for the next set of queries
            dbId=v.getDbId();

            //Get all service costReports
            v.setServiceReport(getCostReport("SERVICE", dbId));
            v.setDamageReport(getCostReport("DAMAGE",dbId));

            //Get all the bill histories
            v.setBillHistory(getBillHistory(dbId));
            // set the daily rate (not currently stored here
            for (Bill b:v.getBillHistory()) {
                b.setDailyRate(v.getVehicleType().getRate());
            }

            // get currentRental
            v.setCurrentRental(getRental(dbId));
        }

         return addFleet;
     }

     public ArrayList<CostReport> getCostReport(String type, long vId) 
             throws BadInputException, SQLException {
         ArrayList<CostReport> inList=new ArrayList<CostReport>();
         String query="Select * from costReport where vehicleid = '" + String.valueOf(vId) +
                 "' and type = '" + type + "'";
         ResultSet rsCostReport;

         // get the CostReports
         try {
             rsCostReport = selectData(query);
             while (rsCostReport.next()) {
                 inList.add(convertRStoCostReport(rsCostReport));
             }
                 } catch (SQLException e) {
            System.out.println("SQL Error in " + query);
            // displaySQLError(e);
            throw new SQLException("Problem with getCostReport for query" + query, e);
         }

        return inList;
     }

     /**
      * Get Bill history for a vehicle
      * @param vId vin of the vehcile
      * @return the bill history
      * @throws BadInputException
      * @throws java.sql.SQLException
      */
    public ArrayList<Bill> getBillHistory(long vId)throws BadInputException, SQLException {
         ArrayList<Bill> inList=new ArrayList<Bill>();
         String query="Select * from billHistory where vehicleid = '" + String.valueOf(vId) +"'";
         ResultSet rsBill;

         // get the CostReports
         try {
             rsBill = selectData(query);
             while (rsBill.next()) {
                 inList.add(convertRStoBill(rsBill));

             }
         } catch (SQLException e) {
            System.out.println("SQL Error in " + query);
            // displaySQLError(e);
             throw new SQLException("Problem with getBill History for query " + query, e);
        }
        return inList;
     }

    /**
     * retrieve all the Corperate credit cards from the database
     * @return corperate Credit cards
     * @throws BadInputException
     * @throws java.sql.SQLException
     */


    

     public ArrayList<CreditCard> getCorperateCards()throws BadInputException, SQLException {
         ArrayList<CreditCard> inList=new ArrayList<CreditCard>();
         String query="Select * from corperateCard";
         ResultSet rsCreditCard;

         // get the CostReports
         try {
             rsCreditCard = selectData(query);
             while (rsCreditCard.next()) {
                 inList.add(convertRStoCreditCard(rsCreditCard));

             }
         } catch (SQLException e) {
            System.out.println("SQL Error in " + query);
            // displaySQLError(e);
             throw new SQLException("Problem with getCorperateCards  for query " + query, e);
        }
        return inList;
     }

     /**
     * retrieve all the Vehicle Types from the database
     * @return list of vehicle types
     * @throws BadInputException
     * @throws java.sql.SQLException
     */
    public ArrayList<VehicleType> getVehicleType()throws BadInputException, SQLException {
         ArrayList<VehicleType> inList=new ArrayList<VehicleType>();
         String query="Select * from vehicleType";
         ResultSet rsVehicleType;

         // get the VehicleType
             rsVehicleType = selectData(query);
             while (rsVehicleType.next()) {
                 inList.add(convertRStoVehicleType(rsVehicleType));

             }
        return inList;
     }

         /**
     * retrieve all the Vehicle Types from the database
     * @return list of vehicle types
     * @throws BadInputException
     * @throws java.sql.SQLException
     */
    public VehicleType getVehicleType(long dbId)throws BadInputException, SQLException {
         VehicleType inVehicle=null;
         String query="Select * from vehicleType where dbid='" + dbId+"'";
         ResultSet rsVehicleType;

         // get the VehicleType
             rsVehicleType = selectData(query);
             while (rsVehicleType.next()) {
                 inVehicle=convertRStoVehicleType(rsVehicleType);

             }

        return inVehicle;
     }

    public String getRevenueReport(int year, int month) throws SQLException, BadInputException {
        String report=null;
        String query ="Select report from monthlyreports where month= '"+month+"' and year = '"+year+"'" +
                " and type like 'revenue'";
        ResultSet rsReport;

        //get revenue report
        rsReport=selectData(query);

        while (rsReport.next()) {
            report=convertRStoReport(rsReport);
        }

        return report;
    }

      public String getVehicleReport(int year, int month,String vin) throws SQLException, BadInputException {
        String report=null;
        String query ="Select report from monthlyreports where month= '"+month+"' and year = '"+year+"'" +
                " and type like '"+vin+"'";
        ResultSet rsReport;

        //get revenue report
        rsReport=selectData(query);

        while (rsReport.next()) {
            report=convertRStoReport(rsReport);
        }

        return report;
    }

    public Rental getRental (long vid) throws BadInputException, SQLException {
        Rental rRental=null;
        String query="Select * from currentRental where vehicleId = '" +String.valueOf(vid) +"'";
        ResultSet rsCustomer;

        rsCustomer = selectData(query);
        if (rsCustomer.next())
            rRental = convertRStoRental(rsCustomer);

        return rRental;
    }

      /**
     * Takes a "SELECT" query and returns a ResultSet for the querry
     *  Uses the connection dbconn
     *  setups up the ResultSet
     *  tries the ResultSet
     *  Deals with all the SQL exceptions
     * @param command - the select querry in a string
     * @return the result of the querry or null if there is an error or no result
     */
    public ResultSet selectData(String command) throws SQLException{
        ResultSet rs = null;
        try {
            rs = dbconn.executeQuery(command);
        } catch (SQLException esql) {
            System.err.println("Problem Executing Query: " + command);
            throw new SQLException("Problem with selectData for command "+ command +" "+ esql);
     //       displaySQLError(esql);
        }
        return rs;
    }

    private Vehicle convertRStoVehicle(ResultSet RS) throws SQLException, BadInputException {
       Vehicle returnVehicle;
       long dbId=-1L;
       String vin="Loading";
       VehicleStatus vs=VehicleStatus.AVAILABLE;
       VehicleType vt=null;
       long vehicleTypeId=-1l;
       double mileage=-1;

        try {
            dbId=RS.getLong("dbid");
            vin=RS.getString("vin").trim();
            vs=VehicleStatus.valueOf(RS.getString("status"));
            vehicleTypeId=RS.getLong("type");
            mileage=RS.getDouble("currentMileage");
        } catch (SQLException e) {
            System.out.println("SQL Error " +e);
            //displaySQLError(e);
            throw new SQLException("Problem with convertRStoVehicle", e);
        }

        vt = new VehicleType("un","un",-1.0,vehicleTypeId);

        returnVehicle = new Vehicle(vin, vt, mileage,vs,dbId);

        return returnVehicle;
    }

    private CostReport convertRStoCostReport(ResultSet rS) throws BadInputException, SQLException{
        CostReport returnReport;
        Date returnDate=null;
        String description="";
        double cost=-1;
        long dbId=-1L;

        try {
            dbId=rS.getLong("dbid");
            returnDate=rS.getDate("referenceDate");
            description=rS.getString("description");
            cost=rS.getDouble("cost");
        } catch (SQLException e) {
            System.out.println("SQL Error convertRStoCostReport" +e);
            //displaySQLError(e);
            throw new SQLException("Problem in convertRStoCostReport",e);
        }
        returnReport=new CostReport(returnDate, description, cost,dbId);

        return returnReport;
    }

    private CreditCard convertRStoCreditCard(ResultSet rS) throws BadInputException, SQLException {
        CreditCard returnCard;
        String corperateName=null;
        String number=null;
        Date expire=null;
        long dbId=-1L;

        try {
            dbId=rS.getLong("dbid");
            corperateName=rS.getString("HolderName");
            number=rS.getString("number");
            expire=rS.getDate("expire");
        } catch (SQLException e) {
            System.out.println("SQL Error convertRStoCreditCard" +e);
            //displaySQLError(e);
            throw new SQLException("Problem in convertRStoCreditCard",e);
        }
        returnCard=new CreditCard(corperateName, number, expire,dbId);

        return returnCard;
    }

    private String convertRStoReport(ResultSet rs) throws BadInputException, SQLException {
        String report=null;

        report=rs.getString("Report");

        return report;
    }

     private VehicleType convertRStoVehicleType(ResultSet rS) throws BadInputException, SQLException {
        VehicleType returnCard;
        String type=null;
        String option=null;
        double rate=-1.0;
        long dbId=-1L;

        try {
            dbId=rS.getLong("dbid");
            type=rS.getString("type");
            option=rS.getString("option");
            rate=rS.getDouble("rate");
        } catch (SQLException e) {
            System.out.println("SQL Error convertRStoVehicleType" +e);
            //displaySQLError(e);
            throw new SQLException("Problem in convertRStoVehicleType",e);
        }
        returnCard=new VehicleType(type, option, rate,dbId);

        return returnCard;
    }

    private Bill convertRStoBill(ResultSet rS) throws SQLException{
        Bill returnBill;
        Date eDate=null;
        Date sDate=null;
        double sMile=-1.0;
        double eMile=-1.0;
        double gasAdd=-1.0;
        double deposit=-1.0;
        double dCost=-1.0;
        double tCost=-1.0;
        long dbId=-1L;

        try {
            dbId=rS.getLong("dbid");
            sDate=rS.getDate("startDate");
            eDate=rS.getDate("endDate");
            sMile=rS.getDouble("startMileage");
            eMile=rS.getDouble("endMileage");
            gasAdd=rS.getDouble("gasAdded");
            deposit=rS.getDouble("deposit");
            dCost=rS.getDouble("damageCost");
            tCost=rS.getDouble("totalCost");
        } catch (SQLException e) {
            System.out.println("SQL Error " + e);
            //displaySQLError(e);
            throw new SQLException("Problem with convertRStoBill", e);
        }

        returnBill=new Bill(sDate,eDate,sMile,eMile,gasAdd,deposit,dCost,tCost,dbId);
        return returnBill;
    }


    private Rental convertRStoRental (ResultSet rS) throws BadInputException, SQLException {
        Rental returnRental;
        CreditCard rCard=null;
        DriverLicense rLicense;
        CustomerInformation rCustomer=null;
        String rString="";
        double sMile=-1.0;
        double deposit=-1.0;
        Date sDate=null;
        Date oDate=null;
        long pNumber=-1L;
        String cNumber=null;
        String dName="";
        long dbId=-1L;

        try {

            rString=rS.getString("cCardName");
            oDate=rS.getDate("cCardExpire");
            cNumber=rS.getString("cCardNumber");
            rCard=new CreditCard(rString,cNumber , oDate);

            sDate=rS.getDate("dlExpire");
            rString = rS.getString("currentRental.dlNumber");
            dName=rS.getString("currentRental.dlName");

            rLicense=new DriverLicense(dName, "", rString, sDate);

            sDate = rS.getDate("startDate");
            sMile=rS.getDouble("startMileage");
            deposit=rS.getDouble("deposit");
            dbId=rS.getLong("dbid");

            dName = rS.getString("customerName");
            pNumber=rS.getLong("phoneNumber");
            rCustomer=new CustomerInformation(dName, pNumber, rLicense, rCard);

        } catch(SQLException e) {
            System.out.println("SQL Error " +e);
            //displaySQLError(e);
            throw new SQLException("Problem with convertRStoRental", e);
        }

        returnRental=new Rental(sDate, sMile, rCustomer, deposit, dbId);
        return returnRental;

    }

    public void store(Vehicle v)  throws BadDatabaseInsertException, SQLException{
        if (v.getDbId() ==-1 ) {
            insert(v);
        } else {
            modify(v);
        }
    }

    public void syncCreditCards(ArrayList<CreditCard> cList)
            throws BadDatabaseInsertException, SQLException, BadInputException {
        ArrayList<CreditCard> dbList=null;
        boolean found=false;
        for (CreditCard c:cList) {
            store(c);
        }

        // remove credit cards no longer in the list
        dbList=getCorperateCards();
        for (CreditCard cc: dbList) {
            found=false;
            for (CreditCard c : cList) {
                if (c.getName().equalsIgnoreCase(cc.getName()) &&
                        c.getNumber().equalsIgnoreCase(cc.getNumber()))
                    found=true;
            }
            if (!found)
                remove(cc);
        }
    }

    public void store(CreditCard c) throws BadDatabaseInsertException, SQLException{
        if (c.getDbId() ==-1 ) {
            insert(c);
        } else {
            modify(c);
        }
    }

    public void store (VehicleType vt) throws BadDatabaseInsertException, SQLException {
            if (vt.getDbId() ==-1)
                insert(vt);
    }

    public void store (int year, int month, String type, String report)
    throws BadDatabaseInsertException, SQLException {
        insert (year,month,type,report);
    }

  /**
   * The insertion of a new vehicle into the database
   * 1 builds the base insert command
   * 2 ensures that the vehicle is not already in database
   * 3 inserts new vehicle
   * 4 adds the databaseId to newVehicle
   * 3 inserts CostReporst, Bill, Rental to databse if present
   *
   * @param newVehicle vehicle to insert
   * @return database id for the newly inserted vehicle
   *
   * @throws java.sql.SQLException
   * @throws BadDatabaseInsertException
   */
    private long insert(Vehicle newVehicle) throws SQLException, BadDatabaseInsertException {
        long rowId = -1;
 
        //Build Sql insert fot the newVehicle
        // then dispatch inserts for bill, costReports and rental

        String insertStmt = "INSERT INTO Vehicle (vin, status, type, currentMileage) " +
                "VALUES ('" + newVehicle.getVehicleId() + "','" + newVehicle.getStatus().toString() +
                "','" + newVehicle.getVehicleType().getDbId() + "','" + newVehicle.getCurrentMilage() +
                "')";

        // make usre that the vehicle does not already exist in the database
        if (dbExists(newVehicle))
            throw new BadDatabaseInsertException("Trying to insert new Vehicle " +
                    "but it exists already " + newVehicle.getVehicleId());

        if (!modifyTable(insertStmt))
            throw new BadDatabaseInsertException("Trying to insert new Vehicle " +
                    "but it failed insert was " + insertStmt);

        // retrieve the row id for the newly inserted User
        rowId = getNewRowid();

        // store the database id in the newVehicle
        newVehicle.setDbId(rowId);

        // Store all the invarient records
        storeVehicleRecords(newVehicle);

        return rowId;
    }

    private long insert(int year,int month, String type, String report)
            throws BadDatabaseInsertException, SQLException {
        long rowid = -1L;

        String insertStmt="Insert into monthlyreports (type, year, month, report) Values ('"+
                type+"','"+year+"','"+month+"','"+report+"')";

        if (!modifyTable(insertStmt))
            throw new BadDatabaseInsertException ("Trying to insert new monthly report but it failed\n"+
                    "Year -" +year+" Month "+month+" type "+ type +"\n");
        rowid=getNewRowid();

        return rowid;
    }

    private long insert(CreditCard newCard) throws SQLException, BadDatabaseInsertException {
        long rowId = -1;
        long tempD = newCard.getExpire().getTime();
        java.sql.Date sdate=new java.sql.Date(tempD);

        //Build Sql insert fot the Credit Card
        String insertStmt="Insert into CorperateCard (HolderName, number, expire) Values ('" +
                newCard.getName() + "','" + newCard.getNumber() + "','" +sdate+"')";

        if (dbExists(newCard))
            throw new BadDatabaseInsertException("Trying to insert new Corperate Card " +
                    "but it exists already " + newCard.toString());

        if (!modifyTable(insertStmt))
            throw new BadDatabaseInsertException("Trying to insert new Card " +
                    "but it failed insert was " + insertStmt);

        // retrieve the row id for the newly inserted User
        rowId = getNewRowid();

        // store the database id in the newVehicle
        newCard.setDbId(rowId);

        return rowId;
    }

   private long modify(CreditCard creditCard) throws SQLException, BadDatabaseInsertException {
        long rowId = -1;
       long tempD = creditCard.getExpire().getTime();
        java.sql.Date sdate=new java.sql.Date(tempD);
        //Build Sql insert fot the newCreditCard
        // then dispatch inserts for bill, costReports and rental

        String insertStmt = "Update CorperateCard  set expire = '" + sdate +
                "' where dbid = '"+creditCard.getDbId()+"'";

        // make usre that the CreditCard does not already exist in the database
        if (!dbExists(creditCard))
            throw new BadDatabaseInsertException("Trying to update a CreditCard " +
                    "but it does not exist " + creditCard.toString()+"\n");

        if (!modifyTable(insertStmt))
            throw new BadDatabaseInsertException("Trying to modify new CreditCard " +
                    "but it failed modify Stmt = " + insertStmt+"\n");

        // retrieve the row id for the newly inserted User
        rowId = getNewRowid();

        return rowId;
   }

   private void remove(CreditCard deleteCard)  throws SQLException, BadDatabaseInsertException {
       String removeStmt="delete from CorperateCard where dbid='"+deleteCard.getDbId()+"'";
       if (!dbExists(deleteCard))
            throw new BadDatabaseInsertException("Trying to delete a CreditCard " +
                    "but it does not exist " + deleteCard.toString()+"\n");

        if (!modifyTable(removeStmt))
            throw new BadDatabaseInsertException("Trying to delete CreditCard " +
                    "but it failed modify Stmt = " + removeStmt+"\n");

   }

     private long insert(VehicleType newType) throws SQLException, BadDatabaseInsertException {
        long rowId = -1;

        //Build Sql insert fot the Vehicle Type
        String insertStmt="Insert into VehicleType (vehicletype.type, vehicletype.option, vehicletype.rate) Values ('" +
                newType.getType() + "','" + newType.getOption() + "','" +newType.getRate()+"')";

        if (dbExists(newType))
            throw new BadDatabaseInsertException("Trying to insert new Vehicle Type " +
                    "but it exists already " + newType.toString()+"\n");

        if (!modifyTable(insertStmt))
            throw new BadDatabaseInsertException("Trying to insert new Card " +
                    "but it failed insert was " + insertStmt+"\n");

        // retrieve the row id for the newly inserted User
        rowId = getNewRowid();

        // store the database id in the newVehicle
        newType.setDbId(rowId);

        return rowId;
    }


 /**
   * Modifies a vehicle already in the database
  *  Since this is check Always modify all vehicles which are not new
   * 1 builds the base insert command
   * 2 ensures that the vehicle is not already in database
   * 3 inserts new vehicle
   * 4 adds the databaseId to newVehicle
   * 3 inserts CostReporst, Bill, Rental to databse if present
   *
   * @param newVehicle vehicle to insert
   * @return database id for the newly inserted vehicle
   *
   * @throws java.sql.SQLException
   * @throws BadDatabaseInsertException
   */
    private long modify(Vehicle vehicle) throws SQLException, BadDatabaseInsertException {
        long rowId = -1;

        //Build Sql insert fot the newVehicle
        // then dispatch inserts for bill, costReports and rental

        String insertStmt = "Update Vehicle  set status = '" + vehicle.getStatus().toString() +"'," +
                " currentMileage = '" + vehicle.getCurrentMilage() +"' where dbid = '"+vehicle.getDbId()+"'";

        // make usre that the vehicle does not already exist in the database
        if (!dbExists(vehicle))
            throw new BadDatabaseInsertException("Trying to update a Vehicle " +
                    "but it does not exist " + vehicle.getVehicleId());

        if (!modifyTable(insertStmt))
            throw new BadDatabaseInsertException("Trying to modify new Vehicle " +
                    "but it failed modify Stmt = " + insertStmt);

        // retrieve the row id for the newly inserted User
        rowId = getNewRowid();

        // Store all the invarient records
        storeVehicleRecords(vehicle);

        return rowId;
    }

    /**
     * Non of the rest of the table elementes are modifiable so either insert, ignore or delete
     *
     * @param newVehicle
     * @throws java.sql.SQLException
     * @throws BadDatabaseInsertException
     */
    private void storeVehicleRecords(Vehicle newVehicle) throws SQLException ,BadDatabaseInsertException {
        long returnedId=-1;
        long rowId=newVehicle.getDbId();
        Rental cRent=newVehicle.getCurrentRental();

        // deal with the bill, CostReports and Rental

        // current Rent
        // if there is a rental
        if (cRent != null ) {
            // new rental to be inserted
            if (cRent.getDbId() == -1 ) {
                // remove old rental
                if (dbExistsRental(rowId)) {
                    deleteRental(rowId);
                }

                //insert rental
                returnedId = insert(cRent, rowId);
                cRent.setDbId(returnedId);
            }
            // if there is no current rental remove old rental
        } else {
            // confirm that there is no rental in the database for the vehicle
               if (dbExistsRental(rowId)) {
                   // if three was delete it
                    deleteRental(rowId);
                }
        }

        // for Bill, CostReport an DbId of -1 means a new record needing to be stored

        // deal with billHistory
        for (Bill b : newVehicle.getBillHistory()) {
            if (b.getDbId() ==-1) {
                returnedId = insert(b, rowId);
                b.setDbId(returnedId);
            }
        }

        // deal with service records
        for (CostReport cR : newVehicle.getServiceReport()) {
            if (cR.getDbId() ==-1 ) {
                returnedId = insert(cR, "SERVICE", rowId);
                cR.setDbId(returnedId);
            }
        }

        // deal with damage reports
        // insert damage reports
        for (CostReport cR : newVehicle.getDamageReport()) {
            if (cR.getDbId() == -1 ) {
                returnedId = insert(cR, "DAMAGE", rowId);
                cR.setDbId(returnedId);
            }
        }
    }

    /**
     * Inserts a new Cost Report into the database
     *
     * @param newReport CostReport to be inserted
     * @param type  type of CostReport - 'DAMAGE' ro 'SERVICE'
     * @param vDbId   the database id for the vehicle to associate this with
     * @return the id of the report inserted
     *
     * @throws java.sql.SQLException
     * @throws BadDatabaseInsertException
     */
    private long insert(CostReport newReport, String type, long vDbId)
            throws SQLException , BadDatabaseInsertException{
        long rowId=-1; // not needed but good practice
        long tempD=newReport.getDate().getTime();
        java.sql.Date sdate=new java.sql.Date(tempD);
        String insertStmt = "insert into costReport (vehicleid, costreport.type, referenceDate, description,cost)" +
                " Values ('" + vDbId +"','"+ type  +"','"+ sdate +"','"+
                newReport.getDescription()+"','"+ newReport.getCost() + "')";

        if (!modifyTable(insertStmt))
            throw new BadDatabaseInsertException("Trying to insert new CostReport " +
                    "but it failed insert was " + insertStmt);

        // retrieve the row id for the newly inserted User
        rowId = getNewRowid();

        return rowId;
    }

    /**
     * Inserts a new Bill into the database for the vehicle with the dabase base id of vDbId
     *
     * @param newBill the new bill to be inserted
     * @param vDbId   the database id for the vehicel that the bill is associated with
     * @return the database id for the new bill
     * @throws java.sql.SQLException
     * @throws BadDatabaseInsertException
     */
    private long insert(Bill newBill, long vDbId)  throws SQLException , BadDatabaseInsertException {
        long rowId = -1; // not needed but good practice
         long tempD=newBill.getStartDate().getTime();
        java.sql.Date sdate=new java.sql.Date(tempD);
        tempD=newBill.getEndDate().getTime();
        java.sql.Date edate=new java.sql.Date(tempD);
        String insertStmt="insert into billHistory (vehicleid, startDate, endDate, startMileage," +
                " endMileage, gasAdded, deposit, damageCost, totalCost) Values ('" + vDbId
                +"', '"+sdate +"', '"+edate+"', '"+newBill.getStartMileage()
                +"', '"+newBill.getEndMileage()+"', '"+newBill.getGasAdded()+"', '"+newBill.getDeposit()
                +"', '"+newBill.getDamageCost()+"', '"+newBill.getTotalFee() + "')";

        if (!modifyTable(insertStmt))
            throw new BadDatabaseInsertException("Trying to insert new Bill " +
                    "but it failed insert was " + insertStmt);

        // retrieve the row id for the newly inserted User
        rowId = getNewRowid();

        return rowId;
    }

    private long insert(Rental newRental, long vDbId)  throws SQLException , BadDatabaseInsertException {
        long rowId = -1; // not needed but good practice
        CustomerInformation cI=newRental.getCustomer();
        CreditCard cC=cI.getCreditCard();
        DriverLicense dL=cI.getDriversLicense();
        long tempD = newRental.getStartDate().getTime();
        java.sql.Date sdate = new java.sql.Date(tempD);
        tempD = cC.getExpire().getTime();
        java.sql.Date cExp = new java.sql.Date(tempD);
        tempD = dL.getExpire().getTime();
        java.sql.Date dExp = new java.sql.Date(tempD);

        String insertStmt="insert into currentRental (vehicleid, startDate, startMileage, deposit, " +
                "customerName,phoneNumber, cCardName, cCardNumber, cCardExpire, dlName, dlNumber, dlExpire)" +
                " Values ('"+ vDbId +"','" + sdate +"','" + newRental.getStartMileage() +
                "','" + newRental.getDeposit() +"','" + cI.getName() +"','" + cI.getPhoneNumber() +
                "','" + cC.getName() +"','" + cC.getNumber() +"','" + cExp  +"','" +
                dL.getName() +"','" + dL.getLicenseCode() +"','" + dExp + "')";

        if (!modifyTable(insertStmt))
            throw new BadDatabaseInsertException("Trying to insert new currentRental " +
                    "but it failed insert was " + insertStmt);

        // retrieve the row id for the newly inserted User
        rowId = getNewRowid();

        return rowId;
    }

   /**
     * Takes a sql statement and tries to apply it on the connected database
     *  ensures that class is connected to the database
     *  sets up the command
     *  executes the command
     *  deals with all sql exceptions
     *  returns sucess or failure
     * @param command the sql statement to modify the table
     * @return true if successfully updated table otherwise false
     */
    public boolean modifyTable(String command) throws SQLException {
        boolean outval = false;
        try {
            int numChanges = dbconn.executeUpdate(command, Statement.RETURN_GENERATED_KEYS);
            outval = true;
        } catch (SQLException esql) {
            System.err.println("Error modifying table with SQL:" + command);
            throw new SQLException("Can not modify table with :" + command, esql);

        }
        return outval;
    }

    /**
     * deletes all currentRentals for a single vehicle (there should only be one)
     *
     * @param dbId the database id for the vehicle which the current rental needs to be removed
     * @return true on success false otherwise
     *
     * @throws java.sql.SQLException
     */
    private boolean deleteRental (long dbId) throws SQLException {
        String deleteCmd="delete from currentRental where vehicleId = '" + dbId +"'";

        return modifyTable(deleteCmd);
    }

    /**
     * Gets the row id from a newly inserted item with an autoincrementing id
     * Note this assumes that the id is in the first column of the table
     * If there is an error dump the stack and print an informative error message
     * @return the rowid or -1 if there is an error
     */
    private long getNewRowid() throws SQLException {
        long rowid = -1;
        try {
            current_resultset = dbconn.getGeneratedKeys();
            if (current_resultset.next()) {
                rowid = current_resultset.getLong(1);
            }

        } catch (SQLException e) {
            // if there was a problem then complain and return -2
            System.err.println("Can not get new row id");
            throw new SQLException("getNewRowid failed", e);
        }
        return rowid;
    }


    /**
     * Checks if a vehicle exists in the database with a specified vehicleId
     *
     * @param testVehicle - vehicle to be tested
     * @return true if it is in the dabase,  falase otherwire
     * @throws java.sql.SQLException
     */
    public boolean dbExists( Vehicle testVehicle) throws SQLException {
        boolean success = false;
        String Query = "Select vin from Vehicle where vin = '" + testVehicle.getVehicleId() + "'";

        try {
            ResultSet rSVehicle = selectData(Query);
            if (rSVehicle.next()) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in " + Query);
            throw new SQLException ("dbExists(Veh9cle failed - for "+ Query +" " ,e);
        }
        return success;
    }

   /**
     * Checks if a VehicleType exists in the database with a specified name and option
     *
     * @param testVehicleType - VehicleType to be tested
     * @return true if it is in the dabase,  false otherwire
     * @throws java.sql.SQLException
     */
    public boolean dbExists( VehicleType testVehicleType) throws SQLException {
        boolean success = false;
        String Query = "Select dbId from VehicleType where vehicletype.type = '" + testVehicleType.getType() + "'" +
                " and vehicletype.option like '"+testVehicleType.getOption()+"'";

        try {
            ResultSet rSVehicleType = selectData(Query);
            if (rSVehicleType.next()) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in " + Query);
            throw new SQLException ("dbExists(VehicleType) failed - for "+ Query +" " ,e);
        }
        return success;
    }

    /**
     * Checks if a CreditCard exists in the database with a specified name and number
     *
     * @param testCreditCard - CreditCard to be tested
     * @return true if it is in the dabase,  falase otherwire
     * @throws java.sql.SQLException
     */
    public boolean dbExists( CreditCard testCreditCard) throws SQLException {
        boolean success = false;
        String Query = "Select dbId from corperateCard where holderName = '" + testCreditCard.getName() +
                "' and number = '" +testCreditCard.getNumber()+"'";

        try {
            ResultSet rSCreditCard = selectData(Query);
            if (rSCreditCard.next()) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in " + Query);
            throw new SQLException ("dbExists(CreditCard) failed - for "+ Query +" " ,e);
        }
        return success;
    }

    /**
     * Checks if there is Rental for a vehicle by the vehicle database id
     *
     * @param dbId vehicle database id
     * @return true if it exists false otherwise
     * @throws java.sql.SQLException
     */
    public boolean dbExistsRental (long dbId) throws SQLException {
        boolean success=false;
        String Query = "Select vehicleId from currentRental where vehicleId = '" + dbId + "'";

        try {
            ResultSet rSVehicle = selectData(Query);
            if (rSVehicle.next()) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in " + Query);
            throw new SQLException ("dbExistsRental failed - for "+ Query +" " ,e);
        }
        return success;

    }

     /**
     * Checks if there is Corperate Card for a database Id
     *
     * @param dbId credit card database id
     * @return true if it exists false otherwise
     * @throws java.sql.SQLException
     */
    public boolean dbExistsCorperateCard (long dbId) throws SQLException {
        boolean success=false;
        String Query = "Select dbId from corperateCard where dbId = '" + dbId + "'";

        try {
            ResultSet rSVehicle = selectData(Query);
            if (rSVehicle.next()) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in " + Query);
            throw new SQLException ("dbExistsCorperateCard failed - for "+ Query +" " ,e);
        }
        return success;

    }

     /**
     * Checks if there is a Vehicle Type for a database Id
     *
     * @param dbId vehicle type database id
     * @return true if it exists false otherwise
     * @throws java.sql.SQLException
     */
    public boolean dbExistsVehicleType (long dbId) throws SQLException {
        boolean success=false;
        String Query = "Select dbId from VehicleType where dbId = '" + dbId + "'";

        try {
            ResultSet rSVehicle = selectData(Query);
            if (rSVehicle.next()) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in " + Query);
            throw new SQLException ("dbExistsVehicleType failed - for "+ Query +" " ,e);
        }
        return success;

    }




}
