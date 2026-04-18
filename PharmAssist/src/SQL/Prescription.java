package SQL;
import java.sql.Date;

public class Prescription {
    
    // -- Class attributes -- //
    private int prescriptionID;
    private int patientID;
    private Date creationDate;
    private Date completionDate;
    private String status;

    // -- Constructors -- //
    public Prescription(int prescriptionID, int patientID, Date creationDate, Date completionDate, String status) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.status = status;
    }

    public Prescription(){
        
    }

    // -- Getters and Setters -- //
    public int getPrescriptionID() {
        return this.prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public int getPatientID() {
        return this.patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCompletionDate() {
        return this.completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // -- toString Override for Testing -- //
    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionID=" + prescriptionID +
                ", patientID='" + patientID + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", completionDate='" + completionDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}