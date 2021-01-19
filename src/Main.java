import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.application.DependencyProvider;
import main.application.Logger;

public class Main extends Application {
    private final String loadingConfigurationFxml = "main/javafx/controllers/main/loadingConfiguration.fxml";
    private final String fatalErrorFxml = "main/javafx/controllers/main/fatalError.fxml";
    private final String mainFxml = "main/javafx/controllers/main/main.fxml";

    private Parent loadFxml(String fxmlPath) {
        try {
            return FXMLLoader.load(getClass().getResource(fxmlPath));
        } catch (Exception e) {
            Logger.log(e.getMessage());
            Logger.fatalError("Fail to load " + getClass().getResource(fxmlPath));
            return null;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        var loadingConfiguration = loadFxml(loadingConfigurationFxml);
        primaryStage.setTitle("Configuration manager");
        primaryStage.setScene(new Scene(loadingConfiguration));
        primaryStage.show();
        DependencyProvider.applicationState.pullFromSources()
                .thenAcceptAsync(success -> {
                    var next = success ? mainFxml : fatalErrorFxml;
                    var fxml = loadFxml(next);
                    if(fxml == null) {
                        fxml = loadFxml(fatalErrorFxml);
                    }
                    primaryStage.getScene().setRoot(fxml);
                });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
