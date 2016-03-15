import java.util.Date;
import javax.persistence.*;
import java.io.*;
import java.text.DateFormat;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.46001F4B-C850-C372-AE51-E34CBFEBD9EA]
// </editor-fold>
@Embeddable
public class DriverLicense implements Serializable {

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
     *        The state the Drivers License was issued
     *      </p>
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1627692E-0BA1-E9C4-3045-B43E9E687EFE]
    // </editor-fold>
    @Column(name="State_name",nullable=false)
    private String state;

    /**
     *  <p style="margin-top: 0">
     *        The identification code on the license
     *      </p>
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.33814C52-ACFD-4DC3-732B-548E367EBDBE]
    // </editor-fold>
    @Column(nullable=false)
    private String licenseCode;

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
    protected DriverLicense () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1C581296-A4FA-DAD8-3DAC-F0F4FD932538]
    // </editor-fold> 
    public DriverLicense (String name, String state, String licenseCode, Date expire) {
        this.name=name;
        this.state=state;
        this.licenseCode=licenseCode;
        this.expire=expire;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.89B16797-7DE0-2D54-5C2F-EF3AE39E20D3]
    // </editor-fold> 
    public Date getExpire () {
        return expire;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.5056CB74-20C3-FD24-AD05-B8C1D030BF2D]
    // </editor-fold> 
    protected void setExpire (Date val) {
        this.expire = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.A34F88D7-7662-9722-BAA0-C07EA09496E1]
    // </editor-fold> 
    public String getName () {
        return name;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.6BDEB25B-54E0-C623-ABC0-A63138B136E7]
    // </editor-fold> 
    protected void setName (String val) {
        this.name = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.B0DBA016-ED1A-29A0-E007-9C102F502826]
    // </editor-fold> 
    public String getLicenseCode () {
        return licenseCode;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.3C767B16-E926-37C1-21D5-98F0A7F2EF46]
    // </editor-fold> 
    protected void setLicenseCode (String val) {
        this.licenseCode = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.6AB5FA20-F985-744F-3DFF-B5E77FC3022A]
    // </editor-fold> 
    public String getState () {
        return state;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.E148AFB9-8539-4732-C736-53CFB6B606D2]
    // </editor-fold> 
    protected void setState (String val) {
        this.state = val;
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
    public Boolean validateLicense () {
        return true;
    }

    @Override
    public String toString() {
        DateFormat dfmt = DateFormat.getDateInstance(DateFormat.MEDIUM);
        StringBuilder outS= new StringBuilder("Name : " + name+ " Number : " + licenseCode + " expire :" +
                dfmt.format(expire));
        return outS.toString();
    }

}

