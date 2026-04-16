package User;
import BCrypt.BCrypt;


public class UserService {

    // -- UserDAO Object Instantiation -- //
    private final UserDAO userDAO = new UserDAOImpl();

    // -- Validate Login Method -- //
    public User validateLogin(String username, String password) {
        
        User user = userDAO.findByUsername(username);
        if (BCrypt.checkpw(password, user.getPasswordHash())) {
            System.out.println(user.toString());
            return user;
        }
        System.out.println("No user found.");
        return null;
    }
}