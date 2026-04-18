package Controller;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import User.User;
import User.UserService;
import javafx.event.ActionEvent;


public class AppController {

    // -- Class Attributes -- //
    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;
    private SceneController sceneController;

    // -- UserService Object Instantiation -- //
    private UserService userService = new UserService();

    // -- Constructor -- //
    public AppController(TextField usernameField, PasswordField passwordField, Label statusLabel, SceneController sceneController) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.statusLabel = statusLabel;
        this.sceneController = sceneController;
    }

    // -- Handle Login Button Method -- //
    public void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // -- Run user input against database info using UserService's class method(s) -- //
        User user = userService.validateLogin(username, password);
        // -- If no User returned (null), add Label and remain on Login Scene -- //
        // -- Else: load Dashboard scene and pass the User object -- //
        if (user == null) {
            statusLabel.setText("Invalid username/password. Please try again.");
            sceneController.switchToLogin();
        } else {
            statusLabel.setText("Success. Redirecting...");
            sceneController.switchToDashboard(user);
        }
    }
}