package datasqltest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class loginController {
    @FXML
    private Label error;

    @FXML
    private Button loginButton,registerButton;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private employee employees;
//    private user users;

    public void initialize(){
        employees = new employee();
//        users = new user();

    }

//    public void cancelButtonOnAction(ActionEvent event){
//        Stage stage = (Stage) ปุ่มอะ.getSecene().getWindow();
//        stage.close();
//    }

    public void loginButtonOnAction(ActionEvent event) throws IOException {

        if (employees.check(username.getText(),password.getText())){
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeEmployee.fxml"));
            stage.setScene(new Scene(loader.load(),1080,680));

            stage.show();

        }
//        if (users.checkLogin(username.getText(),password.getText())){
//            Button b = (Button) event.getSource();
//            Stage stage = (Stage) b.getScene().getWindow();
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("product_list_page.fxml"));
//            stage.setScene(new Scene(loader.load(),800,600));
//
//            stage.show();
//        } else {
//            error.setText("มึงผิดไอ้สัส");
//            username.clear();
//            password.clear();
//        }
    }

    @FXML
    public void registerButtonOnAction(ActionEvent event) throws IOException {

//        Button b = (Button) event.getSource();
//        Stage stage = (Stage) b.getScene().getWindow();
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("registerEmployee.fxml"));
//        stage.setScene(new Scene(loader.load(),800,600));
//
//        stage.show();
        createAccountForm();
    }

    @FXML public void backButton(ActionEvent event) throws IOException {

        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        stage.setScene(new Scene(loader.load(),800,600));

        stage.show();

    }

    public void createAccountForm(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("registerUser.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.DECORATED);
            registerStage.setScene(new Scene(root,800,600));
            registerStage.show();

        }catch (Exception e){
            e.printStackTrace();;
            e.getCause();
        }
    }
}
