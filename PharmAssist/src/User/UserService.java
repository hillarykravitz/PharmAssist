package User;

import BCrypt.BCrypt;
import SQL.Prescription;
import SQL.Patient;
import SQL.Medication;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class UserService {

    // ----- UserDAO Object Instantiation ----- //
    private final UserDAO userDAO = new UserDAOImpl();

    // ----- Validate Login Method ----- //
    public User validateLogin(String username, String password) {
        // -- Create User by inputted username -- //
        User user = userDAO.findByUsername(username);
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

    // ----- PrescriptionID Search ----- //
    public Prescription prescriptionIDSearch(String stringID) {
        try {
            // -- Convert from String to int -- //
            int prescriptionID = Integer.parseInt(stringID);
            // -- Create Prescription object using Query -- //
            Prescription script = userDAO.findByPrescriptionID(prescriptionID);
            return script;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input at PID Search: " + stringID);
        }
        return null;
    }

    // ----- Patient Name Search ----- //
    public Patient patientNameSearch(String name) {
        // -- Split String to First and Last Names -- //
        String[] full = name.split(" ", 2);
        String first = full[0];
        String last = (full.length > 1) ? full[1] : "";
        // -- Create Patient object using Query -- //
        Patient patient = userDAO.findByPatientName(first, last);
        if (patient != null) {
            return patient;
        }
        return null;
    }

    // -- Patient DOB Search -- //
    public Patient patientDOBSearch(String birthdate) {
        // -- Convert from String to SQL Date -- //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(birthdate, formatter);
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        // -- Create Patient object using Query -- //
        Patient patient = userDAO.findByPatientDOB(sqlDate);
        if (patient != null) {
            return patient;
        }
        return null;
    }

    // -- Patient Phone Number Search -- //
    public Patient patientPhoneSearch(String phoneNumber) {
        // -- Create Patient object using Query -- //
        Patient patient = userDAO.findByPhoneNumber(phoneNumber);
        if (patient != null) {
            //System.out.println(patient.toString());
            return patient;
        }
        return null;
    }

    // -- Medication Name Search -- //
    public Medication medicationSearch(String medName) {
        // -- Create Medication object using Query -- //
        Medication medication = userDAO.findByMedName(medName);
        if (medication != null) {
            return medication;
        }
        return null;
    }

    // -- Medication Generic Name Search -- //
    public Medication genericSearch(String genericName) {
        // -- Create Medication object using Query -- //
        Medication medication = userDAO.findByGenericName(genericName);
        if (medication != null) {
            return medication;
        }
        return null;
    }
}