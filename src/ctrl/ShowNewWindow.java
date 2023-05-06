package ctrl;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShowNewWindow {
    public static void start() throws IOException {
		URL location = ShowNewWindow.class.getResource("../application/Main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Pane root = (Pane) fxmlLoader.load();
        Scene scene = new Scene(root, 700, 525);
        //Image open = new Image(getClass().getResourceAsStream("open.gif"));
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Untitled.csv - Attack25");
        newStage.setX(0);
        newStage.setY(0);
        //primaryStage.setWidth(700);
        //primaryStage.setHeight(500);
        //primaryStage.getIcons().add(open);
        newStage.show();
	}
}
