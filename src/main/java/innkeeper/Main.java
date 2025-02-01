package innkeeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
    final String FILE_PATH = "data/tasks.txt";
    private InnKeeper innKeeper = new InnKeeper(FILE_PATH);
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            BorderPane root = (BorderPane) fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setInnKeeper(innKeeper);
            stage.setTitle("InnKeeper");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}