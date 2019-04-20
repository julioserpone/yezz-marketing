package com.yezz.company.yezzclub.services;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yezz.company.yezzclub.R;
import com.yezz.company.yezzclub.helpers.DataStorageManager;
import com.yezz.company.yezzclub.lData.entities.LocalizationLog;
import com.yezz.company.yezzclub.lData.entities.contracts.LocalizationLogContract;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

public class YezzGPS extends Service {
    public static boolean isRunning = false;
    public static final long MIN_CAMBIO_DISTANCIA_PARA_UPDATES = 5; // 10 metros
    //Minimo tiempo para updates en Milisegundos
    public static final long MIN_TIEMPO_ENTRE_UPDATES = 1000 * 60 * 1; // 1 minuto
    //private static final long MIN_TIEMPO_ENTRE_UPDATES = 1000 * 60 * 30; // 30 minuto
    private DataStorageManager dataManager;

    LocationManager mlocManager;
    Localizacion local;
    // How many Geocoder should return our GPSTracker
    int geocoderMaxResults = 1;
    TimerTask timerTask;
    Timer timer;
    private RequestQueue requestQueue;

    public YezzGPS() {
        timer = new Timer();
        dataManager = new DataStorageManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        YezzGPS.isRunning = true;
        Log.v("GPSSERVICE","Iniciado");

        final Context context = this;
        locationStart();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //obtengo la hora
                SimpleDateFormat sdf = new SimpleDateFormat("HH");
                int hora = Integer.parseInt(sdf.format(Calendar.getInstance().getTime()));
                Log.v("GPSSERVICE","Ejecutado");
                //verifico que haya datos de coordenadas y sean las horas correctas
                if (Localizacion.latitud != 0.0 && Localizacion.longitud != 0.0 &&
                        hora >= 6 && hora <= 18) {
                    try {
                        //Intento obtener los datos de usuario
                        JSONObject user = dataManager.getFileJson(context.getString(R.string.fileLastUser)); //getFileJson(context.getString(R.string.fileUserJson));
                        if (user != null) {
                            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            final String lat,lng, userId,date, url;

                            lat = ""+Localizacion.latitud;
                            lng = ""+Localizacion.longitud;
                            userId = user.getString("id");
                            date = mdformat.format(Calendar.getInstance().getTime());

                            //intento enviar la informacion//
                            // Crear nueva cola de peticiones
                            requestQueue= Volley.newRequestQueue(context);
                            url = context.getString(R.string.base) + context.getString(R.string.routeSyncLocalization);

                            JSONArray array=new JSONArray();
                            JSONObject data=new JSONObject();
                            data.put("user",userId);
                            data.put("latitude",lat);
                            data.put("longitude",lng);
                            data.put("created",date);
                            array.put(0,data);

                            // Nueva petición JSONObject
                            JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                    url,
                                    (new JSONObject()).put("localization", array),
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("response",response.toString());
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());
                                            LocalizationLogContract contract = new LocalizationLogContract();
                                            contract.setLatitude(lng);
                                            contract.setLongitude(lat);
                                            contract.setUser(userId);
                                            contract.setCreated(date);
                                            LocalizationLog l = new LocalizationLog(context);
                                            l.create(contract);
                                        }
                                    }
                            );
                            // Añadir petición a la cola
                            requestQueue.add(jsArrayRequest);
                        }
                    } catch (Exception e) {
                        Log.d("Error", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, YezzGPS.MIN_TIEMPO_ENTRE_UPDATES+1);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("service","MyAlarmService.onBind()");
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //create a intent that you want to start again..
        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +1000, restartServicePI);
        super.onTaskRemoved(rootIntent);
    }

    /**
     * Inicia el servicio de geolocalizacion
     */
    private void locationStart() {
        boolean network_enabled = false, passive_enable = false;
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        local = new Localizacion(getBaseContext());

        network_enabled = mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        passive_enable = mlocManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);

        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "Debe habilitar todos los permisos", Toast.LENGTH_SHORT).show();
            Log.v("ErrorService","Debe habilitar todos los permisos");
            return;
        }

        if(!gpsEnabled && !network_enabled && !passive_enable) {
            Toast.makeText(getBaseContext(), "NO HAY MANERA DE OBTENER SU UBICACION", Toast.LENGTH_LONG).show();
            Log.v("ErrorService","NO HAY MANERA DE OBTENER SU UBICACION");
            return;
        }

        if(gpsEnabled) {
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIEMPO_ENTRE_UPDATES, MIN_CAMBIO_DISTANCIA_PARA_UPDATES, local);
        } else if (network_enabled) {
            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIEMPO_ENTRE_UPDATES, MIN_CAMBIO_DISTANCIA_PARA_UPDATES, local);
        } else if(passive_enable) {
            mlocManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, MIN_TIEMPO_ENTRE_UPDATES, MIN_CAMBIO_DISTANCIA_PARA_UPDATES, local);
        } else {
            if (mlocManager != null) {
                mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
    }

    /**
     * Detiene e servicio de Geolocalizacion
     */
    public void stopUsingGPS() {
        if (mlocManager != null) {
            mlocManager.removeUpdates(local);
            timerTask.cancel();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    /**
     * Get list of address by latitude and longitude
     * @return null or List<Address>
     */
    public List<Address> getGeocoderAddress(Context context) {
        if (local != null) {

            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

            try {
                /**
                 * Geocoder.getFromLocation - Returns an array of Addresses
                 * that are known to describe the area immediately surrounding the given latitude and longitude.
                 */
                List<Address> addresses = geocoder.getFromLocation(Localizacion.latitud, Localizacion.longitud, this.geocoderMaxResults);

                return addresses;
            } catch (IOException e) {
                //e.printStackTrace();
                Log.e("SERVICIO", "Impossible to connect to Geocoder", e);
            }
        }

        return null;
    }

    /**
     * Try to get AddressLine
     * @return null or addressLine
     */
    public String getAddressLine(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);

            return addressLine;
        } else {
            return null;
        }
    }

    /**
     * Try to get Locality
     * @return null or locality
     */
    public String getLocality(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String locality = address.getLocality();

            return locality;
        }
        else {
            return null;
        }
    }

    /**
     * Try to get Postal Code
     * @return null or postalCode
     */
    public String getPostalCode(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String postalCode = address.getPostalCode();

            return postalCode;
        } else {
            return null;
        }
    }

    /**
     * Try to get CountryName
     * @return null or postalCode
     */
    public String getCountryName(Context context) {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String countryName = address.getCountryName();

            return countryName;
        } else {
            return null;
        }
    }
}
