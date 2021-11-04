package datasqltest;

import datasqltest.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class productListController implements Initializable {
    @FXML
    private ImageView imageView;

    public void initialize(URL url, ResourceBundle resourceBundle){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();

        String connectQuery = "SELECT * FROM microchipapp.product";
        String connectQueryRole = "";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);
            while (queryOutPut.next()){
                File brandingFile = new File(queryOutPut.getString("image_P"));
                Image brandingImage = new Image(brandingFile.toURI().toString());
                imageView.setImage(brandingImage);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

//        File brandingFile = new File("image/medium-ATTINY202-SOIC-8.png");
//        Image brandingImage = new Image(brandingFile.toURI().toString());
//        imageView.setImage(brandingImage);


    }

    public void logOutButtonOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));

        stage.show();

    }

//    @FXML
//    public void handleUploadButton(ActionEvent event){
//        FileChooser chooser = new FileChooser();
//        // SET FILECHOOSER INITIAL DIRECTORY
//        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
//        System.out.println(chooser);
//        // DEFINE ACCEPTABLE FILE EXTENSION
//        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
//        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
//        Node source = (Node) event.getSource();
//        File file = chooser.showOpenDialog(source.getScene().getWindow());
//        if (file != null){
//            try {
//                // CREATE FOLDER IF NOT EXIST
//                File destDir = new File("images");
//                System.out.println(destDir);
//                if (!destDir.exists()) destDir.mkdirs();
//                // RENAME FILE
//                String[] fileSplit = file.getName().split("\\.");
//                String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
//                        + fileSplit[fileSplit.length - 1];
//                Path target = FileSystems.getDefault().getPath(
//                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
//                );
//                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
//                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING );
//                // SET NEW FILE PATH TO IMAGE
//                userImageView.setImage(new Image(target.toUri().toString()));
//                selectedMemberCard.setImagePath(destDir + "/" + filename);
//                dataSource.writeData(cardList);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
