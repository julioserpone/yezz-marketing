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
public class ForgetPassword extends YezzMeta {
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_forget_password);
        this.initComponent();

    }

    private void initComponent() {
        this.etEmail=(EditText) this.findViewById(R.id.etForgetPasswordEmail);
    }

    public void clickForgetPasswordSent(View view){
        this.checkEmail(etEmail.getText().toString());

    }

    private void checkEmail(final String email){
        String url =this.getUrl(R.string.routeForgetPassword);

        JSONObject params=new JSONObject();
        try{
            params.put("email",email);
        }catch (JSONException e){
            Log.d("params add",e.getMessage());
        }

        this.sendRequest(this,url,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("email")){
                                showMsg(getString(R.string.forget_password_success));
                                finish();
                            }else{
                                showMsg(getString(R.string.forget_password_fail));
                            }
                        }catch (JSONException e){
                            showMsg(getString(R.string.forget_password_fail));
                            Log.i("JSONException: ",e.getMessage());
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("onErrorResponse ",error.getMessage()!=null?error.getMessage():"");
                        showMsg(getString(R.string.no_connection),getString(R.string.no_connection)+ " " + error.getMessage() );
                    }
                });
    }
}
