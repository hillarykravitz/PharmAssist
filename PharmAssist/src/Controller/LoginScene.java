package Controller;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import User.User;
import User.UserService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class LoginScene {

    // private UserService userService;

    // public LoginScene(UserService userService) {
    //     this.userService = userService;
    // }

    // UserService userService = new UserService();
    
    // -- LoginScene getScene Method -- //
    public Scene getScene(SceneController sceneController, UserService userService) {


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

        // -- Login Button Event Handler -- //
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            // -- Run user input against database info using UserService's class method(s) -- //
            User user = userService.validateLogin(username, password);
            // -- If no User returned (null), add Label and remain on Login Scene -- //
            // -- Else: load Dashboard scene and pass the User object -- //
            if (user == null) {
                statusLabel.setText("Invalid username/password. Please try again.");
                sceneController.switchToLogin(userService);
            } else {
                statusLabel.setText("Success. Redirecting...");
                sceneController.switchToDashboard(user, userService);
            }
        });
        return new Scene(vbox, 300, 200);
    }
}