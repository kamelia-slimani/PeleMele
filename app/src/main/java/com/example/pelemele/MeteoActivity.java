package com.example.pelemele;

import android.Manifest;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;


public class MeteoActivity extends AppCompatActivity {
    protected Button longlat;
    protected FusedLocationProviderClient fusedLocationClient;

    protected ImageView icone;
    protected TextView temperature, ressenti, humidite, temp_max, temp_min, pression, ville;
    protected double longitude, latitude;

    protected HashMap<String, Integer> hashMap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        this.temperature = findViewById(R.id.temperature);
        this.ressenti = findViewById(R.id.ressenti);
        this.humidite = findViewById(R.id.humidite);
        this.temp_max = findViewById(R.id.temp_max);
        this.temp_min = findViewById(R.id.temp_min);
        this.pression = findViewById(R.id.pression);
        this.longlat = findViewById(R.id.longlat);
        this.ville = findViewById(R.id.ville);
        this.icone = findViewById(R.id.icone);


        hashMap = new HashMap<>();
        hashMap.put("01d", R.drawable._01d);
        hashMap.put("0nd", R.drawable._01n);
        hashMap.put("02d", R.drawable._02d);
        hashMap.put("02n", R.drawable._02n);
        hashMap.put("03d", R.drawable._03d);
        hashMap.put("03n", R.drawable._03n);
        hashMap.put("04d", R.drawable._04d);
        hashMap.put("04n", R.drawable._04n);
        hashMap.put("09d", R.drawable._09d);
        hashMap.put("09n", R.drawable._09n);
        hashMap.put("10d", R.drawable._10d);
        hashMap.put("10n", R.drawable._10n);
        hashMap.put("11d", R.drawable._11d);
        hashMap.put("11n", R.drawable._11n);
        hashMap.put("13d", R.drawable._13d);
        hashMap.put("13n", R.drawable._13n);
        hashMap.put("50d", R.drawable._50d);
        hashMap.put("50n", R.drawable._50n);

        //icone.setImageResource(R.drawable._10d);

        Runnable runnable = () -> {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener(this, location -> {
                if (location != null) {
                    Log.i("MeteoActivity", "Location différent de null");
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();

                    ExecutorService service = Executors.newSingleThreadExecutor();
                    service.execute(()->{
                        if((this.longitude!=0) && (latitude!= 0)){
                            String url ="https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=b67d7b801a7f3c6347a42667b2a0b282&lang=fr&units=metric";
                            InputStream in = null;

                            try {
                                in = new java.net.URL(url).openStream();
                                JSONObject res = readStream(in);
                                JSONObject v = res.getJSONObject("main");
                                JSONArray jArr = res.getJSONArray("weather");
                                JSONObject JSONWeather = jArr.getJSONObject(0);


                                Log.i("MeteoActivity", "" + JSONWeather.get("icon"));

                                runOnUiThread(() -> {
                                    try {
                                        icone.setImageResource(hashMap.get(JSONWeather.get("icon").toString()));
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                });

                                ville.setText(res.get("name") + "");
                                temperature.setText(getResources().getText(R.string.temperature) + " " + v.get("temp") + "°C");
                                ressenti.setText(getResources().getText(R.string.ressenti) + " " + v.get("feels_like") + "°C");
                                humidite.setText(getResources().getText(R.string.humidite) + " " + v.get("humidity") + "%");
                                temp_max.setText(getResources().getText(R.string.minimum) + " " + v.get("temp_max") + "°C");
                                temp_min.setText(getResources().getText(R.string.maximum) + " " + v.get("temp_min") + "°C");
                                pression.setText(getResources().getText(R.string.pression) + " " + v.get("pressure") + "hPa");


                            } catch (IOException | JSONException  e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
                    runOnUiThread(() -> {
                        Toast.makeText(MeteoActivity.this, "Longitude : " + longitude + " Latitude : " + latitude, Toast.LENGTH_SHORT).show();

                    });

                }
            });
        };



        ExecutorService service = Executors.newSingleThreadExecutor();
        longlat.setOnClickListener(view -> service.execute(runnable));



    }


    private JSONObject readStream(InputStream is) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000 );
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        is.close();
        return new JSONObject(sb.toString());

    }

    protected void getTemp() throws IOException, JSONException {

        String url ="https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=b67d7b801a7f3c6347a42667b2a0b282&lang=fr";
        InputStream in = new java.net.URL(url).openStream();
        JSONObject res = readStream(in) ;
        System.out.println(res);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "srcname");
            return d;
        } catch (Exception e) {
            return null;
        }
    }


}



