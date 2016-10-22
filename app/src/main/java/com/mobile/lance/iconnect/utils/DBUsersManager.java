package com.mobile.lance.iconnect.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by administrator on 6/30/16.
 */
public class DBUsersManager {

    private static final String COLUMN_USERID = "user_id";
    private static final String COLUMN_FIRSTNAME= "firs_tname";
    private static final String COLUMN_LASTNAME= "last_name";
    private static final String COLUMN_EMAIL= "email";
    private static final String COLUMN_PASSWORD= "password";
    private static final String COLUMN_USER_IMAGEPATH = "user_imagepath";
    private static final String COLUMN_LOG_IN_COUNT= "log_in_count";
    private static final String COLUMN_CREATED_AT_USERS = "created_at";
    private static final String COLUMN_UPDATED_AT_USERS = "updated_at";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public DBUsersManager(Context context) {
        dbHelper = new MySQLiteHelper(context);
        Log.d("", "user db created");
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable() {
        database.delete(MySQLiteHelper.TABLE_USERS, null, null);
    }

    // friend search
    public Boolean searchfriend(String email){
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_USERS +" WHERE " + COLUMN_EMAIL + "='" + email + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            return true;
        }
        cursor.close();
        return false;
    }

    public long getFriendsUserid(String email, long my_user_id)
    {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,	null, null, null, null, null, null);
        cursor.moveToFirst();
        long user_id = -2;
        while (!cursor.isAfterLast()) {
            Log.d("email =",cursor.getString(3));
            if(email.equals(cursor.getString(3)) && my_user_id != cursor.getLong(0))
            {
                user_id = cursor.getLong(0);
            }
            cursor.moveToNext();
        }
        return user_id;
    }

    // input data to table
    public void insertusers(TableUserItem item)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, item.getFirstname());
        values.put(COLUMN_LASTNAME, item.getLastname());
        values.put(COLUMN_EMAIL, item.getEmail());
        values.put(COLUMN_PASSWORD, item.getPassword());
        values.put(COLUMN_USER_IMAGEPATH, item.getImage_path());
        values.put(COLUMN_CREATED_AT_USERS, item.getCreated_at_users());
        values.put(COLUMN_UPDATED_AT_USERS, item.getUpdated_at_users());
        values.put(COLUMN_LOG_IN_COUNT, item.getLog_in_count());

        int userid= (int)database.insert(MySQLiteHelper.TABLE_USERS, null,
                values);
        Log.d("id= ", userid + "");
    }

    public void updateUserImagePath(long user_id, String path){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_IMAGEPATH, path);
        database.update(MySQLiteHelper.TABLE_USERS, values, COLUMN_USERID + "=" + user_id, null);
    }

    // get userid with email and password
    public long loginsearchusername(String email, String password)
    {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,	null, null, null, null, null, null);
        cursor.moveToFirst();
        long user_id = -2;
        while (!cursor.isAfterLast()) {
            Log.d("name=",cursor.getString(3));
            if(email.equals(cursor.getString(3)))
            {
                if(password.equals(cursor.getString(4))) {
                    user_id = cursor.getLong(0);
                } else {
                    user_id = -1;
                }
                break;
            }
            cursor.moveToNext();
        }
        return user_id;
    }

    public TableUserItem getuserdbItem(long userId){

        TableUserItem newItem = null;
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_USERS +" WHERE " + COLUMN_USERID + "='" + userId + "'" + " LIMIT 1";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            newItem = cursorToclass(cursor);
        }
        cursor.close();
        return newItem;
    }

    private TableUserItem cursorToclass(Cursor cursor) {
        TableUserItem item = new TableUserItem();
        item.setId(cursor.getLong(0));
        item.setFirstname(cursor.getString(1));
        item.setLastname(cursor.getString(2));
        item.setEmail(cursor.getString(3));
        item.setImage_path(cursor.getString(5));
        item.setCreated_at_users(cursor.getString(6));
        item.setUpdated_at_users(cursor.getString(7));
        item.setLog_in_count(cursor.getLong(8));
//        int test = cursor.getInt(1);
//        Log.d("", "" + test);
        return item;
    }


    public ArrayList<TableUserItem> getfriendsdata(long userId){

        ArrayList<TableUserItem> postList = new ArrayList<>();
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_USERS +" WHERE " + COLUMN_USERID + "='" + userId + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                TableUserItem newItem = cursorToclass(cursor);
                postList.add(newItem);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return postList;
    }

    public boolean checkEmailExist(String email){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS,	null, null, null, null, null, null);
        cursor.moveToFirst();
        boolean isExist = false;
        while (!cursor.isAfterLast()) {
            Log.d("name=",cursor.getString(3));
            if(email.equals(cursor.getString(3)))
            {
                isExist = true;
                break;
            }
            cursor.moveToNext();
        }
        return isExist;
    }

}
