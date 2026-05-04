import Controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;
import User.UserService;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ----- SceneController Instantiation ----- //
        SceneController sceneController = new SceneController(primaryStage);
        UserService userService = new UserService();
        // --- Initial setup of Login screen --- //
        sceneController.switchToLogin(userService);
        primaryStage.show();
    }
    public static void main(String[] args) {
        // ----- Program Entry ----- //
        launch(args);
    }
}