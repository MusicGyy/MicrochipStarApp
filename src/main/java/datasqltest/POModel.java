package datasqltest;

import datasqltest.DatabaseConnection;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class POModel {
    private int id_PO;
    private String name_PO;
    private String phone_PO;
    private String email_PO;
    private String address_PO;
    private String pn_PO;
    private int quantity_PO;
    private  float total_price_PO;
    private String evidence_PO;
    private String date_PO;
    private String status_PO;

    public POModel() {
    }

    public POModel(int id_PO, String name_PO, String phone_PO, String email_PO, String address_PO, String pn_PO, int quantity_PO, float total_price_PO, String evidence_PO, String date_PO, String status_PO) {
        this.id_PO = id_PO;
        this.name_PO = name_PO;
        this.phone_PO = phone_PO;
        this.email_PO = email_PO;
        this.address_PO = address_PO;
        this.pn_PO = pn_PO;
        this.quantity_PO = quantity_PO;
        this.total_price_PO = total_price_PO;
        this.evidence_PO = evidence_PO;
        this.date_PO = date_PO;
        this.status_PO = status_PO;
    }

    public int getId_PO() {
        return id_PO;
    }

    public void setId_PO(int id_PO) {
        this.id_PO = id_PO;
    }

    public String getName_PO() {
        return name_PO;
    }

    public void setName_PO(String name_PO) {
        this.name_PO = name_PO;
    }

    public String getPhone_PO() {
        return phone_PO;
    }

    public void setPhone_PO(String phone_PO) {
        this.phone_PO = phone_PO;
    }

    public String getEmail_PO() {
        return email_PO;
    }

    public void setEmail_PO(String email_PO) {
        this.email_PO = email_PO;
    }

    public String getAddress_PO() {
        return address_PO;
    }

    public void setAddress_PO(String address_PO) {
        this.address_PO = address_PO;
    }

    public String getPn_PO() {
        return pn_PO;
    }

    public void setPn_PO(String pn_PO) {
        this.pn_PO = pn_PO;
    }

    public int getQuantity_PO() {
        return quantity_PO;
    }

    public void setQuantity_PO(int quantity_PO) {
        this.quantity_PO = quantity_PO;
    }

    public float getTotal_price_PO() {
        return total_price_PO;
    }

    public void setTotal_price_PO(float total_price_PO) {
        this.total_price_PO = total_price_PO;
    }

    public String getEvidence_PO() {
        return evidence_PO;
    }

    public void setEvidence_PO(String evidence_PO) {
        this.evidence_PO = evidence_PO;
    }

    public String getDate_PO() {
        return date_PO;
    }

    public void setDate_PO(String date_PO) {
        this.date_PO = date_PO;
    }

    public String getStatus_PO() {
        return status_PO;
    }

    public void setStatus_PO(String status_PO) {
        this.status_PO = status_PO;
    }

    public void buyProduce(String name,String phone,String email,String address, String pn, int quantity,float tp,String evidence,String date){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String insertFields = "INSERT INTO microchipapp.po (name_PO, phone_PO, email_PO, address_PO, pn_PO, quantity_PO, total_price_PO,evidence_PO,date_PO,status_PO) VALUES ('";
        String insertValues = name + "','" + phone + "','" + email + "','" + address + "','" + pn + "','" + quantity+ "','" + tp + "','" + evidence+ "','" + date + "','" + "ยืนยันการสั่งซื้อ"+ "')";
        String insertToRegister = insertFields + insertValues;

        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(insertToRegister);


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void changeStatus(int id,String status,String statusText){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();


        String insertFields = "UPDATE microchipapp.po SET status_PO = '" + status  + "'WHERE id_PO = '" + id + "'AND status_PO = '" + statusText + "'";
//        String insertValues = username + "','" + password + "','" + name + "','" + phone + "','" + address + "','" + "-" + "','" + 0 + "')";
//        String insertToRegister = insertFields + insertValues;
        System.out.println(insertFields);



        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(insertFields);


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    public void update(int priceDB,int quantity_PO,String id) {
        DatabaseConnection connectionNow2 = new DatabaseConnection();
        Connection connectDB2 = connectionNow2.getConnection();
        int result = priceDB - quantity_PO;
//                        int result = quantityDB - poModel.getQuantity_PO();
//        String resultStr = String.valueOf(result);
//                        String updateField = "UPDATE microchipapp.product SET all_quantity_P = + '" + resultStr + "' WHERE id_P = '" + poModel.getPn_PO() + "'";
        String updateField = "UPDATE microchipapp.product SET all_quantity_P = + '" + result + "' WHERE id_P = '" + id + "'";
//        String updateField = "UPDATE microchipapp.product SET all_quantity_P = + '" + editQuantity + "' WHERE id_P = '" + idProduct + "'";

        try {
            System.out.println("1");
            Statement statement2 = connectDB2.createStatement();
            statement2.executeUpdate(updateField);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


//    public void editQuantityButtonOnAction(ActionEvent event){
//        DatabaseConnection connectionNow = new DatabaseConnection();
//        Connection connectDB = connectionNow.getConnection();
//
//        String idProduct = selectedProduct.getId_P();
//        String editQuantity = editQuantity_Field.getText();
//        if (editQuantity_Field.getText().isEmpty()) {
//            editWarning.setText("Please fill in the Information.");
//        }
//        else {
//            if(productDetail.isInt(editQuantity_Field.getText())) {
//                if (Integer.parseInt(editQuantity_Field.getText()) >= 0 ) {
//                    String updateField = "UPDATE microchipapp.product SET all_quantity_P = + '" + editQuantity + "' WHERE id_P = '" + idProduct + "'";
//                    try {
//                        Statement statement = connectDB.createStatement();
//                        statement.executeUpdate(updateField);
//                        clearSelectedProduct();
//                        updateTable();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        e.getCause();
//                    }
//                }
//                else{editWarning.setText("Please enter correct information.");}
//            }else {editWarning.setText("Please enter the numbers.");
//            }
//     }

//    }

    public boolean checkProduct(int quantity_PO , String namePro){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();
        String connectQuery = "SELECT all_quantity_P FROM microchipapp.product WHERE name_P = '" + namePro + "'";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);
            while (queryOutPut.next()){
                if (quantity_PO<Integer.parseInt(queryOutPut.getString("all_quantity_P"))){
                    System.out.println("1");
                    return true;
                }
                else {
                    System.out.println("2");
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("4");
        return false;
    }




}
