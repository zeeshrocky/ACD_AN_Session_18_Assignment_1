package com.example.zeeshan.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static String URL =
            "http://api.openweathermap.org/data/2.5/weather?q=Bangalore,in&appid=d7b900681c37193223281142bd919019";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        new GetWeatherInfo().execute();
    }

    class GetWeatherInfo extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            String response;
            String weather_report = "";
            JSONWeather jsonWeather = new JSONWeather(URL);
            response = jsonWeather.getJSON();

            try {

                JSONObject jsonObject_weatherinfo = new JSONObject(response);

                JSONObject jsonObject_coord = jsonObject_weatherinfo.optJSONObject("coord");
                String latitude = jsonObject_coord.getString("lat");
                String longitude = jsonObject_coord.getString("lon");

                JSONArray jsonArray = jsonObject_weatherinfo.optJSONArray("weather");
                JSONObject jsonObject_desc = jsonArray.getJSONObject(0);
                String description = jsonObject_desc.optString("description");

                String base = jsonObject_weatherinfo.getString("base");

                JSONObject jsonObject_main = jsonObject_weatherinfo.optJSONObject("main");
                String min_temperature = jsonObject_main.getString("temp_min");
                String max_temperature = jsonObject_main.getString("temp_max");

                JSONObject jsonObject_wind = jsonObject_weatherinfo.optJSONObject("wind");
                String speed = jsonObject_wind.getString("speed");
                String degree = jsonObject_wind.getString("deg");

                JSONObject jsonObject_sys = jsonObject_weatherinfo.optJSONObject("sys");
                String country = jsonObject_sys.getString("country");

                String name = jsonObject_weatherinfo.getString("name");

                weather_report = "Country : " + country + "(" + name + ")" + "\n" + "\n" +
                        "Base : " + base + "\n" + "\n" +
                        "Description : " + description + "\n" + "\n" +
                        "Latitude : " + latitude + "\n" + "\n" +
                        "Longitude: " + longitude + "\n" + "\n" +
                        "Minimum Temperature : " + min_temperature + "\n" + "\n" +
                        "Maximum Temperature : " + max_temperature + "\n" + "\n" +
                        "Speed : " + speed + " Degree : " + degree + "\n";
                Log.e("Weather Report in async", weather_report);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return weather_report;
        }

        @Override
        protected void onPostExecute(String weather_report)
        {
            textView.setText(weather_report);
        }
    }
}