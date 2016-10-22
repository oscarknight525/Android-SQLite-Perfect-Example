package com.mobile.lance.iconnect.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by administrator on 6/30/16.
 */
public class MySQLiteHelper extends SQLiteOpenHelper{

    // user table name
    public static final String TABLE_USERS = "users";

    // users table field
    private static final String COLUMN_USERID = "user_id";
    private static final String COLUMN_FIRSTNAME= "firs_tname";
    private static final String COLUMN_LASTNAME= "last_name";
    private static final String COLUMN_EMAIL= "email";
    private static final String COLUMN_PASSWORD= "password";
    private static final String COLUMN_USER_IMAGEPATH = "user_imagepath";
    private static final String COLUMN_LOG_IN_COUNT= "log_in_count";
    private static final String COLUMN_CREATED_AT_USERS = "created_at";
    private static final String COLUMN_UPDATED_AT_USERS = "updated_at";

    // messages table name
    public static final  String TABLE_MESSAGES = "messages";

    // messages table field
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RECEIVER_ID = "receiver_id";
    private static final String COLUMN_SENDER_ID = "sender_id";
    private static final String COLUMN_MESSAGES= "message";
    private static final String COLUMN_CREATED_AT_MESSAGES = "message_created_at";

    // users_profiles table name
    public static final  String TABLE_USERS_PROFILES = "users_profiles";

    // users_profiles table field
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

    // posts table name
    public static final  String TABLE_POSTS = "posts";

    // posts table field
    private static final String COLUMN_POST_ID = "post_id";
    private static final String COLUMN_POST = "post";
    private static final String COLUMN_USER_ID_POSTS = "post_user_id";
    private static final String COLUMN_IMAGEPATH_POSTS = "post_imagepath";
    private static final String COLUMN_CREATED_AT_POSTS = "post_created_at";
    private static final String COLUMN_UPDATED_AT_POSTS = "post_updated_at";

    // comments table name
    public  static final String TABLE_COMMENTS = "comments";

    // comments table field
    private static final String COLUMN_ID_COMMENTS = "id";
    private static final String COLUMN_COMMENT= "comment";
    private static final String COLUMN_COMMENT_USER_ID = "comment_user_id";
    private static final String COLUMN_COMMENT_POST_ID = "comment_post_id";
    private static final String COLUMN_CREATED_AT_COMMENTS = "comment_created_at";
    private static final String COLUMN_UPDATED_AT_COMMENTS = "comment_updated_at";

    // friendship table name
    public static final  String TABLE_FRIENDSHIP = "friendship";

    // friendship table field
    private static final String COLUMN_FRIENDSHIP_ID = "friendship_id";
    private static final String COLUMN_FRIEND_ID = "friend_id";
    private static final String COLUMN_USER_ID_FRIENDSHIP = "user_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_CREATED_AT_FRIENDSHIP = "friendship_created_at";

    // requests table name
    public static final  String TABLE_REQUESTS = "requests";

    // requests table field
    private static final String COLUMN_REQUEST_ID = "request_id";
    private static final String COLUMN_FROM_ID = "from_id";
    private static final String COLUMN_TO_ID = "to_id";
    private static final String COLUMN_ACCEPTED_YN = "accepted_yn";
    private static final String COLUMN_REQUEST = "request";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_CREATED_AT_REQUESTS = "request_created_at";
    private static final String COLUMN_UPDATED_AT_REQUESTS = "request_updated_at";

    // notification table name
    public static final  String TABLE_NOTIFICATIONS = "notifications";

    // messages table field
    private static final String COLUMN_NOTIFICATION_ID = "notification_id";
    private static final String COLUMN_NOTIFICATION_RECEIVER_ID = "notification_receiver_id";
    private static final String COLUMN_NOTIFICATION_SENDER_ID = "notification_sender_id";
    private static final String COLUMN_NOTIFICATION_MESSAGES= "notification_message";
    private static final String COLUMN_NOTIFICATION_TYPE= "notification_type";
    private static final String COLUMN_NOTIFICATION_STATUS= "notification_status";
    private static final String COLUMN_CREATED_AT_NOTIFICATIONS = "notification_created_at";

    // database name
    private static final String DATABASE_NAME = "iconnect.db";
    private static final int DATABASE_VERSION = 1;

    // create all table
    private static final String DATABASE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_FIRSTNAME + " TEXT," +
            COLUMN_LASTNAME + " TEXT," +
            COLUMN_EMAIL + " TEXT," +
            COLUMN_PASSWORD + " TEXT," +
            COLUMN_USER_IMAGEPATH + " TEXT," +
            COLUMN_CREATED_AT_USERS + " DATE," +
            COLUMN_UPDATED_AT_USERS + " DATE," +
            COLUMN_LOG_IN_COUNT + " INTEGER" +
            ");";

    private static final String DATABASE_MESSAGES = "CREATE TABLE " + TABLE_MESSAGES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_RECEIVER_ID + " INTEGER," +
            COLUMN_SENDER_ID + " INTEGER," +
            COLUMN_MESSAGES + " TEXT," +
            COLUMN_CREATED_AT_MESSAGES + " TEXT" +
            ");";

    private static final String DATABASE_USERS_PROFILES = "CREATE TABLE " + TABLE_USERS_PROFILES + " (" +
            COLUMN_USER_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_ID + " INTEGER," +
            COLUMN_USER_NAME + " TEXT," +
            COLUMN_PHONE_NUMBER_CELL + " TEXT," +
            COLUMN_ADDRESS + " TEXT," +
            COLUMN_CITY + " TEXT," +
            COLUMN_STATE + " TEXT," +
            COLUMN_ZIPCODE + " TEXT," +
            COLUMN_BIRTH_DATE + " TEXT," +
            COLUMN_GENDER + " TEXT," +
            COLUMN_DETAILS + " TEXT," +
            COLUMN_CREATED_AT_USER_PROFILE + " DATE," +
            COLUMN_UPDATED_AT_USER_PROFILE + " DATE" +
            ");";

    private static final String DATABASE_POSTS = "CREATE TABLE " + TABLE_POSTS + " (" +
            COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_POST + " TEXT," +
            COLUMN_USER_ID_POSTS + " INTEGER," +
            COLUMN_IMAGEPATH_POSTS + " TEXT," +
            COLUMN_CREATED_AT_POSTS + " DATE," +
            COLUMN_UPDATED_AT_POSTS + " DATE" +
            ");";


    private static final String DATABASE_COMMENTS = "CREATE TABLE " + TABLE_COMMENTS + " (" +
            COLUMN_ID_COMMENTS + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_COMMENT + " TEXT," +
            COLUMN_COMMENT_USER_ID + " INTEGER," +
            COLUMN_COMMENT_POST_ID + " INTEGER," +
            COLUMN_CREATED_AT_COMMENTS + " DATE," +
            COLUMN_UPDATED_AT_COMMENTS + " DATE" +
            ");";

    private static final String DATABASE_FRIENDSHIP = "CREATE TABLE " + TABLE_FRIENDSHIP + " (" +
            COLUMN_FRIENDSHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_FRIEND_ID + " INTEGER," +
            COLUMN_USER_ID_FRIENDSHIP + " INTEGER," +
            COLUMN_TYPE + " TEXT," +
            COLUMN_CREATED_AT_FRIENDSHIP + " DATE" +
            ");";

    private static final String DATABASE_REQUESTS = "CREATE TABLE " + TABLE_REQUESTS + " (" +
            COLUMN_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_FROM_ID + " INTEGER," +
            COLUMN_TO_ID + " INTEGER," +
            COLUMN_ACCEPTED_YN + " BOOLEAN," +
            COLUMN_REQUEST + " TEXT," +
            COLUMN_STATUS + " TEXT," +
            COLUMN_CREATED_AT_REQUESTS + " DATE," +
            COLUMN_UPDATED_AT_REQUESTS + " DATE" +
            ");";

    private static final String DATABASE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATIONS + " (" +
            COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOTIFICATION_RECEIVER_ID + " INTEGER," +
            COLUMN_NOTIFICATION_SENDER_ID + " INTEGER," +
            COLUMN_NOTIFICATION_MESSAGES + " TEXT," +
            COLUMN_NOTIFICATION_TYPE + " TEXT," +
            COLUMN_NOTIFICATION_STATUS + " TEXT," +
            COLUMN_CREATED_AT_NOTIFICATIONS + " TEXT" +
            ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("created", "database Created.");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_USERS);
        database.execSQL(DATABASE_MESSAGES);
        database.execSQL(DATABASE_USERS_PROFILES);
        database.execSQL(DATABASE_POSTS);
        database.execSQL(DATABASE_COMMENTS);
        database.execSQL(DATABASE_FRIENDSHIP);
        database.execSQL(DATABASE_REQUESTS);
        database.execSQL(DATABASE_NOTIFICATIONS);
        Log.i("created", "Table Created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TABLE_USERS);
        db.execSQL("Drop table if exists " + TABLE_MESSAGES);
        db.execSQL("Drop table if exists " + TABLE_USERS_PROFILES);
        db.execSQL("Drop table if exists " + TABLE_POSTS);
        db.execSQL("Drop table if exists " + TABLE_COMMENTS);
        db.execSQL("Drop table if exists " + DATABASE_FRIENDSHIP);
        db.execSQL("Drop table if exists " + DATABASE_REQUESTS);
        db.execSQL("Drop table if exists " + DATABASE_NOTIFICATIONS);
        onCreate(db);
    }

}
