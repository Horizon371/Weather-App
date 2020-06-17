package com.example.weatherapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.weatherapplication.Common.ApiData;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    TextView textView, pressureView, humidityView, feelsLikeView;
    String latitude, longitude;
    LocationManager locationManager;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location == null) {
                Toast.makeText(getApplicationContext(), "An error occurred while requesting the location", Toast.LENGTH_SHORT).show();
                return;
            }

            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());

            try {
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
            } catch (ExecutionException | InterruptedException | JSONException e) {
                Log.d("TRACE", "onLocationChanged: " + e);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        pressureView = findViewById(R.id.pressureView);
        humidityView = findViewById(R.id.humidityView);
        feelsLikeView = findViewById(R.id.feelsLikeView);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
    }
}

