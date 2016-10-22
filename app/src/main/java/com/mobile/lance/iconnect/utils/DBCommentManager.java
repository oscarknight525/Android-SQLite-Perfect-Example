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
public class DBCommentManager {

    private static final String COLUMN_ID_COMMENTS = "id";
    private static final String COLUMN_COMMENT= "comment";
    private static final String COLUMN_COMMENT_USER_ID = "comment_user_id";
    private static final String COLUMN_COMMENT_POST_ID = "comment_post_id";
    private static final String COLUMN_CREATED_AT_COMMENTS = "comment_created_at";
    private static final String COLUMN_UPDATED_AT_COMMENTS = "comment_updated_at";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public DBCommentManager(Context context) {
        dbHelper = new MySQLiteHelper(context);
        Log.d("", "comment db created");
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clearTable() {
        database.delete(MySQLiteHelper.TABLE_COMMENTS, null, null);
    }

    public void insertComment(TableCommentsItem item)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT, item.getComment());
        values.put(COLUMN_COMMENT_USER_ID, item.getComment_user_id());
        values.put(COLUMN_COMMENT_POST_ID, item.getComment_post_id());
        values.put(COLUMN_CREATED_AT_COMMENTS, item.getComment_created_at());
        values.put(COLUMN_UPDATED_AT_COMMENTS, item.getComment_updated_at());
        int comment_id= (int)database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
                values);
        Log.d("id= ", comment_id + "");
    }

    public void updateCommentInfo(long comment_id, TableCommentsItem item){
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT, item.getComment());
        values.put(COLUMN_UPDATED_AT_COMMENTS, item.getComment_updated_at());
        database.update(MySQLiteHelper.TABLE_COMMENTS, values, COLUMN_ID_COMMENTS + "=" + comment_id, null);
    }

    public List<Long> getCommentIDs(long post_id){
        List<Long> commentIDList = new ArrayList<>();
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_COMMENTS +" WHERE " + COLUMN_COMMENT_POST_ID + "='" + post_id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        long comment_id = -2;
        while (!cursor.isAfterLast()) {
            Log.d("comment_id =","" + cursor.getLong(0));
            comment_id = cursor.getLong(0);
            commentIDList.add(comment_id);
            cursor.moveToNext();
        }
        return commentIDList;
    }

    public TableCommentsItem getCommentInfo(long comment_id){

        TableCommentsItem commentItem = null;
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_COMMENTS +" WHERE " + COLUMN_ID_COMMENTS + "='" + comment_id + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            commentItem = cursorToUserItem(cursor);
        }
        cursor.close();
        return commentItem;
    }

    private TableCommentsItem cursorToUserItem(Cursor cursor) {
        TableCommentsItem item = new TableCommentsItem();
        item.setId(cursor.getLong(0));
        item.setComment(cursor.getString(1));
        item.setComment_user_id(cursor.getLong(2));
        item.setComment_post_id(cursor.getLong(3));
        item.setComment_created_at(cursor.getString(4));
        item.setComment_updated_at(cursor.getString(5));
        return item;
    }
}
