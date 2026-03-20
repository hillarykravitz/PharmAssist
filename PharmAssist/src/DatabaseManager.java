import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Properties;

public class DatabaseManager {
    
    // -- Connection Attributes -- //
    static final String DB_URL = "jdbc:mysql://localhost:3306/pharmDB";
    static final String DB_USER = "root";
    static final String DB_PASS = "Ph4rm45515t";

    // --- Establishes connection to Database --- //
    public static Connection getConnection() throws SQLException {
        
        Properties connectionProps = new Properties();
        connectionProps.put("user", DB_USER);
        connectionProps.put("password", DB_PASS);

        Connection conn = DriverManager.getConnection(DB_URL, connectionProps);
        System.out.println("Connection verified.");
        return conn;
    }

    // --- Closes database resources safely --- //
    public static void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
            System.out.println("Resources closed.");
        } catch (SQLException e) {
            System.out.println("Error closing resources.");
        }
    }
}
    

