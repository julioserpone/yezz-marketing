package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by SairioA on 10/05/17.
 */

public class MediaFilesContract {

    protected String id,
                    sourceId,
                    userId,
                    code,
                    path,
                    comment,
                    type,
                    origin,
                    status;

    private void initId(@Nullable String id) {
        if (id==null){
            this.id= UUID.randomUUID().toString();
        }else{
            this.id=id;
        }
    }

    public MediaFilesContract(@Nullable String id, String sourceId, String userId, String code, String path,
                              String comment, String type, String origin, String status) {
        initId(id);
        this.sourceId = sourceId;
        this.userId = userId;
        this.code = code;
        this.path = path;
        this.comment = comment;
        this.type = type;
        this.origin = origin;
        this.status = status;
    }

    public MediaFilesContract() { initId(null);}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getSourceId() {return sourceId;}

    public void setSourceId(String sourceId) {this.sourceId = sourceId;}

    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.userId = userId;}

    public String getCode() {return code;}

    public void setCode(String code) {this.code = code;}

    public String getPath() {return path;}

    public void setPath(String path) {this.path = path;}

    public String getComment() {return comment;}

    public void setComment(String comment) {this.comment = comment;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getOrigin() {return origin;}

    public void setOrigin(String origin) {this.origin = origin;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(MediaFilesContract.MediaFilesEntry.ID,this.id);
        values.put(MediaFilesContract.MediaFilesEntry.SOURCE_ID,this.sourceId);
        values.put(MediaFilesContract.MediaFilesEntry.USER_ID,this.userId);
        values.put(MediaFilesContract.MediaFilesEntry.CODE,this.code);
        values.put(MediaFilesContract.MediaFilesEntry.PATH,this.path);
        values.put(MediaFilesContract.MediaFilesEntry.COMMENT,this.comment);
        values.put(MediaFilesContract.MediaFilesEntry.TYPE,this.type);
        values.put(MediaFilesContract.MediaFilesEntry.ORIGIN,this.origin);
        values.put(MediaFilesContract.MediaFilesEntry.STATUS,this.status);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if(this.id!=null){
            values.put(MediaFilesEntry.ID,this.id);
        }
        if(this.sourceId!=null){
            values.put(MediaFilesEntry.SOURCE_ID,this.sourceId);
        }
        if(this.userId!=null){
            values.put(MediaFilesEntry.USER_ID,this.userId);
        }
        if(this.code!=null){
            values.put(MediaFilesEntry.CODE,this.code);
        }
        if(this.comment!=null){
            values.put(MediaFilesEntry.COMMENT,this.comment);
        }
        if(this.path!=null){
            values.put(MediaFilesEntry.PATH,this.path);
        }
        if(this.type!=null) {
            values.put(MediaFilesEntry.TYPE,this.type);
        }
        if(this.origin!=null) {
            values.put(MediaFilesEntry.ORIGIN,this.origin);
        }
        if(this.status!=null) {
            values.put(MediaFilesEntry.STATUS,this.status);
        }
        return values;
    }

    public String getFilterArguments(){
        String arguments="";
        if(this.id != null){
            arguments=arguments.concat(MediaFilesEntry.ID + " = ?");
        }
        if(this.sourceId != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(MediaFilesEntry.SOURCE_ID + " = ?");
        }
        if(this.userId != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(MediaFilesEntry.USER_ID + " = ?");
        }
        if(this.code != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(MediaFilesEntry.CODE + " = ?");
        }
        if(this.path != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(MediaFilesEntry.PATH + " = ?");
        }
        if(this.comment != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(MediaFilesEntry.COMMENT + " = ?");
        }
        if(this.type != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(MediaFilesEntry.TYPE + " = ?");
        }
        if(this.origin != null){
            arguments=arguments.concat(arguments!= "" ? " AND " : "");
            arguments=arguments.concat(MediaFilesEntry.ORIGIN + " = ?");
        }
        if(this.status != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(MediaFilesEntry.STATUS + " = ?");
        }
        return arguments;
    }

    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();
        if(this.id != null){
            values.add(this.id);
        }
        if(this.sourceId != null){
            values.add(this.sourceId);
        }
        if(this.userId != null){
            values.add(this.userId);
        }
        if(this.code != null){
            values.add(this.code);
        }
        if(this.path != null){
            values.add(this.path);
        }
        if(this.comment != null){
            values.add(this.comment);
        }
        if(this.type != null){
            values.add(this.type);
        }
        if(this.origin != null){
            values.add(this.origin);
        }
        if(this.status != null){
            values.add(this.status);
        }
        return values.toArray(new String[values.size()]);
    }

    public static abstract class MediaFilesEntry implements BaseColumns {

        public static final String TABLE_NAME = "media_files",
                                    ID = "id",
                                    SOURCE_ID = "sourceId",
                                    USER_ID = "userId",
                                    CODE = "code",
                                    PATH = "path",
                                    COMMENT = "comment",
                                    TYPE = "type",
                                    ORIGIN = "origin",
                                    STATUS = "status";

        public static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                SOURCE_ID + " TEXT NOT NULL," +
                USER_ID + " TEXT NOT NULL," +
                CODE + " TEXT NOT NULL," +
                PATH + " TEXT NULL," +
                COMMENT + " TEXT NULL," +
                TYPE + " TEXT NOT NULL," +
                ORIGIN + " TEXT NOT NULL," +
                STATUS + " TEXT NOT NULL," +
                "UNIQUE(" + ID + "))";
    }
}
