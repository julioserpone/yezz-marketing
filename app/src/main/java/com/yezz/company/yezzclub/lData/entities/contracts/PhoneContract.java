package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.IFields;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by RAMON_000 on 17-09-2016.
 */
public class PhoneContract implements IFields {
    private String  id,
                    brand,
                    model,
                    exhibition_media,
                    exhibition_media_comment,
                    store,
                    stock,
                    exhibition,
                    sales,
                    parent_id,
                    sale_price,
                    purchase_price,
                    user,
                    status,branch_id;

    public PhoneContract(@Nullable String id, String brand, String model, String exhibition_media,
                         String exhibition_media_comment, String store, String stock, String exhibition,
                         String sales, @Nullable String parent_id, String sale_price, String purchase_price,
                         String status, String user)
    {
        initId(id);
        this.brand = brand;
        this.model = model;
        this.exhibition_media = exhibition_media;
        this.exhibition_media_comment = exhibition_media_comment;
        this.store = store;
        this.stock = stock;
        this.exhibition = exhibition;
        this.sales = sales;
        this.parent_id = parent_id;
        this.status = status;
        this.user = user;
        this.sale_price = sale_price;
        this.purchase_price = purchase_price;
        this.branch_id = "";
    }

    public PhoneContract(@Nullable String id, String brand, String model, String exhibition_media,
                         String exhibition_media_comment, String store, String stock, String exhibition,
                         String sales, String parent_id, String sale_price, String purchase_price,
                         String user, String status, String branchId_id) {
        initId(id);
        this.brand = brand;
        this.model = model;
        this.exhibition_media = exhibition_media;
        this.exhibition_media_comment = exhibition_media_comment;
        this.store = store;
        this.stock = stock;
        this.exhibition = exhibition;
        this.sales = sales;
        this.parent_id = parent_id;
        this.sale_price = sale_price;
        this.purchase_price = purchase_price;
        this.user = user;
        this.status = status;
        this.branch_id = branchId_id;
    }

    public PhoneContract()
    {
        initId(null);
    }

    private void initId(@Nullable String id) {
        if (id==null){
            this.id= UUID.randomUUID().toString();
        }else{
            this.id=id;
        }
    }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();

        values.put(PhoneEntry.ID,this.id);
        values.put(PhoneEntry.BRAND,this.brand);
        values.put(PhoneEntry.MODEL,this.model);
        values.put(PhoneEntry.EXHIBITION_MEDIA,this.exhibition_media);
        values.put(PhoneEntry.EXHIBITION_MEDIA_COMMET,this.exhibition_media_comment);
        values.put(PhoneEntry.STOCK,this.stock);
        values.put(PhoneEntry.EXHIBITION,this.exhibition);
        values.put(PhoneEntry.SALES,this.sales);
        values.put(PhoneEntry.PARENT_ID,this.parent_id);
        values.put(PhoneEntry.PURCHASE_PRICE,this.purchase_price);
        values.put(PhoneEntry.SALE_PRICE,this.sale_price);
        values.put(PhoneEntry.STATUS,this.status);
        values.put(PhoneEntry.STORE,this.store);
        values.put(PhoneEntry.USER,this.user);
        values.put(PhoneEntry.BRANCHID,this.branch_id);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();

        if (this.id!=null){
            values.put(PhoneEntry.ID,this.id);
        }
        if (this.brand!=null){
            values.put(PhoneEntry.BRAND,this.brand);
        }
        if (this.model!=null){
            values.put(PhoneEntry.MODEL,this.model);
        }
        if (this.exhibition_media!=null){
            values.put(PhoneEntry.EXHIBITION_MEDIA,this.exhibition_media);
        }
        if (this.exhibition_media_comment!=null){
            values.put(PhoneEntry.EXHIBITION_MEDIA_COMMET,this.exhibition_media_comment);
        }
        if (this.stock!=null){
            values.put(PhoneEntry.STOCK,this.stock);
        }
        if (this.exhibition!=null){
            values.put(PhoneEntry.EXHIBITION,this.exhibition);
        }
        if (this.sales!=null){
            values.put(PhoneEntry.SALES,this.sales);
        }
        if (this.parent_id!=null){
            values.put(PhoneEntry.PARENT_ID,this.parent_id);
        }
        if (this.sale_price!=null){
            values.put(PhoneEntry.SALE_PRICE,this.sale_price);
        }
        if (this.purchase_price!=null){
            values.put(PhoneEntry.PURCHASE_PRICE,this.purchase_price);
        }
        if (this.status!=null){
            values.put(PhoneEntry.STATUS,this.status);
        }
        if (this.store!=null){
            values.put(PhoneEntry.STORE,this.store);
        }
        if (this.user!=null){
            values.put(PhoneEntry.USER,this.user);
        }
        if (this.branch_id!=null){
            values.put(PhoneEntry.BRANCHID,this.branch_id);
        }
        return values;
    }

    public String getFilterArguments(){
        String arguments="";

        if (this.id!=null){
            arguments=arguments.concat(PhoneEntry.ID+" = ?");
        }
        if (this.brand!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.BRAND+" = ?");
        }
        if (this.model!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.MODEL+" = ?");
        }
        if (this.exhibition_media!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.EXHIBITION_MEDIA+" = ?");
        }
        if (this.exhibition_media_comment!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.EXHIBITION_MEDIA_COMMET+" = ?");
        }
        if (this.stock!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.STOCK+" = ?");
        }
        if (this.exhibition!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.EXHIBITION+" = ?");
        }
        if (this.sales!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.SALES+" = ?");
        }
        if (this.parent_id!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.PARENT_ID+" = ?");
        }
        if (this.purchase_price!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.PURCHASE_PRICE+" = ?");
        }
        if (this.sale_price!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.SALE_PRICE+" = ?");
        }
        if (this.status!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.STATUS+" = ?");
        }
        if (this.store!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.STORE+" = ?");
        }
        if (this.user!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.USER+" = ?");
        }
        if (this.branch_id!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneEntry.BRANCHID+" = ?");
        }

        return arguments;
    }

    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();

        if (this.id!=null){
            values.add(this.id);
        }
        if (this.brand!=null){
            values.add(this.brand);
        }
        if (this.model!=null){
            values.add(this.model);
        }
        if (this.exhibition_media!=null){
            values.add(this.exhibition_media);
        }
        if (this.exhibition_media_comment!=null){
            values.add(this.exhibition_media_comment);
        }
        if (this.stock!=null){
            values.add(this.stock);
        }
        if (this.exhibition!=null){
            values.add(this.exhibition);
        }
        if (this.sales!=null){
            values.add(this.sales);
        }
        if (this.parent_id!=null){
            values.add(this.parent_id);
        }
        if (this.purchase_price!=null){
            values.add(this.purchase_price);
        }
        if (this.sale_price!=null){
            values.add(this.sale_price);
        }
        if (this.status!=null){
            values.add(this.status);
        }
        if (this.store!=null){
            values.add(this.store);
        }
        if (this.user!=null){
            values.add(this.user);
        }

        if (this.branch_id!=null){
            values.add(this.branch_id);
        }
        return values.toArray(new String[values.size()]);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getExhibition_media() {
        return exhibition_media;
    }

    public void setExhibition_media(String exhibition_media) {
        this.exhibition_media = exhibition_media;
    }

    public String getExhibition_media_comment() {
        return exhibition_media_comment;
    }

    public void setExhibition_media_comment(String exhibition_media_comment) {
        this.exhibition_media_comment = exhibition_media_comment;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getExhibition() {
        return exhibition;
    }

    public void setExhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getFieldName() {
        return this.model + " " + this.brand;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    @Override
    public String getFieldId() {
        return this.id;
    }

    public static abstract class PhoneEntry implements BaseColumns{
        public static final String TABLE_NAME = "phones";

        public static final String  ID= "id";
        public static final String  BRAND= "brand";
        public static final String  MODEL= "model";
        public static final String  EXHIBITION_MEDIA= "exhibition_media";
        public static final String  EXHIBITION_MEDIA_COMMET = "exhibition_media_comment";
        public static final String  PURCHASE_PRICE= "purchase_price";

        public static final String  STOCK= "stock";
        public static final String  EXHIBITION = "exhibition";
        public static final String  SALES= "sales";
        public static final String  PARENT_ID= "parent_id";

        public static final String  SALE_PRICE= "sale_price";

        public static final String  STATUS= "status";
        public static final String  STORE= "store";
        public static final String  USER= "user", BRANCHID = "branchId_id";

        public static final String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                BRAND + " TEXT NOT NULL," +
                MODEL + " TEXT NOT NULL," +
                EXHIBITION_MEDIA + " TEXT NULL," +
                EXHIBITION_MEDIA_COMMET + " TEXT NULL," +
                STOCK + " TEXT NOT NULL," +
                EXHIBITION + " TEXT NOT NULL," +
                SALES + " TEXT NOT NULL," +
                PARENT_ID + " TEXT NULL," +
                PURCHASE_PRICE + " TEXT NULL," +
                SALE_PRICE + " TEXT NOT NULL," +
                STATUS + " TEXT NOT NULL," +
                STORE + " TEXT  NULL," +
                USER + " TEXT  NULL," +
                BRANCHID + " TEXT NOT NULL," +
                "UNIQUE("+ID+"))";
    }
}
