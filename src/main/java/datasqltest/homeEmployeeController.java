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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class homeEmployeeController {
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



    //    ObservableList<PO> poObservableList = FXCollections.observableArrayList();
    ObservableList<POModel> poModelObservableList = FXCollections.observableArrayList();
//    ObservableList<user> userObservableList = FXCollections.observableArrayList();
//    private PO pos;

    public void initialize(URL url, ResourceBundle resourceBundle){
//        pos = new PO();
        poModel = new POModel();
        choiceBoxState.getItems().addAll(state);
        choiceBoxState.setValue("กำลังผลิต");
//        confirmState.setOnAction(e -> getChoice(choiceBoxState));

//        setStatus();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String productViewQuery = "SELECT * FROM microchipapp.po";
        String userViewQuery = "SELECT * FROM microchipapp.user";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(productViewQuery);

//            Statement statementUser = connectDB.createStatement();
//            ResultSet queryOutPutUser = statementUser.executeQuery(userViewQuery);

            while (queryOutPut.next()){
//                if (queryOutPut.getString("status_PO").equals("-")){
//                    System.out.println(queryOutPut.getString("status_PO"));
//                }else {
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
//                    userObservableList.add(new user(queryPOName,queryPOPhone,queryPOAddress));
//                System.out.println(queryOutPut.getString("status_PO"));

//                }

//                Integer queryPOID = queryOutPut.getInt("id_PO");
//                String queryPOName = queryOutPut.getString("name_PO");
//                String queryPOPN = queryOutPut.getString("pn_PO");
//                Integer queryPOQuantity = queryOutPut.getInt("quantity_PO");
//                float queryPOTP = queryOutPut.getFloat("total_price_PO");
//                String queryPOPhone = queryOutPut.getString("phone_PO");
//                String queryPOAddress = queryOutPut.getString("address_PO");
//                String queryPODate = queryOutPut.getString("date_PO");
//                String queryPOStatus = queryOutPut.getString("status_PO");
//                poObservableList.add(new PO(queryPOID,queryPOName,queryPOPhone,queryPOAddress,queryPOPN,queryPOQuantity,queryPOTP,queryPODate,queryPOStatus));
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
//                    else if (PO.getDate_po().toLowerCase().indexOf(searchKeyword) > -1){
//                        return true;
//                    }
                    else if (poModel.getPn_PO().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
//                    else if (PO.getStatus_po().toLowerCase().indexOf(searchKeyword) > -1){
//                        return true;
//                    }
//                    else if (PO.getId_PO().toString().toLowerCase().indexOf(searchKeyword) > -1){
//                        return true;
//                    }
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

    private void showSelectedMemberCard(POModel card) {
//        selectedPO = card;
//        nameTestText.setText(card.getName_po());
//        addressTest.setText(card.getAddress_po());
//        phoneText.setText(card.getPhone_po());
        dateText.setText(card.getDate_PO());
        statesText.setText(card.getStatus_PO());
        System.out.println(card.getId_PO());
        System.out.println(card.getStatus_PO());
        if (card.getStatus_PO().equals("ส่งสินค้าแล้ว")){
            choiceBoxState.setDisable(true);
            confirmState.setDisable(true);
        }else {
            choiceBoxState.setDisable(false);
            confirmState.setDisable(false);
        }
        confirmState.setOnAction(e -> getChoice(choiceBoxState,card.getId_PO(),card.getStatus_PO()));



//        cpLabel.setText(String.format("%.2f", card.getCumulativePurchase()));
//        ptLabel.setText(String.valueOf(card.getStamp()));
//        userImageView.setImage(new Image("file:"+selectedMemberCard.getImagePath(), true));
//        updateInfoButton.setDisable(false);
//        browseButton.setDisable(false);
    }

    public void getChoice(ChoiceBox<String> choiceBox,int id,String statusText){
        String status = choiceBox.getValue();
//         {"กำลังผลิต", "กำลังจัดส่ง","ส่งสินค้าแล้ว"}
        if (statusText.equals("ยืนยันการสั่งซื้อ")){
            if (status.equals("กำลังจัดส่ง") || status.equals("ส่งสินค้าแล้ว")){
                error.setText("ไม่สามารถเปลี่ยน");
            }else {
                poModel.changeStatus(id,status,statusText);
            }
        }else if (statusText.equals("กำลังจัดส่ง")){
            if (status.equals("กำลังผลิต")){
                error.setText("ไม่สามารถเปลี่ยน");
            }else {
                poModel.changeStatus(id,status,statusText);
            }
        }
//        else if (statusText.equals("ยังไม่ชำระสินค้า")){
//            error.setText("ไม่สามารถเปลี่ยนได้");
//        }
        else {
            poModel.changeStatus(id,status,statusText);
        }
        System.out.println(status);

    }

//    private void setStatus(){
//        choiceBoxState.getItems().addAll(state);
//        choiceBoxState.setOnAction(this::getState);
//    }
//
//
//
//
//    public void getState(ActionEvent event){
//        String myState = choiceBoxState.getValue();
//        test.setText(myState);
//
//    }

    @FXML
    public void refreshButtonOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeEmployee.fxml"));
        stage.setScene(new Scene(loader.load(),1080,680));

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
            registerStage.initStyle(StageStyle.DECORATED);
            registerStage.setScene(new Scene(root,800,600));
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
