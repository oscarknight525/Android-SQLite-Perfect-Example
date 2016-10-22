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
public class DBNotificationManager {

    private static final String COLUMN_NOTIFICATION_ID = "notification_id";
    private static final String COLUMN_NOTIFICATION_RECEIVER_ID = "notification_receiver_id";
    private static final String COLUMN_NOTIFICATION_SENDER_ID = "notification_sender_id";
    private static final String COLUMN_NOTIFICATION_MESSAGES= "notification_message";
    private static final String COLUMN_NOTIFICATION_TYPE= "notification_type";
    private static final String COLUMN_NOTIFICATION_STATUS= "notification_status";
    private static final String COLUMN_CREATED_AT_NOTIFICATIONS = "notification_created_at";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public DBNotificationManager(Context context) {
        dbHelper = new MySQLiteHelper(context);
        Log.d("", "notification db created");
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable() {
        database.delete(MySQLiteHelper.TABLE_NOTIFICATIONS, null, null);
    }

    public void insertNotification(TableNotificationsItem item)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTIFICATION_RECEIVER_ID, item.getNotificationReceiver_id());
        values.put(COLUMN_NOTIFICATION_SENDER_ID, item.getNotificationSender_id());
        values.put(COLUMN_NOTIFICATION_MESSAGES, item.getNotificationMessage());
        values.put(COLUMN_NOTIFICATION_TYPE, item.getNotificationType());
        values.put(COLUMN_NOTIFICATION_STATUS, item.getNotificationStatus());
        values.put(COLUMN_CREATED_AT_NOTIFICATIONS, item.getNotification_created_at());

        int message_id= (int)database.insert(MySQLiteHelper.TABLE_NOTIFICATIONS, null,
                values);
        Log.d("id= ", message_id + "");
    }

    public List<Long> getNotificationIDs(long user_id, List<Long> friendID_list){
        List<Long> messageIDList =new ArrayList<>();
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_NOTIFICATIONS +" WHERE " + COLUMN_NOTIFICATION_RECEIVER_ID + "='" + user_id + "' OR "
                + COLUMN_NOTIFICATION_SENDER_ID + "='" + user_id + "' OR " + COLUMN_NOTIFICATION_TYPE + "='Post'" + " ORDER BY "+ COLUMN_NOTIFICATION_ID + " DESC";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        long notification_id = -2;
        while (!cursor.isAfterLast()) {
            Log.d("notification_id =","" + cursor.getLong(0));
            if(cursor.getString(4).equals("Post") && cursor.getLong(2) != user_id){
                for(int i =0; i<friendID_list.size(); i++){
                    if(cursor.getLong(2) == friendID_list.get(i)){
                        notification_id = cursor.getLong(0);
                        messageIDList.add(notification_id);
                        break;
                    }
                }

            }
            else {
                notification_id = cursor.getLong(0);
                messageIDList.add(notification_id);
            }


            cursor.moveToNext();
        }
        return messageIDList;
    }

    public TableNotificationsItem getNotificationInfo(long notification_id){

        TableNotificationsItem messageItem = null;
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_NOTIFICATIONS +" WHERE " + COLUMN_NOTIFICATION_ID + "='" + notification_id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            messageItem = cursorToUserItem(cursor);
        }
        cursor.close();
        return messageItem;
    }

    private TableNotificationsItem cursorToUserItem(Cursor cursor) {
        TableNotificationsItem item = new TableNotificationsItem();
        item.setNotificationId(cursor.getLong(0));
        item.setNotificationReceiver_id(cursor.getLong(1));
        item.setNotificationSender_id(cursor.getLong(2));
        item.setNotificationMessage(cursor.getString(3));
        item.setNotificationType(cursor.getString(4));
        item.setNotificationStatus(cursor.getString(5));
        item.setNotification_created_at(cursor.getString(6));
        return item;
    }

}
