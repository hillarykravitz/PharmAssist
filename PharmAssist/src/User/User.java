package User;

public class User {

    // -- Class Attributes -- //
    private int employeeID;
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private String passwordHash;
    private String email;

    // -- Constructors -- //
    public User(int employeeID, String firstName, String lastName, String username, String role, String passwordHash, String email) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public User() {

    }

    // -- Getters and Setters -- //
    public int getEmployeeID() {
        return this.employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    // -- Shouldn't be able to use I think -- //
    // public void setPasswordHash(String passwordHash) {
    //     this.passwordHash = passwordHash;
    // }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // -- toString Override for Testing -- //
    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
