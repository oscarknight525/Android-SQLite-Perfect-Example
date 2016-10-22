package com.mobile.lance.iconnect.iConnect.notification;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.message.CustomMessagesAdapter;
import com.mobile.lance.iconnect.iConnect.myPost.CreatePostActivity;
import com.mobile.lance.iconnect.iConnect.myPost.CustomMyPostAdapter;
import com.mobile.lance.iconnect.iConnect.myPost.MyPostActivity;
import com.mobile.lance.iconnect.iConnect.myPost.MyPostCell;
import com.mobile.lance.iconnect.utils.DBFriendshipManager;
import com.mobile.lance.iconnect.utils.DBNotificationManager;
import com.mobile.lance.iconnect.utils.TableFriendshipItem;
import com.mobile.lance.iconnect.utils.TableMessagesItem;
import com.mobile.lance.iconnect.utils.TableNotificationsItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FragmentNotification extends Fragment{

    private CustomNotificationsAdapter adapter;
    private ArrayList<TableNotificationsItem> arrayOfNotification;

    private DBNotificationManager notificationdb;
    private DBFriendshipManager friendshipdb;

    private ListView listview_notification;

    private List<Long> friendshipidList;
    private List<Long> friendid_list;
    private List<Long> notificationid_List;
    private long my_user_id;
    private long notification_id;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        listview_notification = (ListView) view.findViewById(R.id.listViewNotification);
        // Change action bar title whenever fragment is swtiched
        getActivity().setTitle("Notifications");

        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", getActivity().MODE_PRIVATE);
        String userid_loggedin = prefs.getString("userid", null);
        my_user_id = Long.parseLong(userid_loggedin);

        notificationdb = new DBNotificationManager(getActivity());
        friendshipdb = new DBFriendshipManager(getActivity());

        Button postButton = (Button) view.findViewById(R.id.CreatePostsButton);
        postButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getActivity(), CreatePostActivity.class);
                startActivity(newIntent);
            }
        });

        Button mypostButton = (Button) view.findViewById(R.id.Mypostbutton);
        mypostButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getActivity(), MyPostActivity.class);
                startActivity(newIntent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayOfNotification = new ArrayList<>();
        notificationid_List = new ArrayList<>();
        friendshipidList = new ArrayList<>();
        friendid_list = new ArrayList<>();

        try{
            notificationdb.open();
            friendshipdb.open();

            friendshipidList = friendshipdb.getFriendsshipId(my_user_id);
            for (int i = 0; i < friendshipidList.size(); i++) {
                TableFriendshipItem item = friendshipdb.getFriendshipData(friendshipidList.get(i));

                long friends_userid = item.getFriend_id();
                if(my_user_id == friends_userid){
                    friends_userid = item.getUser_id();
                }
                friendid_list.add(friends_userid);
            }


            notificationid_List = notificationdb.getNotificationIDs(my_user_id, friendid_list);

            for (int i = 0; i < notificationid_List.size(); i++){
                notification_id = notificationid_List.get(i);
                TableNotificationsItem item = notificationdb.getNotificationInfo(notification_id);
                arrayOfNotification.add(item);
            }
            notificationdb.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        adapter = new CustomNotificationsAdapter(getActivity(), arrayOfNotification);
        listview_notification.setAdapter(adapter);
    }
}
