package com.josepborrell.cafeteriadavinci;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.josepborrell.cafeteriadavinci.llibreries.HttpRequest;
import com.josepborrell.cafeteriadavinci.llibreries.HttpRequest.HttpRequestException;
import com.josepborrell.cafeteriadavinci.llibreries.JSONParser;

public class Productes extends Activity {
	ListView list;
	TextView ver;
	TextView name;
	TextView api;
	Button Btngetdata;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	
	//URL to get JSON Array
	private static String url = "http://josepborrellweb.esy.es/gestio/index.php/Productes/json";
	//JSON Node Names 
	private static final String TAG_OS = "Productes";
	private static final String TAG_VER = "nom";
	private static final String TAG_NAME = "descripcio";
	private static final String TAG_API = "preu";
	private static final String TAG_LINK = "link";
	private static final String TAG_FOTO = "foto";
	
	JSONArray android = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.llistes);
		oslist = new ArrayList<HashMap<String, String>>();
		new JSONParse().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
   	 private ProgressDialog pDialog;
   	@Override
       protected void onPreExecute() {
           super.onPreExecute();
            ver = (TextView)findViewById(R.id.vers);
			 name = (TextView)findViewById(R.id.name);
			 api = (TextView)findViewById(R.id.api);
           pDialog = new ProgressDialog(Productes.this);
           pDialog.setMessage("Un moment estem recullint les dades ...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(true);
           pDialog.show();
           
           
           
   	}
   	
   	@Override
       protected JSONObject doInBackground(String... args) {
   		
   		JSONParser jParser = new JSONParser();

   		// Getting JSON from URL
   		JSONObject json = jParser.getJSONFromUrl(url);
   		return json;
   	}
   	
   	 @Override
        protected void onPostExecute(JSONObject json) {
   		 pDialog.dismiss();
   		 try {
   				// Getting JSON Array from URL
   				android = json.getJSONArray(TAG_OS);
   				for(int i = 0; i < android.length(); i++){
   				JSONObject c = android.getJSONObject(i);
   				
   				
   				
   				// Storing  JSON item in a Variable
   				String ver = "Nom : "+c.getString(TAG_VER);
   				String name = "Descripció : "+c.getString(TAG_NAME);
   				String api = "Preu : "+c.getString(TAG_API);
   				String link = c.getString(TAG_LINK);
   				String foto = c.getString(TAG_FOTO);
   				
   				//ImageView img = (ImageView) findViewById(R.id.list_image);
   				//UrlImageViewHelper.setUrlDrawable(img, foto);

   				//Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

   			    //ImageView myImage = (ImageView) findViewById(R.id.imageView1);
   			    //myImage.setImageBitmap(myBitmap);
   				// Adding value HashMap key => value
   				

   				HashMap<String, String> map = new HashMap<String, String>();

   				map.put(TAG_VER, ver);
   				map.put(TAG_NAME, name);
   				map.put(TAG_API, api);
   				map.put(TAG_LINK, link);
   				map.put(TAG_FOTO, foto);
   				//DownloadTask baixa = (DownloadTask) new DownloadTask().execute(foto);
   				
   				oslist.add(map);
   				list=(ListView)findViewById(R.id.list);

   				ListAdapter adapter = new SimpleAdapter(Productes.this, oslist,
   						R.layout.llistaproducts,
   						new String[] { TAG_VER,TAG_NAME, TAG_API}, new int[] {
   								R.id.title,R.id.artist, R.id.duration}
   				);

   				list.setAdapter(adapter);
   				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

   		            @Override
   		            public void onItemClick(AdapterView<?> parent, View view,
   		                                    int position, long id) {
   		            	Intent i = new Intent(getApplicationContext(), Producte.class);
   		            	i.putExtra("nom", oslist.get(+position).get("nom"));
   		            	i.putExtra("descripcio", oslist.get(+position).get("descripcio"));
   		            	i.putExtra("preu", oslist.get(+position).get("preu"));
   		            	i.putExtra("foto", oslist.get(+position).get("foto"));
   		            	startActivity(i);

   		            }
   		        });

   				}
   		} catch (JSONException e) {
   			e.printStackTrace();
   		}

   		 
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

		    ImageView myImage = (ImageView) findViewById(R.id.list_image);
		    myImage.setImageBitmap(myBitmap);
		    }

		    else{
		      Log.d("MyApp", "Download failed");}
		    
		    
		  }
		  
	}

}
