/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pidev.event;

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
import sun.reflect.Reflection;

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
    private TextField searchField;

    // Your Twilio Account SID and Auth Token
    public static final String ACCOUNT_SID = "AC3b21d23b8d2a2b69b3da427c1b76d1ac";
    public static final String AUTH_TOKEN = "ed7d1b731e8d3e119898a8cdca453808";
    private static final String API_KEY = "6d8b2da1d0e0badc321d2b838d1c5691";

    private FilteredList<Event> filteredData;
    private ObservableList<Event> data;
    @FXML
    private Button btWeather;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnInsert) {
            insertEvent();
        } else if (event.getSource() == btnUpdate) {
            updateEvent();
        } else if (event.getSource() == btnDelete) {
            deleteEvent();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showEvents();
        searchEvents();
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

        colId.setCellValueFactory(new PropertyValueFactory<Event, Integer>("id"));
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

        tvEvents.setItems(list);
        tvEvents.getSortOrder().add(colNom);
        tvEvents.sort();

    }

    private void insertEvent() {
        //String query = "INSERT INTO event VALUES (" + tfId.getText() + ",'" + tfNom.getText() + "','" + tfType.getText() + "')";

        String query = "INSERT INTO event VALUES (" + tfId.getText() + ",'" + tfNom.getText() + "','" + tfType.getText() + "','" + tfDate.getText() + "')";
        executeQuery(query);

        sendSms("+21650307811", "A new event:' " + tfNom.getText() + "' has been added!, go check it out");
        showEvents();
        cleanUp();
        pushNotif("event", "added successfully");
    }

    private void updateEvent() {
        //String query = "UPDATE  books SET title  = '" + tfTitle.getText() + "', author = '" + tfAuthor.getText() + "', year = " + tfYear.getText() + ", pages = " + tfPages.getText() + " WHERE id = " + tfId.getText() + "";

        String query = " UPDATE event SET nom ='" + tfNom.getText() + "',type='" + tfType.getText() + "',date_event='" + tfDate.getText() + "' WHERE id =" + tfId.getText() + "";
        executeQuery(query);
        showEvents();
        cleanUp();
        pushNotif("event", "updated successfully");
    }

    private void deleteEvent() {
        String query = "DELETE FROM event WHERE id =" + tfId.getText() + "";
        executeQuery(query);
        showEvents();
        cleanUp();
        pushNotif("event", "deleted successfully");
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
        Event e = tvEvents.getSelectionModel().getSelectedItem();
        tfId.setText(String.valueOf(e.getId()));
        tfNom.setText(e.getNom());
        tfType.setText(e.getType());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(e.getDate());

        tfDate.setText(dateString);

    }

    @FXML
    private void loadTools(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("ToolInterface.fxml"));
        rootpane.getChildren().setAll(pane);
    }

    @FXML
    private void handlePieChart(ActionEvent event) {
        ObservableList<Event> events = getEventsList();
        Map<String, Long> eventTypeCounts = events.stream()
                .collect(Collectors.groupingBy(Event::getType, Collectors.counting()));
        PieChart chart = new PieChart();
        eventTypeCounts.forEach((type, count) -> chart.getData().add(new PieChart.Data(type, count)));
        Pane root = new Pane();
        root.getChildren().add(chart);
        Scene scene = new Scene(root, 500, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void searchEvents() {
        // search 

        data = tvEvents.getItems();
        filteredData = new FilteredList<>(data, p -> true);
        // Bind the TableView's items property to the filtered list
        tvEvents.setItems(filteredData);

        // Set up an event listener on the searchbar TextField
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
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

    public static void sendSms(String toPhoneNumber, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber), // To phone number
                new PhoneNumber("+15076974179"), // From Twilio phone number
                messageBody)
                .create();

        System.out.println("SMS sent: " + message.getSid());
    }

    public void cleanUp() {
        tfId.setText("");
        tfNom.setText("");
        tfType.setText("");

    }

   /* public static JSONObject getWeatherForecast( String date) {
         
        try {
            // Replace "city" and "countryCode" with the desired location and "date" with the date in the format "yyyy-MM-dd"
            String apiUrl = String.format(
                    "https://api.openweathermap.org/data/2.5/forecast?q=%s,%s&appid=%s&units=metric",
                    "Tunis", "TN", API_KEY);

            HttpResponse<JsonNode> response = Unirest.get(apiUrl).asJson();

            if (response.getStatus() == 200) {
                JSONObject jsonResponse = response.getBody().getObject();
                // Loop through the list of forecasts
                for (int i = 0; i < jsonResponse.getJSONArray("list").length(); i++) {
                    JSONObject forecast = jsonResponse.getJSONArray("list").getJSONObject(i);
                    String forecastDate = forecast.getString("dt_txt").substring(0, 10);

                    if (forecastDate.equals(date)) {
                        return forecast;
                    }
                }
            } else {
                System.out.println("Error fetching weather data: " + response.getStatusText());
            }
        } catch (Exception e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
        }
        return null;
    }
*/
    public void pushNotif(String title, String text) {
        //Image img = new Image ("16px+Free+Set+confirm.png");
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
        notificationBuilder.show();

    }

    @FXML
    private void showWeather(ActionEvent event) throws IOException {
       //getWeatherForecast("2023-04-27");
       
       AnchorPane pane1 = FXMLLoader.load(getClass().getResource("WeatherUI.fxml"));
       rootpane.getChildren().setAll(pane1);
  
   WeatherUIController e = new WeatherUIController();
   //JSONObject j =  e.getWeatherForecast("Tunis", "TN", "2023-27-04");
   
  e.testing();

}
}
