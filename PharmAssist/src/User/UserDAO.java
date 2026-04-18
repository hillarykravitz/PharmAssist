package User;

import java.sql.Date;
import SQL.Medication;
import SQL.Prescription;
import SQL.Patient;
// import SQL.RxDetails;

public interface UserDAO {
    
    // -- Required interface Methods -- //

    User findByUsername(String username);

    Prescription findByPrescriptionID(int prescriptionID);

    Patient findByPatientName(String first, String last);

    Patient findByPatientDOB(Date birthdate);

    Patient findByPhoneNumber(String phoneNumber);

    Medication findByMedName(String medName);

    Medication findByGenericName(String genericName);

    //RxDetails findByStatus(String status);
}