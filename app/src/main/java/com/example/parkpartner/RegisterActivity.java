package com.example.parkpartner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etxtUsername,etxtMobNo,etxtEmail,etxtPassword;
    LinearLayout reslinear;
    ImageView imageView;
    ProgressDialog pDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etxtUsername=findViewById(R.id.etxtUsername);
        etxtMobNo=findViewById(R.id.etxtMobNo);
        etxtEmail=findViewById(R.id.etxtEmail);
        etxtPassword=findViewById(R.id.etxtPassword);
        imageView=findViewById(R.id.imgChange);
        reslinear=findViewById(R.id.reslinear);
       monkeyAnimation();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void monkeyAnimation(){
        etxtPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageView.setImageResource(R.drawable.ashamed);

                return false;
            }
        });

        reslinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageView.setImageResource(R.drawable.happy);
                return false;
            }
        });

        etxtEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageView.setImageResource(R.drawable.deaf);
                return false;
            }
        });

        etxtUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageView.setImageResource(R.drawable.happy);
                return false;
            }
        });
        etxtMobNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageView.setImageResource(R.drawable.happy);
                return false;
            }
        });

    }

    public void register(View view) {
        String username = etxtUsername.getText().toString().trim();
        String mob_no = etxtMobNo.getText().toString().trim();
        String email = etxtEmail.getText().toString().trim();
        String password = etxtPassword.getText().toString().trim();

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
        }
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Uploading your Details");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        String url = "https://apiforprojects.shop/ParkPartner/Register_api.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    pDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("success")){
                        Toast.makeText(RegisterActivity.this, "Registered Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
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
                params.put("User_Name",username);
                params.put("User_Mail",email);
                params.put("User_Number",mob_no);
                params.put("User_Password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        }

}
