package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.CategoryContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SairioA on 23/04/17.
 */

public class Category implements ICrud<CategoryContract>{
    private YezzDB db;
    private Context context;

    public Category(Context context) {
        this.context = context;
    }

    @Override
    public void create(CategoryContract entity) {

    }

    @Override
    public void update(CategoryContract entity, CategoryContract filter) {

    }

    @Override
    public void update(CategoryContract entity, String where) {

    }

    @Override
    public void update(CategoryContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<CategoryContract> getAllRegister() {
        return this.getRegisters(null,null);
    }

    @Override
    public String getName() {
        return null;
    }

    public CategoryContract getRegister(CategoryContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    public CategoryContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(CategoryContract.CategoryEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                CategoryContract categoryContract =this.getCategoryContract(cursor);
                cursor.close();
                this.db.close();
                return categoryContract;
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

    public List<CategoryContract> getRegisters(CategoryContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    public List<CategoryContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<CategoryContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(CategoryContract.CategoryEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                list.add(this.getCategoryContract(cursor));
            }
        }catch (Exception ex){

        }
        finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return list;
    }

    private CategoryContract getCategoryContract(Cursor cursor){
        CategoryContract category  = new CategoryContract();
        category.setId(cursor.getString(cursor.getColumnIndex(CategoryContract.CategoryEntry.ID)));
        category.setName(cursor.getString(cursor.getColumnIndex(CategoryContract.CategoryEntry.NAME)));
        category.setDescription(cursor.getString(cursor.getColumnIndex(CategoryContract.CategoryEntry.DESCRIPTION)));
        return category;
    }
}
