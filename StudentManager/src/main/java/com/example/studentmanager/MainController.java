package com.example.studentmanager;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class MainController implements Initializable {


    @FXML
    private TableView<Student> table_students;

    @FXML
    private TableColumn<Student, Integer> col_std_id;

    @FXML
    private TableColumn<Student, String> col_std_name;

    @FXML
    private TableColumn<Student, String> col_email;

    @FXML
    private TableColumn<Student, String> col_major;


     @FXML
    private TextField txt_std_id;

    @FXML
    private TextField txt_std_fullName;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_major;


    @FXML
    private TextField filterField;


    ObservableList<Student> listM;
    ObservableList<Student> dataList;



    int index = -1;

    Connection conn =null;
    PreparedStatement pst = null;


    public void Add_students (){
        conn = DatabaseConnectivity.ConnectDb();
        String sql = "insert into students (std_id,full_name,email,major)values(?,?,?,? )";
        try {
            assert conn != null;
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_std_id.getText());
            pst.setString(2, txt_std_fullName.getText());
            pst.setString(3, txt_email.getText());
            pst.setString(4, txt_major.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Student added successfully");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    //////// methode select users ///////
    @FXML
    void getSelected (MouseEvent event){
    index = table_students.getSelectionModel().getSelectedIndex();
    if (index <= -1){

        return;
    }
    txt_std_id.setText(String.valueOf(col_std_id.getCellData(index)));
    txt_std_fullName.setText(col_std_name.getCellData(index));
    txt_email.setText(col_email.getCellData(index));
    txt_major.setText(col_major.getCellData(index));

    }

    public void Edit (){
        try {
            conn = DatabaseConnectivity.ConnectDb();
            String value1 = txt_std_id.getText();
            String value2 = txt_std_fullName.getText();
            String value3 = txt_email.getText();
            String value4 = txt_major.getText();
            String sql = "update students set std_id= '"+value1+"', full_name= '"+value2+"', email= '"+
                    value3+"', major= '"+value4+"' where std_id='"+value1+"' ";
            pst= conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Student updated successfully");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void Delete(){
    conn = DatabaseConnectivity.ConnectDb();
    String sql = "delete from students where std_id = ?";
        try {
            assert conn != null;
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_std_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Student deleted successfully");
            UpdateTable();
            search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }


    public void UpdateTable(){
        col_std_id.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        col_std_name.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_major.setCellValueFactory(new PropertyValueFactory<>("major"));

        listM = DatabaseConnectivity.getDatausers();
        table_students.setItems(listM);
    }




 @FXML
    void search_user() {
        col_std_id.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        col_std_name.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_major.setCellValueFactory(new PropertyValueFactory<>("major"));

        dataList = DatabaseConnectivity.getDatausers();
        table_students.setItems(dataList);
        FilteredList<Student> filteredData = new FilteredList<>(dataList, b -> true);
 filterField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(person -> {
    if (newValue == null || newValue.isEmpty()) {
     return true;
    }
    String lowerCaseFilter = newValue.toLowerCase();

    if (person.getStudent_name().toLowerCase().contains(lowerCaseFilter)) {
     return true; // Filter matches name
    } else if (person.getEmail().toLowerCase().contains(lowerCaseFilter)) {
     return true; // Filter matches email
    }else // Does not match.
        if (person.getMajor().toLowerCase().contains(lowerCaseFilter)) {
     return true; // Filter matches major
    }
    else return String.valueOf(person.getStudent_id()).contains(lowerCaseFilter);// Filter matches student id
   }));
  SortedList<Student> sortedData = new SortedList<>(filteredData);
  sortedData.comparatorProperty().bind(table_students.comparatorProperty());
  table_students.setItems(sortedData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    UpdateTable();
    search_user();
    // Code Source in description
    }
}

