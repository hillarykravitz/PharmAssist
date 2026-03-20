import BCrypt.BCrypt;


public class UserService {

    // -- UserDAO Object Instantiation -- //
    private final UserDAO userDAO = new UserDAOImpl();

    // -- Validate Login Method -- //
    public boolean validateLogin(String username, String password) {
        
        User user = userDAO.findByUsername(username);
        if (user != null) {
            return BCrypt.checkpw(password, user.getPasswordHash());
        }
        return false;
    }
}