package com.example.parkpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

public class MyProfileActivity extends AppCompatActivity {

    TextView usernameId,emailId,mobId;
    String username,email,mob_no;
    String user_id;
    ProgressDialog pDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        usernameId = findViewById(R.id.usernameId);
        emailId = findViewById(R.id.emailId);
        mobId = findViewById(R.id.mobId);
        user_id=getIntent().getStringExtra("user_id");
        getDetails();
    }

    public void getDetails(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading your Details");
        pDialog.setCancelable(true);
        pDialog.setIndeterminate(false);
        pDialog.show();
        String url = "https://apiforprojects.shop/ParkPartner/user_details.php";
//        Toast.makeText(MyProfileActivity.this, user_id, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
//                        Toast.makeText(MyProfileActivity.this, user_id, Toast.LENGTH_SHORT).show();
                    if (jsonObject.getString("status").equals("success")){
                        username=jsonObject.getString("User_Name");
                        email=jsonObject.getString("User_Mail");
                        mob_no=jsonObject.getString("User_Number");
                        usernameId.setText(username);
                        emailId.setText(email);
                        mobId.setText(mob_no);
                    }else{
                        Toast.makeText(MyProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyProfileActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                    params.put("User_id",user_id);
                Log.e("Response", " "+user_id);
//                Toast.makeText(MyProfileActivity.this, user_id, Toast.LENGTH_SHORT).show();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}