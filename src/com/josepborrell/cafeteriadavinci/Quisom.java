package com.josepborrell.cafeteriadavinci;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Quisom extends Activity {
    private WebView mWebView;
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quisom);
         
        mWebView = (WebView) findViewById(R.id.webview);
        
        // Activo JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        
        // Cargamos la url que necesitamos    
        mWebView.loadUrl("http://josepborrellweb.esy.es/wordpress/qui-som/");
    }
}