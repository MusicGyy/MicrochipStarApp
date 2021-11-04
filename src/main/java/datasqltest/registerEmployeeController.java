package datasqltest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class registerEmployeeController {
    @FXML
    private TextField name,username;

    @FXML
    private PasswordField password,ConfirmPassword;

    @FXML
    private Label error;

    @FXML
    private Button closeButton;

    private employee employees;
//    private user users;

    public void initialize(){
        employees = new employee();
//        users = new user();
    }

    public void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void registerButtonOnAction(ActionEvent event) throws IOException {
        if (username.getText().isEmpty() || password.getText().isEmpty() || name.getText().isEmpty() || ConfirmPassword.getText().isEmpty()){
            error.setText("กรุณากรอกข้อมูลให้ครบ");
            name.clear();
            username.clear();
            password.clear();
            ConfirmPassword.clear();
        }else {
            if (password.getText().equals(ConfirmPassword.getText())){
//                if (users.checkUsername(username.getText())){
                if (employees.checkUsername(username.getText())){
                    employees.registerEmployee(name.getText(),username.getText(),password.getText());
                    error.setText("สมัครได้");
                }else {
                    error.setText("username นี้มีแล้ว");
                    name.clear();
                    username.clear();
                    password.clear();
                    ConfirmPassword.clear();
                }
//                }else {
//                    error.setText("username นี้มีแล้ว");
//                    name.clear();
//                    username.clear();
//                    password.clear();
//                    ConfirmPassword.clear();
//                }

            }else {
                error.setText("password ไม่ตรงกัน");
                name.clear();
                username.clear();
                password.clear();
                ConfirmPassword.clear();
            }
        }


    }

}
