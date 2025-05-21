package com.example.parkpartner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class LoginActivity extends AppCompatActivity {
    EditText etxtUsername,etxtPassword;
    static String user_id,email,mob_no,username,password,Username;
    String alloter_id,alloter_mail,alloter_space,price,latitude,longitude,alloter_mob,alloter_name;
    ProgressDialog progressDialog;
    boolean loginSuccess;
    RadioGroup radioGroup;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etxtUsername=findViewById(R.id.etxtUsername);
        etxtPassword=findViewById(R.id.etxtPassword);
        radioGroup=findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=group.findViewById(checkedId);
            }
        });
    }

    public void btnLogIn(View view) {
        password = etxtPassword.getText().toString().trim();
        username = etxtUsername.getText().toString().trim();

        if (username.isEmpty()) {
            etxtUsername.setError("Enter Username");
            etxtUsername.requestFocus();
        }
        if (password.isEmpty()) {
            etxtPassword.setError("Enter Username");
            etxtPassword.requestFocus();
        }
        else{
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Validating Details.....");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
        int checkedIndex = radioGroup.getCheckedRadioButtonId();
        if (checkedIndex == -1) {
            Toast.makeText(this, "Select User type", Toast.LENGTH_SHORT).show();
        } else {
            RadioButton radioButton = findViewById(checkedIndex);
            if (radioButton.getText().equals("User Log in")) {
                LogInUser(username, password);

            } else {
                LogInAlloter(username, password);
            }
        }
    }
    }

    public void btnRegister(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void btnAloterRegister(View view) {
        Intent intent = new Intent(LoginActivity.this,AlloterRegisterActivity.class);
        startActivity(intent);
    }

    public void LogInUser(String username,String password){

        String url="https://apiforprojects.shop/ParkPartner/login_api.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response.

                        Log.d("Response",response);
                        JSONObject jsonObject = null;
                        progressDialog.dismiss();
                        try {
                            loginSuccess=false;
                            jsonObject = new JSONObject(response);
                            if(jsonObject.getString("status").equals("success")){
                                user_id = jsonObject.getString("User_id");
                                Username=jsonObject.getString("User_Name");
                                email = jsonObject.getString("User_Mail");
                                mob_no = jsonObject.getString("User_Number");
                                loginSuccess=true;
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            if (loginSuccess){
                                String query ="DELETE FROM Configuration";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('User_Name', '" +  Username + "')";
                                DBClass.execNonQuery(query);

                                query="INSERT INTO Configuration(CName,CValue)";
                                query += "VALUES('User_id', '" + user_id + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('User_Mail', '" + email + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('User_Number', '" + mob_no + "')";
                                DBClass.execNonQuery(query);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Username or Password not found...", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Check Internet Connection...", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Exception", error.toString());
                Toast.makeText(getApplicationContext(), "Check Internet Connection...", Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("User_Number",username);
                params.put("User_Password",password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void LogInAlloter(String username,String password){
        String url="https://apiforprojects.shop/ParkPartner/alloter_login_api.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response.

                        Log.d("Response",response);
                        JSONObject jsonObject = null;
                        progressDialog.dismiss();
                        try {
                            loginSuccess=false;
                            jsonObject = new JSONObject(response);
                            if(jsonObject.getString("status").equals("success")){
                                alloter_id = jsonObject.getString("Alloter_id");
                                alloter_mail = jsonObject.getString("Alloter_Mail");
                                alloter_space = jsonObject.getString("Alloter_Space");
                                price = jsonObject.getString("Price");
                                alloter_name = jsonObject.getString("Alloter_Name");
                                latitude = jsonObject.getString("Latitude");
                                longitude = jsonObject.getString("Longitude");
                                loginSuccess=true;
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);

                            }
                            if (loginSuccess){
                                String query ="DELETE FROM Configuration";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('Alloter_Name', '" + alloter_name + "')";
                                DBClass.execNonQuery(query);

                                query="INSERT INTO Configuration(CName,CValue)";
                                query += "VALUES('Alloter_id', '" + alloter_id + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('Alloter_Mail', '" + alloter_mail + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('Alloter_Space', '" + alloter_space + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('Alloter_Number', '" + username + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('Price', '" + price + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('Latitude', '" + latitude + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('Longitude', '" + longitude + "')";
                                DBClass.execNonQuery(query);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Username or Password not found...", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Check Internet Connection...", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Exception", error.toString());
                Toast.makeText(getApplicationContext(), "Check Internet Connection...", Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("Alloter_Number",username);
                params.put("Alloter_Password",password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}