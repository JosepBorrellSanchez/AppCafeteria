package com.josepborrell.cafeteriadavinci;


import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.josepborrell.cafeteriadavinci.llibreries.HttpRequest;
import com.josepborrell.cafeteriadavinci.llibreries.HttpRequest.HttpRequestException;

public class Producte extends ActionBarActivity  {
	Animation   animacion1;
	Animation   animacion2;
	Animation   animacion3;
	
	

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plantillaproducte);
        getActionBar().setHomeButtonEnabled(true);
        RelativeLayout layout =(RelativeLayout)findViewById(R.id.vista1);
              
        Intent i = getIntent();
        
        String nom = i.getStringExtra("nom");
        String descripcio = i.getStringExtra("descripcio");
        String foto = i.getStringExtra("foto");
        String preu = i.getStringExtra("preu");
            	
        
        //layout.setBackgroundResource(R.drawable.textura2);
        
        TextView nomproducte = (TextView) findViewById(R.id.nom);
        TextView descproducte = (TextView) findViewById(R.id.descripcio);
        TextView preuproducte = (TextView) findViewById(R.id.preu);
        nomproducte.setText(nom);
        descproducte.setText(descripcio);
        preuproducte.setText(preu);
        nomproducte.setTextSize(40); 
        preuproducte.setTextSize(30);
        
        animacion3 = AnimationUtils.loadAnimation(
                Producte.this, 
                R.anim.bounce);

animacion3.setStartTime(700);
animacion3.setDuration(4000);
nomproducte.startAnimation(animacion3);

animacion1 = AnimationUtils.loadAnimation(
		Producte.this, 
        R.anim.bounce);

animacion1.setStartTime(700);
animacion1.setDuration(5000);
descproducte.startAnimation(animacion1);

animacion2 = AnimationUtils.loadAnimation(
		Producte.this, 
        R.anim.bounce);

animacion2.setStartTime(700);
animacion2.setDuration(6000);
preuproducte.startAnimation(animacion2);
        
        Typeface miPropiaTypeFace = 
        Typeface.createFromAsset(getAssets(),
        "Carrington.ttf");
        
        nomproducte.setTypeface(miPropiaTypeFace);
        
        
        
        Log.i("xd", nom);
        Log.i("xd2", descripcio);
        Log.i("xd3", foto);	
        Log.i("xd4", preu);
        DownloadTask baixa = (DownloadTask) new DownloadTask().execute(foto);
        
        
    }
	
	@Override
	public void onBackPressed() {
		this.finish();
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
	            case android.R.id.home:
	                // ProjectsActivity is my 'home' activity
	            	finish();
	                startActivity(new Intent(getApplicationContext(),MainActivity.class));
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }
	
	public class DownloadTask extends AsyncTask<String, Long, File> {
		  protected File doInBackground(String... urls) {
		    try {
		      HttpRequest request =  HttpRequest.get(urls[0]); //per la del exemple
		      File file = null;
		      if (request.ok()) {
		        try {
					file = File.createTempFile("download", ".tmp"); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        request.receive(file);
		        publishProgress(file.length());
		      }
		      return file;
		    } catch (HttpRequestException exception) {
		      return null;
		    }
		  }

		  protected void onProgressUpdate(Long... progress) {
		    Log.d("MyApp", "Downloaded bytes: " + progress[0]);
		  }

		  protected void onPostExecute(File file) {
		    if (file != null){
		      Log.d("MyApp", "Downloaded file to: " + file.getAbsolutePath());
		    Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

		    //ImageView myImage = (ImageView) findViewById(R.id.imageView1);
		    //myImage.setImageBitmap(myBitmap);
		    Drawable d = new BitmapDrawable(getResources(),myBitmap);
		    RelativeLayout layout =(RelativeLayout)findViewById(R.id.vista1);
		    layout.setBackgroundDrawable(d);
		    layout.getBackground().setAlpha(75);
		    
		    }

		    else{
		      Log.d("MyApp", "Download failed");
		    RelativeLayout layout =(RelativeLayout)findViewById(R.id.vista1);
		      layout.setBackgroundResource(R.drawable.gioconda);
		      layout.getBackground().setAlpha(75);}
		    
		    
		  }
		  
	}

	
}

