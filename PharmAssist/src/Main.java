import javafx.stage.Stage;
import Controller.SceneCreator;
import javafx.application.Application;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        SceneCreator sceneCreator = new SceneCreator(primaryStage);
        
        // -- Initial setup -- //
        primaryStage.setTitle("PharmAssist Login");
        primaryStage.setScene(sceneCreator.createLoginScene());
        primaryStage.show();
    }

    // -- Main Method -- //
    public static void main(String[] args) {
        launch(args);
    }
}