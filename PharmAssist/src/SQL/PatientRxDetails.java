package SQL;

import java.sql.Date;

public class PatientRxDetails {
    
    // -- Class Attributes -- //
    private int prescriptionID;
    private int medID;
    private int dosageID;
    private int patientID;
    private Date pickupDate;

    // -- Constructors -- //
    public PatientRxDetails(int prescriptionID, int medID, int dosageID, int patientID, Date pickupDate) {
        this.prescriptionID = prescriptionID;
        this.medID = medID;
        this.dosageID = dosageID;
        this.patientID = patientID;
        this.pickupDate = pickupDate;
    }

    public PatientRxDetails() {}

    // -- Getters and Setters -- //
    public int getPrescriptionID() { return this.prescriptionID; }
    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }

    public int getMedID() { return this.medID; }
    public void setMedID(int medID) { this.medID = medID; }

    public int getDosageID() { return this.dosageID; }
    public void setDosageID(int dosageID) { this.dosageID = dosageID; }

    public int getPatientID() { return this.patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

    public Date getPickupDate() { return this.pickupDate; }
    public void setPickupDate(Date pickupDate) { this.pickupDate = pickupDate; }

    // -- toString Override for Testing -- //
    @Override
    public String toString() {
        return "PatientRxDetails{" +
                "prescriptionID=" + prescriptionID +
                ", medID='" + medID + '\'' +
                ", dosageID='" + dosageID + '\'' +
                ", patientID='" + patientID + '\'' +
                ", pickupDate='" + pickupDate + '\'' +
                '}';
    }
}
