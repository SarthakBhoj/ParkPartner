package com.example.parkpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebLatlongActivity extends AppCompatActivity {
    WebView webv;

    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_latlong);
        webv=findViewById(R.id.webv);
        WebSettings webSettings = webv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webv.setWebViewClient(new WebViewClient());
        setLink();
    }

    public void setLink(){
        webv.loadUrl("https://www.latlong.net/");
    }
}