package com.mobile.lance.iconnect.iConnect.friends.friendrequest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBFriendshipManager;
import com.mobile.lance.iconnect.utils.DBNotificationManager;
import com.mobile.lance.iconnect.utils.DBRequestManager;
import com.mobile.lance.iconnect.utils.TableFriendshipItem;
import com.mobile.lance.iconnect.utils.TableNotificationsItem;
import com.mobile.lance.iconnect.utils.TableRequestItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by administrator on 7/18/16.
 */
public class CustomFriendsRequestAdapter extends BaseAdapter {

    private DBRequestManager requestdb;
    private DBFriendshipManager friendshipdb;
    private DBNotificationManager notificationdb;

    private TableRequestItem requestinfo;
    private TableFriendshipItem friendshipItem;
    private TableNotificationsItem notification_item;

    Context ctx;
    ArrayList<TableUserItem> friendslist;
    ArrayList<TableRequestItem> requestList;
    LayoutInflater inflater;

    private long my_user_id;
    private long friend_id;

    public CustomFriendsRequestAdapter(Context context, ArrayList<TableUserItem> friendslist, ArrayList<TableRequestItem> requestList) {
        this.friendslist = friendslist;
        this.requestList = requestList;
        ctx = context;
        requestdb = new DBRequestManager(ctx);
        friendshipdb = new DBFriendshipManager(ctx);
        notificationdb = new DBNotificationManager(ctx);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        SharedPreferences prefs = ctx.getSharedPreferences("UserData", ctx.MODE_PRIVATE);
        String userid_loggedin = prefs.getString("userid", null);
        my_user_id = Long.parseLong(userid_loggedin);
    }

    @Override
    public int getCount() {
        return friendslist.size();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
//        TableUserItem friendslistcell = friendslist.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friends_request_cell, null);
        }

        // press remove and viewposts button in listview cell

        requestinfo = requestList.get(position);
        friend_id = requestinfo.getFrom_id();
        Button acceptButton = (Button) convertView.findViewById(R.id.AcceptButton);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    friendshipdb.open();
                    friendshipItem = new TableFriendshipItem();
                    friendshipItem.setFriend_id(friend_id);
                    friendshipItem.setUser_id(my_user_id);
                    friendshipItem.setType("friend");
                    friendshipItem.setFriendship_created_at(getCurrentDate());
                    friendshipdb.insertFriendShip(friendshipItem);

                    requestdb.open();
                    requestinfo.setStatus("Accept");
                    requestinfo.setAccepted_yn(true);
                    requestinfo.setRequest_updated_at(getCurrentDate());
                    requestdb.updateFriendsRequestInfo(requestinfo.getRequest_id(), requestinfo);

                    notificationdb.open();
                    notification_item = new TableNotificationsItem();

                    notification_item.setNotificationReceiver_id(friend_id);
                    notification_item.setNotificationSender_id(my_user_id);
                    notification_item.setNotificationMessage("");
                    notification_item.setNotificationType("friend_request");
                    notification_item.setNotificationStatus("Accept");
                    notification_item.setNotification_created_at(getCurrentDate());
                    notificationdb.insertNotification(notification_item);

                    friendslist.remove(position);
                    requestList.remove(position);


                    CustomFriendsRequestAdapter.this.notifyDataSetChanged();

                    AppConstants.alertShow(ctx, "Alert", "sent request successfully");

                    requestdb.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Button declineButton = (Button) convertView.findViewById(R.id.DeclineButton);
        declineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("", "");

                try {
                    requestdb.open();

                    requestinfo.setStatus("Decline");
                    requestinfo.setAccepted_yn(false);
                    requestinfo.setRequest_updated_at(getCurrentDate());
                    requestdb.updateFriendsRequestInfo(requestinfo.getRequest_id(), requestinfo);

                    notificationdb.open();
                    notification_item = new TableNotificationsItem();

                    notification_item.setNotificationReceiver_id(friend_id);
                    notification_item.setNotificationSender_id(my_user_id);
                    notification_item.setNotificationMessage("");
                    notification_item.setNotificationType("friend_request");
                    notification_item.setNotificationStatus("Decline");
                    notification_item.setNotification_created_at(getCurrentDate());
                    notificationdb.insertNotification(notification_item);

                    friendslist.remove(position);
                    requestList.remove(position);
                    CustomFriendsRequestAdapter.this.notifyDataSetChanged();

                    AppConstants.alertShow(ctx, "Alert", "it is declined friends request");

                    requestdb.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        TextView tvUsername = (TextView) convertView.findViewById(R.id.txtViewCellYN);
        // Populate the data into the template view using the data object
        tvUsername.setText(friendslist.get(position).getFirstname() + " " + friendslist.get(position).getLastname());
        // Return the completed view to render on screen

        return convertView;
    }

    private  static String  getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }
}
