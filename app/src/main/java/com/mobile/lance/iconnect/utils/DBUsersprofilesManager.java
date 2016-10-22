package com.mobile.lance.iconnect.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.database.Cursor;
import java.sql.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by administrator on 7/1/16.
 */
public class DBUsersprofilesManager {

    private static final String COLUMN_USER_PROFILE_ID = "user_profile_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_PHONE_NUMBER_CELL = "phone_number_cell";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_ZIPCODE = "zipcode";
    private static final String COLUMN_BIRTH_DATE = "birth_date";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_DETAILS = "details";
    private static final String COLUMN_CREATED_AT_USER_PROFILE = "user_profile_created_at";
    private static final String COLUMN_UPDATED_AT_USER_PROFILE = "user_profile_updated_at";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public DBUsersprofilesManager(Context context) {
        dbHelper = new MySQLiteHelper(context);
        Log.d("", "database created");
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable() {
        database.delete(MySQLiteHelper.TABLE_USERS_PROFILES, null, null);
    }

    public void insertUsersProfiles(TableUsersProfilesItem item, int userid)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, item.getUser_name());
        values.put(COLUMN_USER_ID, userid);
        values.put(COLUMN_PHONE_NUMBER_CELL, item.getPhone_number_cell());
        values.put(COLUMN_ADDRESS, item.getAddress());
        values.put(COLUMN_CITY, item.getCity());
        values.put(COLUMN_STATE, item.getState());
        values.put(COLUMN_ZIPCODE, item.getZipcode());
        values.put(COLUMN_BIRTH_DATE, item.getBirth_date());
        values.put(COLUMN_GENDER, item.getGender());
        values.put(COLUMN_DETAILS, item.getDetails());

        int profileid= (int)database.insert(MySQLiteHelper.TABLE_USERS_PROFILES, null,
                values);
        Log.d("id= ", profileid + "");

    }

    public void updateUsersProfiles(TableUsersProfilesItem item, int userid)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, item.getUser_name());
        values.put(COLUMN_PHONE_NUMBER_CELL, item.getPhone_number_cell());
        values.put(COLUMN_ADDRESS, item.getAddress());
        values.put(COLUMN_CITY, item.getCity());
        values.put(COLUMN_STATE, item.getState());
        values.put(COLUMN_ZIPCODE, item.getZipcode());
        values.put(COLUMN_BIRTH_DATE, item.getBirth_date());
        values.put(COLUMN_GENDER, item.getGender());
        values.put(COLUMN_DETAILS, item.getDetails());

        int longid = database.update(MySQLiteHelper.TABLE_USERS_PROFILES, values, COLUMN_USER_ID + "=" + userid, null);
        Log.d("id= ", longid + "");

    }

    public Boolean getdbItem(long userId){

        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_USERS_PROFILES+" WHERE " + COLUMN_USER_ID + "='" + userId + "'" + " LIMIT 1";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            return true;
        }
        cursor.close();
        return false;
    }

    public TableUsersProfilesItem getuserprofiledbItem(long userId){

        TableUsersProfilesItem newItem = null;
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_USERS_PROFILES +" WHERE " + COLUMN_USER_ID + "='" + userId + "'" + " LIMIT 1";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            newItem = cursorToclass(cursor);
        }
        cursor.close();
        return newItem;
    }

    private TableUsersProfilesItem cursorToclass(Cursor cursor) {
        TableUsersProfilesItem item = new TableUsersProfilesItem();
        item.setUser_name(cursor.getString(2));
        item.setPhone_number_cell(cursor.getString(3));
        item.setAddress(cursor.getString(4));
        item.setCity(cursor.getString(5));
        item.setState(cursor.getString(6));
        item.setZipcode(cursor.getString(7));
        item.setBirth_date(cursor.getString(8));
        item.setGender(cursor.getString(9));
        item.setDetails(cursor.getString(10));

        int test = cursor.getInt(1);
        Log.d("", "" + test);
        return item;
    }
}
