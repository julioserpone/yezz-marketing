package com.yezz.company.yezzclub;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by company on 24-07-2016.
 */
public class Login extends YezzMeta {
    private EditText etUser,etPassword;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        this.initComponent();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
    }

    private void initComponent() {
        this.etPassword=(EditText) this.findViewById(R.id.etLoginPassword);
        this.etUser=(EditText) this.findViewById(R.id.etLoginUser);

        JSONObject lastUser=this.getLastUser();
        if (lastUser!=null){
            try {
                this.etUser.setText(lastUser.getString("email"));
            }catch (JSONException ex){
                Log.d("lastUser email",ex.getMessage());
            }
        }
    }

    public void clickEnterLogin(View view){
        String user=this.etUser.getText().toString();
        String password=this.etPassword.getText().toString();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        this.checkLogin(user,password);

    }

    private void checkLogin(final String user, final String password){
        String url =this.getUrl(R.string.routeLogin);
        JSONObject params=new JSONObject();
        try{
            params.put("email",user);
            params.put("password",password);
            params.put("device_info",this.getDeviceInfo());
            params.put("", Build.SERIAL);
        }catch (JSONException e){
            Log.d("params add",e.getMessage());
        }

        this.sendRequest(this,url,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("login")){
                                if (response.getBoolean("temp")){
                                    Intent intent=new Intent(Login.this,ResetPassword.class);
                                    intent.putExtra("email",user);
                                    intent.putExtra("password",password);
                                    startActivity(intent);
                                    return;
                                }
                                setLogin(response);
                                startActivity(new Intent(Login.this,LoadCache.class));
                                finish();
                            }else{
                                showMsg(getString(R.string.credential_invalid));
                            }
                            pDialog.dismiss();
                        }catch (JSONException e){
                            pDialog.dismiss();
                            showMsg(getString(R.string.credential_invalid));
                            Log.i("JSONException: ",e.getMessage());
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        showMsg(getString(R.string.no_connection),getString(R.string.no_connection)+ " " + error.getMessage() );
                    }
                });
    }

    public void clickLoginForgetPassword(View view){
        this.startActivity(new Intent(this,ForgetPassword.class));
    }

    private String getDeviceInfo(){
        return "VERSION.RELEASE : "+ Build.VERSION.RELEASE
                +"\nVERSION.INCREMENTAL : "+Build.VERSION.INCREMENTAL
                +"\nVERSION.SDK.NUMBER : "+Build.VERSION.SDK_INT
                +"\nBOARD : "+Build.BOARD
                +"\nBOOTLOADER : "+Build.BOOTLOADER
                +"\nBRAND : "+Build.BRAND
                +"\nCPU_ABI : "+Build.CPU_ABI
                +"\nCPU_ABI2 : "+Build.CPU_ABI2
                +"\nDISPLAY : "+Build.DISPLAY
                +"\nFINGERPRINT : "+Build.FINGERPRINT
                +"\nHARDWARE : "+Build.HARDWARE
                +"\nHOST : "+Build.HOST
                +"\nID : "+Build.ID
                +"\nMANUFACTURER : "+Build.MANUFACTURER
                +"\nMODEL : "+Build.MODEL
                +"\nPRODUCT : "+Build.PRODUCT
                +"\nSERIAL : "+Build.SERIAL
                +"\nTAGS : "+Build.TAGS
                +"\nTIME : "+Build.TIME
                +"\nTYPE : "+Build.TYPE
                +"\nUNKNOWN : "+Build.UNKNOWN
                +"\nUSER : "+Build.USER;
    }
}
