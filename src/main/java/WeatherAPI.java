import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPI {

    public String getWeatherData() {
        StringBuilder apiResponse = new StringBuilder();
        try {
            String apiKey = "f1390de3bfafce751194a52297be7e34";
            int cityID = 524901;//Moscow City ID
            String apiURL = "http://api.openweathermap.org/data/2.5/forecast?id=" + cityID + "&appid=" + apiKey;

            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
            }//end if

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;

            while ((output = br.readLine()) != null) {
                apiResponse.append(output);
            }//end while
            conn.disconnect();
            //System.out.println(apiResponse.toString());
            JSONObject jsonResponse = new JSONObject(apiResponse.toString());
            JSONArray list = jsonResponse.getJSONArray("list");
            JSONObject firstListObject = list.getJSONObject(0);
            JSONArray weather = firstListObject.getJSONArray("weather");
            JSONObject weatherObject = weather.getJSONObject(0);
            String description = weatherObject.getString("description");
            JSONObject main = firstListObject.getJSONObject("main");
            double temperature = main.getDouble("temp");

            return "Weather in Moscow: " + description + " Current temperature in Moscow: " + temperature;

        } catch (Exception e) {
            System.out.println("Exception in WeatherApp.main():" + e.getMessage());
        }//end catch
        return "";
    }//end getWeatherData
}//end class
