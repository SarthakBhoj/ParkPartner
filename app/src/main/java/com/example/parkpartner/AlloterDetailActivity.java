package com.example.parkpartner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AlloterDetailActivity extends AppCompatActivity {
    TextView emailId,mobId,locationId,nameId,priceId;

    String alloter_name,alloter_no,alloter_mail,alloter_price;
    ProgressDialog pDialog;
    String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alloter_detail);
        emailId=findViewById(R.id.emailId);
        mobId=findViewById(R.id.mobId);
        locationId=findViewById(R.id.locationId);
        nameId=findViewById(R.id.nameId);
        priceId=findViewById(R.id.priceId);
        id  = getIntent().getStringExtra("id");
        getDetails();
    }

    public void getDetails(){

        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading your Details");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        String url = "https://apiforprojects.shop/ParkPartner/alloter_details_api.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("success")){
                        Log.e("Responce", "onResponse: "+response);
                        nameId.setText(jsonObject.getString("Alloter_Name"));
                        mobId.setText(jsonObject.getString("Alloter_Number"));
                        emailId.setText(jsonObject.getString("Alloter_Mail"));
                        alloter_name=jsonObject.getString("Alloter_Name");
                        alloter_mail = jsonObject.getString("Alloter_Mail");
                        alloter_no = jsonObject.getString("Alloter_Number");
                        alloter_price = jsonObject.getString("Price");

                        String price=priceId.getText().toString();
                        price+=jsonObject.getString("Price");
                        priceId.setText(price);
                    }
                }
                catch (JSONException ex){
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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


    public void btnBook(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Booking Alert");
        builder.setMessage("Do You Want To Book Slot...!");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                pDialog.setMessage("Booking Your Lot,Please Wait.....");
                pDialog.setCancelable(true);
                pDialog.setIndeterminate(false);
                pDialog.show();

                String url = "https://apiforprojects.shop/ParkPartner/booking.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("success")){
                                pDialog.dismiss();
                                Toast.makeText(AlloterDetailActivity.this, "Booked Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AlloterDetailActivity.this,RouteMapActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                            else {
                                pDialog.dismiss();
                                Toast.makeText(AlloterDetailActivity.this, "Request Time out try again..", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            pDialog.dismiss();
                            Toast.makeText(AlloterDetailActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                            throw new RuntimeException(e);
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
//                Toast.makeText(AlloterDetailActivity.this, nameId.getText().toString(), Toast.LENGTH_SHORT).show();
                        Map<String, String> params = new HashMap<>();
                        String query = "SELECT CValue FROM Configuration WHERE CName = 'User_id'";
                        params.put("User_id",DBClass.getSingleValue(query));
                        params.put("Alloter_Name",alloter_name);
                        params.put("Alloter_Mail",alloter_mail);
                        params.put("Alloter_Number",alloter_no);
                        params.put("Price",alloter_price);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlloterDetailActivity.this, "Booking Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    public void btnFindRoute(View view) {
        Intent intent = new Intent(AlloterDetailActivity.this,RouteMapActivity.class);
        intent.putExtra("id",id);
//        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}