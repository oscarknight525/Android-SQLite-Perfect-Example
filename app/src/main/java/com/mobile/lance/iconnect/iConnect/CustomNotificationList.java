package com.mobile.lance.iconnect.iConnect;

import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.lance.iconnect.R;

/**
 * Created by administrator on 6/21/16.
 */
public class CustomNotificationList extends ArrayAdapter<String> {

    private Integer[] imageid;
    private String[] states;
    private String[] description;
    private Activity context;

    public CustomNotificationList(Activity context, String[] states, String[] description, Integer[] imageid) {
        super(context, R.layout.other_notification_listcell, states);
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View notificationListViewItem = inflater.inflate(R.layout.other_notification_listcell, null, true);
        TextView textViewState = (TextView) notificationListViewItem.findViewById(R.id.txt_notification_username);
        TextView textViewDescription = (TextView) notificationListViewItem.findViewById(R.id.txt_other_notification);
        ImageView imageProfile = (ImageView) notificationListViewItem.findViewById(R.id.img_notification_otherprofile);

        textViewState.setText(states[position]);
        textViewDescription.setText(description[position]);
        imageProfile.setImageResource(imageid[position]);
        return  notificationListViewItem;
    }
}
