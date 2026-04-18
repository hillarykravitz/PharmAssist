package SQL;

import java.sql.Date;

public class RxDetails {
    
    // -- Class Attributes -- //
    private int prescriptionID;
    private int medID;
    private int dosageID;
    private String status;
    private Date pickupDate;

    // -- Constructors -- //
    public RxDetails(int prescriptionID, int medID, int dosageID, String status, Date pickupDate) {
        this.prescriptionID = prescriptionID;
        this.medID = medID;
        this.dosageID = dosageID;
        this.status = status;
        this.pickupDate = pickupDate;
    }

    public RxDetails() {

    }

    // -- Getters and Setters -- //
    public int getPrescriptionID() {
        return this.prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public int getMedID() {
        return this.medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
    }

    public int getDosageID() {
        return this.dosageID;
    }

    public void setDosageID(int dosageID) {
        this.dosageID = dosageID;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPickupDate() {
        return this.pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    // -- toString Override for Testing -- //
    @Override
    public String toString() {
        return "RxDetails{" +
                "prescriptionID=" + prescriptionID +
                ", medID='" + medID + '\'' +
                ", dosageID='" + dosageID + '\'' +
                ", status='" + status + '\'' +
                ", pickupDate='" + pickupDate + '\'' +
                '}';
    }


}
