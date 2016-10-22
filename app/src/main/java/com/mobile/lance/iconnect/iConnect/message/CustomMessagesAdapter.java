package com.mobile.lance.iconnect.iConnect.message;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBMessageManager;
import com.mobile.lance.iconnect.utils.DBRequestManager;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableMessagesItem;
import com.mobile.lance.iconnect.utils.TableRequestItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by administrator on 7/21/16.
 */
public class CustomMessagesAdapter extends BaseAdapter{


    private DBUsersManager userdb;
    private TableUserItem userinfo;

    private DBMessageManager messagedb;

    long my_user_id;

    Context ctx;
    ArrayList<TableMessagesItem> messagesslist;
    LayoutInflater inflater;

    private float icon_width;
    private float icon_height;
    public CustomMessagesAdapter(Context context, ArrayList<TableMessagesItem> messagesslist) {
        this.messagesslist = messagesslist;
        ctx = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userdb = new DBUsersManager(ctx);
        messagedb = new DBMessageManager(ctx);
        SharedPreferences prefs = ctx.getSharedPreferences("UserData", ctx.MODE_PRIVATE);
        String userid_loggedin = prefs.getString("userid", null);
        my_user_id = Long.parseLong(userid_loggedin);
    }

    @Override
    public int getCount() {
        return messagesslist.size();
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
        TableMessagesItem friendslistcell = messagesslist.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        TextView tvMessage = null;
        convertView = null;
        if (convertView == null) {
            if(my_user_id == messagesslist.get(position).getReceiver_id()) {
                convertView = inflater.inflate(R.layout.message_receive_cell, null);

                tvMessage = (TextView) convertView.findViewById(R.id.txt_mesage_receive);
                Drawable img = ctx.getResources().getDrawable(
                        R.mipmap.message_down);
                icon_width = 22 * AppConstants.Screen_Density;
                icon_height = 22 * AppConstants.Screen_Density;
                img.setBounds(0, 0, (int)icon_width, (int)icon_height);
                tvMessage.setCompoundDrawables(img, null, null, null);
                tvMessage.setCompoundDrawablePadding((int)(6 * AppConstants.Screen_Density));
            }
            else {
                convertView = inflater.inflate(R.layout.message_send_cell, null);
                tvMessage = (TextView) convertView.findViewById(R.id.txt_mesage_send);
                Drawable img = ctx.getResources().getDrawable(
                        R.mipmap.message_up);
                icon_width = 22 * AppConstants.Screen_Density;
                icon_height = 22 * AppConstants.Screen_Density;
                img.setBounds(0, 0, (int)icon_width, (int)icon_height);
                tvMessage.setCompoundDrawables(null, null, img, null);
                tvMessage.setCompoundDrawablePadding((int)(6 * AppConstants.Screen_Density));
            }
        }

        tvMessage.setText(messagesslist.get(position).getMessage());

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
