package com.josepborrell.cafeteriadavinci;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pushbots.push.Pushbots;

public class MainActivity extends Activity {
	Animation   animacion1;
	Animation   animacion2;
	Animation   animacion3;
	Animation   animacion4;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        RelativeLayout layout =(RelativeLayout)findViewById(R.id.principal);
	      layout.getBackground().setAlpha(75);
        SharedPreferences prefs =
        	     getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        Boolean marcat = prefs.getBoolean("marcat", false);
        Log.i("marcat", marcat.toString());
        
        
        /**
         * Creating all buttons instances
         * */
         //Dashboard News feed button
        
        Button btn_newsfeed = (Button) findViewById(R.id.btn_news_feed);
        animacion3 = AnimationUtils.loadAnimation(
                MainActivity.this, 
                R.anim.bounce);

animacion3.setStartTime(700);
animacion3.setDuration(4000);
btn_newsfeed.startAnimation(animacion3);
        
        // Dashboard Friends button
/*
        Button btn_friends = (Button) findViewById(R.id.btn_friends);
        animacion4 = AnimationUtils.loadAnimation(
                MainActivity.this, 
                R.anim.bounce);

animacion4.setStartTime(1000);
animacion4.setDuration(7000);
btn_friends.startAnimation(animacion4);
*/
        
        // Dashboard Messages button
        Button btn_messages = (Button) findViewById(R.id.btn_messages);
        animacion1 = AnimationUtils.loadAnimation(
                MainActivity.this, 
                R.anim.bounce);

animacion1.setStartTime(700);
animacion1.setDuration(5000);
btn_messages.startAnimation(animacion1);
        
        // Dashboard Places button
        Button btn_places = (Button) findViewById(R.id.btn_places);
        animacion2 = AnimationUtils.loadAnimation(
                MainActivity.this, 
                R.anim.bounce);

animacion2.setStartTime(700);
animacion2.setDuration(6000);
btn_places.startAnimation(animacion2);

Typeface miPropiaTypeFace = 
Typeface.createFromAsset(getAssets(),
"Carrington.ttf");

btn_messages.setTypeface(miPropiaTypeFace);
btn_newsfeed.setTypeface(miPropiaTypeFace);
btn_places.setTypeface(miPropiaTypeFace);

btn_messages.setTextSize(20);
btn_newsfeed.setTextSize(20);
btn_places.setTextSize(20);
        
        ImageView face = (ImageView) findViewById(R.id.imageface);

        /**
         * Handling all button click events
         * */
        
        face.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uri = "fb://page/494871740634414";
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri ));
				startActivity(intent);
			}
        	
        });
        
     // Listening to News Feed button click
        
        btn_newsfeed.setOnClickListener(new View.OnClickListener() {
        	
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				FragmentManager fragmentManager = getFragmentManager();
		           DialogoAlerta dialogo = new DialogoAlerta();
		        dialogo.show(fragmentManager, "tagAlerta");
			}
		});
        
       // Listening Friends button click
        /*
        btn_friends.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), Categories.class);
				startActivity(i);
			}
		});
        */
        // Listening Messages button click
        btn_messages.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
				dialog.setCancelable(true);
				dialog.setMessage("Vols veure tota la llista de productes o filtrar-los per categories?");
				//dialog.setCancelable(false);
				dialog.setPositiveButton("Tota la llista", new DialogInterface.OnClickListener() {
				 
				  @Override
				  public void onClick(DialogInterface dialog, int which) {
				     Intent i = new Intent(getApplicationContext(), Productes.class);
						startActivity(i);
				  }
				});
				dialog.setNegativeButton("Categories", new DialogInterface.OnClickListener() {
					 
					   @Override
					   public void onClick(DialogInterface dialog, int which) {
						   Intent i = new Intent(getApplicationContext(), Categories.class);
							startActivity(i);
					   }
					});
				
				dialog.setCancelable(true);
				dialog.show();
				
			}
			
		});
        
        // Listening to Places button click
        btn_places.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), Onestem.class);
				startActivity(i);
				
				
				
			}
		});
        /*
        // Listening to Events button click
        btn_events.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
			}
		});
        
        // Listening to Photos button click
        btn_photos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
			}
		});
		*/
        
        
    }
    
    public void onResume(Bundle savedInstanceState) {
    	super.onResume();
    	SharedPreferences prefs =
       	     getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
       Boolean marcat = prefs.getBoolean("marcat", false);
       Log.i("marcat", marcat.toString());
       if(marcat)
       	Pushbots.init(this, "490854279742","5384b34d1d0ab1d2048b459f");
      // if(marcat==false)
    	//   Pushbots.(this, "490854279742","5384b34d1d0ab1d2048b459f");
    	
    	
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferencies:
            	Intent i = new Intent(getApplicationContext(),Settings.class);
            	startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    
    
}
