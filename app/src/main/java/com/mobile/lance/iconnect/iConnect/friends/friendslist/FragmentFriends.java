package com.mobile.lance.iconnect.iConnect.friends.friendslist;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.friends.find.FindFriendsActivity;
import com.mobile.lance.iconnect.iConnect.friends.friendrequest.FriendsRequestActivity;
import com.mobile.lance.iconnect.utils.DBFriendshipManager;
import com.mobile.lance.iconnect.utils.DBRequestManager;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableFriendshipItem;
import com.mobile.lance.iconnect.utils.TableRequestItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FragmentFriends extends Fragment{

    // sqlite database
    private DBUsersManager userdb;
    private TableUserItem userinfo;

    private DBRequestManager requestdb;
    private TableRequestItem requestinfo;

    private DBFriendshipManager friendshipdb;
    private TableFriendshipItem friendshipItem;

    private Context context;
    private String username;
    private long friends_userid;

    private List<Long> friendshipidList;
    ListView listView;
    ArrayList<TableUserItem> arrayOfUsers;
    CustomFriendsListAdapter adapter;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {

        View friendsview = inflater.inflate(R.layout.fragment_friends, container, false);

        listView = (ListView) friendsview.findViewById(R.id.listView);


        userdb = new DBUsersManager(getActivity());
        userinfo = new TableUserItem();

        requestdb = new DBRequestManager(getActivity());
        requestinfo = new TableRequestItem();

        friendshipdb = new DBFriendshipManager(getActivity());
        friendshipItem = new TableFriendshipItem();

        // Change action bar title whenever fragment is switched
        getActivity().setTitle("My Friends");

        // button click event
        Button friendsRequestButton = (Button) friendsview.findViewById(R.id.FindRequestsButton);
        friendsRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent =  new Intent(getActivity(), FriendsRequestActivity.class);
                startActivity(newIntent);
            }
        });

        Button findFriendsButton = (Button) friendsview.findViewById(R.id.FindFriendsButton);
        findFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent =  new Intent(getActivity(), FindFriendsActivity.class);
                startActivity(newIntent);
            }
        });

        return friendsview;
    }

    @Override
    public void onResume() {
        super.onResume();
        friendshipidList = new ArrayList<>();
        arrayOfUsers = new ArrayList<>();

        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", getActivity().MODE_PRIVATE);
        String userid_loggedin = prefs.getString("userid", null);
        long my_id = Long.parseLong(userid_loggedin);
        try {
            friendshipdb.open();
            userdb.open();

            friendshipidList = friendshipdb.getFriendsshipId(my_id);

            for (int i = 0; i < friendshipidList.size(); i++) {
                TableFriendshipItem item = friendshipdb.getFriendshipData(friendshipidList.get(i));

                friends_userid = item.getFriend_id();
                if(my_id == friends_userid){
                    friends_userid = item.getUser_id();
                }
                ArrayList<TableUserItem> getfriends = userdb.getfriendsdata(friends_userid);
                arrayOfUsers.addAll(getfriends);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        adapter = new CustomFriendsListAdapter(getActivity(), arrayOfUsers, friendshipidList);
        listView.setAdapter(adapter);
    }
}
