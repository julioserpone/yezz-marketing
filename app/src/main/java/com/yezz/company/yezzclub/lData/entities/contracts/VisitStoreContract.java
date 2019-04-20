package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by arkanhell on 10/09/17.
 */

public class VisitStoreContract {
    public String   id,
                    userId,
                    branchId,
                    comment,
                    start,
                    date,
                    status;

    public VisitStoreContract(@Nullable String id, String userId, String branchId, String comment, String start, String date, String status) {
        initId(id);
        this.userId = userId;
        this.branchId = branchId;
        this.comment = comment;
        this.start = start;
        this.date = date;
        this.status = status;
    }

    public VisitStoreContract() {
        initId(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        values.put(VisitStoreEntry.ID,this.id);
        values.put(VisitStoreEntry.USERID,this.userId);
        values.put(VisitStoreEntry.START,this.start);
        values.put(VisitStoreEntry.BRANCHID,this.branchId);
        values.put(VisitStoreEntry.COMMENT, this.comment);
        values.put(VisitStoreEntry.STATUS, this.status);
        values.put(VisitStoreEntry.DATE, this.date);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if (this.id!=null){
            values.put(VisitStoreEntry.ID,this.id);
        }
        if (this.branchId!=null){
            values.put(VisitStoreEntry.BRANCHID,this.branchId);
        }
        if (this.userId!=null){
            values.put(VisitStoreEntry.USERID,this.userId);
        }
        if (this.date!=null){
            values.put(VisitStoreEntry.DATE,this.date);
        }
        if (this.start != null){
            values.put(VisitStoreEntry.START,this.start);
        }

        if (this.status!=null){
            values.put(VisitStoreEntry.STATUS,this.status);
        }

        if (this.comment!=null){
            values.put(VisitStoreEntry.COMMENT,this.comment);
        }
        return values;
    }

    public String getFilterArguments(){
        String arguments="";
        if (this.id!=null){
            arguments=arguments.concat(VisitStoreEntry.ID+" =?");
        }
        if (this.start!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(VisitStoreEntry.START+" =?");
        }
        if (this.date!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(VisitStoreEntry.DATE+" =?");
        }
        if (this.userId!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(VisitStoreEntry.USERID+" =?");
        }
        if (this.branchId!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(VisitStoreEntry.BRANCHID+" =?");
        }
        if (this.status!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(VisitStoreEntry.STATUS+" =?");
        }
        if (this.comment != null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(VisitStoreEntry.COMMENT+" = ?");
        }
        return arguments;
    }

    public String[] getFilterArgumentsValues(){

        List<String> values=new ArrayList<String>();

        if (this.id != null){
            values.add(this.id);
        }
        if (this.userId!=null){
            values.add(this.userId);
        }
        if (this.branchId != null){
            values.add(this.branchId);
        }
        if (this.start != null){
            values.add(this.start);
        }
        if (this.date != null) {
            values.add(this.date);
        }
        if (this.status != null){
            values.add(this.status);
        }
        if (this.comment != null){
            values.add(this.comment);
        }
        return values.toArray(new String[values.size()]);
    }

    public static abstract class VisitStoreEntry implements BaseColumns {
        public static final String TABLE_NAME="visitStore";

        public static final String  ID="id",
                USERID = "userId",
                BRANCHID = "branchId",
                COMMENT = "comment",
                START = "start",
                DATE = "date",
                STATUS = "status";

        public static final String CREATE_TABLE="CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                USERID + " TEXT NOT NULL," +
                BRANCHID + " TEXT NOT NULL," +
                COMMENT + " TEXT NULL," +
                START + " TEXT NOT NULL," +
                DATE + " TEXT NULL," +
                STATUS + " TEXT NOT NULL," +
                "UNIQUE(" + ID + "))";
    }
}
