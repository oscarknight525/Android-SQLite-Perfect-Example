package com.mobile.lance.iconnect.iConnect.myPost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableCommentsItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by administrator on 7/21/16.
 */
public class CustomMyCommentsAdapter extends BaseAdapter{


    private DBUsersManager userdb;
    private TableUserItem userinfo;

    long user_id;

    Context ctx;
    ArrayList<TableCommentsItem> commentslist;
    LayoutInflater inflater;

    public CustomMyCommentsAdapter(Context context, ArrayList<TableCommentsItem> commentslist) {
        this.commentslist = commentslist;
        ctx = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userdb = new DBUsersManager(ctx);
        userinfo = new TableUserItem();
    }

    @Override
    public int getCount() {
        return commentslist.size();
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
        TableCommentsItem commentslistcell = commentslist.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_comment_cell, null);
        }

        // press add friend button in listview cell
        user_id = commentslistcell.getComment_user_id();
        try {
            userdb.open();
            userinfo = userdb.getuserdbItem(user_id);
        }catch (SQLException e){
            e.printStackTrace();
        }

        TextView tvUsername = (TextView) convertView.findViewById(R.id.txt_comment_username);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.txt_comment_description);
        // Populate the data into the template view using the data object
        tvUsername.setText(userinfo.getFirstname() + " " + userinfo.getLastname() + " :");
        tvDescription.setText(commentslistcell.getComment());
        // Return the completed view to render on screen
        return convertView;
    }

}
