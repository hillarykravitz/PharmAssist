// -- I'm not sure I need this file, I don't think it's really doing anything currently..? -- //

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private String passwordHash;
    private String email;

    public User(int userID, String firstName, String lastName, String username, String role, String passwordHash, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
        this.passwordHash = passwordHash;
        this.email = email;
    }

    public User() {

    }

    // Getters and Setters for accessing/modifying data
    public int getID() {
        return this.userID;
    }

    public void setID(int userID) {
        this.userID = userID;
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

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
