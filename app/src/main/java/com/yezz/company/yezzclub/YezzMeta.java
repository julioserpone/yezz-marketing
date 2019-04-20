package com.yezz.company.yezzclub;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yezz.company.yezzclub.helpers.Constants;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.LocalizationLog;
import com.yezz.company.yezzclub.lData.entities.VisitStore;
import com.yezz.company.yezzclub.lData.entities.contracts.LocalizationLogContract;
import com.yezz.company.yezzclub.lData.entities.contracts.RoutesContract;
import com.yezz.company.yezzclub.lData.entities.contracts.VisitStoreContract;
import com.yezz.company.yezzclub.services.Localizacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by company on 24-07-2016.
 */
public abstract class YezzMeta extends AppCompatActivity {
    public JSONObject user;
    protected LocationManager mlocManager;
    protected Localizacion local;
    protected ProgressDialog pDialog;

    /*User Session*/
    protected boolean isLogged(){
        if (this.user==null){
            this.iniUserJson();
        }
        this.checkSession();
        return this.user!=null;
    }

    private void checkSession(){
        if (this.user!=null){
            try{
                if (!this.user.getString("date").equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))){
                    this.user=null;
                    this.logout(this);
                }
            }catch (Exception e){
                Log.d("checkSession",e.getMessage());
            }
        }
    }

    public void iniUserJson(){
        this.user=this.getFileJson(this.getString(R.string.fileUserJson));
    }

    protected void setLogin(JSONObject data) {
        this.setLogin(data.toString());
    }

    protected void setLogin(String data) {
        this.setUserJson(data);
        this.iniUserJson();
    }

    protected void logout(AppCompatActivity activity){
        this.clearUserJson();
        if (this.checkBranchSession()){
            this.closeBranchSession();
        }
        Intent intent= new Intent(activity,Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        activity.finish();
    }

    protected void setUserJson(JSONObject data){
        this.setUserJson(data.toString());
    }

    protected void setUserJson(String data){
        this.setFileJson(getString(R.string.fileUserJson),data);
    }

    protected boolean clearUserJson(){
        return this.clearFileJson(this.getString(R.string.fileUserJson));
    }
    /*End User Session*/

    /*User data*/
    protected JSONObject getPersonData(){
        try {
            return this.user.getJSONObject("person");
        }catch (Exception e){
            Log.d("getPersonData",e.getMessage());
        }
        return null;
    }

    protected JSONObject getProfileData(){
        try {
            return this.user.getJSONObject("profile");
        }catch (Exception e){
            Log.d("getProfileData",e.getMessage());
        }
        return null;
    }
    /*End User data*/

    /*Branch Session*/
    protected void startBranchSession(String branchId){
        try {
            JSONObject data=new JSONObject();
            VisitStore visiStore = new VisitStore(this);
            String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            data.put("key",branchId);
            data.put("start",start);
            this.updateRouteJson(data,"start");
            this.setFileJson(this.getString(R.string.fileStoreJson),data);
            visiStore.create(new VisitStoreContract(null,"",branchId,"0",start,"","active"));
        } catch (JSONException e) {
            Log.d("setBranchSession",e.getMessage());
        }
    }

    public void updateKeyBranchSession(String newKey,String oldKey){
        try {
            JSONObject data=this.getBranchSession();
            if (data == null || !data.getString("key").equals(oldKey)){
                return;
            }
            data.put("newKey",newKey);
            this.updateRouteJson(data,"update");
            data.put("key",newKey);
            this.setFileJson(this.getString(R.string.fileStoreJson),data);
            VisitStore visitiStore = new VisitStore(this);
            VisitStoreContract visitStoreContract = visitiStore.getRegister(VisitStoreContract.VisitStoreEntry.STATUS,
                    new String[]{Constants.STATUS_ACTIVE});
            visitStoreContract.setBranchId(newKey);
            visitiStore.update(VisitStoreContract.VisitStoreEntry.TABLE_NAME, visitStoreContract.toContentValues(),
                    VisitStoreContract.VisitStoreEntry.ID,visitStoreContract.getId());

        } catch (JSONException e) {
            Log.d("setBranchSession",e.getMessage());
        }
    }

    public void addCommentToBranch(String comment) {
        try {
            JSONObject data=this.getBranchSession();
            if (data == null){
                return;
            }
            data.put("comment",comment);
            this.setFileJson(this.getString(R.string.fileStoreJson),data);
            VisitStore visitStore = new VisitStore(this);
            VisitStoreContract visitStoreContract = visitStore.getRegister(VisitStoreContract.VisitStoreEntry.STATUS+" =?",
                    new String[]{Constants.STATUS_ACTIVE});
            visitStoreContract.setComment(comment);
            visitStore.update(VisitStoreContract.VisitStoreEntry.TABLE_NAME, visitStoreContract.toContentValues(),
                    VisitStoreContract.VisitStoreEntry.ID, visitStoreContract.getId());
        } catch (JSONException e) {
            Log.d("setCommentVisit",e.getMessage());
        }
    }

    protected void closeBranchSession(){
        try {
            JSONObject data=this.getBranchSession();
            data.put("end",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            this.updateRouteJson(data,"end");
            this.clearFileJson(this.getString(R.string.fileStoreJson));

        } catch (JSONException e) {
            Log.d("setBranchSession",e.getMessage());
        }
    }

    protected boolean checkBranchSession(){
        return this.getBranchSession()!=null;
    }

    public JSONObject getBranchSession(){
        return this.getFileJson(this.getString(R.string.fileStoreJson));
    }

    private void updateRouteJson(JSONObject data,String action){
        try{
            JSONArray array=this.getRouteData();
            List<JSONObject> newArray=new ArrayList<JSONObject>();
            JSONObject branch=null;
            for (int i=0;i<array.length();i++){
                if (branch==null){
                    branch=data.getString("key").equals(array.getJSONObject(i).getString("branch_id"))?array.getJSONObject(i):null;
                    if (branch!=null){
                        if (action.equals("start")){
                            branch.put("checked","true");
                            branch.put("start",data.getString("start"));
                        }else if (action.equals("end")){
                            branch.put("end",data.getString("end"));
                        }else{
                            branch.put("branch_id",data.getString("newKey"));
                        }
                        newArray.add(branch);
                    }else{
                        newArray.add(array.getJSONObject(i));
                    }
                }else{
                    newArray.add(array.getJSONObject(i));
                }
            }
            if (branch==null){
                branch=new JSONObject();
                branch.put("checked","true");
                branch.put("route","false");
                branch.put("end","null");
                branch.put("branch_id",data.getString("key"));
                branch.put("start",data.getString("start"));
                newArray.add(branch);
            }
            this.setRouteData(new JSONArray(newArray));
        }catch (JSONException e){
            Log.d("updateRouteJson",e.getMessage());
        }
    }

    protected void setRouteData(JSONArray data){
        try {
            JSONObject route=new JSONObject();

            if(data.length() > 0){
                final YezzDB dbHelper=new YezzDB(getBaseContext());
                dbHelper.delete(RoutesContract.RoutesEntry.TABLE_NAME,null,null);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                for (int i = 0; i < data.length();i++){
                    JSONObject ro = data.getJSONObject(i);
                    dbHelper.save(new RoutesContract(ro.getString("branch_id"),null,null,dateFormat.format(date),"1","0","0",null,null));
                }
            }
            route.put("routes",data);
            setFileJson(this.getString(R.string.fileRouteUser),route);
        }catch (JSONException e){
            Log.d("iniRouteFile",e.getMessage());
        }
    }

    protected JSONArray getRouteData(){
        try {
            JSONObject route=this.getFileJson(this.getString(R.string.fileRouteUser));
            return route.getJSONArray("routes");
        }catch (JSONException e){
            Log.d("iniRouteFile",e.getMessage());
        }
        return null;
    }
    /*End Branch Session*/

    /*Last User Session*/
    @Nullable
    protected JSONObject getLastUser(){
        return this.getFileJson(this.getString(R.string.fileLastUser));
    }
    /*End Last User Session*/

    /*Manage File*/
    protected void setFileJson(String fileName,JSONObject json){
        this.setFileJson(fileName,json.toString());
    }

    protected void setFileJson(@NonNull String fileName,@NonNull String json){
        try{
            OutputStreamWriter txt=new OutputStreamWriter(openFileOutput(fileName,Context.MODE_PRIVATE));
            txt.write(json);
            txt.close();
        }catch (Exception e){
            Log.d("setFileJson file: "+fileName,e.getMessage());
        }
    }

    protected boolean clearFileJson(@NonNull String fileName){
        try{
            File file=new File(getFilesDir(),fileName);
            return file.delete();
        }catch (Exception e){
            Log.d("clearFileJson file: "+fileName,e.getMessage());
        }
        return false;
    }

    @Nullable
    protected JSONObject getFileJson(@NonNull String fileName){
        JSONObject json=null;
        try{
            BufferedReader txt=new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            json=new JSONObject(txt.readLine());
            txt.close();
            return json;
        }catch (Exception e){
            Log.d("getFileJson file: "+fileName,e.getMessage());
        }
        return json;
    }
    /*End Manage File*/

    /*Msg*/
    public void showMsg(String string,String debug){
        if (getString(R.string.debug).equals("true")){
            this.showMsg(debug);
        }else{
            this.showMsg(string);
        }
    }

    public void showMsg(String string){
        Toast.makeText(this.getApplication().getApplicationContext(),string,Toast.LENGTH_LONG).show();
    }
    /*End Msg*/

    /*HTTP Request Manage*/
    public String getUrl(){
        return this.getString(R.string.base);
    }

    public String getUrl(int RSource){
        return this.getUrl().concat(this.getString(RSource));
    }

    protected String getDeviceSerial(){
        return Build.SERIAL;
    }

    public void sendRequest(YezzMeta activity, String url, @Nullable JSONObject params, Response.Listener<JSONObject> response, Response.ErrorListener error){
        this.sendRequest(activity,Request.Method.POST,url,params,response,error);
    }

    public void sendRequest(YezzMeta activity, String url, @Nullable String[][] params, Response.Listener<JSONObject> response, Response.ErrorListener error){
        this.sendRequest(activity,Request.Method.POST,url,params,response,error);
    }

    public void sendRequest(YezzMeta activity,int method, String url, @Nullable JSONObject params, Response.Listener<JSONObject> response, Response.ErrorListener error){
        if (params==null){
            params=new JSONObject();
        }
        params=this.iniParams(params);
        this.sendRequestRun(activity,method,url,params,response,error);
    }

    public void sendRequest(YezzMeta activity,int method, String url, @Nullable String[][] params, Response.Listener<JSONObject> response, Response.ErrorListener error){
        JSONObject data=new JSONObject();
        if (params!=null){
            try {
                for(int i=0;i<params.length;i++){
                    data.put(params[i][0],params[i][1]);
                }
            } catch (JSONException e) {
                Log.d("addParams Array to Json",e.getMessage());
            }
        }
        data=this.iniParams(data);
        this.sendRequestRun(activity,method,url,data,response,error);
    }

    private void sendRequestRun(YezzMeta activity, int method,String url, @Nullable JSONObject params, Response.Listener<JSONObject> response, Response.ErrorListener error){
        Log.d("sendRequestRun",url);
        Log.d("sendRequestRun",params.toString());
        method=this.validateMethod(method);
        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonObjectRequest stringRequest = new JsonObjectRequest(method,url,params,response,error){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private JSONObject iniParams(JSONObject params){
        try {
            if (this.isLogged()){
                    params.put("user_id",this.user.getString("id"));
            }
            params.put("device_serial",this.getDeviceSerial());
        } catch (JSONException e) {
            Log.d("iniParams",e.getMessage());
        }
        return params;
    }

    private int validateMethod(int method){
        if (method!=Request.Method.POST && method!=Request.Method.GET){
            return Request.Method.POST;
        }
        return method;
    }

    public String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public String prepareUrlElement(String element){
        String url = null;
        String base = getResources().getString(R.string.base);

        if(!base.endsWith("/")){
            url = base + "/";
        }else{
            url = base;
        }

        if(element.startsWith("/")){
            url += element.substring(1,element.length());
        }
        else{
            url += element;
        }
        return url;
    }
    /*End HTTP Request Manage*/

    /**MANAGE UBICATION**/
    protected void locationStart() {
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        local = new Localizacion(getApplicationContext());

        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        final boolean network_enabled = mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!gpsEnabled && !network_enabled) {
            Toast.makeText(getApplicationContext(), "NO SE PUEDE OBTENER UBICACION. TODOS LOS SERVICIOS ESTAN DESHABILITADOS", Toast.LENGTH_LONG).show();
            Log.d("debugGPS", "NO HAY SERVICIO DE GEOLOCALIZACION" + Localizacion.latitud + " " + Localizacion.longitud);
        }
        //Si no esta activo, lo inicio
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(getApplicationContext(), "Debe habilitar todos los permisos", Toast.LENGTH_SHORT).show();
            //Esto deberia solicitar los permisos
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        } else {
            //30 segundos
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 20, 0, local);
            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 20, 0, local);
            if (mlocManager != null) {
                mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
    }

    protected void stopUsingGPS() {
        if (mlocManager != null) {
            mlocManager.removeUpdates(local);
        }
    }

    protected void saveUbication(@Nullable String storeId) {
        if (Localizacion.latitud != 0.0 && Localizacion.longitud != 0.0) {
            try {
                LocalizationLogContract contract = new LocalizationLogContract();
                contract.setLatitude(""+Localizacion.latitud);
                contract.setLongitude(""+Localizacion.longitud);
                iniUserJson();
                contract.setUser(this.user.getString("id"));
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                contract.setCreated(mdformat.format(calendar.getTime()));
                if (storeId != null) {
                    contract.setStore_id(storeId);
                }
                LocalizationLog l = new LocalizationLog(getApplicationContext());
                l.create(contract);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("EXIT GPS", "guarde " + Localizacion.latitud + " " + Localizacion.longitud);
            this.stopUsingGPS();
        }
    }

    public void ShowMessageGps() {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            //si no te gusta la pantalla, comenta las dos lienas de arriba y descomenta la que sigue
            //this.showMsg("DEBE ACTIVAR LOS SERVICIOS DE LOCALIZACION");
        }
    }

    protected void showLoad(int count) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Espere..");
        pDialog.show();
        for (int i = 0; i < count; i++){};
        pDialog.dismiss();
        pDialog = null;
    }
}
