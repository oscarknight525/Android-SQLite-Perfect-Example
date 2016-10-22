package com.mobile.lance.iconnect.iConnect.myPost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.TablePostsItem;

import java.util.ArrayList;

/**
 * Created by administrator on 7/8/16.
 */
public class CustomMyPostAdapter extends BaseAdapter {

    Context                 ctx;
    ArrayList<TablePostsItem>   myposts;
    LayoutInflater			inflater;

    public CustomMyPostAdapter(Context context, ArrayList<TablePostsItem> myposts) {
        this.myposts = myposts;
        ctx = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myposts.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TablePostsItem mypostcell = myposts.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_my_post_cell, null);
        }

        TextView tvDate = (TextView) convertView.findViewById(R.id.textViewPostDate);
        TextView tvPostContent = (TextView) convertView.findViewById(R.id.textViewPostContent);
        // Populate the data into the template view using the data object
        tvDate.setText(mypostcell.getPost_created_at());
        tvPostContent.setText(mypostcell.getPost());
        // Return the completed view to render on screen
        return convertView;
    }
}
