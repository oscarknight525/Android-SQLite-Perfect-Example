package com.mobile.lance.iconnect.iConnect.friends.find;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBNotificationManager;
import com.mobile.lance.iconnect.utils.DBRequestManager;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableNotificationsItem;
import com.mobile.lance.iconnect.utils.TableRequestItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by administrator on 7/13/16.
 */
public class CustomFindFriendsAdapter extends BaseAdapter{

    private DBRequestManager requestdb;
    private TableRequestItem requestinfo;

    private DBNotificationManager notificationdb;
    private TableNotificationsItem notification_item;

    long friend_id;
    long my_user_id;
    Context ctx;
    ArrayList<TableUserItem> friendslist;
    LayoutInflater inflater;

    public CustomFindFriendsAdapter(Context context, ArrayList<TableUserItem> friendslist) {
        this.friendslist = friendslist;
        ctx = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        requestdb = new DBRequestManager(ctx);
        notificationdb = new DBNotificationManager(ctx);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TableUserItem friendslistcell = friendslist.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.find_friends_cell, null);
        }

        // press add friend button in listview cell
        Button addfriendsButton = (Button) convertView.findViewById(R.id.buttonAddFriends);
        addfriendsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                // get user id logged in
                SharedPreferences prefs = ctx.getSharedPreferences("UserData", ctx.MODE_PRIVATE);
                String userid_loggedin = prefs.getString("userid", null);
                my_user_id = Long.parseLong(userid_loggedin);


                requestinfo = new TableRequestItem();
                notification_item = new TableNotificationsItem();

                try {
                    requestdb.open();
                    notificationdb.open();

                    friend_id = friendslist.get(position).getId();

                    requestinfo.setFrom_id(my_user_id);
                    requestinfo.setTo_id(friend_id);
                    requestinfo.setAccepted_yn(false);
                    requestinfo.setRequest("friendsRequest");
                    requestinfo.setStatus("Waiting");
                    requestinfo.setRequest_created_at(getCurrentDate());

                    requestdb.insertFriendsRequestInfo(requestinfo);

                    notification_item.setNotificationReceiver_id(friend_id);
                    notification_item.setNotificationSender_id(my_user_id);
                    notification_item.setNotificationMessage("");
                    notification_item.setNotificationType("friend_request");
                    notification_item.setNotificationStatus("Waiting");
                    notification_item.setNotification_created_at(getCurrentDate());

                    notificationdb.insertNotification(notification_item);

                    friendslist.remove(position);

                    AppConstants.alertShow(ctx, "Alert", "sent request successfully");

                    notifyDataSetChanged();
                    requestdb.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        TextView tvUsername = (TextView) convertView.findViewById(R.id.textViewCellEmail);
        // Populate the data into the template view using the data object
        tvUsername.setText(friendslistcell.getEmail());
        // Return the completed view to render on screen
        return convertView;
    }

    // get current date
    private  static String  getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }
}
