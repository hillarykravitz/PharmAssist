package User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import SQL.DatabaseManager;

public class UserDAOImpl implements UserDAO {
    
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

}