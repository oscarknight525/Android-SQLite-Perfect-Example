package com.mobile.lance.iconnect.iConnect.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBMessageManager;
import com.mobile.lance.iconnect.utils.DBNotificationManager;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableMessagesItem;
import com.mobile.lance.iconnect.utils.TableNotificationsItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by administrator on 7/21/16.
 */
public class CustomNotificationsAdapter extends BaseAdapter{


    private DBUsersManager userdb;
    private TableUserItem userinfo;

    long my_user_id;

    Context ctx;
    ArrayList<TableNotificationsItem> notificationslist;
    LayoutInflater inflater;

    private float icon_width;
    private float icon_height;

    private String notification_friend_request;
    private String notification_username;

    String picturePath;
    Bitmap thumbnail;
    Bitmap adjustedBitmap;
    Bitmap profileImage;

    public CustomNotificationsAdapter(Context context, ArrayList<TableNotificationsItem> notificationslist) {
        this.notificationslist = notificationslist;
        ctx = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userdb = new DBUsersManager(ctx);
        SharedPreferences prefs = ctx.getSharedPreferences("UserData", ctx.MODE_PRIVATE);
        String userid_loggedin = prefs.getString("userid", null);
        my_user_id = Long.parseLong(userid_loggedin);
    }

    @Override
    public int getCount() {
        return notificationslist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TableNotificationsItem notificationslistcell = notificationslist.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        TextView tv_Notification = null;
        TextView tv_notification_username = null;
        ImageView profile_image = null;
        convertView = null;
        if (convertView == null) {
            if(notificationslistcell.getNotificationReceiver_id() == my_user_id || notificationslistcell.getNotificationReceiver_id() == -1) {
                convertView = inflater.inflate(R.layout.other_notification_listcell, null);
                tv_notification_username = (TextView) convertView.findViewById(R.id.txt_notification_username);
                tv_Notification = (TextView) convertView.findViewById(R.id.txt_other_notification);
                profile_image = (ImageView) convertView.findViewById(R.id.img_notification_otherprofile);
                try{
                    userdb.open();
                    userinfo = new TableUserItem();

                    userinfo = userdb.getuserdbItem(notificationslistcell.getNotificationSender_id());
                    notification_username = userinfo.getFirstname() + " " + userinfo.getLastname();

                    tv_notification_username.setText(notification_username);

                    if(!userinfo.getImage_path().equals("")){
                        picturePath = userinfo.getImage_path();
                        File file = new File(picturePath);
                        if(file.exists()) {
                            try {
                                thumbnail = (BitmapFactory.decodeFile(picturePath));
                            } catch (Exception e) {
                                e.printStackTrace();
                            } catch (OutOfMemoryError e) {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 10;
                                thumbnail = (BitmapFactory.decodeFile(picturePath, options));
                            }
                            profileImage = Bitmap.createScaledBitmap(thumbnail, 200, 200, true);
                            profile_image.setImageBitmap(profileImage);
                        }
                    }

                    if(notificationslistcell.getNotificationType().equals("friend_request")){
                        if(notificationslistcell.getNotificationStatus().equals("Waiting")){
                            notification_friend_request = " have sent friend request to you.";
                        }
                        else if(notificationslistcell.getNotificationStatus().equals("Accept")){
                            notification_friend_request = " have accepted friend request from you.";
                        }
                        else{
                            notification_friend_request = " have declined friend request from you.";
                        }
                        tv_Notification.setText(notification_username + notification_friend_request);
                    }
                    else if(notificationslistcell.getNotificationType().equals("Post")){
                        tv_Notification.setText(notificationslistcell.getNotificationMessage());
                    }

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            else {
                convertView = inflater.inflate(R.layout.my_notification_listcell, null);
                tv_Notification = (TextView) convertView.findViewById(R.id.txt_my_notification);
                profile_image = (ImageView) convertView.findViewById(R.id.img_notification_myprofile);
                try{
                    userdb.open();
                    userinfo = new TableUserItem();

                    userinfo = userdb.getuserdbItem(notificationslistcell.getNotificationReceiver_id());
                    TableUserItem myuserinfo = userdb.getuserdbItem(my_user_id);
                    notification_username = userinfo.getFirstname() + " " + userinfo.getLastname();

                    if(!myuserinfo.getImage_path().equals("")){
                        picturePath = myuserinfo.getImage_path();
                        File file = new File(picturePath);
                        if(file.exists()) {
                            thumbnail = (BitmapFactory.decodeFile(picturePath));
                            adjustedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight());
                            profileImage = Bitmap.createScaledBitmap(adjustedBitmap, 200, 200, true);
                            profile_image.setImageBitmap(profileImage);
                        }
                    }

                }catch (SQLException e){
                    e.printStackTrace();
                }

                if(notificationslistcell.getNotificationType().equals("friend_request")){
                    if(notificationslistcell.getNotificationStatus().equals("Waiting")){
                        notification_friend_request = "You have sent friend request to ";
                    }
                    else if(notificationslistcell.getNotificationStatus().equals("Accept")){
                        notification_friend_request = "You have accepted friend request from ";
                    }
                    else{
                        notification_friend_request = "You have declined friend request from ";
                    }
                    tv_Notification.setText(notification_friend_request + notification_username + ".");
                }
            }
        }

        return convertView;
    }

}
