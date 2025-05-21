package com.example.parkpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RouteMapActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    String endLat;
    String endLong;
    String id;
    double lat,longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        id  = getIntent().getStringExtra("id");
        getLocation();
        getMyLocation();
    }

    public void getLocation() {
        if (ContextCompat.checkSelfPermission(RouteMapActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RouteMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }


        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, RouteMapActivity.this);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    longi = location.getLongitude();

                }
            }
        });
    }

    public void getMyLocation(){

        String url = "https://apiforprojects.shop/ParkPartner/get_lat_long.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("success")){
                        endLat=jsonObject.getString("Latitude");
                        endLong=jsonObject.getString("Longitude");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&origin="+lat+","+longi+"&destination="+endLat+","+endLong+""));
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(RouteMapActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException ex){
                    ex.printStackTrace();
                    Toast.makeText(RouteMapActivity.this, "Exception", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RouteMapActivity.this, "Exception", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map <String,String> params = new HashMap<>();
                params.put("Alloter_id",id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}