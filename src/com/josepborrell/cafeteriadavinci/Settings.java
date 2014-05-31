package com.josepborrell.cafeteriadavinci;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Settings extends Activity {
	CheckBox notificacions;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        notificacions = (CheckBox) findViewById(R.id.checkBox1);
        final SharedPreferences prefs =
       	     getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        final Boolean marcat = prefs.getBoolean("marcat", false);       
        if(marcat)
        	notificacions.setChecked(true);
        if(marcat==false)
        	notificacions.setChecked(false);
        
        Log.i("valor", marcat.toString());
        
        	
        	notificacions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

        	       @Override
        	       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
        	    	   SharedPreferences.Editor editor = prefs.edit();
        	           editor.putBoolean("marcat", notificacions.isChecked());
        	           editor.commit();
        	           Log.i("valor", marcat.toString());

        	       }
        	   }
        	);    
        
	}
}
