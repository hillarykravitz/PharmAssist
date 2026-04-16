package Controller;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import User.User;
import User.UserService;
import javafx.event.ActionEvent;


public class LoginController {

    // -- Attributes -- //
    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;
    private SceneCreator sceneCreator;

    // -- UserService Object Instantiation -- //
    private UserService userService = new UserService();

    // -- Constructor -- //
    public LoginController(TextField usernameField, PasswordField passwordField, Label statusLabel, SceneCreator sceneCreator) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.statusLabel = statusLabel;
        this.sceneCreator = sceneCreator;
    }

    // -- Handle Login Button Method -- //
    public void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // -- Run user input against database info using UserService's class method(s) -- //
        //boolean isValid = userService.validateLogin(username, password);

        User user = userService.validateLogin(username, password);


        
        if (user == null) {
            statusLabel.setText("Invalid username/password. Please try again.");
            statusLabel.setText("Success. Redirecting...");
            sceneCreator.getStage().setScene(sceneCreator.createDashboardScene());
        } else {
            statusLabel.setText("Success. Redirecting...");
            sceneCreator.getStage().setScene(sceneCreator.createDashboardScene());
        }
    }
}
