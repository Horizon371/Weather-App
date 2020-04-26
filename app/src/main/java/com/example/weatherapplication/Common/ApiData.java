package com.example.weatherapplication.Common;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class ApiData extends AsyncTask<Void, Void, String> {
    public static String API_KEY = "48b4b3a3e8e18b4be761e54587412577";
    public static String API_LINK = "api.openweathermap.org/data/2.5/weather?lat=35&lon=139";
    String latitude, longitude;

    public ApiData(String latitude, String longitude) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String apiRequest() {
        return "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&APPID=" + API_KEY + "&units=metric";
    }

//    public String unixTimeStampToDateTime(double unixTimeStamp)
//    {
//        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
//        Date date = new Date();
//        date.setTime((long)unixTimeStamp*1000);
//        return dateFormat.format(date);
//    }

    public String getDataFromSite(String urlString)
    {
        String siteData = null;
        try {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder siteDataSB = new StringBuilder();
            String inputLine;
            while ((inputLine = input.readLine()) != null)
                siteDataSB.append(inputLine);
            input.close();
            siteData = siteDataSB.toString();

        }catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return siteData;
    }

    public String getImage(String icon)
    {
        return null;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return getDataFromSite(apiRequest());
    }
}
