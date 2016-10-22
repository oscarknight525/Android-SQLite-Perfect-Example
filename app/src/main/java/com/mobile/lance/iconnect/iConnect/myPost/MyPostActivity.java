package com.mobile.lance.iconnect.iConnect.myPost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBPostManager;
import com.mobile.lance.iconnect.utils.MySQLiteHelper;
import com.mobile.lance.iconnect.utils.TablePostsItem;
import com.mobile.lance.iconnect.utils.TableUsersProfilesItem;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by administrator on 7/7/16.
 */
public class MyPostActivity extends AppCompatActivity implements View.OnClickListener{

    private DBPostManager postdb;
    private TablePostsItem postinfo;

    private String post;
    private String post_date;
    private int logined_user_id = 0;

    private Context context;

    ListView                listView;
    ArrayList<TablePostsItem>   arrayOfUsers;
    CustomMyViewPostAdapter     adapter;

    private Button btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        listView = (ListView) findViewById(R.id.lvMyPost);
        btn_back = (Button) findViewById(R.id.btn_mypost_back) ;
        btn_back.setOnClickListener(this);


        context = this;
        postdb = new DBPostManager(context);
        postinfo = new TablePostsItem();

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String restoredText = prefs.getString("userid", null);
        if (restoredText != null) {
            logined_user_id = Integer.parseInt(restoredText); //0 is the default value.
        }





    }

    public void onClickCreatePostOnMyPost(View v)
    {
        Intent intent = new Intent (this, CreatePostActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayOfUsers = new ArrayList<>();
        try {
            postdb.open();
            ArrayList<TablePostsItem> getpostdata = postdb.getPostList(logined_user_id);
            arrayOfUsers.addAll(getpostdata);
            postdb.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter = new CustomMyViewPostAdapter(this, arrayOfUsers);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mypost_back:
                finish();
                break;
        }
    }
}
