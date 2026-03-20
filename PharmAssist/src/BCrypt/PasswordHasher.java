package BCrypt;

public class PasswordHasher {

    // Method to hash a plaintext password
    public static String hashPassword(String plaintext) {
        // Generate a salt with the default workload factor (10-12 is common)
        String salt = BCrypt.gensalt(12);
        // Hash the password using the salt
        return BCrypt.hashpw(plaintext, salt);
    }

    // Method to verify a password against a stored hash
    public static boolean verifyPassword(String plaintext, String storedHash) {
        return BCrypt.checkpw(plaintext, storedHash);
    }

    // public static void main(String[] args) {
    //     String password = "PASSWORD_TO_HASH_GOES_HERE";
    //     String hashedPassword = hashPassword(password);
    //     System.out.println("Hashed Password: " + hashedPassword);
    // }
}
