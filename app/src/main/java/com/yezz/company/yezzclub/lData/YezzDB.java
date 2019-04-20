package com.yezz.company.yezzclub.lData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;
import com.yezz.company.yezzclub.lData.entities.contracts.BrandPhoneContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CategoryContract;
import com.yezz.company.yezzclub.lData.entities.contracts.ChainContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CityContract;
import com.yezz.company.yezzclub.lData.entities.contracts.LocalizationLogContract;
import com.yezz.company.yezzclub.lData.entities.contracts.MediaFilesContract;
import com.yezz.company.yezzclub.lData.entities.contracts.PhoneContract;
import com.yezz.company.yezzclub.lData.entities.contracts.PhoneModelContract;
import com.yezz.company.yezzclub.lData.entities.contracts.RoutesContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StateContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreContactContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreTypeContract;
import com.yezz.company.yezzclub.lData.entities.contracts.TownshipsContract;
import com.yezz.company.yezzclub.lData.entities.contracts.VisitStoreContract;

/**
 * Created by ramon_000 on 24-08-2016.
 */
public class YezzDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="yezzclublite.db";
    private static final int DATABASE_VERSION=2;

    public YezzDB(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(StoreTypeContract.StoreTypeEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(ChainContract.StoreChainEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(BranchContract.BranchEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(PhoneContract.PhoneEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(BrandPhoneContract.BrandEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(PhoneModelContract.PhoneModelEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(RoutesContract.RoutesEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(StoreContactContract.StoreContactEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(LocalizationLogContract.LocalizationLogEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(CategoryContract.CategoryEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(MediaFilesContract.MediaFilesEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(StateContract.StateEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(CityContract.CityEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(TownshipsContract.TownshipsEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(VisitStoreContract.VisitStoreEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(BranchContract.BranchEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(StoreContactContract.StoreContactEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(LocalizationLogContract.LocalizationLogEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(CategoryContract.CategoryEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(MediaFilesContract.MediaFilesEntry.CREATE_TABLE);
    }

    public void deleteThisDatabase(Context context){
        context.deleteDatabase(this.DATABASE_NAME);
    }

    public long save(StoreTypeContract storeType){
        return this.save(StoreTypeContract.StoreTypeEntry.TABLE_NAME,storeType.toContentValues());
    }

    public long save(ChainContract storeChain){
        return this.save(ChainContract.StoreChainEntry.TABLE_NAME,storeChain.toContentValues());
    }

    public long save(BranchContract branch){
        return this.save(BranchContract.BranchEntry.TABLE_NAME,branch.toContentValues());
    }

    public long save(PhoneContract phone){
        return this.save(PhoneContract.PhoneEntry.TABLE_NAME,phone.toContentValues());
    }

    public long save(RoutesContract route){
        return this.save(RoutesContract.RoutesEntry.TABLE_NAME,route.toContentValues());
    }

    public long save(StoreContactContract contact){
        return this.save(StoreContactContract.StoreContactEntry.TABLE_NAME,contact.toContentValues());
    }

    public long save(LocalizationLogContract localization){
        return this.save(LocalizationLogContract.LocalizationLogEntry.TABLE_NAME,localization.toContentValues());
    }

    public long save(CategoryContract category){
        return this.save(CategoryContract.CategoryEntry.TABLE_NAME,category.toContentValues());
    }

    public long save(MediaFilesContract mediaFile) {
        return this.save(MediaFilesContract.MediaFilesEntry.TABLE_NAME, mediaFile.toContentValues());
    }

    public long save(TownshipsContract townshipsContract) {
        return this.save(TownshipsContract.TownshipsEntry.TABLE_NAME, townshipsContract.toContentValues());
    }

    public long save(StateContract state) {
        return this.save(StateContract.StateEntry.TABLE_NAME, state.toContentValues());
    }

    public long save(CityContract city) {
        return this.save(CityContract.CityEntry.TABLE_NAME, city.toContentValues());
    }

    public long save(PhoneModelContract model) {
        return this.save(PhoneModelContract.PhoneModelEntry.TABLE_NAME, model.toContentValues());
    }

    public long save(BrandPhoneContract brand) {
        return this.save(BrandPhoneContract.BrandEntry.TABLE_NAME, brand.toContentValues());
    }

    public long save(String table, ContentValues values){
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        return sqLiteDatabase.insert(table,null,values);
    }


    public long update(String table, ContentValues values){
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        return sqLiteDatabase.update(table,values,null,null);
    }

    public long update(String table, ContentValues values,String field,String value){
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        return sqLiteDatabase.update(table,values,field +" = ?",new String[]{value});
    }

    public long update(String table, ContentValues values,String filter, String[] filterValues){
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        return sqLiteDatabase.update(table,values,filter,filterValues);
    }

    public int delete(String table, String where, String[] whereArguments){
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        return sqLiteDatabase.delete(table,where,whereArguments);
    }

    public SQLiteDatabase query(){
        return getReadableDatabase();
    }

    public Cursor getRawCursor(@NonNull String sql){
        return getReadableDatabase().rawQuery(sql,null);
    }

    public Cursor getRawCursor(@NonNull String sql,String[] filterValues){
        return getReadableDatabase().rawQuery(sql,filterValues);
    }

    public Cursor getCursor(@NonNull String table){
        return getReadableDatabase().query(table,null,null,null,null,null,null,null);
    }

    public Cursor getCursor(@NonNull String table,@Nullable String[] select){
        return getReadableDatabase().query(table,select,null,null,null,null,null,null);
    }

    public Cursor getCursor(@NonNull String table,@Nullable String[] select,@Nullable String filter,@Nullable String[] filterValues){
        return getReadableDatabase().query(table,select,filter,filterValues,null,null,null,null);
    }

    public Cursor getCursor(@NonNull String table,@Nullable String[] select,@Nullable String filter,@Nullable String[] filterValues,@Nullable String groupBy,@Nullable String having){
        return getReadableDatabase().query(table,select,filter,filterValues,groupBy,having,null,null);
    }

    public Cursor getCursor(@NonNull String table,@Nullable String[] select,@Nullable String filter,@Nullable String[] filterValues,@Nullable String groupBy,@Nullable String having,@Nullable String orderBy){
        return getReadableDatabase().query(table,select,filter,filterValues,groupBy,having,orderBy,null);
    }

    public Cursor getCursor(@NonNull String table,@Nullable String[] select,@Nullable String filter,@Nullable String[] filterValues,@Nullable String groupBy,@Nullable String having,@Nullable String orderBy,@Nullable String limit){
        return getReadableDatabase().query(table,select,filter,filterValues,groupBy,having,orderBy,limit);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
