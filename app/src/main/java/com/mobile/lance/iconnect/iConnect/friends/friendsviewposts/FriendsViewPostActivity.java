package com.mobile.lance.iconnect.iConnect.friends.friendsviewposts;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.friends.find.CustomFindFriendsAdapter;
import com.mobile.lance.iconnect.iConnect.friends.friendslist.CustomFriendsListAdapter;
import com.mobile.lance.iconnect.iConnect.friends.friendslist.FriendsListCell;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBPostManager;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TablePostsItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by administrator on 7/17/16.
 */
public class FriendsViewPostActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    ArrayList<TablePostsItem> arrayOfPosts;
    CustomFriendsViewPostAdapter adapter;
    Intent intent;
    private long friend_id;
    public static String FRIEND_USERID = "FRIEND ID";

    private DBPostManager postdb;

    private Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_viewpost);

        intent = getIntent();
        friend_id = intent.getLongExtra(FRIEND_USERID,-2);
        postdb = new DBPostManager(this);
        arrayOfPosts = new ArrayList<>();
        try {
            postdb.open();
            ArrayList<TablePostsItem> getpostdata = postdb.getPostList(friend_id);
            arrayOfPosts.addAll(getpostdata);
            postdb.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(arrayOfPosts.size() == 0){
            AppConstants.alertShow(this,"Alert","There are no Posts");
        }



        adapter = new CustomFriendsViewPostAdapter(this, arrayOfPosts);
        listView = (ListView) findViewById(R.id.list_friendposts);
        listView.setAdapter(adapter);

        setTitle("Friend's Posts");

        btn_back = (Button)findViewById(R.id.btn_frind_viewpost_back);
        btn_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_frind_viewpost_back:
                finish();
                break;
        }
    }
}
