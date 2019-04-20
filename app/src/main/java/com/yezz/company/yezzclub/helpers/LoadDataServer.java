package com.yezz.company.yezzclub.helpers;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yezz.company.yezzclub.R;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoadDataServer {
    private Context ctx;
    private DataStorageManager dataManager;

    public LoadDataServer(Context ctx) {
        this.ctx = ctx;
        dataManager = new DataStorageManager(ctx);
    }

    /**
     * Obtiene la data del servidor y procesa la data obenida
     * @param response
     * @param dbHelper
     * @return
     */
    public boolean processData(JSONObject response, YezzDB dbHelper) {
        boolean resp = true;
        try {
            if(!response.getBoolean("login")){
                resp = false;
            }

            if(response.getBoolean("cache") && resp){

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
                    dbHelper.delete(CityContract.CityEntry.TABLE_NAME,
                            CityContract.CityEntry.STATUS+ "<>? OR status IS NULL",
                            new String[]{Constants.STATUS_NEW});
                    for (int i=0;i<cities.length();i++){
                        JSONObject city=cities.getJSONObject(i);
                        dbHelper.save(new CityContract(city.getString("id"),
                                city.getString("name"),city.getString("state_id"),Constants.STATUS_ACTIVE));
                    }
                }

                JSONArray chains=response.getJSONArray("chains");
                if (chains.length()>0){
                    dbHelper.delete(ChainContract.StoreChainEntry.TABLE_NAME,
                            ChainContract.StoreChainEntry.STATUS+ "<>? OR status IS NULL",
                            new String[]{Constants.STATUS_NEW});
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
                            TownshipsContract.TownshipsEntry.STATUS+ "<>? OR status IS NULL",
                            new String[]{Constants.STATUS_NEW});
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
                           "(" + BranchContract.BranchEntry.STATUS+ "<>? AND " + BranchContract.BranchEntry.STATUS +
                                    " <> ? ) OR status IS NULL",
                            new String[]{Constants.STATUS_NEW,Constants.STATUS_UPDATE});
                    for (int i=0;i<branchs.length();i++){
                        JSONObject branch=branchs.getJSONObject(i);
                        try {
                            dbHelper.save(new BranchContract(branch.getString("id"), branch.getString("name"),
                                    branch.getString("code"),branch.getString("address"),
                                    branch.getString("phone"),branch.getString("zip_code"),branch.getString("type_id"),
                                    branch.getString("chain_id"), branch.getString("country_id"),branch.getString("state_id"),
                                    branch.getString("city_id"),branch.getString("township_id"), branch.getString("latitude"),
                                    branch.getString("longitude"),branch.getString("category_id"), branch.getString("contact_id"),
                                    branch.getString("is_customer"),Constants.STATUS_ACTIVE, branch.getString("has_pop"),
                                    branch.getString("comments")));
                        } finally{}
                    }
                }

                JSONArray contacts=response.getJSONArray("contacts");
                if (contacts.length()>0){
                    dbHelper.delete(StoreContactContract.StoreContactEntry.TABLE_NAME,
                            StoreContactContract.StoreContactEntry.STATUS+ "<>?",
                            new String[]{Constants.STATUS_NEW});
                    for (int i=0;i<contacts.length();i++){
                        JSONObject contact=contacts.getJSONObject(i);
                        dbHelper.save(new StoreContactContract(contact.getString("id"),contact.getString("name_customer"),
                                contact.getString("surname_customer"),contact.getString("store_position_customer"),
                                contact.getString("phone_customer"),contact.getString("email_customer"),
                                contact.getString("address_customer"), Constants.STATUS_ACTIVE));
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
                            PhoneModelContract.PhoneModelEntry.STATUS+ "<>? OR status IS NULL",
                            new String[]{Constants.STATUS_NEW});
                    for (int i=0;i<phoneModels.length();i++){
                        JSONObject model = phoneModels.getJSONObject(i);
                        dbHelper.save(new PhoneModelContract(null, model.getString("name"),
                                model.getString("brand"), model.getString("productId"), Constants.STATUS_ACTIVE));
                    }
                }

                JSONArray phoneBrands=response.getJSONArray("phoneBrands");
                if (phoneBrands.length()>0){
                    dbHelper.delete(BrandPhoneContract.BrandEntry.TABLE_NAME,
                            BrandPhoneContract.BrandEntry.STATUS+ "<>? OR status IS NULL",
                            new String[]{Constants.STATUS_NEW});
                    for (int i=0;i<phoneBrands.length();i++){
                        JSONObject Brand=phoneBrands.getJSONObject(i);
                        dbHelper.save(new BrandPhoneContract(null, Brand.getString("name"),
                                Brand.getString("is_yezz"), Constants.STATUS_ACTIVE));
                    }
                }

                dbHelper.delete(PhoneContract.PhoneEntry.TABLE_NAME,
                        PhoneContract.PhoneEntry.STATUS+ "=? OR status IS NULL",
                        new String[]{Constants.STATUS_ACTIVE});
                dbHelper.delete(LocalizationLogContract.LocalizationLogEntry.TABLE_NAME,
                        LocalizationLogContract.LocalizationLogEntry.STATUS+ "=?  OR status IS NULL",
                        new String[]{Constants.STATUS_ACTIVE});
                dbHelper.delete(MediaFilesContract.MediaFilesEntry.TABLE_NAME,
                        MediaFilesContract.MediaFilesEntry.STATUS + "=?  OR status IS NULL",
                        new String[]{Constants.STATUS_ACTIVE});
                dbHelper.delete(VisitStoreContract.VisitStoreEntry.TABLE_NAME, VisitStoreContract.VisitStoreEntry.STATUS +"=?",
                        new String[]{Constants.STATUS_CLOSE});

                JSONObject user = this.dataManager.getFileJson(ctx.getString(R.string.fileUserJson));

                setLastUser(user.getString("email"),user.getString("country_id"),user.getString("id"));
                this.dataManager.setRouteData(response.getJSONArray("routes"));

                resp=true;
            }
        }catch (JSONException e){
            resp = false;
        } finally {
            dbHelper.close();
        }
        return resp;
    }

    /**
     * Verifica los registro y envia el status de las tablas para de esta conocer si debe o no enviar dicha data
     * @param dbHelper
     * @return
     */
    public JSONObject getParams(@NonNull YezzDB dbHelper, boolean addDataLogin, boolean forceDownload){
        JSONObject params=new JSONObject();
        JSONObject user=null;
        try {
            user= this.getFileJson(ctx.getString(R.string.fileUserJson));
            if(user == null) {
                return null;
            }

            params.put("cache","1");
            String[][] strings=new String[][]{
                    {"chains", ChainContract.StoreChainEntry.TABLE_NAME,user.getString("numChains")},
                    {"type_channels", StoreTypeContract.StoreTypeEntry.TABLE_NAME,user.getString("numTypeChannels")},
                    {"contacts", StoreContactContract.StoreContactEntry.TABLE_NAME,user.getString("numContacts")},
            };
            for(int i=0;i<strings.length;i++){
                if (forceDownload) {
                    params.put(strings[i][0],"1");
                } else {
                    if(!checkNumResult(dbHelper,strings[i][1],Integer.parseInt(strings[i][2]))){
                        params.put(strings[i][0],"1");
                    }
                }
            }

            JSONObject last= getFileJson(ctx.getString(R.string.fileLastUser));
            if(last==null || !last.getString("country").equals(user.getString("country_id"))){
                params.put("branchs","1");
            }else if(!checkNumResult(dbHelper, BranchContract.BranchEntry.TABLE_NAME,user.getInt("numBranchs"))){
                params.put("branchs","1");
            }
            params.put("country", user.getString("country_id"));
            if (addDataLogin) {
                params.put("user_id",user.getString("id"));
                params.put("device_serial",Build.SERIAL);
            }
            return params;
        }catch (Exception ex){
            Log.d("getUrlParams",ex.getMessage());
        }
        return params;
    }

    /**
     * Genera los parametros de autenticacion para la sincronizacion desatendida de datos
     * @return
     */
    public JSONObject getSyncDataParam() {
        JSONObject params=new JSONObject();
        JSONObject user=null;
        try {
            user= this.getFileJson(ctx.getString(R.string.fileUserJson));
            if(user == null) {
                return null;
            }
            params.put("cache","1");
            params.put("country", user.getString("country_id"));
            params.put("user_id",user.getString("id"));
            params.put("device_serial",Build.SERIAL);

        }catch (Exception ex){
            params = null;
            Log.d("getUrlParams",ex.getMessage());
        }
        return params;
    }

    /**
     * Retorna el numero de registros fde la tabla dada
     * @param dbHelper
     * @param table
     * @param num
     * @return
     */
    public boolean checkNumResult(@NonNull YezzDB dbHelper, String table, int num){
        Cursor mCount=dbHelper.getRawCursor("SELECT COUNT(id) FROM "+table);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count==num;
    }

    /**
     * Actualiza la data del ultimo usuario que utilizo la app
     * @param email
     * @param country
     */
    private void setLastUser(String email,String country, String id){
        try {
            JSONObject json=new JSONObject();
            json.put("id",id);
            json.put("email",email);
            json.put("country",country);
            this.setFileJson(ctx.getString(R.string.fileLastUser), json.toString());
        }catch (JSONException ex){
            Log.d("setUserJson",ex.getMessage());
        }
    }

    /**
     * Registra los datos en formato json en un archito de texto dado
     * @param fileName
     * @param json
     */
    private void setFileJson(@NonNull String fileName,@NonNull String json){
        try{
            OutputStreamWriter txt=new OutputStreamWriter(ctx.openFileOutput(fileName,ctx.MODE_PRIVATE));
            txt.write(json);
            txt.close();
        }catch (Exception e){
            Log.d("setFileJson file: "+fileName,e.getMessage());
        }
    }

    /**
     * Registra los datos en formato json en un archito de texto dado
     * @param fileName
     * @return
     */
    @Nullable
    protected JSONObject getFileJson(@NonNull String fileName){
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
}
