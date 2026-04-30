package User;

import BCrypt.BCrypt;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import SQL.Dosage;
import SQL.Medication;
import SQL.Prescription;
import SQL.PatientRxDetails;
import SQL.Patient;


public class UserService {

    // ----- UserDAO Object Instantiation ----- //
    private final UserDAO userDAO = new UserDAOImpl();

    // ----- Add Prescription (Pharmacist Only) ----- //
    public boolean addNewPrescription(int patientID, int medID, int dosageID) {
        // --- Verification: check if IDs are valid (> 0)
        if (patientID <= 0 || medID <= 0 || dosageID <= 0) {
            return false;
        }
        return userDAO.createPrescription(patientID, medID, dosageID);
    }

    // ----- Add Patient ----- //
    public boolean addPatient(String firstName, String lastName, java.sql.Date birthdate, String phoneNumber) {
        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty() || birthdate == null) {
            System.out.println("Validation Error: Name and Birthdate are required.");
            return false;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("Validation Error: Invalid phone number format. Use XXX-XXX-XXXX");
            return false;
        }

        return userDAO.addPatient(firstName, lastName, birthdate, phoneNumber);
    }

    // ----- canFillPrescription? for Button Display ----- //
    public boolean canFillPrescription(int medID, int dosageID) {
        int required = getPrescriptionQuantity(dosageID);
        if (required <= 0) {
            System.out.println("Error: Dosage quantity is invalid.");
            return false;
        }
        return hasEnoughStock(medID, required);
    }

    // ----- Complete Prescription ----- //
    public boolean completePrescription(int rxID) {
        return userDAO.updatePrescriptionStatus(rxID, "Complete");
    }

    // ----- Fill Prescription ----- //
    public boolean fillPrescription(int rxID, int medID, int amount) {
        return userDAO.fillPrescription(rxID, medID, amount);
    }

    // ----- Check for Stock Prior to Filling ----- //
    public boolean hasEnoughStock(int medID, int requiredAmount) {
        Medication med = userDAO.findMedicationByID(medID);
        return med != null && med.getPharmacyQuantity() >= requiredAmount;
    }

    // ----- Validates Phone Number ----- //
    private boolean isValidPhoneNumber(String phone) {
        if (phone == null) return false;
        // --- Regex Explanation:
        // ^\\d{10}$ ---> Matches exactly 10 digits
        // ^(\\d{3}-){2}\\d{4}$ ---> Matches XXX-XXX-XXXX 
        // --- So this String is checking if it matches exactly
        // --- 10 digits, or (|) is in that format
        String regex = "^(\\d{3}-){2}\\d{4}$|^\\d{10}$";
        return phone.matches(regex);
    }

    // ----- Update Patient Information ----- //
    public boolean updatePatient(int id, String first, String last, java.sql.Date dob, String phone) {
        if (first == null || first.isEmpty() || last == null || last.isEmpty() || dob == null) return false;
        if (!isValidPhoneNumber(phone)) return false;

        return userDAO.updatePatient(id, first, last, dob, phone);
    }

    // ----- Update Prescription Status ----- //
    public boolean updatePrescriptionStatus(int rxID, String newStatus) {
        return userDAO.updatePrescriptionStatus(rxID, newStatus);
    }

    // ----- Get Prescription Quantity ----- //
    public int getPrescriptionQuantity(int dosageID) {
        Dosage dosage = userDAO.findDosageByID(dosageID);
        if (dosage != null) {
            return dosage.getQuantity();
        }
        return 0;
    }

    // ----- Patient Name Search ----- //
    public List<Patient> patientNameSearch(String name) {
        // -- Split String to First and Last Names -- //
        String[] full = name.split(" ", 2);
        String first = full[0];
        String last = (full.length > 1) ? full[1] : "";
        // -- Create Patient object using Query -- //
        return userDAO.findByPatientName(first, last);
    }

    // ----- Patient DOB Search ----- //
    public List<Patient> patientDOBSearch(String birthdate) {
        // -- Convert from String to SQL Date -- //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(birthdate, formatter);
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        // -- Create Patient object using Query -- //
        List<Patient> patients = userDAO.findByPatientDOB(sqlDate);
            return patients;
    }

    // ----- Patient Phone Number Search ----- //
    public List<Patient> patientPhoneSearch(String phoneNumber) {
        // -- Create Patient object using Query -- //
        List<Patient> patients = userDAO.findByPhoneNumber(phoneNumber);
            return patients;
    }

    // ----- Medication Generic Name Search ----- //
    public List<Medication> genericSearch(String genericName) {
        // -- Create Medication object using Query -- //
        List<Medication> medications = userDAO.findByGenericName(genericName);
            return medications;
    }

    // ----- Get All Medications ----- //
    public List<Medication> getAllMedications() {
         return userDAO.getAllMedications();
    }

    // ----- Medication Name Search ----- //
    public List<Medication> medicationSearch(String medName) {
        // -- Create Medication object using Query -- //
        List<Medication> medications = userDAO.findByMedName(medName);
            return medications;
    }

    // ----- Get Prescription with Details ----- //
    public List<Prescription> getDetailedPrescriptions(String status) {
        return userDAO.getFullPrescriptionDetails(status);
    }

    // ----- Get Patient History ----- //
    public List<Prescription> getPatientHistory(int patientID) {
        return userDAO.getPatientHistory(patientID);
    }

    // ----- Get Queue by Pending Status ----- //
    public List<Prescription> getPendingQueue() {
        return userDAO.getFullPrescriptionDetails("Pending");
    }

    // ----- Get Prescription based on Status ----- //
    public List<Prescription> getPrescriptionByStatus(String status) {
        return userDAO.findByStatus(status);
    }

    // ----- Get List of Prescriptions ----- //
    public List<Prescription> getScriptList() {
        return userDAO.retrievePrescriptions();
    }

    // ----- PrescriptionID Search ----- //
    public List<Prescription> prescriptionIDSearch(String stringID) {
        try {
            // -- Convert from String to int -- //
            int prescriptionID = Integer.parseInt(stringID);
            // -- Create Prescription object using Query -- //
            List<Prescription> scripts = userDAO.findByPrescriptionID(prescriptionID);
            return scripts;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input at PID Search: " + stringID);
        }
        return null;
    }
    
    // ----- Get Interaction Warning ----- //
    public String getInteractionWarning(int patientID, int medID) {
        return userDAO.checkForInteractions(patientID, medID);
    }

    // ----- Get PatientRxDetails ----- //
    public PatientRxDetails getRxDetails(int prescriptionID) {
        return userDAO.findRxDetailsByPrescriptionID(prescriptionID);
    }

    // ----- Validate Login ----- //
    public User validateLogin(String username, String password) {
        // -- Create User by inputted username -- //
        User user = userDAO.findByUsername(username);
        // -- Check if User exists to prevent NullPointerException -- //
        if (user == null) {
            System.out.println("No user found with username: " + username);
            return null;
        }
        // -- Use BCrypt checkpw method with inputted password and stored password hash -- //
        // -- If it returns True: return the User object -- //
        // -- Otherwise return null -- //
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            System.out.println(user.toString());
            return user;
        }
        System.out.println("No user found.");
        return null;
    }

    // ----- Log Medication Access ----- //
    public void logMedAccess(int empID, int medID) {
        userDAO.logAccess("empMedAccessLog", "medID", empID, medID);
    }
    
    // ----- Log Patient Access ----- //
    public void logPatientAccess(int empID, int patientID) {
        userDAO.logAccess("empPatientAccessLog", "patientID", empID, patientID);
    }

    // ----- Log Prescription Access ----- //
    public void logRxAccess(int empID, int rxID) {
        userDAO.logAccess("empRxAccessLog", "prescriptionID", empID, rxID);
    }

    // ----- updateMedicationStock ----- //
    public void updateMedicationStock(int medID, int additionalAmount) {
        userDAO.updateMedicationStock(medID, additionalAmount);
    }
}