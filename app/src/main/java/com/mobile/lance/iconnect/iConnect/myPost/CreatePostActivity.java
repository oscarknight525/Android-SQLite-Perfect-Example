package com.mobile.lance.iconnect.iConnect.myPost;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBNotificationManager;
import com.mobile.lance.iconnect.utils.DBPostManager;
import com.mobile.lance.iconnect.utils.TableNotificationsItem;
import com.mobile.lance.iconnect.utils.TablePostsItem;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreatePostActivity extends AppCompatActivity{

    private TablePostsItem postinfo;
    private DBPostManager postdb;

    private DBNotificationManager notificationdb;
    private TableNotificationsItem notification_item;

    private Context context;

    ImageView postImageView;
    Bitmap postImage = null;

    String picturePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        context = this;
        postdb = new DBPostManager(context);
        picturePath = "";
        notificationdb = new DBNotificationManager(context);

        postImageView = (ImageView)findViewById(R.id.PostImageView);
    }

    // get image from gallery or take picture using camera
    public void onPostImageClick(View v){
        final CharSequence[] items = { "Take Photo", "Choose from Gallery", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePostActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        Uri selectedImage = data.getData();
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        picturePath = c.getString(columnIndex);
        c.close();
        Bitmap thumbnail = null;
        try {
            thumbnail = (BitmapFactory.decodeFile(picturePath));
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            thumbnail = (BitmapFactory.decodeFile(picturePath, options));
        }
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(picturePath);
        } catch (IOException e) {
            picturePath = "";
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        int degree;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
            default:
                degree = 0;
                break;
        }
        Matrix matrix = new Matrix();
        if (degree != 0f) {
            matrix.preRotate(degree);
        }
        Bitmap adjustedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);
        postImage = Bitmap.createScaledBitmap(adjustedBitmap, 200, 200, true);
        postImageView.setImageBitmap(postImage);
    }

    // back and create post buttons actions
    public void onBackButtonCreatePostClick(View v) {
        super.onBackPressed();
    }

    public void onCreatePostButtonClick(View v) {
        EditText post_content = (EditText) findViewById(R.id.editText_comment);
        String post_contentstr = post_content.getText().toString();

        // get user id logged in
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String postid_loggedin = prefs.getString("userid", null);

        long loggedin_user = Long.parseLong(postid_loggedin);

        postinfo = new TablePostsItem();

        try {
            if (!post_contentstr.isEmpty()) {

                postdb.open();

                postinfo.setPostUserId(loggedin_user);
                postinfo.setPost(post_contentstr);
                postinfo.setPostImagePath(picturePath);
                postinfo.setPost_created_at(getCurrentDate());
                postdb.insertPostScript(postinfo);

                notificationdb.open();
                notification_item = new TableNotificationsItem();

                notification_item.setNotificationReceiver_id(-1);
                notification_item.setNotificationSender_id(loggedin_user);
                notification_item.setNotificationMessage(post_contentstr);
                notification_item.setNotificationType("Post");
                notification_item.setNotificationStatus("");
                notification_item.setNotification_created_at(getCurrentDate());

                notificationdb.insertNotification(notification_item);

//                AppConstants.alertShow(context, "Alert", "Posted Successfully!");

                postdb.close();
                notificationdb.close();
                finish();
            } else {
                AppConstants.alertShow(context, "Alert", "Now you can post!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get current date
    private  static String  getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }
}
