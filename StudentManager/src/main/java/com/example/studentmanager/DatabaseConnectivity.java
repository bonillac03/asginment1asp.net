package com.example.studentmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

public class DatabaseConnectivity {

    Connection conn = null;
    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/assign1_javafx","root","");
           // JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    public static ObservableList<Student> getDatausers(){
        Connection conn = ConnectDb();
        ObservableList<Student> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from students");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Student(Integer.parseInt(rs.getString("std_id")), rs.getString("full_name"), rs.getString("email"), rs.getString("major")));
            }
        } catch (Exception e) {
        }
        return list;
    }

}

