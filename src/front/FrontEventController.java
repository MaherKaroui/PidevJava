/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package front;

//API STUFF
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import org.controlsfx.control.Notifications;

// other shiiii
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.pidev.event.entities.Event;
import sun.reflect.Reflection;

/**
 * FXML Controller class
 *
 * @author mizoj
 */
public class FrontEventController implements Initializable {

    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Event> tvEventsFront;
    @FXML
   private TableColumn<Event, String> colNom;
    @FXML
    private TableColumn<Event, String> colType;
    @FXML
    private TableColumn<Event, String> colDate;
    @FXML
    private TextField searchFieldFront;
private FilteredList<Event> filteredData;
    private ObservableList<Event> data;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showEvents();
        searchEventFront();
    }    
 public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projet3a", "root", "");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
    public ObservableList<Event> getEventsList() {
        ObservableList<Event> eventList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM event";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Event e;
            while (rs.next()) {
                e = new Event(rs.getInt("id"), rs.getString("nom"), rs.getString("type"), rs.getDate("date_event"));
                eventList.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return eventList;
    }
    
    public void showEvents() {

        ObservableList<Event> list = getEventsList();

        //colId.setCellValueFactory(new PropertyValueFactory<Event, Integer>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Event, String>("nom"));
        colType.setCellValueFactory(new PropertyValueFactory<Event, String>("type"));
        colDate.setCellValueFactory(cellData -> {
            // Use a SimpleStringProperty to display the birthdate as a String
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_event = dateFormat.format(cellData.getValue().getDate());
            return new SimpleStringProperty(date_event);
        });
        // Set the custom comparator for the name column
        colNom.setComparator(String::compareToIgnoreCase);

        tvEventsFront.setItems(list);
        //tvEvents.getSortOrder().add(colNom);
        //tvEvents.sort();

    }

     private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void handeMouseAction(MouseEvent event) {
    }

    @FXML
    private void loadToolsFront(ActionEvent event) {
    }


    
    public void searchEventFront() {
             // search 

        data = tvEventsFront.getItems();
        filteredData = new FilteredList<>(data, p -> true);
        // Bind the TableView's items property to the filtered list
        tvEventsFront.setItems(filteredData);

        // Set up an event listener on the searchbar TextField
        searchFieldFront.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myData -> {
                // If searchbar is empty, show all data
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare the search keyword with data values using the contains() method
                String lowerCaseFilter = newValue.toLowerCase();
                if (myData.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // keyword matches with Value1
                } else if (myData.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // keyword matches with Value2
                }
                return false; // keyword doesn't match with any value
            });
        });
    }
    
}
