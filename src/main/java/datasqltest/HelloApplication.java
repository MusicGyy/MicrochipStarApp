package datasqltest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    public static final String CURRENCY = "ID:";
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setResizable(false);
        stage.setTitle("MicrochipStarApp!");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}