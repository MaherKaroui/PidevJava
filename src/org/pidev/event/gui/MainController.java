/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pidev.event.gui;

//API STUFF
import org.pidev.event.entities.Event;
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
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

// pdf related
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import java.awt.Desktop;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

/**
 *
 * @author mizoj
 */
public class MainController implements Initializable {

    //private Label label;
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

    private AutoCompletionBinding<String> autoCompletionBinding;
    private String[] possibleSuggestions = {"test"};

    private Set<String> possibleSuggestionsSet = new HashSet<>(Arrays.asList(possibleSuggestions));

    // Your Twilio Account SID and Auth Token
    public static final String ACCOUNT_SID = "AC3b21d23b8d2a2b69b3da427c1b76d1ac";
    public static final String AUTH_TOKEN = "ed7d1b731e8d3e119898a8cdca453808";
    private static final String API_KEY = "6d8b2da1d0e0badc321d2b838d1c5691";

    private FilteredList<Event> filteredData;
    private ObservableList<Event> data;
    @FXML
    private Button btWeather;
    @FXML
    private WebView webView;
    
private WebEngine webEngine;

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
    /*
        //map stuff    
        webEngine = webView.getEngine();
    webEngine.load(getClass().getResource("map.html").toExternalForm());
      */
        //learning textfield 
        autoCompletionBinding = TextFields.bindAutoCompletion(searchField, possibleSuggestionsSet);
        searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                switch (ke.getCode()) {
                    case ENTER:
                        autoCompletionLearnWord(searchField.getText().trim());
                        break;
                    default:
                        break;
                }
            }
        });
        //showDirections("Ennasr");
    }

    private void autoCompletionLearnWord(String newWord) {
        possibleSuggestionsSet.add(newWord);
        // we dispose of old binding and recreate a new binding
        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(searchField, possibleSuggestionsSet);
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

        tvEvents.setItems(list);
        //tvEvents.getSortOrder().add(colNom);
        //tvEvents.sort();
        showDirections("ennasr");


    }

    private void insertEvent() {
        //String query = "INSERT INTO event VALUES (" + tfId.getText() + ",'" + tfNom.getText() + "','" + tfType.getText() + "')";

        String query = "INSERT INTO `event`(`nom`, `type`, `date_event`) VALUES ('" + tfNom.getText() + "','" + tfType.getText() + "','" + tfDate.getText() + "');";
        executeQuery(query);

        sendSms("+21650307811", "A new event:'" + tfNom.getText() + "' has been added!, go check it out");
        showEvents();
        cleanUp();
        pushNotif("event", "added successfully");
    }

    private void updateEvent() {
        //String query = "UPDATE  books SET title  = '" + tfTitle.getText() + "', author = '" + tfAuthor.getText() + "', year = " + tfYear.getText() + ", pages = " + tfPages.getText() + " WHERE id = " + tfId.getText() + "";

        String query = " UPDATE event SET nom ='" + tfNom.getText() + "',type='" + tfType.getText() + "',date_event='" + tfDate.getText() + "' WHERE nom ='" + tfNom.getText() + "'";
        executeQuery(query);
        showEvents();
        cleanUp();
        pushNotif("event", "updated successfully");
    }

    private void deleteEvent() {
        String query = "DELETE FROM event WHERE nom ='" + tfNom.getText() + "'";
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
        //tfId.setText(String.valueOf(e.getId()));
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
        // tfId.setText("");
        tfNom.setText("");
        tfType.setText("");
        tfDate.setText("");

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
        e.testing(tfDate.getText());

    }

    public <T> void exportTableViewToPDF(TableView<T> tableView, String fileName) {
        // Initialize PDF writer
        PdfWriter writer;
        try {
            writer = new PdfWriter(new FileOutputStream(new File(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
// Create a custom color
        Color customColor = new DeviceRgb(12, 108, 172);
        // Add title
        Paragraph title = new Paragraph("COME ONE COME ALL")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(24)
                .setMarginBottom(20)
                .setFontColor(customColor);

        document.add(title);

        // Optionally add an image
        try {
            //deja fama librairie testaamel fel image donc explicite hakka chissirech conflict 
            com.itextpdf.layout.element.Image img = new com.itextpdf.layout.element.Image(ImageDataFactory.create("C:\\Users\\mizoj\\Desktop\\KRAYA\\3rd year\\2EME SEM\\PiDEV\\java\\TEAMWORK\\pidevjava3a54\\src\\org\\pidev\\event\\back-logo.jpeg"))
                    .setWidth(UnitValue.createPercentValue(50))
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);

            document.add(img);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Create a table
        int numOfColumns = tableView.getColumns().size();
        Table table = new Table(numOfColumns);
        table.setWidth(UnitValue.createPercentValue(100));

        // Add column headers
        for (TableColumn<T, ?> col : tableView.getColumns()) {
            table.addHeaderCell(new Cell()
                    .add(new Paragraph(col.getText()))
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
        }

        // Add rows data
        ObservableList<T> items = tableView.getItems();
        for (T item : items) {
            for (TableColumn<T, ?> col : tableView.getColumns()) {
                Object cellData = ((TableColumn<T, Object>) col).getCellObservableValue(item).getValue();
                String cellText = cellData == null ? "" : cellData.toString();
                table.addCell(new Cell()
                        .add(new Paragraph(cellText))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPadding(5));
            }
        }

        // Add table to document
        document.add(table);
        document.close();
    }

    @FXML
    private void exportToPdf(ActionEvent event) {
        String fileName = "exported_data.pdf";
        exportTableViewToPDF(tvEvents, fileName);
        pushNotif("PDF", "created");
    }


public void showDirections(String destination) {
    WebEngine webEngine = webView.getEngine();
    String apiKey = "AIzaSyAu_mO7cXmq6woA_NFn-idriInCb5wx4DU"; // Replace with your Google Maps API key
    
    String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "  <style>\n" +
            "    #map { height: 100%; }\n" +
            "    html, body { height: 100%; margin: 0; padding: 0; }\n" +
            "  </style>\n" +
            "  <script src=\"https://maps.googleapis.com/maps/api/js?key=" + apiKey + "\"></script>\n" +
            "  <script>\n" +
            "    function initMap() {\n" +
            "      var directionsService = new google.maps.DirectionsService();\n" +
            "      var directionsRenderer = new google.maps.DirectionsRenderer();\n" +
            "      var map = new google.maps.Map(document.getElementById('map'));\n" +
            "      directionsRenderer.setMap(map);\n" +
            "      navigator.geolocation.getCurrentPosition(function(position) {\n" +
            "        var origin = { lat: position.coords.latitude, lng: position.coords.longitude };\n" +
            "        var destination = \"" + destination + "\";\n" +
            "        var request = { origin: origin, destination: destination, travelMode: 'DRIVING' };\n" +
            "        directionsService.route(request, function(result, status) {\n" +
            "          if (status == 'OK') { directionsRenderer.setDirections(result); }\n" +
            "        });\n" +
            "      });\n" +
            "    }\n" +
            "  </script>\n" +
            "</head>\n" +
            "<body onload=\"initMap()\">\n" +
            "  <div id=\"map\"></div>\n" +
            "</body>\n" +
            "</html>";
    
    webEngine.loadContent(html);
}

    @FXML
    private void openHtmlInBrowser(ActionEvent event) {
            File htmlFile = new File("C:\\Users\\mizoj\\Desktop\\KRAYA\\3rd year\\2EME SEM\\PiDEV\\java\\TEAMWORK\\pidevjava3a54\\src\\rsc\\map.html");
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
