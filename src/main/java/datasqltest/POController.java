package datasqltest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class POController implements Initializable {
    @FXML
    private Label total_price_text,quantityText,nameText,evidenceTexT,error,summaryShowNameLabel,summaryShowPhoneLabel,summaryShowMailLabel,summaryShowAddressLabel;

    @FXML
    private Button backButton,addProductButton,cancelButton,list_summary_Button,evidenceButton,confirmButton,summaryB;

    @FXML
    private ChoiceBox<String> ChoiceProductName;

    @FXML
    private TextField quantity,name,phone,email;

    @FXML
    private TextArea address;

    @FXML
    private ImageView productView,evidenceView;

    private POModel poModel;

    private float tps;

    private Product product;


    public void initialize(URL url, ResourceBundle resourceBundle){
        poModel = new POModel();
        confirmButton.setDisable(true);
        evidenceButton.setDisable(true);

        poModel.setEvidence_PO("");
//        pos = new PO();
//        ChoiceProductName.getItems().addAll(product);
//        ChoiceProductName.setValue("-----เลือกสินค้า-----");
//        confirmState.setOnAction(e -> getChoice(choiceBoxState));

        //        setStatus();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

//        String productViewQuery = "SELECT * FROM microchipapp.po";
        String productViewQuery2 = "SELECT * FROM microchipapp.product";

        try {

//            Statement statement = connectDB.createStatement();
//            ResultSet queryOutPut = statement.executeQuery(productViewQuery);

            Statement statement2 = connectDB.createStatement();
            ResultSet queryOutPut2 = statement2.executeQuery(productViewQuery2);


//            Statement statementUser = connectDB.createStatement();
//            ResultSet queryOutPutUser = statementUser.executeQuery(userViewQuery);

            while (queryOutPut2.next()){
                ChoiceProductName.getItems().addAll(queryOutPut2.getString("name_P"));
                ChoiceProductName.setOnAction(this::getProductName);
            }

//            while (queryOutPut.next()){
//                System.out.println(queryOutPut.getString("name_PO"));
//
//            }


        }catch (SQLException e){
            Logger.getLogger(homeEmployeeController.class.getName()).log(Level.SEVERE,null,e);
            e.printStackTrace();
        }




    }

//    private void updateDetail(){
//
//
//
//
//
//
//
//    }


    private void clearDetail(){
        ChoiceProductName.setValue("");
        quantity.clear();
        name.clear();
        phone.clear();
        email.clear();
        address.clear();
        total_price_text.setText("");
        quantityText.setText("");
        nameText.setText("");
        error.setText("");
        summaryShowNameLabel.setText("");
        summaryShowPhoneLabel.setText("");
        summaryShowMailLabel.setText("");
        summaryShowAddressLabel.setText("");
        productView.setVisible(false);
        evidenceView.setDisable(false);
    }

    public void getProductName(ActionEvent event){
        product = new Product();
        String productName = ChoiceProductName.getValue();
        poModel.setPn_PO(productName);

//        total_price_text.setText(quantity.getText());
//        System.out.println(quantity.getText());

        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String connectQuery = "SELECT image_P,price_P FROM microchipapp.product WHERE name_P = '" + poModel.getPn_PO() + "'";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);
            while (queryOutPut.next()){
//                 File brandingFile = new File("image/"+product.getImage_P());
//                 Image brandingImage = new Image(brandingFile.toURI().toString());
//                 productView.setImage(brandingImage);


                File brandingFile = new File("image/"+queryOutPut.getString("image_P"));
                Image brandingImage = new Image(brandingFile.toURI().toString());
                productView.setImage(brandingImage);



//                nameText.setText(queryOutPut.getString(""));


//                if (isNumeric(quantity.getText())){
//                    float price = Float.parseFloat(quantity.getText());
//                    float priceDB = Float.parseFloat(queryOutPut.getString("price_P"));
//                    float tp = price * priceDB;
//                    tps = tp;
//                    poModel.setTotal_price_PO(tp);
//                    total_price_text.setText(String.valueOf(tp));
//                }else {
//                    total_price_text.setText("...");
//                }



//                float price = Float.parseFloat(quantity.getText());
//                float priceDB = Float.parseFloat(queryOutPut.getString("price_P"));
//                float tp = price * priceDB;
//                total_price_text.setText(String.valueOf(tp));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML public void cancelButtonOnAction(ActionEvent event){
        clearDetail();
    }

    public void nextButtonOnAction(ActionEvent event)throws IOException{
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("market-view.fxml"));
        stage.setScene(new Scene(loader.load(), 1080, 600));
        stage.setTitle("MicrochipStarApp!");
        stage.show();




    }

//    Connection connectionDB = connectionNow.getConnection();
//
//    String connectQuery = "SELECT * FROM microchipapp.product";
//
//        try {
//        Statement statement = connectionDB.createStatement();
//        ResultSet queryOutPut = statement.executeQuery(connectQuery);
//        while (queryOutPut.next()){
//            File brandingFile = new File("image/"+selectedProduct.getImage_P());
//            Image brandingImage = new Image(brandingFile.toURI().toString());
//            imageView.setImage(brandingImage);
//        }
//    } catch (Exception e){
//        e.printStackTrace();
//    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    public void buyProductButtonOnAction(ActionEvent event)throws IOException {
         setDetail();
//        float tp = Float.parseFloat(total_price_text.getText());
//        poModel.buyProduce(name.getText(),phone.getText(),email.getText(),address.getText(),productName,price,tps,poModel.getEvidence_PO(),dateAndTime);
        if (name.getText().isEmpty() || phone.getText().isEmpty() || email.getText().isEmpty() || address.getText().isEmpty()
                 ||poModel.getPn_PO()==null|| poModel.getEvidence_PO().isEmpty()){
//            || poModel.getEvidence_PO().isEmpty()
            error.setText("Please fill in the Information.");
        }else if (!isNumeric(quantity.getText())){
            error.setText("Please enter correct information.");
        }else {
            if (poModel.checkProduct(Integer.parseInt(quantity.getText()))){
//            ChoiceProductName.setItems(;);
//            String productName = ChoiceProductName.getValue();
//            int price = Integer.parseInt(quantity.getText());
            Date date = new Date();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
            String time = timeFormat.format(date);
            String dates = dateFormat.format(date);
            String dateAndTime = dates + "-" + time;
            poModel.setName_PO(name.getText());
            poModel.setDate_PO(dateAndTime);
            poModel.setAddress_PO(address.getText());
            poModel.setEmail_PO(email.getText());
            poModel.setPhone_PO(phone.getText());
            poModel.setQuantity_PO(Integer.parseInt(quantity.getText()));
            poModel.setPn_PO(nameText.getText());
            poModel.buyProduce(poModel.getName_PO(), poModel.getPhone_PO(),poModel.getEmail_PO() ,poModel.getAddress_PO(),
                     poModel.getPn_PO(),poModel.getQuantity_PO(), poModel.getTotal_price_PO(), poModel.getEvidence_PO(), poModel.getDate_PO());

                DatabaseConnection connectionNow = new DatabaseConnection();
                Connection connectionDB = connectionNow.getConnection();

                String connectQuery = "SELECT all_quantity_P,id_P FROM microchipapp.product WHERE name_P = '" + poModel.getPn_PO() + "'";

                try {
                    Statement statement = connectionDB.createStatement();
                    ResultSet queryOutPut = statement.executeQuery(connectQuery);
                    while (queryOutPut.next()) {
                        int quantityDB = Integer.parseInt(queryOutPut.getString("all_quantity_P"));
                        String id_PDB = queryOutPut.getString("id_P");
                        System.out.println(String.valueOf(quantityDB) + "--" + String.valueOf(poModel.getQuantity_PO()) + "---" + String.valueOf(poModel.getPn_PO()));

                        poModel.update(quantityDB, poModel.getQuantity_PO(), id_PDB);

                       }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("POpage.fxml"));
            stage.setScene(new Scene(loader.load(), 1080, 600));
            stage.setTitle("MicrochipStarApp!");
            stage.show();

        }else {
                error.setText("Stock is not enough.");

            }
        }

    }






    @FXML public void handleUploadButton(ActionEvent event){
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        System.out.println(chooser);
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null){
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("evidence");
                System.out.println(destDir);
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                System.out.println(filename);
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename

                );
                System.out.println(target);
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
                // SET NEW FILE PATH TO IMAGE
                evidenceView.setImage(new Image(target.toUri().toString()));
                poModel.setEvidence_PO(destDir + "/" + filename);
                confirmButton.setDisable(false);



//                dataSource.writeData(cardList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean setDetail() {
        if (name.getText().isEmpty() || phone.getText().isEmpty() || email.getText().isEmpty() || address.getText().isEmpty()
                ||quantity.getText().equals("")||poModel.getPn_PO()==null) {
            error.setText("Please fill in the Information.");
            return false;
        } else if (!isNumeric(quantity.getText())) {
            error.setText("Please enter correct information.");
            return false;
        } else {
            nameText.setText(poModel.getPn_PO());
            summaryShowAddressLabel.setText(address.getText());
            summaryShowMailLabel.setText(email.getText());
            summaryShowNameLabel.setText(name.getText());
            summaryShowPhoneLabel.setText(phone.getText());
            quantityText.setText(quantity.getText());








            DatabaseConnection connectionNow = new DatabaseConnection();
            Connection connectionDB = connectionNow.getConnection();

            String connectQuery = "SELECT price_P FROM microchipapp.product WHERE name_P = '" + poModel.getPn_PO() + "'";

            try {
                Statement statement = connectionDB.createStatement();
                ResultSet queryOutPut = statement.executeQuery(connectQuery);
                while (queryOutPut.next()) {
                    float price = Float.parseFloat(quantity.getText());
                    float priceDB = Float.parseFloat(queryOutPut.getString("price_P"));
                    float tp = price * priceDB;
                    tps = tp;
                    poModel.setTotal_price_PO(tp);
                    total_price_text.setText(String.valueOf(tp));

                    return true;
//                float price = Float.parseFloat(quantity.getText());
//                float priceDB = Float.parseFloat(queryOutPut.getString("price_P"));
//                float tp = price * priceDB;
//                total_price_text.setText(String.valueOf(tp));
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
return false;
        }
    }
    @FXML private void summaryButton(ActionEvent event){


                    if(setDetail()){
                        evidenceButton.setDisable(false);

                    }
                    else {
                        evidenceButton.setDisable(true);
                    }

        }















}
