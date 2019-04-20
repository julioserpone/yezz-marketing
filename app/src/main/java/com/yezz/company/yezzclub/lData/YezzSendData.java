package com.yezz.company.yezzclub.lData;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yezz.company.yezzclub.Login;
import com.yezz.company.yezzclub.R;
import com.yezz.company.yezzclub.YezzMeta;
import com.yezz.company.yezzclub.helpers.Constants;
import com.yezz.company.yezzclub.lData.entities.Branch;
import com.yezz.company.yezzclub.lData.entities.Chain;
import com.yezz.company.yezzclub.lData.entities.City;
import com.yezz.company.yezzclub.lData.entities.LocalizationLog;
import com.yezz.company.yezzclub.lData.entities.MediaFiles;
import com.yezz.company.yezzclub.lData.entities.Phone;
import com.yezz.company.yezzclub.lData.entities.StoreContact;
import com.yezz.company.yezzclub.lData.entities.Township;
import com.yezz.company.yezzclub.lData.entities.VisitStore;
import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;
import com.yezz.company.yezzclub.lData.entities.contracts.ChainContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CityContract;
import com.yezz.company.yezzclub.lData.entities.contracts.LocalizationLogContract;
import com.yezz.company.yezzclub.lData.entities.contracts.MediaFilesContract;
import com.yezz.company.yezzclub.lData.entities.contracts.PhoneContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreContactContract;
import com.yezz.company.yezzclub.lData.entities.contracts.TownshipsContract;
import com.yezz.company.yezzclub.lData.entities.contracts.VisitStoreContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ramon_000 on 23-09-2016.
 */
public class YezzSendData {
    private YezzMeta activity;
    private YezzDB dbHelper;
    private JSONObject currentSesion = null;

    public YezzSendData(YezzMeta activity) {
        this.activity = activity;
        this.dbHelper = new YezzDB(this.activity.getApplicationContext());
    }

    public boolean checkConnection(){
        ConnectivityManager connectivity=(ConnectivityManager) this.activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networks=connectivity.getAllNetworkInfo();
        for (int i=0;i<networks.length;i++){
            if (networks[i].getState()==NetworkInfo.State.CONNECTED){
                return true;
            }
        }
        return false;
    }

    public boolean checkAnyData(){
        boolean result=false;
        result=result ? result : this.checkData(BranchContract.BranchEntry.TABLE_NAME,BranchContract.BranchEntry.STATUS);
        result=result ? result : this.checkData(ChainContract.StoreChainEntry.TABLE_NAME, ChainContract.StoreChainEntry.STATUS);
        result=result ? result : this.checkData(PhoneContract.PhoneEntry.TABLE_NAME,PhoneContract.PhoneEntry.STATUS);
        result=result ? result : this.checkData(LocalizationLogContract.LocalizationLogEntry.TABLE_NAME,LocalizationLogContract.LocalizationLogEntry.STATUS);
        result=result ? result : this.checkData(StoreContactContract.StoreContactEntry.TABLE_NAME, StoreContactContract.StoreContactEntry.STATUS);
        return result;
    }

    private boolean checkData(String module,String field){
        Cursor mCount=this.dbHelper.getRawCursor("SELECT COUNT(id) FROM "+module+" WHERE "+field+" = 'new'");
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count>0;
    }

    private boolean checkData(String module,String field, boolean update){
        String query = "SELECT COUNT(id) FROM "+module+" WHERE "+field+" = 'new' OR "+field+" = 'update'";
        Cursor mCount=this.dbHelper.getRawCursor(query);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count>0;
    }

    public void synchronize(){
        /*JSONObject branchInfo = activity.getBranchSession();
        activity.iniUserJson();
        JSONObject user = activity.user;
        if (branchInfo != null) {
            try {
                branchInfo.put("comment", "");
                if (user == null) {
                    activity.iniUserJson();
                }
                branchInfo.put("user",user.getString("id"));
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                branchInfo.put("date", mdformat.format(calendar.getTime()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
        this.synchronize(null);
    }

    public void synchronize(@Nullable JSONObject BranchSesion){
        if (!this.checkConnection()){
            return;
        }

        JSONObject param=new JSONObject();
        boolean band=false;
        if (this.checkData(ChainContract.StoreChainEntry.TABLE_NAME, ChainContract.StoreChainEntry.STATUS)){
            param=this.getStoreChainsParams(param);
            band=true;
        }

        if (this.checkData(BranchContract.BranchEntry.TABLE_NAME, BranchContract.BranchEntry.STATUS, true)){
            param = this.getBranchesParams(param);
            band = true;
        }
        if (this.checkData(PhoneContract.PhoneEntry.TABLE_NAME, PhoneContract.PhoneEntry.STATUS)){
            param = this.getPhonesParams(param);
            band = true;
        }
        if(this.checkData(LocalizationLogContract.LocalizationLogEntry.TABLE_NAME,LocalizationLogContract.LocalizationLogEntry.STATUS)){
            param = this.getLocalizationParams(param);
            band = true;
        }
        if(this.checkData(StoreContactContract.StoreContactEntry.TABLE_NAME, StoreContactContract.StoreContactEntry.STATUS)){
            param = this.getContactParams(param);
            band = true;
        }

        if (this.checkData(MediaFilesContract.MediaFilesEntry.TABLE_NAME, MediaFilesContract.MediaFilesEntry.STATUS)) {
            param = this.getMediaFilesParams(param);
            band = true;
        }

        if (this.checkData(CityContract.CityEntry.TABLE_NAME, CityContract.CityEntry.STATUS)) {
            param = this.getCitesParams(param);
            band = true;
        }

        if (this.checkData(TownshipsContract.TownshipsEntry.TABLE_NAME, TownshipsContract.TownshipsEntry.STATUS)) {
            param = this.getTownshipParams(param);
            band = true;
        }

        param = getVisitStore(param, BranchSesion);
        currentSesion = BranchSesion;

        if(param != null || param.length() > 0) {
            band = true;
        }

        if (band){
            this.activity.sendRequest(this.activity,this.activity.getUrl(R.string.routeSendSynchronize),
                    param,this.getResponse(),this.getErrorResponse());
        }else{
            this.activity.showMsg("No hay datos para sincronizar");
        }
    }

    private JSONObject getStoreChainsParams(JSONObject param){
        List<ChainContract> registers=  new Chain(this.activity.getApplicationContext()).getRegisters(
                ChainContract.StoreChainEntry.STATUS + " = ?",
                new String[]{"new"}
        );
        if (registers==null){
            return param;
        }
        try {
            JSONArray array=new JSONArray();
            for (int i=0;i<registers.size();i++){
                JSONObject object=new JSONObject();
                ChainContract instance=registers.get(i);
                object.put("id",instance.getId());
                object.put("name",instance.getName());
                object.put("country",instance.getCountry_id());
                array.put(i,object);
            }
            param.put("chains",array);
        }catch (JSONException e){
            Log.d("getStoreChainsParams",e.getMessage());
        }
        return param;
    }

    private JSONObject getBranchesParams(JSONObject param){
        List<BranchContract> registers= new Branch(this.activity.getApplicationContext()).getRegisters(
                BranchContract.BranchEntry.STATUS + " = ? OR " + BranchContract.BranchEntry.STATUS + " = ?",
                new String[]{"new", "update"}
        );
        if (registers==null){
            return param;
        }
        try {
            JSONArray array=new JSONArray();
            for (int i=0;i<registers.size();i++){
                JSONObject object=new JSONObject();
                BranchContract instance=registers.get(i);
                object.put("id",instance.getId());
                object.put("name",instance.getName());
                object.put("code",instance.getCode());
                object.put("address",instance.getAddress());
                object.put("phone",instance.getPhone());
                object.put("type_id",instance.getType_id());
                object.put("chain_id",instance.getChain_id());
                object.put("country_id",instance.getCountry_id());
                object.put("state_id",instance.getState_id());
                object.put("city_id",instance.getCity_id());
                object.put("township_id",instance.getTownship_id());
                object.put("is_customer",instance.getIs_customer());
                object.put("has_pop",instance.getHas_pop());
                object.put("contact",instance.getContact());
                object.put("category",instance.getCategory());
                object.put("status", instance.getStatus());
                object.put("comments", instance.getComments() != null ? instance.getComments() : "");
                array.put(i,object);
            }
            param.put("branches",array);
        }catch (JSONException e){
            Log.d("getBranchesParams",e.getMessage());
        }
        return param;
    }

    private JSONObject getPhonesParams(JSONObject param){
        List<PhoneContract> registers= new Phone(this.activity.getApplicationContext()).getRegisters(
                PhoneContract.PhoneEntry.STATUS + " = ?",
                new String[]{"new"}
        );
        if (registers==null){
            return param;
        }
        try {
            JSONArray array=new JSONArray();
            for (int i=0;i<registers.size();i++){
                JSONObject object=new JSONObject();
                PhoneContract instance=registers.get(i);
                object.put("id",instance.getId());
                object.put("brand",instance.getBrand());
                object.put("model",instance.getModel());
                object.put("exhibition_media",instance.getExhibition_media());
                object.put("exhibition_media_comment",instance.getExhibition_media_comment());
                object.put("stock",instance.getStock());
                object.put("exhibition",instance.getExhibition());
                object.put("sales",instance.getSales());
                object.put("parent",instance.getParent_id());
                object.put("purchase_price",instance.getPurchase_price());
                object.put("sale_price",instance.getSale_price());
                object.put("user",instance.getUser());
                object.put("branch_id", instance.getBranch_id());
                array.put(i,object);
            }
            param.put("phones",array);
        }catch (JSONException e){
            Log.d("getPhonesParams",e.getMessage());
        }
        return param;
    }

    private JSONObject getLocalizationParams(JSONObject param){
        List<LocalizationLogContract> registers= new LocalizationLog(this.activity.getApplicationContext()).getRegisters(
               LocalizationLogContract.LocalizationLogEntry.STATUS + " = ?",
                new String[]{"new"}
        );
        if (registers==null){
            return param;
        }
        try {
            JSONArray array=new JSONArray();
            for (int i=0;i<registers.size();i++){
                JSONObject object=new JSONObject();
                LocalizationLogContract instance=registers.get(i);
                object.put("id",instance.getId());
                object.put("store_id",instance.getStore_id());
                object.put("longitude",instance.getLongitude());
                object.put("latitude",instance.getLatitude());
                object.put("created",instance.getCreated());
                object.put("user",instance.getUser());
                array.put(i,object);
            }
            param.put("localization",array);
        }catch (JSONException e){
            Log.d("getLocalizationParams",e.getMessage());
        }
        return param;
    }

    private JSONObject getMediaFilesParams(JSONObject param) {
        List<MediaFilesContract> registers = new MediaFiles(this.activity).getRegisters(
                MediaFilesContract.MediaFilesEntry.STATUS + " = ?",
                new String[]{"new"}
        );
        if (registers == null){
            return param;
        }
        try {
            JSONArray array = new JSONArray();
            for (int i=0;i<registers.size();i++){
                JSONObject object=new JSONObject();
                MediaFilesContract instance=registers.get(i);
                object.put("id",instance.getId());
                object.put("sourceId",instance.getSourceId());
                object.put("userId",instance.getUserId());
                object.put("code",instance.getCode());
                object.put("path",instance.getPath());
                object.put("comment",instance.getComment());
                object.put("type",instance.getType());
                object.put("origin",instance.getOrigin());
                array.put(i,object);
            }
            param.put("mediaFiles",array);
        }catch (JSONException e){
            Log.d("mediaFilesParams",e.getMessage());
        }
        return param;
    }

    private JSONObject getContactParams(JSONObject param){
        List<StoreContactContract> registers= new StoreContact(this.activity.getApplicationContext()).getRegisters(
                StoreContactContract.StoreContactEntry.STATUS + " = ?",
                new String[]{"new"}
        );
        if (registers==null){
            return param;
        }
        try {
            JSONArray array=new JSONArray();
            for (int i=0;i<registers.size();i++){
                JSONObject object=new JSONObject();
                StoreContactContract instance=registers.get(i);
                object.put("id",instance.getId());
                object.put("name",instance.getName());
                object.put("surname",instance.getSurname());
                object.put("store_position",instance.getStore_position());
                object.put("phone",instance.getPhone());
                object.put("email",instance.getEmail());
                object.put("address",instance.getAddress());
                array.put(i,object);
            }
            param.put("contacts",array);
        }catch (JSONException e){
            Log.d("getContactParams",e.getMessage());
        }
        return param;
    }

    private JSONObject getVisitStore(JSONObject param, JSONObject branchInfo) {
        try {
            JSONArray array=new JSONArray();
            int index = 0;

            if (branchInfo != null) {
                array.put(index, branchInfo);
                index++;
            }

            List<VisitStoreContract> registers =  new VisitStore(this.activity).getRegisters(
                    VisitStoreContract.VisitStoreEntry.STATUS + " = ?",
                    new String[]{Constants.STATUS_NEW}
            );

            if (registers==null && branchInfo == null){
                return param;
            }

            for (int i=0;i<registers.size();i++){
                JSONObject object=new JSONObject();
                VisitStoreContract instance=registers.get(i);
                object.put("key",instance.getBranchId());
                object.put("user",instance.getUserId());
                object.put("comment",instance.getComment());
                object.put("start",instance.getStart());
                object.put("date",instance.getDate());
                array.put(index,object);
                index++;
            }
            param.put("visitStore",array);
        }catch (JSONException e){
            Log.d("getVisitStoreParams",e.getMessage());
        }
        return param;
    }

    private JSONObject getCitesParams(JSONObject param) {
        List<CityContract> registers=  new City(this.activity.getApplicationContext()).getRegisters(
                CityContract.CityEntry.STATUS + " = ?",
                new String[]{"new"}
        );
        if (registers==null){
            return param;
        }
        try {
            JSONArray array=new JSONArray();
            for (int i=0;i<registers.size();i++){
                JSONObject object=new JSONObject();
                CityContract instance=registers.get(i);
                object.put("id",instance.getId());
                object.put("name",instance.getName());
                object.put("state",instance.getState_id());
                array.put(i,object);
            }
            param.put("cities",array);
        }catch (JSONException e){
            Log.d("getStoreCitiesParams",e.getMessage());
        }
        return param;
    }

    private JSONObject getTownshipParams(JSONObject param){
        List<TownshipsContract> registers=  new Township(this.activity.getApplicationContext()).getRegisters(
                TownshipsContract.TownshipsEntry.STATUS + " = ?",
                new String[]{"new"}
        );
        if (registers==null){
            return param;
        }
        try {
            JSONArray array=new JSONArray();
            for (int i=0;i<registers.size();i++){
                JSONObject object=new JSONObject();
                TownshipsContract instance=registers.get(i);
                object.put("id",instance.getId());
                object.put("name",instance.getName());
                object.put("city",instance.getCity_id());
                array.put(i,object);
            }
            param.put("townships",array);
        }catch (JSONException e){
            Log.d("getStoreTownshipParams",e.getMessage());
        }
        return param;
    }

    private Response.Listener<JSONObject> getResponse(){
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(!response.getBoolean("login")){
                        activity.startActivity(new Intent(activity,Login.class));
                        activity.finish();
                    }
                    Context context=activity.getApplicationContext();
                    JSONArray townships=response.getJSONArray("townships");
                    if (townships.length()>0){
                        for (int i=0;i<townships.length();i++){
                            JSONObject township=townships.getJSONObject(i);
                            TownshipsContract newTownship=new TownshipsContract();
                            newTownship.setId(township.getString("new"));
                            newTownship.setStatus(Constants.STATUS_ACTIVE);
                            TownshipsContract oldTownship=new TownshipsContract();
                            oldTownship.setId(township.getString("old"));
                            (new Township(context)).update(newTownship,oldTownship);
                        }
                    }

                    JSONArray cities=response.getJSONArray("cities");
                    if (cities.length()>0){
                        for (int i=0;i<cities.length();i++){
                            JSONObject city=cities.getJSONObject(i);
                            CityContract newCity=new CityContract();
                            newCity.setId(city.getString("new"));
                            newCity.setStatus(Constants.STATUS_ACTIVE);
                            CityContract oldCity=new CityContract();
                            oldCity.setId(city.getString("old"));
                            (new City(context)).update(newCity,oldCity);
                        }
                    }

                    JSONArray chains=response.getJSONArray("chains");
                    if (chains.length()>0){
                        for (int i=0;i<chains.length();i++){
                            JSONObject chain=chains.getJSONObject(i);
                            ChainContract newStoreChain=new ChainContract();
                            newStoreChain.setId(chain.getString("new"));
                            newStoreChain.setStatus(Constants.STATUS_ACTIVE);
                            ChainContract oldStoreChain=new ChainContract();
                            oldStoreChain.setId(chain.getString("old"));
                            (new Chain(context)).update(newStoreChain,oldStoreChain);
                        }
                    }

                    JSONArray branches=response.getJSONArray("branches");
                    if (branches.length()>0){
                        for (int i=0;i<branches.length();i++){
                            JSONObject branch=branches.getJSONObject(i);
                            BranchContract newBranchContract=new BranchContract();
                            newBranchContract.setId(branch.getString("new"));
                            newBranchContract.setStatus(Constants.STATUS_ACTIVE);
                            BranchContract oldBranchContract=new BranchContract();
                            oldBranchContract.setId(branch.getString("old"));
                            (new Branch(context)).update(newBranchContract,oldBranchContract);
                            PhoneContract newPhoneContract=new PhoneContract();
                            newPhoneContract.setStore(branch.getString("new"));
                            PhoneContract oldPhoneContract=new PhoneContract();
                            oldPhoneContract.setStore(branch.getString("old"));
                            (new Phone(context)).update(newPhoneContract,oldPhoneContract);
                            activity.updateKeyBranchSession(branch.getString("new"),branch.getString("old"));
                        }
                    }
                    JSONArray phones = response.getJSONArray("phones");
                    if (phones.length() > 0){
                        for (int i=0;i<phones.length();i++) {
                            JSONObject phone=phones.getJSONObject(i);
                            PhoneContract newPhoneContract=new PhoneContract();
                            newPhoneContract.setId(phone.getString("new"));
                            PhoneContract exist=new Phone(context).getRegister(newPhoneContract);
                            if (exist == null) {
                                newPhoneContract.setStatus(Constants.STATUS_ACTIVE);
                                PhoneContract oldPhoneContract=new PhoneContract();
                                oldPhoneContract.setId(phone.getString("old"));
                                (new Phone(context)).update(newPhoneContract,oldPhoneContract);
                            }
                        }
                    }

                    JSONArray contacts=response.getJSONArray("contacts");
                    if (contacts.length() > 0) {
                        for (int i=0;i<contacts.length();i++) {
                            JSONObject contact = contacts.getJSONObject(i);
                            StoreContactContract newContactContract = new StoreContactContract();
                            newContactContract.setId(contact.getString("new"));
                            StoreContactContract exist = new StoreContact(context).getRegister(newContactContract);
                            if(exist == null){
                                newContactContract.setStatus(Constants.STATUS_ACTIVE);
                                StoreContactContract oldStoreContactContract = new StoreContactContract();
                                oldStoreContactContract.setId(contact.getString("old"));
                                (new StoreContact(context)).update(newContactContract, oldStoreContactContract);
                            }
                        }
                    }
                    dbHelper.delete(LocalizationLogContract.LocalizationLogEntry.TABLE_NAME,
                            LocalizationLogContract.LocalizationLogEntry.STATUS + "=?",
                            new String[]{Constants.STATUS_NEW});

                    dbHelper.delete(MediaFilesContract.MediaFilesEntry.TABLE_NAME, MediaFilesContract.MediaFilesEntry.STATUS + "=?",
                            new String[]{Constants.STATUS_NEW});

                    dbHelper.delete(VisitStoreContract.VisitStoreEntry.TABLE_NAME, VisitStoreContract.VisitStoreEntry.STATUS + "=? OR " +
                                    VisitStoreContract.VisitStoreEntry.STATUS +"=?",
                            new String[]{Constants.STATUS_CLOSE,Constants.STATUS_NEW});

                }catch (JSONException e){
                    Log.d("error getResponse",e.getMessage());
                } finally {
                    dbHelper.close();
                }
            }
        };
    }

    private Response.ErrorListener getErrorResponse(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (currentSesion != null) {
                    VisitStore visitStore = new VisitStore(activity);
                    VisitStoreContract visitStoreContract = visitStore.getRegister(VisitStoreContract.VisitStoreEntry.STATUS +" =?",
                            new String[]{Constants.STATUS_CLOSE});
                    visitStoreContract.setStatus(Constants.STATUS_NEW);
                    visitStore.update(VisitStoreContract.VisitStoreEntry.TABLE_NAME, visitStoreContract.toContentValues(),
                            VisitStoreContract.VisitStoreEntry.ID, visitStoreContract.getId());
                }
                Log.d("getErrorResponse",error!=null?(error.getMessage()!=null?error.getMessage():""):"");
            }
        };
    }
}
