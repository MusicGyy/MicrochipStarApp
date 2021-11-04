package datasqltest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarketViewController implements Initializable {

    @FXML
    private VBox chosenProfileCard;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView productImage;
    @FXML
    private Text idText, nameProductText,quantityText;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;



    private List<Product> profiles = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    ObservableList<Product> observableList = FXCollections.observableArrayList();








    public List<Product> getData(){
        List<Product> products = new ArrayList<>();
        Product product;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
//        System.out.println("aaaaaa");
        //SQL Query - Executed in the backed database
        String productViewQuery = "SELECT * FROM microchipapp.product";
//            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM restadvisor.name");
        try {

            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(productViewQuery);
            while (queryOutput.next()){
//                System.out.println("-----");
                product = new Product();
                product.setId_P(queryOutput.getString("id_P"));
                product.setImage_P(queryOutput.getString("image_P"));
                product.setName_P(queryOutput.getString("name_P"));
                product.setPrice_P(queryOutput.getFloat("price_P"));
                product.setQuantity_P(queryOutput.getInt("all_quantity_P"));
//                System.out.println(queryOutput.getString("id_p"));
                // Populate the ObservableList
                products.add(product);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TableController.class.getName()).log(Level.SEVERE, null, ex );
        }

//        profiles.setCellValueFactory(new PropertyValueFactory<>("id_P"));
//        name_ProductTableColumn.setCellValueFactory(new PropertyValueFactory<>("name_P"));
//        price_ProductTableColumn.setCellValueFactory(new PropertyValueFactory<>("price_P"));
//        quantity_ProductTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity_P"));
//        image_ProductTableColumn.setCellValueFactory(new PropertyValueFactory<>("image_P"));
//        profiles.set(observableList);
//
//
//


        return products;
    }


//    private List<Product> getDatsas(){
//        List<Product> profiles = new ArrayList<>();
//        Product profile;
//
//        profile = new Product();
//        profile.setName_P("Gygee");
//        profile.setId_P("1");
//        profile.setImage_P("image/medium-ATTINY202-SOIC-8.png");
////        profile.setColor("6A7324");
//        profile.setPrice_P(500);
//        profile.setQuantity_P(5000);
//        profiles.add(profile);
//
//        profile = new Product();
//        profile.setName_P("sfasf");
//        profile.setId_P("2");
//        profile.setImage_P("image/medium-PIC12F1612-PDIP-8.png");
////        profile.setColor("6A7324");
//        profile.setPrice_P(20);
//        profile.setQuantity_P(100);
//        profiles.add(profile);
//
//
//
//
//
//
//
//
//
//        return profiles;
//    }


    private void setChosenProfile(Product profileModel){
        quantityText.setText("In Stock: "+String.valueOf(profileModel.getQuantity_P()));
        nameProductText.setText(profileModel.getName_P());
        idText.setText(HelloApplication.CURRENCY + profileModel.getId_P());
        priceLabel.setText("฿ " + String.valueOf(profileModel.getPrice_P()));
//        image = new Image(getClass().getResourceAsStream(profileModel.getImgSrc()));


        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String connectQuery = "SELECT * FROM microchipapp.product";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);
            while (queryOutPut.next()){
                File brandingFile = new File("image/"+profileModel.getImage_P());
                Image brandingImage = new Image(brandingFile.toURI().toString());
                productImage.setImage(brandingImage);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

//-----------------------------------------------------------
//        image = new Image("file:" + profileModel.getImage_P(), true);
//        productImage.setImage(image);
// -----------------------------------------------------------




//        chosenProfileCard.setStyle("-fx-background-color: #"+ profileModel.getColor() + ";\n" +
//                "   -fx-background-radius: 30;");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profiles.addAll(getData());
        if (profiles.size() > 0){
            setChosenProfile(profiles.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product profileModel) {
                    setChosenProfile(profileModel);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < profiles.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/datasqltest/item-view.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(profiles.get(i),myListener);

                if (column == 4) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane,column++, row);
                //set width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);


                GridPane.setMargin(anchorPane,new Insets(10));

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void nextButtonOnAction(ActionEvent event)throws IOException{
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("POpage.fxml"));
        stage.setScene(new Scene(loader.load(), 1080, 600));
        stage.setTitle("MicrochipStarApp!");
        stage.show();




    }
}
