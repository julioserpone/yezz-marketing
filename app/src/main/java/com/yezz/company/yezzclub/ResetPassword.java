package com.yezz.company.yezzclub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramon_000 on 19-09-2016.
 */
public class ResetPassword extends YezzMeta{
    private EditText etPassword,etConfirm;
    private String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_reset_password);
        this.initComponent();
    }

    private void initComponent() {
        this.etPassword=(EditText) this.findViewById(R.id.etResetPasswordPassword);
        this.etConfirm=(EditText) this.findViewById(R.id.etResetPasswordConfirm);

        this.email=this.getIntent().getExtras().getString("email");
        this.password=this.getIntent().getExtras().getString("password");
    }

    public void clickResetPasswordSend(View view){
        if(etPassword.getText().toString().equals(etConfirm.getText().toString())){
            this.checkPassword(etPassword.getText().toString());
        }else{
            this.showMsg(this.getString(R.string.reset_password_confirm_fail));
        }
    }

    private void checkPassword(final String password){
        String url =this.getUrl(R.string.routeResetPassword);

        JSONObject params=new JSONObject();
        try{
            params.put("email",this.email);
            params.put("password",this.password);
            params.put("newPassword",password);
        }catch (JSONException e){
            Log.d("params add",e.getMessage());
        }

        this.sendRequest(this,url,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("login")){
                                showMsg(getString(R.string.reset_password_success));
                            }else{
                                showMsg(getString(R.string.reset_password_fail));
                            }
                        }catch (JSONException e){
                            showMsg(getString(R.string.reset_password_fail));
                            Log.d("JSONException: ",e.getMessage());
                        }
                        finish();
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("onErrorResponse ",error.getMessage()!=null?error.getMessage():"");
                        showMsg(getString(R.string.no_connection),getString(R.string.no_connection)+ " " + error.getMessage() );
                    }
                });
    }
}
