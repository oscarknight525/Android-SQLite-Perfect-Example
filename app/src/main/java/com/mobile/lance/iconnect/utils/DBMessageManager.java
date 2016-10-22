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
public class DBMessageManager {

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RECEIVER_ID = "receiver_id";
    private static final String COLUMN_SENDER_ID = "sender_id";
    private static final String COLUMN_MESSAGES= "message";
    private static final String COLUMN_CREATED_AT_MESSAGES = "message_created_at";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public DBMessageManager(Context context) {
        dbHelper = new MySQLiteHelper(context);
        Log.d("", "message db created");
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable() {
        database.delete(MySQLiteHelper.TABLE_MESSAGES, null, null);
    }

    public void insertMessage(TableMessagesItem item)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECEIVER_ID, item.getReceiver_id());
        values.put(COLUMN_SENDER_ID, item.getSender_id());
        values.put(COLUMN_MESSAGES, item.getMessage());
        values.put(COLUMN_CREATED_AT_MESSAGES, item.getMessage_created_at());

        int message_id= (int)database.insert(MySQLiteHelper.TABLE_MESSAGES, null,
                values);
        Log.d("id= ", message_id + "");
    }

    public List<Long> getMessageIDs(long user_id){
        List<Long> messageIDList =new ArrayList<>();
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_MESSAGES +" WHERE " + COLUMN_RECEIVER_ID + "='" + user_id + "' OR " + COLUMN_SENDER_ID + "='" + user_id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        long message_id = -2;
        while (!cursor.isAfterLast()) {
            Log.d("message_id =","" + cursor.getLong(0));
            message_id = cursor.getLong(0);
            messageIDList.add(message_id);
            cursor.moveToNext();
        }
        return messageIDList;
    }

    public TableMessagesItem getMessageInfo(long message_id){

        TableMessagesItem messageItem = null;
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_MESSAGES +" WHERE " + COLUMN_ID + "='" + message_id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            messageItem = cursorToUserItem(cursor);
        }
        cursor.close();
        return messageItem;
    }

    private TableMessagesItem cursorToUserItem(Cursor cursor) {
        TableMessagesItem item = new TableMessagesItem();
        item.setId(cursor.getLong(0));
        item.setReceiver_id(cursor.getLong(1));
        item.setSender_id(cursor.getLong(2));
        item.setMessage(cursor.getString(3));
        item.setMessage_created_at(cursor.getString(4));
        return item;
    }

}
