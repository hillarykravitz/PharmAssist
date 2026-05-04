package User;

import java.sql.Date;
import java.util.List;
import SQL.Dosage;
import SQL.Medication;
import SQL.Patient;
import SQL.PatientRxDetails;
import SQL.Prescription;

public interface UserDAO {
    
    // -- Required interface Methods -- //
    boolean addPatient(String first, String last, java.sql.Date birthdate, String phoneNumber);
    boolean createPrescription(int patientID, int medID, int dosageID);
    boolean fillPrescription(int rxID, int medID, int amount);
    boolean updatePatient(int patientID, String firstName, String lastName, java.sql.Date birthdate, String phoneNumber);
    boolean updatePrescriptionStatus(int rxID, String newStatus);
    Dosage findDosageByID(int dosageID);
    List<Medication> findByMedName(String medName);
    List<Medication> findByGenericName(String genericName);
    List<Medication> getAllMedications();
    List<Patient> findByPatientDOB(Date birthdate);
    List<Patient> findByPatientName(String first, String last);
    List<Patient> findByPhoneNumber(String phoneNumber);
    List<Prescription> findByPrescriptionID(int prescriptionID);
    List<Prescription> findByStatus(String status);
    List<Prescription> getPatientHistory(int patientID);
    List<Prescription> getFullPrescriptionDetails(String statusFilter);
    List<Prescription> retrievePrescriptions();
    Medication findMedicationByID(int medID);
    PatientRxDetails findRxDetailsByPrescriptionID(int prescriptionID);
    String checkForInteractions(int patientID, int newMedID);
    User findByUsername(String username);
    void logAccess(String table, String idColumn, int employeeID, int targetID);
    void updateMedicationStock(int medID, int additionalAmount);
}