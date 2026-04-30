package Controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import User.User;
import User.UserService;

public class SceneController {
    
    // ----- Class Attributes ----- //
    private Stage stage;
    private LoginScene loginScene = new LoginScene();
    private TechnicianScene techScene = new TechnicianScene();
    private PharmacistScene pharmScene = new PharmacistScene();
    public UserService userService = new UserService();

    // ----- Constructor ----- //
    public SceneController(Stage stage) {
        this.stage = stage;
    }

    // ----- Set Main Scene ----- //
    public void setMainScene(Scene scene) {
        this.stage.setScene(scene);
    }

    // ----- Login Scene ----- //
    public void switchToLogin(UserService userService) {
        // -- Set Startup Title: "PharmAssist Login" -- //
        stage.setTitle("PharmAssist Login");
        stage.setScene(this.loginScene.getScene(this, userService));
    }

    // ----- Dashboard Scene ----- //
    public void switchToDashboard(User user, UserService userService) {
        stage.setTitle("PharmAssist - Employee Dashboard");
        // --- Check Role: if Technician, set TechnicianScene --- //
        if (user.getRole().equalsIgnoreCase("Technician")) {
            stage.setScene(techScene.getScene(stage, user, this));
        // --- Check Role: if Pharmacist, set PharmacistScene --- //
        } else if (user.getRole().equalsIgnoreCase("Pharmacist")) {
            stage.setScene(pharmScene.getScene(stage, user, this));
        }
    }

    // ----- Getters and Setters ----- //
    public Stage getStage() {
        return stage;
    }
}

