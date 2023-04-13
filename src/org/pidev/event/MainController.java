/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pidev.event;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author mizoj
 */
public class MainController implements Initializable {
    
    //private Label label;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfDate;
    @FXML
    private TableView<Event> tvEvents;
    @FXML
    private TableColumn<Event, Integer> colId;
    @FXML
    private TableColumn<Event, String> colNom;
    @FXML
    private TableColumn<Event, String> colType;
    @FXML
    private TableColumn<Event, String> colDate;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private AnchorPane rootpane;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
         if(event.getSource() == btnInsert)
             insertEvent();
         else if (event.getSource() == btnUpdate)
             updateEvent();
         else if (event.getSource() == btnDelete)
             deleteEvent();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showEvents();
    }    
    
public Connection getConnection(){
    Connection conn;
     try{
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projet3a", "root","");
         return conn;
    }catch(Exception ex){
        System. out. println( "Error: "+ ex.getMessage());
        return null;
    }
}

public ObservableList <Event> getEventsList(){
ObservableList<Event> eventList = FXCollections.observableArrayList();
Connection conn = getConnection();
String query = "SELECT * FROM event";
    Statement st;
    ResultSet rs;
    try{
        st = conn.createStatement();
        rs = st.executeQuery(query);
        Event e ;
       while(rs.next()){  
         e = new Event(rs.getInt("id"), rs.getString("nom"), rs.getString("type"),rs.getDate("date_event"));
         eventList.add(e);
       }
    }
    catch(Exception ex){
        ex.printStackTrace();
        
    }
return eventList;
}


public void showEvents(){

    ObservableList<Event> list = getEventsList();

colId.setCellValueFactory(new PropertyValueFactory<Event,Integer>("id"));
colNom.setCellValueFactory(new PropertyValueFactory<Event,String>("nom"));
colType.setCellValueFactory(new PropertyValueFactory<Event,String>("type"));
colDate.setCellValueFactory(new PropertyValueFactory<Event,String>("date_event"));

tvEvents.setItems(list);
}

    private void insertEvent(){
        //String query = "INSERT INTO event VALUES (" + tfId.getText() + ",'" + tfNom.getText() + "','" + tfType.getText() + "')";
        
        String query = "INSERT INTO event VALUES ("+ tfId.getText() +",'" + tfNom.getText()+  "','" + tfType.getText() + "','" +tfDate.getText() + "')" ;
        executeQuery(query);
        
        showEvents();
    }

   private void updateEvent(){
 //String query = "UPDATE  books SET title  = '" + tfTitle.getText() + "', author = '" + tfAuthor.getText() + "', year = " + tfYear.getText() + ", pages = " + tfPages.getText() + " WHERE id = " + tfId.getText() + "";
    
       String query = " UPDATE event SET nom ='"+tfNom.getText()+ "',type='"+tfType.getText()+"',date_event='"+tfDate.getText()+"' WHERE id ="+tfId.getText() +"";
        executeQuery(query);
        showEvents();
    }



  private void deleteEvent(){
        String query = "DELETE FROM event WHERE id =" + tfId.getText() + "";
        executeQuery(query);
        showEvents();
    }




 private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void handeMouseAction(MouseEvent event) {
        Event e = tvEvents.getSelectionModel().getSelectedItem();
        tfId.setText(String.valueOf(e.getId()));
        tfNom.setText(e.getNom());
        tfType.setText(e.getType());
     
    }

    @FXML
    private void loadTools(ActionEvent event) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("ToolInterface.fxml"));
    rootpane.getChildren().setAll(pane);
    } 

   
}
