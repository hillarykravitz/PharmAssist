import javafx.stage.Stage;
import Controller.SceneController;
import javafx.application.Application;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // -- SceneCreator Instantiation -- //
        SceneController sceneController = new SceneController(primaryStage);
        
        // -- Initial setup of Login screen -- //
        sceneController.switchToLogin();
        primaryStage.show();
    }

    // -- Main Method -- //
    public static void main(String[] args) {
        launch(args);
    }
}