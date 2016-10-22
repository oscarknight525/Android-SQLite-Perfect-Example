package com.mobile.lance.iconnect.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by administrator on 7/1/16.
 */
public class DBPostManager {

    private static final String COLUMN_POST_ID = "post_id";
    private static final String COLUMN_POST = "post";
    private static final String COLUMN_USER_ID_POSTS = "post_user_id";
    private static final String COLUMN_IMAGEPATH_POSTS = "post_imagepath";
    private static final String COLUMN_CREATED_AT_POSTS = "post_created_at";
    private static final String COLUMN_UPDATED_AT_POSTS = "post_updated_at";

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public DBPostManager(Context context) {
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
        database.delete(MySQLiteHelper.TABLE_POSTS, null, null);
    }

    public void insertPostScript(TablePostsItem item) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_POST, item.getPost());
        values.put(COLUMN_USER_ID_POSTS, item.getPostUserId());
        values.put(COLUMN_IMAGEPATH_POSTS, item.getPostImagePath());
        values.put(COLUMN_CREATED_AT_POSTS, item.getPost_created_at());
        values.put(COLUMN_UPDATED_AT_POSTS, item.getPost_updated_at());

        int postid = (int)database.insert(MySQLiteHelper.TABLE_POSTS, null,
                values);
        Log.d("id = ", postid + "");
    }

    public ArrayList<TablePostsItem> getPostList(long userId){

        ArrayList<TablePostsItem> postList = new ArrayList<>();
        String selectQuery= "SELECT * FROM " + MySQLiteHelper.TABLE_POSTS +" WHERE " + COLUMN_USER_ID_POSTS + "='" + userId + "' ORDER BY " + COLUMN_POST_ID + " DESC" ;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                TablePostsItem newItem = cursorToclass(cursor, userId);
                postList.add(newItem);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return postList;
    }

    private TablePostsItem cursorToclass(Cursor cursor, long userid) {
        TablePostsItem item = new TablePostsItem();

        item.setPost(cursor.getString(1));
        item.setPostUserId(userid);
        item.setPost_created_at(cursor.getString(4));
        item.setId(cursor.getLong(0));
        item.setPostImagePath(cursor.getString(3));
        return item;
    }
}
