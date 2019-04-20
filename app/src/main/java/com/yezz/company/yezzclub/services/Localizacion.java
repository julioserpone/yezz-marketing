package com.yezz.company.yezzclub.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by SairioP on 19/04/17.
 */

public class Localizacion implements LocationListener {

    public static double latitud;
    public static double longitud;
    public Context context;

    public Localizacion(){

    }

    public Localizacion(Context context){
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location loc) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la deteccion de un cambio de ubicacion

        Localizacion.latitud  = loc.getLatitude();
        Localizacion.longitud = loc.getLongitude();

       /* String Text = "Mi ubicacion actual es: " + "\n Lat = "
                + loc.getLatitude() + "\n Long = " + loc.getLongitude();
        Log.d("LOCALIZACION",Text);*/
    }

    @Override // Este metodo se ejecuta cuando el GPS es desactivado
    public void onProviderDisabled(String provider) {
        Log.d("GPS","GPS Desactivado");
        Toast.makeText(context, "DEBE ACTIVAR EL GPS " + provider, Toast.LENGTH_LONG).show();
    }

    @Override // Este metodo se ejecuta cuando el GPS es activado
    public void onProviderEnabled(String provider) {
        Log.d("GPS","GPS Activado");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }
}
