package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.weatherapplication.Common.ApiData;
import com.example.weatherapplication.Common.GPSTracker;
import com.example.weatherapplication.Model.Weather;
import com.example.weatherapplication.Model.WeatherApi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity{

    Button refreshButton;
    TextView textView, pressureView, humidityView, feelsLikeView;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshButton = findViewById(R.id.refreshButton);
        textView = findViewById(R.id.textView);
        pressureView = findViewById(R.id.pressureView);
        humidityView = findViewById(R.id.humidityView);
        feelsLikeView = findViewById(R.id.feelsLikeView);
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

    public void onClick(View v) throws ExecutionException, InterruptedException, JSONException {
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        Location location = gpsTracker .getLocation();
        if (location != null)
        {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
        }
        ApiData apiData = new ApiData(latitude, longitude);
        String data = apiData.execute().get();
        JSONObject jsonObject = new JSONObject(data);
        JSONObject main = jsonObject.getJSONObject("main");
        String temp = String.valueOf(main.getDouble("temp"));
        String feels_like = String.valueOf(main.getDouble("feels_like"));
        String pressure = String.valueOf(main.getDouble("pressure"));
        String humidity = String.valueOf(main.getDouble("humidity"));

        textView.setText(temp);
        pressureView.setText(pressure);
        feelsLikeView.setText(feels_like);
        humidityView.setText(humidity);
    }
}

