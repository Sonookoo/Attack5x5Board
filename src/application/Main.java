package application;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("Main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Pane root = (Pane) fxmlLoader.load();
        Scene scene = new Scene(root, 700, 525);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Untitled.csv - Attack25");
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.show();
    }
}