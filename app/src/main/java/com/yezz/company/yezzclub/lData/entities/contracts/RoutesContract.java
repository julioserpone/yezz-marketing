package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Created by ramon_000 on 20-10-2016.
 */

public class RoutesContract {
    private String store_id,
                    start,
                    end,
                    route_date,
                    in_route,
                    active,
                    send,
                    latitude,
                    longitude;

    public RoutesContract(String store_id, String start, String end, String route_date, String in_route, String active, String send, String latitude, String longitude) {
        this.store_id = store_id;
        this.start = start;
        this.end = end;
        this.route_date = route_date;
        this.in_route = in_route;
        this.active = active;
        this.send = send;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public RoutesContract() {
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getRoute_date() {
        return route_date;
    }

    public void setRoute_date(String route_date) {
        this.route_date = route_date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getIn_route() {
        return in_route;
    }

    public void setIn_route(String in_route) {
        this.in_route = in_route;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(RoutesEntry.ID,this.store_id);
        values.put(RoutesEntry.ROUTE_DATE,this.route_date);
        values.put(RoutesEntry.START,this.start);
        values.put(RoutesEntry.END,this.end);
        values.put(RoutesEntry.IN_ROUTE,this.in_route);
        values.put(RoutesEntry.ACTIVE,this.active);
        values.put(RoutesEntry.SEND,this.send);
        values.put(RoutesEntry.LATITUDE,this.latitude);
        values.put(RoutesEntry.LONGITUDE,this.longitude);
        return values;
    }

    public static abstract class RoutesEntry implements BaseColumns{

        public static final String TABLE_NAME="routes";

        public static final String ID="store_id";
        public static final String ROUTE_DATE = "route_date";
        public static final String START="start";
        public static final String END="end";
        public static final String IN_ROUTE="in_route";
        public static final String ACTIVE="active";
        public static final String SEND="send";
        public static final String LATITUDE="latitude";
        public static final String LONGITUDE="longitude";

        public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+"(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                ROUTE_DATE + " TEXT NOT NULL," +
                START + " TEXT NULL," +
                END + " TEXT NULL," +
                IN_ROUTE + " TEXT NOT NULL," +
                ACTIVE + " TEXT NOT NULL," +
                SEND + " TEXT NOT NULL," +
                LATITUDE + " TEXT NULL," +
                LONGITUDE + " TEXT NULL," +
                "UNIQUE("+ID+") )";
    }
}
