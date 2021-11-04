package datasqltest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class homeEmployeeController implements Initializable {
    @FXML
    private Button addEmployee,confirmState;

    @FXML
    private TextField serach;

    @FXML
    private TableView<POModel> POTable,testTable;

    @FXML
    private TableColumn<POModel, Integer> id_po,quantity,tp;

    @FXML private TableColumn<POModel, String> name,pn,phone,address,status,date;

    @FXML private Label error,test,nameTestText,addressTest,phoneText,dateText,statesText;

    @FXML private ChoiceBox<String> choiceBoxState;

    private String[] state = {"กำลังผลิต", "กำลังจัดส่ง","ส่งสินค้าแล้ว"};

    //    private PO selectedPO;
    private POModel poModel;
//    private user selectedUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        choiceBoxState.getItems().addAll(state);
        updateTable();

    }



    public void updateTable(){
        error.setText("");
        ObservableList<POModel> poModelObservableList = FXCollections.observableArrayList();
        poModel = new POModel();
        nameTestText.setText("");
        addressTest.setText("");
        phoneText.setText("");
        dateText.setText("");
        statesText.setText("");
        choiceBoxState.setValue("กำลังผลิต");


        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String productViewQuery = "SELECT * FROM microchipapp.po";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(productViewQuery);


            while (queryOutPut.next()){

                Integer queryPOID = queryOutPut.getInt("id_PO");
                String queryPOName = queryOutPut.getString("name_PO");
                String queryPOPN = queryOutPut.getString("pn_PO");
                Integer queryPOQuantity = queryOutPut.getInt("quantity_PO");
                float queryPOTP = queryOutPut.getFloat("total_price_PO");
                String queryPOPhone = queryOutPut.getString("phone_PO");
                String queryPOAddress = queryOutPut.getString("address_PO");
                String queryPODate = queryOutPut.getString("date_PO");
                String queryPOStatus = queryOutPut.getString("status_PO");
                String queryPOEmail = queryOutPut.getString("email_PO");
                String queryPOEvidence = queryOutPut.getString("evidence_PO");

                poModelObservableList.add(new POModel(queryPOID,queryPOName,queryPOPhone,queryPOEmail,queryPOAddress,queryPOPN,queryPOQuantity,queryPOTP,queryPOEvidence,queryPODate,queryPOStatus));
//                System.out.println(queryOutPut.getString("status_PO"));

            }



            id_po.setCellValueFactory(new PropertyValueFactory<>("id_PO"));
            name.setCellValueFactory(new PropertyValueFactory<>("name_PO"));
            phone.setCellValueFactory(new PropertyValueFactory<>("phone_PO"));
            address.setCellValueFactory(new PropertyValueFactory<>("address_PO"));
            pn.setCellValueFactory(new PropertyValueFactory<>("pn_PO"));
            quantity.setCellValueFactory(new PropertyValueFactory<>("quantity_PO"));
            tp.setCellValueFactory(new PropertyValueFactory<>("total_price_PO"));
            date.setCellValueFactory(new PropertyValueFactory<>("date_PO"));
            status.setCellValueFactory(new PropertyValueFactory<>("status_PO"));

            POTable.setItems(poModelObservableList);

            POTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    showSelectedMemberCard(newValue);
                }
            });

            FilteredList<POModel> filteredData = new FilteredList<>(poModelObservableList, b -> true);

            serach.textProperty().addListener((observable, oldValue, newValue) ->{
                filteredData.setPredicate(poModel -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (poModel.getDate_PO().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
//
                    else if (poModel.getPn_PO().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (poModel.getStatus_PO().indexOf(searchKeyword) > -1

                    ) {
                        return true;
                    }
//
                    else {
                        return false;
                    }

                });
            });


            SortedList<POModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(POTable.comparatorProperty());

            POTable.setItems(sortedData);

        }catch (SQLException e){
            Logger.getLogger(homeEmployeeController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }

    }

//    public void clearSelectedProduct(){
//        POTable.refresh();
//        POTable.getSelectionModel().clearSelection();
//    }

    private void showSelectedMemberCard(POModel card) {
//        selectedPO = card;
        error.setText("");
        nameTestText.setText(card.getName_PO());
        addressTest.setText(card.getAddress_PO());
        phoneText.setText(card.getPhone_PO());

        dateText.setText(card.getDate_PO());
        statesText.setText(card.getStatus_PO());
//        System.out.println(card.getId_PO());
//        System.out.println(card.getStatus_PO());
        if (card.getStatus_PO().equals("ส่งสินค้าแล้ว")){
            choiceBoxState.setDisable(true);
            confirmState.setDisable(true);
        }else {
            choiceBoxState.setDisable(false);
            confirmState.setDisable(false);
        }
        confirmState.setOnAction(e -> getChoices(choiceBoxState,card.getId_PO(),card.getStatus_PO()));



//        cpLabel.setText(String.format("%.2f", card.getCumulativePurchase()));
//        ptLabel.setText(String.valueOf(card.getStamp()));
//        userImageView.setImage(new Image("file:"+selectedMemberCard.getImagePath(), true));
//        updateInfoButton.setDisable(false);
//        browseButton.setDisable(false);
    }

//    public void getChoice(ChoiceBox<String> choiceBox,int id,String statusText){
//
//        String po = poModel.getStatus_PO();
//        String status = choiceBox.getValue();
////         {"กำลังผลิต", "กำลังจัดส่ง","ส่งสินค้าแล้ว"}
//        if (statusText.equals("ยืนยันการสั่งซื้อ")){
//            if (status.equals("กำลังจัดส่ง") || status.equals("ส่งสินค้าแล้ว")){
//                error.setText("ไม่สามารถเปลี่ยน");
//                System.out.println("1");
//            }else {
//                ///  กำลังผลิต   ///
//                System.out.println("2");
//                poModel.changeStatus(id,status,statusText);
//                updateTable();
//            }
//        }else if (statusText.equals("กำลังจัดส่ง")){
//            if (status.equals("กำลังผลิต")){
//                System.out.println("3");
//                error.setText("ไม่สามารถเปลี่ยน");
//            }else {
//                //////
//                System.out.println("4");
//                poModel.changeStatus(id,status,statusText);
//                updateTable();
//            }
//        }
////        else if (statusText.equals("ยังไม่ชำระสินค้า")){
////            error.setText("ไม่สามารถเปลี่ยนได้");
////        }
//        else {
//            //กำลังจัดส่ง///
//            System.out.println("5");
//            updateTable();
//            poModel.changeStatus(id,status,statusText);
//        }





        public void getChoices(ChoiceBox<String> choiceBox,int id,String statusText){
            String po = poModel.getStatus_PO();
        String status = choiceBox.getValue();
//         {"กำลังผลิต", "กำลังจัดส่ง","ส่งสินค้าแล้ว"}


            //        {"ยืนยันการสั่งซื้อ","กำลังผลิต", "กำลังจัดส่ง","ส่งสินค้าแล้ว"}
        if (statusText.equals("ยืนยันการสั่งซื้อ")){
            if (status.equals("กำลังจัดส่ง") || status.equals("ส่งสินค้าแล้ว")){
                error.setText("ไม่สามารถเปลี่ยน");
                System.out.println("1");
            }else {
                ///  กำลังผลิต   ///
                System.out.println("2");
                poModel.changeStatus(id,status,statusText);
                updateTable();
            }
        }
        else if (statusText.equals("กำลังจัดส่ง")){
            if (status.equals("กำลังผลิต")){
                System.out.println("3");
                error.setText("ไม่สามารถเปลี่ยน");
            }else {
                ///ส่งสินค้าแล้ว///
                System.out.println("4");
                poModel.changeStatus(id,status,statusText);
                updateTable();
            }
        }
//        else if (statusText.equals("ยังไม่ชำระสินค้า")){
//            error.setText("ไม่สามารถเปลี่ยนได้");
//        }
        else {



            //กำลังจัดส่ง///
            System.out.println("5");
            updateTable();
            poModel.changeStatus(id,status,statusText);
        }



        }

//        POTable.getColumns().clear();
//        clearSelectedProduct();


//        System.out.println(status);




//    @FXML
//    public void refreshButtonOnAction(ActionEvent event) throws IOException {
//        Button b = (Button) event.getSource();
//        Stage stage = (Stage) b.getScene().getWindow();
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeEmployee.fxml"));
//        stage.setScene(new Scene(loader.load(),1080,680));
//
//        stage.show();
//    }

    @FXML
    public void goPOButton(ActionEvent event) throws IOException{
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("POpage.fxml"));
        stage.setScene(new Scene(loader.load(),1080,600));

        stage.show();
    }

    @FXML
    public void addProductButtonOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("product-view.fxml"));
        stage.setScene(new Scene(loader.load(),1080,680));

        stage.show();
    }

    @FXML
    public void addEmployeeButtonOnAction() {
        createAccountForm();
    }

    public void createAccountForm(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("registerEmployee.fxml"));
            Stage registerStage = new Stage();
            registerStage.setScene(new Scene(root,650,650));
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.show();

        }catch (Exception e){
            e.printStackTrace();;
            e.getCause();
        }
    }

    public void logOutButtonOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));

        stage.show();

    }
}
