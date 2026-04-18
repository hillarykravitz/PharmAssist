package Controller;

import javafx.stage.Stage;
import User.User;

public class SceneController {
    
    private Stage stage;

    // -- Constructor -- //
    public SceneController(Stage stage) {
        this.stage = stage;
    }

    // -- Login Scene -- //
    public void switchToLogin() {
        LoginScene loginScene = new LoginScene();
        stage.setScene(loginScene.getScene(stage, this));
    }

    // -- Dashboard Scene -- //
    public void switchToDashboard(User user) {
        TechnicianScene dashboardScene = new TechnicianScene();
        stage.setScene(dashboardScene.getScene(stage, user, this));
    }

    // -- Getters and Setters -- //
    public Stage getStage() {
        return stage;
    }
}

