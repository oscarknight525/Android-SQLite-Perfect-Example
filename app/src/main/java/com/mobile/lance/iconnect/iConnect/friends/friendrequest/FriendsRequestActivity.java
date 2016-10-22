package com.mobile.lance.iconnect.iConnect.friends.friendrequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.friends.find.CustomFindFriendsAdapter;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBRequestManager;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableRequestItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 7/14/16.
 */
public class FriendsRequestActivity extends AppCompatActivity {

    // sqlite database
    private DBUsersManager userdb;
    private TableUserItem userinfo;

    private DBRequestManager requestdb;
    private TableRequestItem requestinfo;
    private  ArrayList<TableRequestItem> requestList;

    private Context context;
    private String username;
    private long friends_userid;
    private List<Long> freindslist;

    ListView listView;
    ArrayList<TableUserItem> arrayOfUsers;
    CustomFriendsRequestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_requests);

        freindslist = new ArrayList<>();
        requestList = new ArrayList<>();
        arrayOfUsers = new ArrayList<>();

        adapter = new CustomFriendsRequestAdapter(this, arrayOfUsers, requestList);
        listView = (ListView) findViewById(R.id.listView3);
        listView.setAdapter(adapter);

        context = this;
        userdb = new DBUsersManager(context);
        userinfo = new TableUserItem();

        requestdb = new DBRequestManager(context);
        requestinfo = new TableRequestItem();
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String userid_loggedin = prefs.getString("userid", null);
        long my_user_id = Long.parseLong(userid_loggedin);
        try{
            requestdb.open();
            userdb.open();



                freindslist = requestdb.getFriendRequestsToid(my_user_id);
                for(int i=0; i<freindslist.size();i++)
                {
                    friends_userid = freindslist.get(i);
                    requestinfo = requestdb.getRequestsInfo(freindslist.get(i));
                    requestList.add(requestinfo);
                }

                for(int i=0; i<requestList.size();i++)
                {
                    friends_userid = requestList.get(i).getFrom_id();
                    ArrayList<TableUserItem> getfriends = userdb.getfriendsdata(friends_userid);
                    arrayOfUsers.addAll(getfriends);
                }


                adapter.notifyDataSetChanged();

                if(freindslist.isEmpty())
                    AppConstants.alertShow(context, "Alert", "There are no friends request yet");

            userdb.close();
            requestdb.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    // back and create post buttons actions
    public void onBackClick(View v) {
        super.onBackPressed();
    }
}
