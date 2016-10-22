package com.mobile.lance.iconnect.iConnect.friends.find;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.text.TextWatcher;
import android.text.Editable;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.AppConstants;
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
 * Created by administrator on 6/22/16.
 */
public class FindFriendsActivity extends AppCompatActivity implements View.OnClickListener{

    // sqlite database
    private TableUserItem userinfo;
    private DBUsersManager userdb;

    private DBRequestManager requestdb;
    private DBFriendshipManager friendshipdb;
    private List<Long> frienshipid_list;

    private TableFriendshipItem friendshipItem;

    private Context context;
    private String username;
    private long friend_userid;
    private long my_user_id;

    ListView listView;
    ArrayList<TableUserItem> arrayOfUsers;
    CustomFindFriendsAdapter adapter;

    private EditText etSearh;
    private Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        arrayOfUsers = new ArrayList<>();
        adapter = new CustomFindFriendsAdapter(this, arrayOfUsers);
        listView = (ListView) findViewById(R.id.listViewFindFriends);
        listView.setAdapter(adapter);

        btn_back = (Button) findViewById(R.id.btn_find_friend_back);
        btn_back.setOnClickListener(this);

        context = this;
        userdb = new DBUsersManager(context);
        userinfo = new TableUserItem();
        friendshipdb = new DBFriendshipManager(context);
        requestdb = new DBRequestManager(context);

        friendshipItem = new TableFriendshipItem();
    }

    // press the search button
    public void onSearchFriendsClick(View v)
    {
        etSearh = (EditText)findViewById(R.id.editTextSearch);
        String searchstr = etSearh.getText().toString();

//        SharedPreferences sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("friends_email",  "" + searchstr);
//        editor.commit();

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String userid_loggedin = prefs.getString("userid", null);
        my_user_id = Long.parseLong(userid_loggedin);

        // check if searchstr is equal to email
        try{
            userdb.open();
            requestdb.open();
            friendshipdb.open();

            boolean searchflag = userdb.searchfriend(searchstr);
            if(searchflag) {
                // display friends to listview
                friend_userid = userdb.getFriendsUserid(searchstr, my_user_id);
                boolean isWaiting = requestdb.isWaitingOfRequest(friend_userid, my_user_id);
                boolean isFriendWaiting = requestdb.isWaitingOfRequest(my_user_id, friend_userid);
                boolean isFriend = false;

                frienshipid_list = friendshipdb.getFriendsshipId(my_user_id);

                for(int i = 0; i < frienshipid_list.size(); i++){
                    friendshipItem = friendshipdb.getFriendshipData(frienshipid_list.get(i));
                    long friend_id = friendshipItem.getFriend_id();
                    if(my_user_id == friend_id){
                        friend_id = friendshipItem.getUser_id();
                    }
                    if(friend_id == friend_userid){
                        isFriend = true;
                    }
                }

                if(!isWaiting && !isFriendWaiting && !isFriend) {
                    ArrayList<TableUserItem> getfriends = userdb.getfriendsdata(friend_userid);
                    arrayOfUsers.addAll(getfriends);
                    adapter.notifyDataSetChanged();
                }
                else if(isFriend){
                    AppConstants.alertShow(context, "Alert", "This user is one of your friends.");
                }
                else if(isFriendWaiting){
                    AppConstants.alertShow(context, "Alert", "You have received friend request already.");
                }
                else{
                    AppConstants.alertShow(context, "Alert", "You have sent friend request already.");
                }
            } else {
                AppConstants.alertShow(context, "Alert", "It doesn't be existed friends that you are finding");
            }

            userdb.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

        // EditText changed event
        etSearh.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayOfUsers.clear();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arrayOfUsers.clear();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_find_friend_back:
                finish();
                break;
        }
    }
}
