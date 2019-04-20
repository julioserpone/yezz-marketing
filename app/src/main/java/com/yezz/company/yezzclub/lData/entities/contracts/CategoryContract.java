package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.IFields;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by SairioA on 23/04/17.
 */

public class CategoryContract implements IFields {
    public String id,
                  name,
                  description;

    private void initId(@Nullable String id){
        if (id==null){
            this.id= UUID.randomUUID().toString();
        }else{
            this.id=id;
        }
    }
    public CategoryContract(@Nullable String id, String name, String description) {
        initId(id);
        this.name = name;
        this.description = description;
    }

    public CategoryContract() {
        initId(null);
    }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(CategoryEntry.ID,this.id);
        values.put(CategoryEntry.DESCRIPTION,this.description);
        values.put(CategoryEntry.NAME,this.name);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if (this.id!=null){
            values.put(CategoryEntry.ID,this.id);
        }
        if (this.description!=null){
            values.put(CategoryEntry.DESCRIPTION,this.description);
        }
        if (this.name!=null){
            values.put(CategoryEntry.NAME,this.name);
        }
        return values;
    }
    public String getFilterArguments(){
        String arguments="";
        if (this.id!=null){
            arguments=arguments.concat(CategoryEntry.ID+ " =?");
        }
        if (this.description!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(CategoryEntry.DESCRIPTION+ " =?");
        }
        if (this.name!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(CategoryEntry.NAME+ " =?");
        }
        return arguments;
    }
    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();
        if (this.id!=null){
            values.add(this.id);
        }
        if (this.description!=null){
            values.add(this.description);
        }
        if (this.name!=null){
            values.add(this.name);
        }
        return values.toArray(new String[values.size()]);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getFieldName() {
        return this.name;
    }

    @Override
    public String getFieldId() {
        return this.id;
    }

    public static abstract class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "store_category";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";

        public static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"(" +
                _ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID+ " TEXT NOT NULL," +
                NAME+ " TEXT NOT NULL," +
                DESCRIPTION+ " TEXT NOT NULL," +
                "UNIQUE("+ID+")"+
                ")";
    }
}
