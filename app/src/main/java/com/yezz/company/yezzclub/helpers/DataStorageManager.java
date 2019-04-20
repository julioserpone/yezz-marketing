package com.yezz.company.yezzclub.helpers;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yezz.company.yezzclub.R;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.RoutesContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataStorageManager {
    Context ctx;

    public DataStorageManager(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Retorna la estrcutura de usuario contenida en un archivo de texto
     * @param fileName ruta del archivo a consultar
     * @return JSONObject
     */
    @Nullable
    public JSONObject getFileJson(@NonNull String fileName){
        JSONObject json=null;
        try{
            BufferedReader txt=new BufferedReader(new InputStreamReader(ctx.openFileInput(fileName)));
            json=new JSONObject(txt.readLine());
            txt.close();
            return json;
        }catch (Exception e){
            Log.d("getFileJson file: "+fileName,e.getMessage());
        }
        return json;
    }

    /**
     * Registrar un archivo de texto con data de usuario en formato json
     * @param fileName
     * @param json
     */
    public void setFileJson(@NonNull String fileName,@NonNull JSONObject json){
        setFileJson(fileName,json.toString());
    }

    /**
     * Registrar un archivo de texto con data de usuario en formato json
     * @param fileName
     * @param json
     */
    public void setFileJson(@NonNull String fileName,@NonNull String json){
        try{
            OutputStreamWriter txt=new OutputStreamWriter(ctx.openFileOutput(fileName,ctx.MODE_PRIVATE));
            txt.write(json);
            txt.close();
        }catch (Exception e){
            Log.d("setFileJson file: "+fileName,e.getMessage());
        }
    }

    /**
     * Registra los datos de una ruta
     * @param data
     */
    public void setRouteData(JSONArray data){
        try {
            JSONObject route=new JSONObject();

            if(data.length() > 0){
                final YezzDB dbHelper=new YezzDB(ctx);
                dbHelper.delete(RoutesContract.RoutesEntry.TABLE_NAME,null,null);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                for (int i = 0; i < data.length();i++){
                    JSONObject ro = data.getJSONObject(i);
                    dbHelper.save(new RoutesContract(ro.getString("branch_id"),null,null,dateFormat.format(date),"1","0","0",null,null));
                }
            }
            route.put("routes",data);
            setFileJson(ctx.getString(R.string.fileRouteUser),route.toString());
        }catch (JSONException e){
            Log.d("iniRouteFile",e.getMessage());
        }
    }
}
