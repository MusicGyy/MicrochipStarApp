package datasqltest;

import datasqltest.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeController {
    @FXML
    private Label error;

    public void connectButton(ActionEvent event){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String connectQuery = "SELECT * FROM microchipapp.employees";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);


            while (queryOutPut.next()){
                error.setText(queryOutPut.getString("username_e"));
                System.out.println(queryOutPut.getString("username_e"));
                System.out.println(queryOutPut.getString("id_e"));

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML public void handleGoToLoginOnAction(ActionEvent event) throws IOException {

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));

        stage.show();

    }

    @FXML public void handleGoToBuyOnAction(ActionEvent event) throws IOException {

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("market-view.fxml"));
        stage.setScene(new Scene(loader.load(),1080,680));

        stage.show();

    }
//    public void createAccountForm(){
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//            Stage registerStage = new Stage();
//            registerStage.initStyle(StageStyle.DECORATED);
//            registerStage.setScene(new Scene(root,800,600));
//            registerStage.show();
//
//        }catch (Exception e){
//            e.printStackTrace();;
//            e.getCause();
//        }
//    }
}
