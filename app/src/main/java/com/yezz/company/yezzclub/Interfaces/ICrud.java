package com.yezz.company.yezzclub.Interfaces;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Ramon_000 on 04-09-2016.
 */
public interface ICrud<T> {
    public void create(T entity);
    public void update(T entity,T filter);
    public void update(T entity,String where);
    public void update(T entity,String filter,String[] filterValues);
    public void delete(int id);
    public void delete(String id);
    public T getRegister(T entity);
    public T getRegister(@Nullable String filter,@Nullable String[] filterValues);
    public List<T> getRegisters(T entity);
    public List<T> getRegisters(@Nullable String filter,@Nullable String[] filterValues);
    public List<T> getAllRegister();

    //only for use generic spinnerAdapter
    public String getName();


   /* public T find(int id);
    public T find(String id);
    public T find(T entidad);*/

}
