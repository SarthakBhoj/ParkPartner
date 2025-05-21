package com.example.parkpartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parkpartner.adapter.History;
import com.example.parkpartner.adapter.HistoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingHistoryActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    private ArrayList<History> historyArrayList;
    private HistoryAdapter historyAdapter;
    RecyclerView rview;
    Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        rview = findViewById(R.id.rview);
        context=getApplicationContext();
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

        String url = "https://apiforprojects.shop/ParkPartner/history.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("Response ", ">> " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            historyArrayList = new ArrayList<>();
                            for (int i = 0; i < jsonData.length(); i++) {
                                History history = new History();
                                JSONObject jo = jsonData.getJSONObject(i);
                                history.booking_id = jo.getString("Booking_id");
                                history.user_id = jo.getString("User_id");
                                history.alloter_name = jo.getString("Alloter_Name");
                                history.alloter_mail = jo.getString("Alloter_Mail");
                                history.alloter_no = jo.getString("Alloter_Number");
                                history.alloter_price = jo.getString("Price");
                                historyArrayList.add(history);
                            }
                            historyAdapter = new HistoryAdapter(getApplicationContext(), historyArrayList);
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                            rview.setLayoutManager(layoutManager);
                            rview.setAdapter(historyAdapter);
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
                String query ="SELECT CValue FROM Configuration WHERE CName ='User_id'";
                Log.e("Response", ""+DBClass.getSingleValue(query));
                params.put("User_id",DBClass.getSingleValue(query));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}