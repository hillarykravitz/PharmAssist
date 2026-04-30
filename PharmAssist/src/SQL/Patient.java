package SQL;
import java.sql.Date;

public class Patient {
    
    // -- Class Attributes -- //
    private int patientID;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String phoneNumber;

    // -- Constructors -- //
    public Patient(int patientID, String firstName, String lastName, Date birthdate, String phoneNumber) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
    }

    public Patient() {};

    // Getters and Setters -- //
    public int getPatientID() { return this.patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

    public String getFirstName() { return this.firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return this.lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Date getBirthdate() { return this.birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }

    public String getPhoneNumber() { return this.phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    // -- toString Override for Testing -- //
    @Override
    public String toString() {
        return "Patient{" +
                "patientID=" + patientID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}