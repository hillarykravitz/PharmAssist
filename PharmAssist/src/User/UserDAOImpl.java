package User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import SQL.Prescription;
import SQL.Patient;
import SQL.Medication;
import SQL.DatabaseManager;

public class UserDAOImpl implements UserDAO {
    
    // -- findByUsername Override -- //
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

    // -- findByPrescriptionID Override -- //
    @Override
    public Prescription findByPrescriptionID(int prescriptionID) {
        String sql = "SELECT * FROM Prescriptions WHERE prescriptionID = ?";
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, prescriptionID);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        return new Prescription(rs.getInt("prescriptionID"), rs.getInt("patientID"), rs.getDate("creationDate"), rs.getDate("completionDate"), rs.getString("status"));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByPrescriptionID");
                }
                return null;
    }

    // -- findByPatientName Override -- //
    @Override
    public Patient findByPatientName(String first, String last) {
        String sql = "SELECT * FROM Patients WHERE firstName = ? AND lastName = ?";
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, first);
                    pstmt.setString(2, last);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        return new Patient(rs.getInt("patientID"), rs.getString("firstName"), rs.getString("lastName"), rs.getDate("birthdate"), rs.getString("phoneNumber"));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByPatientName");
                }
                return null;
    }

    // -- findByPatientDOB Override -- //
    @Override
    public Patient findByPatientDOB(Date birthdate) {
        String sql = "SELECT * FROM Patients WHERE birthdate = ?";
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setDate(1, birthdate);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        return new Patient(rs.getInt("patientID"), rs.getString("firstName"), rs.getString("lastName"), rs.getDate("birthdate"), rs.getString("phoneNumber"));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByPatientDOB");
                }
                return null;
    }

    // -- findByPhoneNumber Override -- //
    @Override
    public Patient findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM Patients WHERE phoneNumber = ?";
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, phoneNumber);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        return new Patient(rs.getInt("patientID"), rs.getString("firstName"), rs.getString("lastName"), rs.getDate("birthdate"), rs.getString("phoneNumber"));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByPhoneNumber");
                }
                return null;
    }

    // -- findByMedName Override -- //
    @Override
    public Medication findByMedName(String medName) {
        String sql = "SELECT * FROM Medications WHERE medName = ?";
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, medName);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        boolean inStock;
                        if (rs.getInt("isInStock") == 0) {
                            inStock = false;
                        } else {
                            inStock = true;
                        }
                        return new Medication(rs.getInt("medID"), rs.getString("medName"), rs.getString("genericName"), inStock, rs.getInt("pharmacyQuantity"));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at findByMedName");
                }
                return null;
    }

    // -- findByGenericName Override -- //
    @Override
    public Medication findByGenericName(String genericName) {
        String sql = "SELECT * FROM Medications WHERE genericName = ?";
            try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, genericName);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        boolean inStock;
                        if (rs.getInt("isInStock") == 0) {
                            inStock = false;
                        } else {
                            inStock = true;
                        }
                        return new Medication(rs.getInt("medID"), rs.getString("medName"), rs.getString("genericName"), inStock, rs.getInt("pharmacyQuantity"));
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Exception at executeSearch");
                }
                return null;
    }
}