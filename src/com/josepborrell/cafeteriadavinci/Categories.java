package com.josepborrell.cafeteriadavinci;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.josepborrell.cafeteriadavinci.llibreries.JSONParser;

public class Categories extends Activity {
	ListView list;
	TextView ver;
	TextView name;
	TextView api;
	Button Btngetdata;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	
	//URL to get JSON Array
	private static String url = "http://josepborrellweb.esy.es/gestio/index.php/Categories/json";
	
	//JSON Node Names 
	private static final String TAG_OS = "Categories";
	private static final String TAG_VER = "name";
	private static final String TAG_NAME = "description";
	private static final String TAG_API = "term_taxonomy_id";
	
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
           pDialog = new ProgressDialog(Categories.this);
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
   		Log.i("xdd", url);
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
   				String ver = "Nom : "+ c.getString(TAG_VER);
   				String name = "Descripcio : "+c.getString(TAG_NAME);
   				String api = c.getString(TAG_API);
   				
   			
   				
   				
   				// Adding value HashMap key => value
   				

   				HashMap<String, String> map = new HashMap<String, String>();

   				map.put(TAG_VER, ver);
   				map.put(TAG_NAME, name);
   				map.put(TAG_API, api);
   				
   				oslist.add(map);
   				list=(ListView)findViewById(R.id.list);
   				
   				
   				
   		        
   				
   				ListAdapter adapter = new SimpleAdapter(Categories.this, oslist,
   						R.layout.llistacategories,
   						new String[] { TAG_VER,TAG_NAME}, new int[] {
   								R.id.title,R.id.artist});

   				list.setAdapter(adapter);
   				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

   		            @Override
   		            public void onItemClick(AdapterView<?> parent, View view,
   		                                    int position, long id) {
   		            	Intent intent = new Intent(getApplicationContext(), Productesfiltrats.class);
   		            	intent.putExtra("term_taxonomy_id", oslist.get(+position).get("term_taxonomy_id"));
   		            	Log.i("xdd", oslist.get(+position).get("term_taxonomy_id"));
   		            	startActivity(intent);
   		                

   		            }
   		        });

   				}
   		} catch (JSONException e) {
   			e.printStackTrace();
   		}

   		 
   	 }
   }

}
