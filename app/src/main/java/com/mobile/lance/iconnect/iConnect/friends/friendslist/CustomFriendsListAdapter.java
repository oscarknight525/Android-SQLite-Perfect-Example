package com.mobile.lance.iconnect.iConnect.friends.friendslist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.friends.friendsviewposts.FriendsViewPostActivity;
import com.mobile.lance.iconnect.utils.DBFriendshipManager;
import com.mobile.lance.iconnect.utils.DBRequestManager;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableFriendshipItem;
import com.mobile.lance.iconnect.utils.TableRequestItem;
import com.mobile.lance.iconnect.utils.TableUserItem;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 7/17/16.
 */
public class CustomFriendsListAdapter extends BaseAdapter {

    private DBRequestManager requestdb;
    private TableRequestItem requestinfo;

    private DBUsersManager userdb;
    private TableUserItem userinfo;

    private DBFriendshipManager friendshipdb;
    private TableFriendshipItem friendshipItem;

    Context ctx;
    ArrayList<TableUserItem> friendslist;
    List<Long> friendshipidList;
    LayoutInflater inflater;
    Activity act;

    public CustomFriendsListAdapter(Activity act, ArrayList<TableUserItem> friendslist, List<Long> friendshipidList) {
        this.friendslist = friendslist;
        this.friendshipidList = friendshipidList;
        inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.act = act;
        friendshipdb = new DBFriendshipManager(act);
        userdb = new DBUsersManager(act);
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
//        FriendsListCell friendslistcell = friendslist.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friends_listcell, null);
        }

        // press remove and viewposts button in listview cell
        Button removeButton = (Button) convertView.findViewById(R.id.RemoveButton);
        removeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(act);
                alert.setMessage("Are you sure you want to remove friend?");
                alert.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                try {
                                    friendshipdb.open();
                                    friendshipdb.removeFriendShip(friendshipidList.get(position));
                                    friendslist.remove(position);
                                    CustomFriendsListAdapter.this.notifyDataSetChanged();
                                    friendshipdb.close();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                alert.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                            }
                        });
                alert.show();
            }
        });

        Button viewPostsButton = (Button) convertView.findViewById(R.id.ViewPostsButton);
        viewPostsButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent newIntent = new Intent(act, FriendsViewPostActivity.class);
                newIntent.putExtra(FriendsViewPostActivity.FRIEND_USERID, friendslist.get(position).getId());
                act.startActivity(newIntent);
            }
        });

        TextView tvFullName = (TextView) convertView.findViewById(R.id.textViewFullName);
        // Populate the data into the template view using the data object
        tvFullName.setText(friendslist.get(position).getFirstname() + " " + friendslist.get(position).getLastname());
        tvFullName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppConstants.alertShow(act, "Alert", "View Friend Profile");
                Intent newIntent = new Intent(act, FriendProfile.class);
                newIntent.putExtra(FriendProfile.FRIEND_USERID, friendslist.get(position).getId());
                act.startActivity(newIntent);
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
