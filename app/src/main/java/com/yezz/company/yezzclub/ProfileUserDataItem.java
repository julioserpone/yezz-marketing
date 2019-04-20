package com.yezz.company.yezzclub;

/**
 * Created by ramon_000 on 19-08-2016.
 */
public class ProfileUserDataItem {
    private int meta;
    private String data;

    public ProfileUserDataItem(int meta, String data){
        this.meta=meta;
        this.data=data;
    }

    public int getMeta() {
        return meta;
    }

    public String getData() {
        return data;
    }
}
