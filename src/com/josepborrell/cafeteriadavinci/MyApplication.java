package com.josepborrell.cafeteriadavinci;

import com.pushbots.push.Pushbots;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences prefs =
       	     getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
       Boolean marcat = prefs.getBoolean("marcat", false);
       Log.i("marcat", marcat.toString());
       if(marcat==false)
        Pushbots.init(this, "490854279742","5384b34d1d0ab1d2048b459f");
     }
    
    
 }