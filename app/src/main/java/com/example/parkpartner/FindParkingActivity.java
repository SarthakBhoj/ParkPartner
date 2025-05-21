package com.example.parkpartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkpartner.adapter.Parker;
import com.example.parkpartner.adapter.ParkerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindParkingActivity extends AppCompatActivity {

    private ArrayList<Parker> parkerArrayList;
    private ParkerAdapter parkerAdapter;
    RecyclerView rview;
    ProgressDialog pDialog;
    Context context = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_parking);
        context = getApplicationContext();
        rview =  findViewById(R.id.rview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void list()
    {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("downloading, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        pDialog.show();

        String url = "https://apiforprojects.shop/ParkPartner/alloters.php";

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("Response ", ">> " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            parkerArrayList = new ArrayList<>();
                            for (int i = 0; i < jsonData.length(); i++) {
                                Parker parker = new Parker();
                                JSONObject jo = jsonData.getJSONObject(i);
                                parker.alloter_id = jo.getString("Alloter_id");
                                parker.alloter_name = jo.getString("Alloter_Name");
                                parker.password = jo.getString("Alloter_Password");
                                parker.alloter_mail = jo.getString("Alloter_Mail");
                                parker.mob_no = jo.getString("Alloter_Number");
                                parker.alloter_space = jo.getString("Alloter_Space");
                                parker.alloter_price = jo.getString("Price");
                                parker.latitude = jo.getString("Latitude");
                                parker.longitude = jo.getString("Longitude");

                                parkerArrayList.add(parker);
                            }
                            parkerAdapter = new ParkerAdapter(getApplicationContext(), parkerArrayList);
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                            rview.setLayoutManager(layoutManager);
                            rview.setAdapter(parkerAdapter);
                        } catch (Exception e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
