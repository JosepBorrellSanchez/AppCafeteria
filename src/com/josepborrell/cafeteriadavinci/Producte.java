package com.josepborrell.cafeteriadavinci;


import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.josepborrell.cafeteriadavinci.llibreries.HttpRequest;
import com.josepborrell.cafeteriadavinci.llibreries.HttpRequest.HttpRequestException;

public class Producte extends Activity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producte);
        RelativeLayout layout =(RelativeLayout)findViewById(R.id.vista1);
              
        Intent i = getIntent();
        
        String nom = i.getStringExtra("nom");
        String descripcio = i.getStringExtra("descripcio");
        String foto = i.getStringExtra("foto");
        String preu = i.getStringExtra("preu");
            	
        
        //layout.setBackgroundResource(R.drawable.textura2);
        
        TextView nomproducte = (TextView) findViewById(R.id.preu);
        TextView descproducte = (TextView) findViewById(R.id.descripcio);
        TextView preuproducte = (TextView) findViewById(R.id.nom);
        nomproducte.setText(preu);
        descproducte.setText(descripcio);
        preuproducte.setText(nom);
        
        
        Log.i("xd", nom);
        Log.i("xd2", descripcio);
        Log.i("xd3", foto);	
        Log.i("xd4", preu);
        DownloadTask baixa = (DownloadTask) new DownloadTask().execute(foto);
        
        
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
		      layout.setBackgroundResource(R.drawable.textura2);}
		    
		    
		  }
		  
	}

	
}

