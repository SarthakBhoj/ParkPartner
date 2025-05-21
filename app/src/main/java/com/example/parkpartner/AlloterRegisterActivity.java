package com.example.parkpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class AlloterRegisterActivity extends AppCompatActivity {
    EditText etxtUsername,etxtEmail,etxtPassword,etxtSpace,etxtPrice,etxtLatitude,etxtLongitude,etxtMobNo;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alloter_register);
        etxtUsername = findViewById(R.id.etxtUsername);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
        etxtSpace = findViewById(R.id.etxtSpace);
        etxtPrice = findViewById(R.id.etxtPrice);
        etxtLatitude = findViewById(R.id.etxtLatitude);
        etxtLongitude = findViewById(R.id.etxtLongitude);
        etxtMobNo = findViewById(R.id.etxtMobNo);
    }

    public void getLatLong(View view) {
        Intent intent = new Intent(AlloterRegisterActivity.this,WebLatlongActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        String username = etxtUsername.getText().toString();
        String email = etxtEmail.getText().toString();
        String mob_no = etxtMobNo.getText().toString();
        String password = etxtPassword.getText().toString();
        String space = etxtSpace.getText().toString();
        String price = etxtPrice.getText().toString();
        String latitude = etxtLatitude.getText().toString();
        String longitude = etxtLongitude.getText().toString();

        if (username.isEmpty()){
            etxtUsername.setError("Enter username");
            etxtUsername.requestFocus();
        }if (mob_no.isEmpty()){
            etxtMobNo.setError("Enter mobile number");
            etxtMobNo.requestFocus();
        }if (email.isEmpty()){
            etxtEmail.setError("Enter Email");
            etxtEmail.requestFocus();
        }if (password.isEmpty()){
            etxtPassword.setError("Enter Password");
            etxtPassword.requestFocus();
        }if (space.isEmpty()){
            etxtSpace.setError("Enter Space");
        }if (price.isEmpty()){
            etxtPrice.setError("Enter Price");
        }if (latitude.isEmpty()){
            etxtLatitude.setError("Enter Latitude Value");
        }if (longitude.isEmpty()){
            etxtLongitude.setError("Enter Longitude Value");
        }
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Uploading your Details");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        String url = "https://apiforprojects.shop/ParkPartner/alloter_register.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("success")){
                        Toast.makeText(AlloterRegisterActivity.this, "Registered Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AlloterRegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
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
                params.put("Alloter_Name",username);
                params.put("Alloter_Mail",email);
                params.put("Alloter_Password",password);
                params.put("Alloter_Number",mob_no);
                params.put("Alloter_Space",space);
                params.put("Price",price);
                params.put("Latitude",latitude);
                params.put("Longitude",longitude);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
