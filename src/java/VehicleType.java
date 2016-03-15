import java.io.Serializable;
import java.text.NumberFormat;

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.7393B061-6F8E-2A02-8EBE-E2DB6200534E]
// </editor-fold> 
public class VehicleType implements Serializable{

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.DAE5ABCD-48A6-EDD8-4CE0-966FDD0B98ED]
    // </editor-fold> 
//    COMPACT2DOOR("Compact/Mid size 2 Door",15.00),

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4C1AC3C2-8DC3-24A7-D1F0-0BF8EA3F7F96]
    // </editor-fold> 
  //  COMPACT4DOOR("Compact/Mid size 4 Door",20.00),

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.47BF85F3-E7DF-E80D-1C97-B8BF6A815FC5]
    // </editor-fold> 
    //STANDARDNOCHILD("Standard/Large/Family size without Child Seat",30.00),

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.90EA8608-5899-909C-082D-C895050625EB]
    // </editor-fold> 
//    STANARDWITHCHILD("Standard/Large/Family size with Child Seat",40.00),

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B7A36868-EBA8-F47D-F24A-5C6A17C59BA1]
    // </editor-fold> 
//    PREMIUM("Premium/Luxery",55.00),

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.7521211D-F52D-FBA2-B604-A7FF6BEB1911]
    // </editor-fold> 
//    SUVNOTV("SUV/Minivan without TV",55.00),

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.0704B93A-41C5-30C1-EF4B-2353392FE18E]
    // </editor-fold> 
//    SUVWITHTV("SUV/Minivan with TV",65.00),

//    CONVERTIBLE("Convertible",65.00);
    ;

 
    private long hid;
    private String type;
    private String option;
    private double rate;

   public VehicleType (String type, String option ,double rentalRate) {
        this.type=type;
        this.option=option;
        this.rate=rentalRate;
        this.hid=-1L;
    }

   /**
    * Constructor for the database metho
    * @param type
    * @param option
    * @param rentalRate
    * @param hid
    */
    public VehicleType (String type, String option ,double rentalRate,long hid) {
        this.type=type;
        this.option=option;
        this.rate=rentalRate;
        this.hid=hid;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.EAFA91E6-94DF-09B0-19D9-168536C18352]
    // </editor-fold>
    /**
     * The dailey rental rate for the vehicle
     * @return the daily rental rate for the vehicle
     */
    public double getRate () {
        return rate;
    }

    public String getType() {
        return type;
    }

    public String getOption() {
        return option;
    }

    public long getDbId () {
        return hid;
    }

    public void setDbId (long hid) {
        this.hid=hid;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.39C70F94-2C38-DDC1-F124-6641DB606A98]
    // </editor-fold>
    /**
     * Human readable verviosn of the enumeration - used for pretty displays
     * @return the name of the vehicle type
     */
    public String getName () {
        return type+" "+option;
    }

    public boolean typeEquals(String typeOption) {
        if (typeOption.equalsIgnoreCase(this.getName()))
            return true;
        return false;
    }

    public boolean typeEquals(String type, String option) {
        if (type.equalsIgnoreCase(this.type) && option.equalsIgnoreCase(this.option))
            return true;
        return false;
    }

    @Override
    public String toString() {
        NumberFormat dft=NumberFormat.getCurrencyInstance();
        String d = dft.format(rate);
        return String.format ("%s %s",getName(),d);
    }
}

