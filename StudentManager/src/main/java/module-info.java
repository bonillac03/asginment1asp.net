module com.example.studentmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;


    opens com.example.studentmanager to javafx.fxml;
    exports com.example.studentmanager;
}