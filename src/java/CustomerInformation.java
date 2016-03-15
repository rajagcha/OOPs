import javax.persistence.*;
import java.io.*;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.46803810-11A9-BD47-C759-7F8FDF4D7165]
// </editor-fold>
@Embeddable
public class CustomerInformation implements Serializable {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.175BFC0E-B088-B3D3-76D3-BDEA0886290D]
    // </editor-fold>
    private String name;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.30405D16-76BB-35D3-1880-4F98D6678DDF]
    // </editor-fold>
    @Column(nullable=false)
    private long phoneNumber;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E838DCEB-7882-3E2A-6BF7-7C2AF8C2447D]
    // </editor-fold>
    @Embedded
    @Column(nullable=false)
    private DriverLicense driversLicense;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BC75EC03-BD90-C559-3622-452CC17B00F1]
    // </editor-fold>
    @Embedded
    @Column(nullable=false)
    private CreditCard creditCard;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A669F181-2C16-C319-2B2E-F9D5A812A6A0]
    // </editor-fold> 
    protected CustomerInformation () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.34F70022-5BE3-AE71-E59E-0FBAAA936F45]
    // </editor-fold> 
    public CustomerInformation (String name, long phoneNumber, DriverLicense driverLicense,
            CreditCard creditCard) throws BadInputException {

        // ensure that there is a name
        if (name == null || name.length() <2)
            throw new BadInputException ("Customer name need to exist and be longer than 2 characters");

        // ensure that there is a phone number
        if (phoneNumber <1999999)
            throw new BadInputException ("Phone number must be 7 digits for Customer");

        // ensure that there is a credit card
        if (creditCard == null)
            throw new BadInputException ("Customer must have a credit card to rent vehicle");

        // ensure that there is a drivers license
        if (driverLicense == null)
            throw new BadInputException("Customer must have a driver license to rent vehicle");

        this.name=name;
        this.phoneNumber=phoneNumber;
        this.creditCard=creditCard;
        this.driversLicense = driverLicense;

    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.B4177B8B-A997-AC23-0F33-60F29E6A2094]
    // </editor-fold> 
    public CreditCard getCreditCard () {
        return creditCard;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.91D0C81F-6D48-85BC-151E-2EAC1FC3E9BF]
    // </editor-fold> 
    public DriverLicense getDriversLicense () {
        return driversLicense;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.F915ECCE-7A55-F176-8069-F6FCB908F85B]
    // </editor-fold> 
    public String getName () {
        return name;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7A83ABA8-545D-5308-2D42-38C4F91D60DD]
    // </editor-fold> 
    public long getPhoneNumber () {
        return phoneNumber;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DBE5BCF8-7A0F-2A69-0451-9C780C70CBE8]
    // </editor-fold> 
    protected void setCreditCard (CreditCard val) {
        this.creditCard = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.AC78E3DC-E677-82B4-7EE0-962A68F88124]
    // </editor-fold> 
    protected void setDriversLicense (DriverLicense val) {
        this.driversLicense = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7D37FA3A-F15B-4C53-D661-ED380A385EAD]
    // </editor-fold> 
    protected void setName (String val) {
        this.name = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.FA0CDAB1-8814-6052-8D95-D6C6F1926A67]
    // </editor-fold> 
    protected void setPhoneNumber (long val) {
        this.phoneNumber = val;
    }

}

