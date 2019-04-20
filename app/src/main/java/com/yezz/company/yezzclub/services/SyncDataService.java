package com.yezz.company.yezzclub.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yezz.company.yezzclub.R;
import com.yezz.company.yezzclub.helpers.LoadDataServer;
import com.yezz.company.yezzclub.lData.YezzDB;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SyncDataService extends IntentService {

    public static boolean isRunning = false;
    //Minimo tiempo para updates en Milisegundos
    public static final long UPDATE_TIME = 1000 * 60 * 15; // 15 minuto


    TimerTask timerTask;
    Timer timer;
    LoadDataServer loadDataServer;
    private RequestQueue requestQueue;
    private boolean first;

    public SyncDataService() {
        super("SyncDataService");
        timer = new Timer();
        loadDataServer = new LoadDataServer(this);
        first = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        return START_STICKY;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("syncData", "service created");
        final Context context = this;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!first) {
                    //loadDataServer.
                    requestQueue= Volley.newRequestQueue(context);
                    String url = context.getString(R.string.base) + context.getString(R.string.routeSyncData);
                    final YezzDB dbHelper = new YezzDB(context);
                    final JSONObject params =loadDataServer.getSyncDataParam();
                    if (params == null) {
                        Log.d("syncData", "No user login");
                        return;
                    }
                    // Nueva petición JSONObject
                    JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            params,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("syncData",response.toString());
                                    loadDataServer.processData(response,dbHelper);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("syncData", "Error Respuesta en JSON: " + error.getMessage());
                                }
                            }
                    );
                    // Añadir petición a la cola
                    requestQueue.add(jsArrayRequest);
                    dbHelper.close();
                }
                first = false;
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, SyncDataService.UPDATE_TIME);
    }
}
