import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;


public class LoginController {

    // -- Attributes -- //
    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;
    private SceneCreator sceneCreator;

    // -- Constructor -- //
    public LoginController(TextField usernameField, PasswordField passwordField, Label statusLabel, SceneCreator sceneCreator) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.statusLabel = statusLabel;
        this.sceneCreator = sceneCreator;
    }

    // -- UserService Object Instantiation -- //
    private UserService userService = new UserService();

    // -- Handle Login Button Method -- //
    public void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // -- Run user input against database info using UserService's class method(s) -- //
        boolean isValid = userService.validateLogin(username, password);
        if (isValid) {
            statusLabel.setText("Success. Redirecting...");
            sceneCreator.getStage().setScene(sceneCreator.createDashboardScene());
        } else {
            statusLabel.setText("Invalid username/password. Please try again.");
            // sceneCreator.getStage().setScene(sceneCreator.createLoginScene());
        }
    }
}
