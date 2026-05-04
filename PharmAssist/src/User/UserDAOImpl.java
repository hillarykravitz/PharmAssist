package User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import SQL.Prescription;
import SQL.PatientRxDetails;
import SQL.Patient;
import SQL.Medication;
import SQL.DatabaseManager;
import SQL.Dosage;

public class UserDAOImpl implements UserDAO {

    // ----- addPatient Override ----- //
    @Override
    public boolean addPatient(String first, String last, java.sql.Date birthdate, String phoneNumber) {
        String sql = "INSERT INTO Patients (firstName, lastName, birthdate, phoneNumber) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, first);
                pstmt.setString(2, last);
                pstmt.setDate(3, birthdate);
                pstmt.setString(4, phoneNumber);
                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("An error occurred at addPatient: " + e.getMessage());
                return false;
            }
    }

    // ----- Create Prescription Override ----- //
    @Override
    public boolean createPrescription(int patientID, int medID, int dosageID) {
        String insertPrescriptionSQL = "INSERT INTO Prescriptions (status, creationDate) VALUES ('Pending', CURRENT_DATE)";
        String insertDetailsSQL = "INSERT INTO PatientRxDetails (prescriptionID, medID, dosageID, patientID) VALUES (?, ?, ?, ?)";

        // --- Insert into Prescriptions table --- //
        Connection conn = null;
        try {
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false); // Start transaction
            int newRxID = -1;
            try (PreparedStatement pstmt1 = conn.prepareStatement(insertPrescriptionSQL, Statement.RETURN_GENERATED_KEYS)) {
                pstmt1.executeUpdate();
                ResultSet rs = pstmt1.getGeneratedKeys();
                if (rs.next()) {
                    newRxID = rs.getInt(1);
                }
            }
            // --- Insert into PatientRxDetails table using the new ID --- //
            if (newRxID != -1) {
            try (PreparedStatement pstmt2 = conn.prepareStatement(insertDetailsSQL)) {
                pstmt2.setInt(1, newRxID);
                pstmt2.setInt(2, medID);
                pstmt2.setInt(3, dosageID);
                pstmt2.setInt(4, patientID);
                pstmt2.executeUpdate();
            }
            conn.commit(); // --- Success? Save both
            return true;
            }
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        }
        return false;
    }

    // ----- fillPrescription Override ----- //
    @Override
    public boolean fillPrescription(int rxID, int medID, int amount) {
        String updateRX = "UPDATE Prescriptions SET status = 'Waiting For Verification' WHERE prescriptionID = ?";
        String updateStock = "UPDATE Medications " +
                                "SET pharmacyQuantity = pharmacyQuantity - ?, " +
                                "isInStock = CASE WHEN (pharmacyQuantity - ?) > 0 THEN 1 ELSE 0 END " +
                                "WHERE medID = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false); // Starts transaction

            try (PreparedStatement ps1 = conn.prepareStatement(updateRX);
                PreparedStatement ps2 = conn.prepareStatement(updateStock)) {

                    ps1.setInt(1, rxID);
                    ps1.executeUpdate();

                    ps2.setInt(1, amount);
                    ps2.setInt(2, amount); // 2nd parameter for CASE
                    ps2.setInt(3, medID);
                    ps2.executeUpdate();

                    conn.commit(); // --- Saves changes to SQL DB
                    return true;
                } catch (SQLException e) {
                    conn.rollback(); // --- Undo if something fails
                    return false;
                }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ----- updatePatient Override ----- // 
    @Override
    public boolean updatePatient(int patientID, String firstName, String lastName, java.sql.Date birthdate, String phoneNumber) {
        String sql = "UPDATE Patients SET firstName = ?, lastName = ?, birthdate = ?, phoneNumber = ? WHERE patientID = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setDate(3, birthdate);
                pstmt.setString(4, phoneNumber);
                pstmt.setInt(5, patientID);

                return pstmt.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error updating patient: " + e.getMessage());
                return false;
            }
    }
    
    // ----- updatePrescriptionStatus Override ----- //
    @Override
    public boolean updatePrescriptionStatus(int rxID, String newStatus) {
        String sql; // = "UPDATE Prescriptions SET status = ?, completionDate = CURRENT_DATE WHERE prescriptionID = ?";
        if ("Complete".equalsIgnoreCase(newStatus)) {
            sql = "UPDATE Prescriptions SET status = ?, completionDate = CURRENT_DATE WHERE prescriptionID = ?";
        } else {
            sql = "UPDATE Prescriptions SET status = ? WHERE prescriptionID = ?";
        }
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newStatus);
                pstmt.setInt(2, rxID);

                return pstmt.executeUpdate() > 0;

            } catch (SQLException e) {
                System.err.println("Error updating status: " + e.getMessage());
                return false;
            }
    }

    // ----- findByDosageID Override ----- //
    @Override
    public Dosage findDosageByID(int dosageID) {
        String sql = "SELECT * FROM Dosages WHERE dosageID = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, dosageID);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return new Dosage(
                        rs.getInt("dosageID"),
                        rs.getInt("medID"),
                        rs.getString("strength"),
                        rs.getString("form"),
                        rs.getString("appearance"),
                        rs.getString("frequency"),
                        rs.getInt("quantity"),
                        rs.getString("unit")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();   
            }
            return null;
    }

    // -- findByMedName Override -- //
    @Override
    public List<Medication> findByMedName(String medName) {
        String sql = "SELECT * FROM Medications WHERE medName LIKE ?";
        List<Medication> mList = new ArrayList<>();
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, medName);
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        boolean inStock;
                        if (rs.getInt("isInStock") == 0) {
                            inStock = false;
                        } else {
                            inStock = true;
                        }
                        mList.add(new Medication(
                            rs.getInt("medID"), 
                            rs.getString("medName"), 
                            rs.getString("genericName"), 
                            inStock, 
                            rs.getInt("pharmacyQuantity")
                        ));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByMedName");
                }
                return mList;
    }

    // -- findByGenericName Override -- //
    @Override
    public List<Medication> findByGenericName(String genericName) {
        String sql = "SELECT * FROM Medications WHERE genericName LIKE ?";
        List<Medication> mList = new ArrayList<>();
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, genericName);
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        boolean inStock;
                        if (rs.getInt("isInStock") == 0) {
                            inStock = false;
                        } else {
                            inStock = true;
                        }
                        mList.add(new Medication(
                            rs.getInt("medID"), 
                            rs.getString("medName"), 
                            rs.getString("genericName"), 
                            inStock, 
                            rs.getInt("pharmacyQuantity")
                        ));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at executeSearch");
                }
                return mList;
    }

    // ----- getAllMedications Override ----- //
    @Override
    public List<Medication> getAllMedications() {
        List<Medication> medList = new ArrayList<>();
        String sql = "SELECT * FROM Medications ORDER BY medName ASC";

        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Medication med = new Medication();
                    med.setMedID(rs.getInt("medID"));
                    med.setMedName(rs.getString("medName"));
                    med.setGenericName(rs.getString("genericName"));
                    med.setIsInStock(rs.getBoolean("isInStock"));
                    med.setPharmacyQuantity(rs.getInt("pharmacyQuantity"));
                    medList.add(med);
                }
            } catch (SQLException e) {
                System.err.println("Error fetching medications: " + e.getMessage());
            }
            return medList;
    }

    // -- findByPatientDOB Override -- //
    @Override
    public List<Patient> findByPatientDOB(Date birthdate) {
        String sql = "SELECT * FROM Patients WHERE birthdate LIKE ?";
        List<Patient> pList = new ArrayList<>();
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setDate(1, birthdate);
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        pList.add(new Patient(
                            rs.getInt("patientID"), 
                            rs.getString("firstName"), 
                            rs.getString("lastName"), 
                            rs.getDate("birthdate"), 
                            rs.getString("phoneNumber")
                        ));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByPatientDOB");
                }
                return pList;
    }

     // -- findByPatientName Override -- //
    @Override
    public List<Patient> findByPatientName(String first, String last) {
        String fullNameSearch = "%" + first + " " + last + "%";
        String sql = "SELECT * FROM Patients WHERE CONCAT(firstName, ' ', lastName) LIKE ?";
        List<Patient> pList = new ArrayList<>();
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, fullNameSearch);
                    // pstmt.setString(2, last);
                    
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        pList.add(new Patient(
                            rs.getInt("patientID"), 
                            rs.getString("firstName"), 
                            rs.getString("lastName"), 
                            rs.getDate("birthdate"), 
                            rs.getString("phoneNumber")
                        ));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByPatientName");
                }
                return pList;
    }

    // -- findByPhoneNumber Override -- //
    @Override
    public List<Patient> findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM Patients WHERE phoneNumber LIKE ?";
        List<Patient> pList = new ArrayList<>();
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, phoneNumber);
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        pList.add(new Patient(
                            rs.getInt("patientID"), 
                            rs.getString("firstName"), 
                            rs.getString("lastName"), 
                            rs.getDate("birthdate"), 
                            rs.getString("phoneNumber")
                        ));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByPhoneNumber");
                }
                return pList;
    }

    // -- findByPrescriptionID Override -- //
    @Override
    public List<Prescription> findByPrescriptionID(int prescriptionID) {
        String sql = "SELECT * FROM Prescriptions WHERE prescriptionID = ?";
        List<Prescription> pList = new ArrayList<>();
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, prescriptionID);
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        pList.add(new Prescription(
                            rs.getInt("prescriptionID"), 
                            // rs.getInt("patientID"), 
                            rs.getDate("creationDate"), 
                            rs.getDate("completionDate"), 
                            rs.getString("status")
                        ));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByPrescriptionID");
                }
                return pList;
    }

    // ----- findByStatus Override ----- //
    @Override 
    public List<Prescription> findByStatus(String status) {
        String sql = "SELECT * FROM Prescriptions WHERE status = ?";
        List<Prescription> pList = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, status);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    pList.add(new Prescription(
                        rs.getInt("prescriptionID"),
                        rs.getDate("creationDate"),
                        rs.getDate("completionDate"),
                        rs.getString("status")
                    ));
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception at findByStatus: " + e);
            }
        return pList;
    }

    // ----- getPatientHistory Override ----- //
    @Override
    public List<Prescription> getPatientHistory(int patientID) {
        List<Prescription> history = new ArrayList<>();
        String sql = "SELECT pr.prescriptionID, pr.status, pr.creationDate, pr.completionDate, " +
                    "m.medName, d.strength, d.form, d.quantity, d.unit " +
                    "FROM Prescriptions pr " +
                    "JOIN PatientRxDetails rd ON pr.prescriptionID = rd.prescriptionID " +
                    "JOIN Medications m ON rd.medID = m.medID " +
                    "JOIN Dosages d ON rd.dosageID = d.dosageID " +
                    "WHERE rd.patientID = ? AND pr.status = 'Complete' " +
                    "ORDER BY pr.completionDate DESC";

        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, patientID);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Prescription rx = new Prescription();
                    rx.setPrescriptionID(rs.getInt("prescriptionID"));
                    rx.setMedName(rs.getString("medName"));
                    rx.setStrength(rs.getString("strength"));
                    rx.setDosageQuantity(rs.getInt("quantity"));
                    rx.setUnit(rs.getString("unit"));
                    rx.setCreationDate(rs.getDate("creationDate"));
                    rx.setCompletionDate(rs.getDate("completionDate"));
                    rx.setStatus("Complete");
                    history.add(rx);
                }
            } catch (SQLException e) {
                System.err.println("An SQL Error occurred: " + e.getMessage());
            }
        return history;
    }

    // ----- getFullPrescriptionDetails Override ----- //
    @Override
    public List<Prescription> getFullPrescriptionDetails(String statusFilter) {
        List<Prescription> list = new ArrayList<>();

        // The statusFilter allows use for the Queue (ALL) or specific 
        // buttons (Pending/Ready For Pickup)
        String sql = "SELECT pr.prescriptionID, pr.creationDate, pr.completionDate, pr.status, " +
                    "p.firstName, p.lastName, p.birthdate, " +
                    "m.medName, d.strength, d.form, d.frequency, " + 
                    "d.quantity, d.unit " +
                        "FROM Prescriptions pr " +
                        "JOIN PatientRxDetails rd ON pr.prescriptionID = rd.prescriptionID " +
                        "JOIN Patients p ON rd.patientID = p.patientID " +
                        "JOIN Medications m ON rd.medID = m.medID " +
                        "JOIN Dosages d ON rd.dosageID = d.dosageID";

        // If a status is provided, add a WHERE clause
        if (statusFilter != null && !statusFilter.equals("All")) {
            sql += " WHERE pr.status = ?";
        }

        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (statusFilter != null && !statusFilter.equals("All")) {
                    pstmt.setString(1, statusFilter);
                }

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Prescription rx = new Prescription();
                    rx.setPrescriptionID(rs.getInt("prescriptionID"));
                    rx.setCreationDate(rs.getDate("creationDate"));
                    rx.setCompletionDate(rs.getDate("completionDate"));
                    rx.setStatus(rs.getString("status"));
                    rx.setPatientName(rs.getString("firstName") + " " + rs.getString("lastName"));
                    rx.setBirthdate(rs.getDate("birthdate"));
                    rx.setMedName(rs.getString("medName") + " (" + rs.getString("strength") + ")");
                    rx.setStrength(rs.getString("strength"));
                    rx.setForm(rs.getString("form"));
                    rx.setFrequency(rs.getString("frequency"));
                    rx.setDosageQuantity(rs.getInt("quantity"));
                    rx.setUnit(rs.getString("unit"));
                    System.out.println("Found Prescription: " + rx.getPrescriptionID() + " for " + rx.getPatientName());

                    list.add(rx);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }

    // -- retrievePrescriptions Override -- //
    @Override
    public List<Prescription> retrievePrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        String sql = "SELECT prescriptionID, creationDate, completionDate, status FROM Prescriptions";

        try (Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Prescription p = new Prescription(
                        rs.getInt("prescriptionID"),
                        rs.getDate("creationDate"),
                        rs.getDate("completionDate"),
                        rs.getString("status"),
                        rs.getString("firstName") + " " + rs.getString("lastName"),
                        rs.getDate("birthdate"),
                        rs.getString("medName"),
                        rs.getInt("quantity"),
                        rs.getString("unit") 
                    );
                    prescriptions.add(p);
                }
            } catch (SQLException e) {
                System.out.println("An error has occurred at retrievePrescriptions(): " + e);
        }
        return prescriptions;
    }

    // ---- findMedicationByID Override ----- //
    @Override
    public Medication findMedicationByID(int medID) {
        String sql = "SELECT * FROM Medications WHERE medID = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, medID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new Medication(
                        rs.getInt("medID"),
                        rs.getString("medName"),
                        rs.getString("genericName"),
                        rs.getInt("pharmacyQuantity") > 0,
                        rs.getInt("pharmacyQuantity")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }

    // ----- findRxDetailsByPrescriptionID Override ----- //
    @Override
    public PatientRxDetails findRxDetailsByPrescriptionID(int prescriptionID) {
        String sql = "SELECT * FROM PatientRxDetails WHERE prescriptionID = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, prescriptionID);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return new PatientRxDetails(
                        rs.getInt("prescriptionID"),
                        rs.getInt("medID"),
                        rs.getInt("dosageID"),
                        rs.getInt("patientID"),
                        rs.getDate("pickupDate")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }

    // ----- Check for Drug Interactions Override ----- //
    @Override
    public String checkForInteractions(int patientID, int newMedID) {
        String sql = "SELECT di.description, di.level FROM DrugInteractions di " +
                    "WHERE (" +
                    " (di.medID_1 = ? AND di.medID_2 IN (SELECT prd.medID FROM PatientRxDetails prd JOIN Prescriptions p ON prd.prescriptionID = p.prescriptionID WHERE prd.patientID = ? AND p.status IN ('Complete', 'Ready For Pickup'))) " +
                    " OR " +
                    " (di.medID_2 = ? AND di.medID_1 IN (SELECT prd.medID FROM PatientRxDetails prd JOIN Prescriptions p ON prd.prescriptionID = p.prescriptionID WHERE prd.patientID = ? AND p.status IN ('Complete', 'Ready For Pickup')))" +
                    ")";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, newMedID);
                pstmt.setInt(2, patientID);
                pstmt.setInt(3, newMedID);
                pstmt.setInt(4, patientID);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return rs.getString("level") + ": " + rs.getString("description");
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving interaction(s).");
            }
        return null;
    }

    // ----- findByUsername Override ----- //
    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM Employees WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return new User(rs.getInt("employeeID"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("username"), rs.getString("role"), rs.getString("passwordHash"), rs.getString("email"));
                }
        } catch (SQLException e) {
            System.out.println("An SQLException has occurred at the implemented level.");
        }
        return null;
    }

    // ----- logAccess Override ----- //
    @Override
    public void logAccess(String table, String idColumn, int employeeID, int targetID) {
        String sql = "INSERT INTO " + table + " (employeeID, " + idColumn + ") VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, employeeID);
                pstmt.setInt(2, targetID);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Audit Log Failed: " + e.getMessage());
            }
    }

    // ----- updateMedicationStock Override ----- //
    @Override
    public void updateMedicationStock(int medID, int additionalAmount) {
        String sql = "UPDATE Medications " +
                        "SET pharmacyQuantity = pharmacyQuantity + ?, " +
                        "isInStock = CASE WHEN (pharmacyQuantity + ?) > 0 THEN 1 ELSE 0 END " +
                        "WHERE medID = ?";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, additionalAmount);
                pstmt.setInt(2, additionalAmount);
                pstmt.setInt(3, medID);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Stock updated and status flag synchronized for MedID: " + medID);
                }
            } catch (SQLException e) {
                System.err.println("Failed to update stock and status: " + e.getMessage());
            }
    }
}