/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pidev.event.gui;

import org.pidev.event.entities.Tool;
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
import javafx.scene.input.MouseEvent;;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.CheckBoxTableCell;
/**
 * FXML Controller class
 *
 * @author mizoj
 */
public class ToolInterfaceController implements Initializable {

    @FXML
    private TextField tfIdTool;
    @FXML
    private TextField tfNameTool;
    @FXML
    private CheckBox cbReturnedTool;
    @FXML
    private TableView<Tool> tvTools;
    @FXML
    private TableColumn<Tool, Integer> colIdTool;
    @FXML
    private TableColumn<Tool, String> colNameTool;
    @FXML
    private TableColumn<Tool, Boolean> colReturned;
    @FXML
    private Button btnInsertTool;
    @FXML
    private Button btnUpdateTool;
    @FXML
    private Button btnDeleteTool;
    @FXML
    private AnchorPane toolpane;
    @FXML
    private Button loadEvents;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showTools();
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
         if(event.getSource() == btnInsertTool)
             insertTool();
         else if (event.getSource() == btnUpdateTool)
             updateTool();
         else if (event.getSource() == btnDeleteTool)
             deleteTool();
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

public ObservableList <Tool> getToolsList(){
ObservableList<Tool> toolList = FXCollections.observableArrayList();
Connection conn = getConnection();
String query = "SELECT * FROM tool";
    Statement st;
    ResultSet rs;
    try{
        st = conn.createStatement();
        rs = st.executeQuery(query);
        Tool t ;
       while(rs.next()){  
         t = new Tool(rs.getInt("id"), rs.getString("name"), rs.getBoolean("returned"));
         toolList.add(t);
       }
    }
    catch(Exception ex){
        ex.printStackTrace();
        
    }
return toolList;
}

public void showTools(){

    ObservableList<Tool> list = getToolsList();

colIdTool.setCellValueFactory(new PropertyValueFactory<Tool,Integer>("id"));
colNameTool.setCellValueFactory(new PropertyValueFactory<Tool,String>("name"));
colReturned.setCellValueFactory(new PropertyValueFactory<>("returned"));
//colReturned.setCellFactory(CheckBoxTableCell.forTableColumn(returned));



tvTools.setItems(list);
}

  private void insertTool(){
       
        String query = "INSERT INTO tool(id, name, returned) VALUES ("+ tfIdTool.getText()+",'"+tfNameTool.getText()+"',"+cbReturnedTool.isSelected()+")";
       // "INSERT INTO event VALUES ("+ tfId.getText() +",'" + tfNom.getText()+  "','" + tfType.getText() + "','" +tfDate.getText() + "')" ;
        executeQuery(query);
        
        showTools();
    }
private void updateTool(){
 //String query = "UPDATE  books SET title  = '" + tfTitle.getText() + "', author = '" + tfAuthor.getText() + "', year = " + tfYear.getText() + ", pages = " + tfPages.getText() + " WHERE id = " + tfId.getText() + "";
    
       String query = " UPDATE tool SET name ='"+tfNameTool.getText()+ "',returned="+cbReturnedTool.isSelected()+" WHERE id ="+tfIdTool.getText() +"";
        executeQuery(query);
        showTools();
    }



  private void deleteTool(){
        String query = "DELETE FROM tool WHERE id =" + tfIdTool.getText() + "";
        executeQuery(query);
        showTools();
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
    private void loadEvents(ActionEvent event) throws IOException {
    AnchorPane pane = FXMLLoader.load(getClass().getResource("Main.fxml"));
    toolpane.getChildren().setAll(pane);
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
    Tool t = tvTools.getSelectionModel().getSelectedItem();
        tfIdTool.setText(String.valueOf(t.getId()));
        tfNameTool.setText(t.getName());
        //cbReturnedTool.setText(String.valueOf(t.isReturned()));
    }

}
