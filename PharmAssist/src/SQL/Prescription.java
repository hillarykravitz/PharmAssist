package SQL;
import java.sql.Date;

public class Prescription {
    
    // ----- Class attributes ----- //
    // --- Persistent fields --- //
    private int prescriptionID;
    private Date creationDate;
    private Date completionDate;
    private String status;
    // --- Fetched/Transient fields --- //
    private String patientName;
    private Date birthdate;
    private String medName;
    private String strength;
    private String form;
    private String frequency;
    private int dosageQuantity;
    private String unit;
    public static final String STATUS_COMPLETE = "Complete";
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_READY_FOR_PICKUP = "Ready For Pickup";

    // ----- Constructors ----- //
    // --- Constructor for basic DB operations --- //
    public Prescription(int prescriptionID, Date creationDate, Date completionDate, String status) {
        this.prescriptionID = prescriptionID;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.status = status;
    }
    // --- Expanded Constructor for Fetched/Transient Fields --- //
    public Prescription(int prescriptionID, Date creationDate, Date completionDate, String status, String patientName, Date birthdate, String medName, int dosageQuantity, String unit) {
        this.prescriptionID = prescriptionID;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.status = status;
        this.patientName = patientName;
        this.birthdate = birthdate;
        this.medName = medName;
        this.dosageQuantity = dosageQuantity;
        this.unit = unit;
    }
    // --- Default Constructor --- //
    public Prescription(){}

    // ----- Getters and Setters ----- //
    // --- prescriptionID --- //
    public int getPrescriptionID() { return this.prescriptionID; }
    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }
    // --- creationDate --- //
    public Date getCreationDate() { return this.creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    // --- completionDate --- //
    public Date getCompletionDate() { return this.completionDate; }
    public void setCompletionDate(Date completionDate) { this.completionDate = completionDate; }
    // --- status --- //
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
    // --- patientName --- //
    public String getPatientName() { return this.patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    // --- birthdate --- //
    public Date getBirthdate() { return this.birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }
    // --- medName --- //
    public String getMedName() { return this.medName; }
    public void setMedName(String medName) { this.medName = medName; }
    // --- strength --- //
    public String getStrength() { return this.strength; }
    public void setStrength(String strength) { this.strength = strength; }
    // --- form --- //
    public String getForm() { return this.form; }
    public void setForm(String form) { this.form = form; }
    // --- frequency --- //
    public String getFrequency() { return this.frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    // --- dosageQuantity --- //
    public int getDosageQuantity() { return this.dosageQuantity; }
    public void setDosageQuantity(int dosageQuantity) { this.dosageQuantity = dosageQuantity; }
    // --- unit --- //
    public String getUnit() { return this.unit; }
    public void setUnit(String unit) { this.unit = unit; }

    // -- toString Override for Testing -- //
    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionID=" + prescriptionID +
                ", creationDate='" + creationDate + '\'' +
                ", completionDate='" + completionDate + '\'' +
                ", status='" + status + '\'' +
                ", patientName='" + patientName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", medName='" + medName + '\'' +
                ", dosageQuantity='" + dosageQuantity + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}