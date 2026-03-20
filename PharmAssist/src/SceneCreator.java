import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class SceneCreator {
    
    private Stage stage;

    // -- Constructor -- //
    public SceneCreator(Stage stage) {
        this.stage = stage;
    }

    // -- Login Scene Creator Method -- //
    public Scene createLoginScene() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // -- Username Input -- //
        Label usernameLabel = new Label("Username: ");
        grid.add(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        grid.add(usernameField, 1, 0);

        // -- Password Input -- //
        Label passwordLabel = new Label("Password: ");
        grid.add(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        grid.add(passwordField, 1, 1);

        // -- Login Button -- //
        Button loginButton = new Button("LOGIN");
        grid.add(loginButton, 1, 2);

        // Status Label (Empty to Start - Only shows upon change due to Login Auth Failure) -- //
        Label statusLabel = new Label();

        // -- VBox Containing Status Label (for Login Auth Failure) and GridPane -- //
        VBox vbox = new VBox(10); // 10px spacing between Children
        vbox.setAlignment(Pos.CENTER); // Center all children within
        vbox.getChildren().addAll(statusLabel, grid);

        // -- LoginController Object Instantiation to handle Event Logic -- //
        LoginController loginController = new LoginController(usernameField, passwordField, statusLabel, this);

        // -- Login Button Event Handler -- //
        loginButton.setOnAction(loginController::handleLoginButtonAction);
        return new Scene(vbox, 300, 200);
    }

    // 
    public Scene createDashboardScene() {
        Label label = new Label("Login success! Welcome to Dashboard.");
        VBox vbox = new VBox(label);
        vbox.setAlignment(Pos.CENTER);
        return new Scene(vbox, 300, 200);
    }

    public Stage getStage() {
        return stage;
    }
}
