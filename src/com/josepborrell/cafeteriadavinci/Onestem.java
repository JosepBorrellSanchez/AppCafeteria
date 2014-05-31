package com.josepborrell.cafeteriadavinci;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class Onestem extends android.support.v4.app.FragmentActivity {

	private GoogleMap mapa = null;
	private int vista = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onestem);
		getActionBar().setHomeButtonEnabled(true);
		mapa = ((SupportMapFragment) getSupportFragmentManager()
	            .findFragmentById(R.id.map)).getMap();
		LatLng madrid = new LatLng(40.818448,0.507283);
		CameraPosition camPos = new CameraPosition.Builder()
		.target(madrid) //Centramos el mapa en Madrid
		.zoom(19) //Establecemos el zoom en 19
		.bearing(45) //Establecemos la orientación con el noreste arriba
		.tilt(70) //Bajamos el punto de vista de la cámara 70 grados
		.build();

		CameraUpdate camUpd3 =
		CameraUpdateFactory.newCameraPosition(camPos);
		mostrarMarcador(40.818448,0.507283);

		mapa.animateCamera(camUpd3);


			mapa.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(Marker marker) {
			Toast.makeText( Onestem.this, "Marcador pulsado:\n" + marker.getTitle(),
			Toast.LENGTH_SHORT).show();
			return false;
			}
			});
			}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menuonestem, menu);
		return true;
	}
	@Override
public boolean onOptionsItemSelected(MenuItem item)
{	
switch(item.getItemId())
{

case R.id.menu_vista:
	alternarVista();
break;
case R.id.menu_3d:
LatLng madrid = new LatLng(40.818448,0.507283);
CameraPosition camPos = new CameraPosition.Builder()
.target(madrid) //Centramos el mapa en Madrid
.zoom(19) //Establecemos el zoom en 19
.bearing(45) //Establecemos la orientación con el noreste arriba
.tilt(70) //Bajamos el punto de vista de la cámara 70 grados
.build();

CameraUpdate camUpd3 =
CameraUpdateFactory.newCameraPosition(camPos);

mapa.animateCamera(camUpd3);
break;
case android.R.id.home:
    // ProjectsActivity is my 'home' activity
	finish();
    startActivity(new Intent(getApplicationContext(),MainActivity.class));
    return true;
    }

return super.onOptionsItemSelected(item);
}

private void mostrarMarcador(double lat, double lng)
{
mapa.addMarker(new MarkerOptions()
        .position(new LatLng(lat, lng))
        .title("Pais: Espa�a"));
}
private void alternarVista()
{
    vista = (vista + 1) % 4;

    switch(vista)
    {
        case 0:
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            break;
        case 1:
            mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            break;
        case 2:
            mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            break;
        case 3:
            mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            break;
    }
}

}