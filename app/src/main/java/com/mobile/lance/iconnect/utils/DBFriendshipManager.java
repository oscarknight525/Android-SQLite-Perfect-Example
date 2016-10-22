package com.mobile.lance.iconnect.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 7/1/16.
 */
public class DBFriendshipManager {

    private static final String COLUMN_FRIENDSHIP_ID = "friendship_id";
    private static final String COLUMN_FRIEND_ID = "friend_id";
    private static final String COLUMN_USER_ID_FRIENDSHIP = "user_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_CREATED_AT_FRIENDSHIP = "friendship_created_at";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public DBFriendshipManager(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable() {
        database.delete(MySQLiteHelper.TABLE_FRIENDSHIP, null, null);
    }

    public void insertFriendShip(TableFriendshipItem item)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FRIEND_ID, item.getFriend_id());
        values.put(COLUMN_USER_ID_FRIENDSHIP, item.getUser_id());
        values.put(COLUMN_TYPE, item.getType());
        values.put(COLUMN_CREATED_AT_FRIENDSHIP, item.getFriendship_created_at());

        int friendship_id= (int)database.insert(MySQLiteHelper.TABLE_FRIENDSHIP, null,
                values);
        Log.d("id= ", friendship_id + "");
    }

    public List<Long> getFriendsshipId(long my_user_id)
    {
        List<Long> freindslist =new ArrayList<>();
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_FRIENDSHIP +" WHERE " + COLUMN_USER_ID_FRIENDSHIP + "='" + my_user_id + "' OR " + COLUMN_FRIEND_ID + "='" + my_user_id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        long friend_id = -2;
        while (!cursor.isAfterLast()) {
            Log.d("friend_id =","" + cursor.getLong(1));
            Log.d("type =","" + cursor.getLong(3));
            friend_id = cursor.getLong(0);
            freindslist.add(friend_id);

            cursor.moveToNext();
        }

        return freindslist;
    }

    public TableFriendshipItem getFriendshipData(long friendship_id){
        TableFriendshipItem friendshipItem = null;
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_FRIENDSHIP +" WHERE " + COLUMN_FRIENDSHIP_ID + "='" + friendship_id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            friendshipItem = cursorToUserItem(cursor);
        }
        cursor.close();
        return friendshipItem;
    }

    private TableFriendshipItem cursorToUserItem(Cursor cursor) {
        TableFriendshipItem item = new TableFriendshipItem();
        item.setFriendship_id(cursor.getLong(0));
        item.setFriend_id(cursor.getLong(1));
        item.setUser_id(cursor.getLong(2));
        item.setType(cursor.getString(3));
        item.setFriendship_created_at(cursor.getString(4));
        return item;
    }

    public boolean removeFriendShip(long id){
        return database.delete(MySQLiteHelper.TABLE_FRIENDSHIP,  COLUMN_FRIENDSHIP_ID + "='" + id + "'", null) > 0;
    }
}
