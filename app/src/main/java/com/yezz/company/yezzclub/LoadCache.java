package com.yezz.company.yezzclub;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yezz.company.yezzclub.helpers.Constants;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;
import com.yezz.company.yezzclub.lData.entities.contracts.BrandPhoneContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CategoryContract;
import com.yezz.company.yezzclub.lData.entities.contracts.ChainContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CityContract;
import com.yezz.company.yezzclub.lData.entities.contracts.LocalizationLogContract;
import com.yezz.company.yezzclub.lData.entities.contracts.MediaFilesContract;
import com.yezz.company.yezzclub.lData.entities.contracts.PhoneContract;
import com.yezz.company.yezzclub.lData.entities.contracts.PhoneModelContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StateContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreContactContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreTypeContract;
import com.yezz.company.yezzclub.lData.entities.contracts.TownshipsContract;
import com.yezz.company.yezzclub.lData.entities.contracts.VisitStoreContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramon_000 on 30-08-2016.
 */
public class LoadCache extends YezzMeta{
    private ProgressBar pbBar;
    private boolean band=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_load_cache);
        this.initComponent();
    }

    private void initComponent(){
        this.pbBar=(ProgressBar) this.findViewById(R.id.pbLoadCacheBar);
        this.pbBar.setProgress(50);
        this.setSystemCache();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!this.band){
            this.clearUserJson();
        }
        else{
            try{
                String url = this.prepareUrlElement(this.getPersonData().getString("pic_url"));
                new DownloadImageTask(null,this).execute(url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setSystemCache(){

        final YezzDB dbHelper = new YezzDB(getBaseContext());
        String url = this.getUrl(R.string.routeCache);

        JSONObject params=this.getParams(dbHelper);

        this.sendRequest(this,url,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(!response.getBoolean("login")){
                                startActivity(new Intent(LoadCache.this,Login.class));
                                finish();
                            }
                            if(response.getBoolean("cache")){

                                JSONArray types=response.getJSONArray("type_channels");
                                if (types.length()>0){
                                    dbHelper.delete(StoreTypeContract.StoreTypeEntry.TABLE_NAME,null,null);
                                    for (int i=0;i<types.length();i++){
                                        JSONObject type=types.getJSONObject(i);
                                        dbHelper.save(new StoreTypeContract(type.getString("id"),
                                                type.getString("name")));
                                    }
                                }

                                JSONArray cities=response.getJSONArray("cities");
                                if (cities.length()>0){
                                    dbHelper.delete(CityContract.CityEntry.TABLE_NAME,null,null);
                                    for (int i=0;i<cities.length();i++){
                                        JSONObject city=cities.getJSONObject(i);
                                        dbHelper.save(new CityContract(city.getString("id"),
                                                city.getString("name"),city.getString("state_id"),
                                                Constants.STATUS_ACTIVE));
                                    }
                                }

                                JSONArray chains=response.getJSONArray("chains");
                                if (chains.length()>0){
                                    dbHelper.delete(ChainContract.StoreChainEntry.TABLE_NAME,
                                            ChainContract.StoreChainEntry.STATUS+ "=?",new String[]{Constants.STATUS_ACTIVE});
                                    for (int i=0;i<chains.length();i++){
                                        JSONObject chain=chains.getJSONObject(i);
                                        dbHelper.save(new ChainContract(chain.getString("id"),chain.getString("chain_user_id"),
                                                chain.getString("chain_country_id"),chain.getString("identification_chain"),
                                                chain.getString("name_chain"),chain.getString("phone_chain"),chain.getString("email_chain"),
                                                chain.getString("address_chain"), Constants.STATUS_ACTIVE));
                                    }
                                }

                                JSONArray townships=response.getJSONArray("townships");
                                if (townships.length()>0){
                                    dbHelper.delete(TownshipsContract.TownshipsEntry.TABLE_NAME,
                                            TownshipsContract.TownshipsEntry.STATUS+ "=?",new String[]{Constants.STATUS_ACTIVE});
                                    for (int i=0;i<townships.length();i++){
                                        JSONObject township=townships.getJSONObject(i);
                                        dbHelper.save(new TownshipsContract(township.getString("id"),township.getString("city_id"),
                                                township.getString("name"),Constants.STATUS_ACTIVE));
                                    }
                                }

                                JSONArray states=response.getJSONArray("states");
                                if (states.length()>0){
                                    dbHelper.delete(StateContract.StateEntry.TABLE_NAME,null,null);
                                    for (int i=0;i<states.length();i++){
                                        JSONObject state=states.getJSONObject(i);
                                        dbHelper.save(new StateContract(state.getString("id"),state.getString("name"),
                                                state.getString("country_id")));
                                    }
                                }

                                JSONArray branchs=response.getJSONArray("branchs");
                                if (branchs.length()>0){
                                    dbHelper.delete(BranchContract.BranchEntry.TABLE_NAME,
                                            BranchContract.BranchEntry.STATUS+ "=?",new String[]{Constants.STATUS_ACTIVE});
                                    for (int i=0;i<branchs.length();i++){
                                        JSONObject branch=branchs.getJSONObject(i);
                                        dbHelper.save(new BranchContract(branch.getString("id"), branch.getString("name"),
                                                branch.getString("code"),branch.getString("address"), branch.getString("phone"),
                                                branch.getString("zip_code"),branch.getString("type_id"), branch.getString("chain_id"),
                                                branch.getString("country_id"),branch.getString("state_id"), branch.getString("city_id"),
                                                branch.getString("township_id"), branch.getString("latitude"), branch.getString("longitude"),
                                                branch.getString("category_id"), branch.getString("contact_id"), branch.getString("is_customer"),
                                                Constants.STATUS_ACTIVE, branch.getString("has_pop"), branch.getString("comments")));
                                    }
                                }

                                JSONArray contacts=response.getJSONArray("contacts");
                                if (contacts.length()>0){
                                    dbHelper.delete(StoreContactContract.StoreContactEntry.TABLE_NAME,
                                            StoreContactContract.StoreContactEntry.STATUS+ "=?",new String[]{Constants.STATUS_ACTIVE});
                                    for (int i=0;i<contacts.length();i++){
                                        JSONObject contact=contacts.getJSONObject(i);
                                        dbHelper.save(new StoreContactContract(contact.getString("id"),
                                                contact.getString("name_customer"),contact.getString("surname_customer"),
                                                contact.getString("store_position_customer"),contact.getString("phone_customer"),
                                                contact.getString("email_customer"),contact.getString("address_customer"),
                                                Constants.STATUS_ACTIVE));
                                    }
                                }

                                JSONArray categories=response.getJSONArray("categories");
                                if (categories.length()>0){
                                    dbHelper.delete(CategoryContract.CategoryEntry.TABLE_NAME,null,null);
                                    for (int i=0;i<categories.length();i++){
                                        JSONObject category=categories.getJSONObject(i);
                                        dbHelper.save(new CategoryContract(category.getString("id"),category.getString("name"),
                                                category.getString("description")));
                                    }
                                }

                                JSONArray phoneModels=response.getJSONArray("phoneModels");
                                if (phoneModels.length()>0){
                                    dbHelper.delete(PhoneModelContract.PhoneModelEntry.TABLE_NAME,
                                            PhoneModelContract.PhoneModelEntry.STATUS+ "=?",new String[]{Constants.STATUS_ACTIVE});
                                    for (int i=0;i<phoneModels.length();i++){
                                        JSONObject model = phoneModels.getJSONObject(i);
                                        dbHelper.save(new PhoneModelContract(null, model.getString("name"),
                                                model.getString("brand"), model.getString("productId"), Constants.STATUS_ACTIVE));
                                    }
                                }

                                JSONArray phoneBrands=response.getJSONArray("phoneBrands");
                                if (phoneBrands.length()>0){
                                    dbHelper.delete(BrandPhoneContract.BrandEntry.TABLE_NAME,
                                            BrandPhoneContract.BrandEntry.STATUS+ "=?",new String[]{"active"});
                                    for (int i=0;i<phoneBrands.length();i++){
                                        JSONObject Brand=phoneBrands.getJSONObject(i);
                                        dbHelper.save(new BrandPhoneContract(null, Brand.getString("name"),
                                                Brand.getString("is_yezz"), Constants.STATUS_ACTIVE));
                                    }
                                }

                                dbHelper.delete(PhoneContract.PhoneEntry.TABLE_NAME, PhoneContract.PhoneEntry.STATUS+ "=?",
                                        new String[]{Constants.STATUS_ACTIVE});
                                dbHelper.delete(LocalizationLogContract.LocalizationLogEntry.TABLE_NAME, LocalizationLogContract.LocalizationLogEntry.STATUS+ "=?",
                                        new String[]{Constants.STATUS_ACTIVE});
                                dbHelper.delete(MediaFilesContract.MediaFilesEntry.TABLE_NAME, MediaFilesContract.MediaFilesEntry.STATUS + "=?",
                                        new String[]{Constants.STATUS_ACTIVE});
                                dbHelper.delete(VisitStoreContract.VisitStoreEntry.TABLE_NAME, VisitStoreContract.VisitStoreEntry.STATUS +"=?",
                                        new String[]{Constants.STATUS_CLOSE});

                                setLastUser(user.getString("email"),user.getString("country_id"),user.getString("id"));
                                setRouteData(response.getJSONArray("routes"));

                                band=true;
                                dbHelper.close();
                                startActivity(new Intent(LoadCache.this,Dashboard.class));
                                finish();
                            }else{
                                showMsg(getString(R.string.error_request )+ "\n" + getString(R.string.error_close_app));
                                finish();
                            }
                        }catch (JSONException e){
                            showMsg(getString(R.string.error_request )+ "\n" + getString(R.string.error_close_app),
                                    getString(R.string.error_request )+ "\n" + getString(R.string.error_close_app) + " " + e.getMessage());
                            Log.i("setSystemCache: ",e.getMessage());
                            finish();
                        } finally {
                            dbHelper.close();
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showMsg(getString(R.string.error_request) + "\n" + getString(R.string.error_close_app),
                                getString(R.string.error_request) + "\n" + getString(R.string.error_close_app) + " " + error.getMessage());
                        Log.d("setSystemCache: ", error.getMessage());
                        finish();
                    }
                });
    }

    private JSONObject getParams(@NonNull YezzDB dbHelper){
        JSONObject params=new JSONObject();
        try {
            this.iniUserJson();
            params.put("cache","1");

            String[][] strings=new String[][]{
                    {"chains", ChainContract.StoreChainEntry.TABLE_NAME,this.user.getString("numChains")},
                    {"type_channels",StoreTypeContract.StoreTypeEntry.TABLE_NAME,this.user.getString("numTypeChannels")},
                    {"contacts", StoreContactContract.StoreContactEntry.TABLE_NAME,this.user.getString("numContacts")},
            };
            for(int i=0;i<strings.length;i++){
                if(!checkNumResult(dbHelper,strings[i][1],Integer.parseInt(strings[i][2]))){
                    params.put(strings[i][0],"1");
                }
            }

            JSONObject last=this.getLastUser();
            if(last==null || !last.getString("country").equals(this.user.getString("country_id"))){
                params.put("branchs","1");
            }else if(!checkNumResult(dbHelper, BranchContract.BranchEntry.TABLE_NAME,this.user.getInt("numBranchs"))){
                params.put("branchs","1");
            }
            params.put("country", this.user.getString("country_id"));
            return params;
        }catch (Exception ex){
            Log.d("getUrlParams",ex.getMessage());
        }
        return params;
    }

    private boolean checkNumResult(@NonNull YezzDB dbHelper, String table, int num){
        Cursor mCount=dbHelper.getRawCursor("SELECT COUNT(id) FROM "+table);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count==num;
    }

    private void setLastUser(String email,String country, String id){
        try {
            JSONObject json=new JSONObject();
            json.put("id",id);
            json.put("email",email);
            json.put("country",country);
            this.setFileJson(this.getString(R.string.fileLastUser),json);
        }catch (JSONException ex){
            Log.d("setUserJson",ex.getMessage());
        }
    }
}
