package com.mobile.lance.iconnect.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 7/1/16.
 */
public class DBRequestManager {

    private static final String COLUMN_REQUEST_ID = "request_id";
    private static final String COLUMN_FROM_ID = "from_id";
    private static final String COLUMN_TO_ID = "to_id";
    private static final String COLUMN_ACCEPTED_YN = "accepted_yn";
    private static final String COLUMN_REQUEST = "request";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_CREATED_AT_REQUESTS = "request_created_at";
    private static final String COLUMN_UPDATED_AT_REQUESTS = "request_updated_at";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public DBRequestManager(Context context) {
        dbHelper = new MySQLiteHelper(context);
        Log.d("", "Request db created");
    }

    // input data to table
    public void insertFriendsRequestInfo(TableRequestItem item)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FROM_ID, item.getFrom_id());
        values.put(COLUMN_TO_ID, item.getTo_id());
        values.put(COLUMN_ACCEPTED_YN, item.getAccepted_yn());
        values.put(COLUMN_REQUEST, item.getRequest());
        values.put(COLUMN_STATUS, item.getStatus());
        values.put(COLUMN_CREATED_AT_REQUESTS, item.getRequest_created_at());
        values.put(COLUMN_UPDATED_AT_REQUESTS, item.getRequest_updated_at());

        int requestid= (int)database.insert(MySQLiteHelper.TABLE_REQUESTS, null,
                values);
        Log.d("id= ", requestid + "");
    }

    public void updateFriendsRequestInfo(long request_id, TableRequestItem item){
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, item.getStatus());
        values.put(COLUMN_ACCEPTED_YN, item.getAccepted_yn());
        values.put(COLUMN_UPDATED_AT_REQUESTS, item.getRequest_updated_at());
        database.update(MySQLiteHelper.TABLE_REQUESTS, values, COLUMN_REQUEST_ID + "=" + request_id, null);
    }

    // friend search
    public Boolean friend_yn(String requeststring){
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_REQUESTS +" WHERE " + COLUMN_REQUEST + "='" + requeststring + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean isWaitingOfRequest(long friend_id, long my_user_id)
    {
        boolean isWaiting = false;
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_REQUESTS +" WHERE " + COLUMN_TO_ID + "='" + friend_id + "' AND " +
                            COLUMN_FROM_ID + "='" + my_user_id + "' AND " + COLUMN_STATUS + "='Waiting'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        long request_id = -2;
        while (!cursor.isAfterLast()) {
            isWaiting = true;
            cursor.moveToNext();
        }

        return isWaiting;
    }

    public List<Long> getFriendRequestsToid(long my_user_id)
    {
        List<Long> freindslist =new ArrayList<>();
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_REQUESTS +" WHERE " + COLUMN_TO_ID + "='" + my_user_id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        long request_id = -2;
        while (!cursor.isAfterLast()) {
            if(cursor.getString(5).equals("Waiting"))
            {
                request_id = cursor.getLong(0);
                freindslist.add(request_id);
            }
            cursor.moveToNext();
        }

        return freindslist;
    }


    public TableRequestItem getRequestsInfo(long request_id){

        TableRequestItem requestItem = null;
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_REQUESTS +" WHERE " + COLUMN_REQUEST_ID + "='" + request_id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            requestItem = cursorToUserItem(cursor);
        }
        cursor.close();
        return requestItem;
    }

    private TableRequestItem cursorToUserItem(Cursor cursor) {
        TableRequestItem item = new TableRequestItem();
        item.setFrom_id(cursor.getLong(1));
        item.setTo_id(cursor.getLong(2));
        item.setStatus(cursor.getString(5));
        item.setRequest(cursor.getString(4));
        item.setRequest_id(cursor.getLong(0));
//        int test = cursor.getInt(1);
//        Log.d("", "" + test);
        return item;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable() {
        database.delete(MySQLiteHelper.TABLE_REQUESTS, null, null);
    }
}
