/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pidev.event;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.json.JSONObject;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * FXML Controller class
 *
 * @author mizoj
 */
public class WeatherUIController implements Initializable {

    @FXML
    private Label cityName;
    @FXML
    private ImageView weatherIcon;
    @FXML
    private Label temperature;
    @FXML
    private Label humidity;
    @FXML
    private Label weatherDescription;

    /**
     * Initializes the controller class.
     */
    public static JSONObject getWeatherForecast(String city, String countryCode, String date) {
        String API_KEY = "6d8b2da1d0e0badc321d2b838d1c5691";
        try {
            // Replace "city" and "countryCode" with the desired location and "date" with the date in the format "yyyy-MM-dd"
            String apiUrl = String.format(
                    "https://api.openweathermap.org/data/2.5/forecast?q=%s,%s&appid=%s&units=metric",
                    city, countryCode, API_KEY);

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
    @FXML
    private AnchorPane paneWeather;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

       LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        String city = "Tunis";
        String countryCode = "TN";
        String date = formattedDate; // Replace with the desired date in the format "yyyy-MM-dd"

        JSONObject forecast = this.getWeatherForecast(city, countryCode, date);

        if (forecast != null) {
            JSONObject main = forecast.getJSONObject("main");
            JSONObject weather = forecast.getJSONArray("weather").getJSONObject(0);
            String iconUrl = "http://openweathermap.org/img/wn/" + weather.getString("icon") + ".png";

            cityName.setText(city + ", " + countryCode);
            weatherIcon.setImage(new Image(iconUrl));
            temperature.setText(String.format("%.1f°C", main.getDouble("temp")));
            humidity.setText("Humidity: " + main.getInt("humidity") + "%");
            weatherDescription.setText(weather.getString("description"));
        } else {
            cityName.setText("Weather data not available");
        }

    }

    public void testing() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        String city = "Tunis";
        String countryCode = "TN";
        String date = formattedDate; // Replace with the desired date in the format "yyyy-MM-dd"

        JSONObject forecast = this.getWeatherForecast(city, countryCode, date);

        if (forecast != null) {
            JSONObject main = forecast.getJSONObject("main");
            JSONObject weather = forecast.getJSONArray("weather").getJSONObject(0);
            String iconUrl = "http://openweathermap.org/img/wn/" + weather.getString("icon") + ".png";

            cityName.setText(city + ", " + countryCode);
            weatherIcon.setImage(new Image(iconUrl));
            temperature.setText(String.format("%.1f°C", main.getDouble("temp")));
            humidity.setText("Humidity: " + main.getInt("humidity") + "%");
            weatherDescription.setText(weather.getString("description"));
        } else {
            cityName.setText("Weather data not available");
        }
    }

    @FXML
    private void btnBackEvent(ActionEvent event) throws IOException {

        AnchorPane pane2 = FXMLLoader.load(getClass().getResource("Main.fxml"));
        paneWeather.getChildren().setAll(pane2);

    }

}
