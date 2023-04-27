import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

public class WeatherHelper {
   // private static final String API_KEY = "6d8b2da1d0e0badc321d2b838d1c5691";

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

}
