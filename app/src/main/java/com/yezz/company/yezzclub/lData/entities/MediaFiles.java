package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.MediaFilesContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SairioA on 10/05/17.
 */

public class MediaFiles implements ICrud<MediaFilesContract> {

    private YezzDB db;
    private Context context;
    private long last_id;

    public MediaFiles(Context context) {
        this.context = context;
    }

    public long getLast_id() {
        return last_id;
    }

    @Override
    public void create(MediaFilesContract entity) {
        this.db = new YezzDB(context);
        entity.setStatus("new");
        long result = this.db.save(MediaFilesContract.MediaFilesEntry.TABLE_NAME,entity.toContentValues());
        this.db.close();
        this.last_id = result;
    }

    @Override
    public void update(MediaFilesContract entity, MediaFilesContract filter) {

    }

    @Override
    public void update(MediaFilesContract entity, String where) {

    }

    @Override
    public void update(MediaFilesContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public MediaFilesContract getRegister(MediaFilesContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public MediaFilesContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(MediaFilesContract.MediaFilesEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                MediaFilesContract localizationLogContract = this.getMediaFilesContract(cursor);
                cursor.close();
                this.db.close();
                return localizationLogContract;
            }
        }catch (Exception ex){

        }
        finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return null;
    }

    @Override
    public List<MediaFilesContract> getRegisters(MediaFilesContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<MediaFilesContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<MediaFilesContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(MediaFilesContract.MediaFilesEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                list.add(this.getMediaFilesContract(cursor));
            }
        }catch (Exception ex){

        }
        finally{
            if(cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return list;
    }

    @Override
    public List<MediaFilesContract> getAllRegister() {
        return null;
    }

    private MediaFilesContract getMediaFilesContract(Cursor cursor){
        MediaFilesContract contact  = new MediaFilesContract();
        contact.setId(cursor.getString(cursor.getColumnIndex(MediaFilesContract.MediaFilesEntry.ID)));
        contact.setSourceId(cursor.getString(cursor.getColumnIndex(MediaFilesContract.MediaFilesEntry.SOURCE_ID)));
        contact.setUserId(cursor.getString(cursor.getColumnIndex(MediaFilesContract.MediaFilesEntry.USER_ID)));
        contact.setCode(cursor.getString(cursor.getColumnIndex(MediaFilesContract.MediaFilesEntry.CODE)));
        contact.setPath(cursor.getString(cursor.getColumnIndex(MediaFilesContract.MediaFilesEntry.PATH)));
        contact.setType(cursor.getString(cursor.getColumnIndex(MediaFilesContract.MediaFilesEntry.TYPE)));
        contact.setOrigin(cursor.getString(cursor.getColumnIndex(MediaFilesContract.MediaFilesEntry.ORIGIN)));
        contact.setComment(cursor.getString(cursor.getColumnIndex(MediaFilesContract.MediaFilesEntry.COMMENT)));
        return contact;
    }

    @Override
    public String getName() {
        return null;
    }
}
