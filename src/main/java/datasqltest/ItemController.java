package datasqltest;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ItemController {

    @FXML
    private Label nameLabel,idLabel;
    @FXML
    private Text idText;

    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent){
        myListener.onClickListener(profileModel);
    }

    private Product profileModel;
    private MyListener myListener;

    public void setData(Product profileModel, MyListener myListener){
        this.profileModel = profileModel;
        this.myListener = myListener;
        nameLabel.setText(profileModel.getName_P());
        idText.setText(HelloApplication.CURRENCY + profileModel.getId_P());
//        Image image = new Image(getClass().getResourceAsStream(profileModel.getImgSrc()));

        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String connectQuery = "SELECT * FROM microchip.product";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);
            while (queryOutPut.next()){
                File brandingFile = new File("image/"+profileModel.getImage_P());
                Image brandingImage = new Image(brandingFile.toURI().toString());
                img.setImage(brandingImage);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
//        Image image = new Image("file:" + profileModel.getImage_P(), true);
//        img.setImage(image);

    }
}
