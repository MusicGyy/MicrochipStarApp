package datasqltest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class employee {
    private String nameM;
    private String usernameM;
    private String passwordM;

    public employee(String nameM, String usernameM, String passwordM) {
        this.nameM = nameM;
        this.usernameM = usernameM;
        this.passwordM = passwordM;
    }

    public employee() {
    }

    public boolean check(String username, String password){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String connectQuery = "SELECT * FROM microchipapp.employees";
        String connectQueryRole = "";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);
            while (queryOutPut.next()){
                if (username.equals(queryOutPut.getString("username_e")) && password.equals(queryOutPut.getString("password_e"))){
                    return true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkUsername(String username){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String connectQuery = "SELECT * FROM microchipapp.employees";
        String connectQueryRole = "";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);
            while (queryOutPut.next()){
                if (username.equals(queryOutPut.getString("username_e"))){
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public void registerEmployee(String name,String username, String password){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String insertFields = "INSERT INTO microchipapp.employees (name_e, username_e,password_e) VALUES ('";
        String insertValues = name + "','" + username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(insertToRegister);


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
