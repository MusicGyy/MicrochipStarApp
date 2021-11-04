module com.example.datasqltest {
    requires javafx.controls;
    requires javafx.fxml;
//    requires java.sql;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;
    requires javafx.graphics;

    opens datasqltest to javafx.fxml;
    exports datasqltest;


}