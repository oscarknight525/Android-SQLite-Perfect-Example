package com.mobile.lance.iconnect.iConnect.friends.friendslist;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.Credential.MainActivity;
import com.mobile.lance.iconnect.iConnect.EditProfileActivity;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.DBUsersprofilesManager;
import com.mobile.lance.iconnect.utils.TableUserItem;
import com.mobile.lance.iconnect.utils.TableUsersProfilesItem;

import java.io.File;
import java.sql.SQLException;

public class FriendProfile extends AppCompatActivity {

    private TableUserItem userinfo;
    private DBUsersManager userdb;

    private TableUsersProfilesItem usersprofilesinfo;
    private DBUsersprofilesManager usersprofilesdb;

    Intent intent;
    private long friend_id;

    public static String FRIEND_USERID = "FRIEND ID";

    private ImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        friend_id = intent.getLongExtra(FRIEND_USERID,-2);

        setContentView(R.layout.activity_friends_profile);

        // Change action bar title whenever fragment is switched
        setTitle("Friend's Profile");

        profile_image = (ImageView) findViewById(R.id.img_viewfriend_profile);

        TextView fullname = (TextView)findViewById(R.id.txt_friend_fullname);
        TextView firstname = (TextView)findViewById(R.id.txt_friend_firstname);
        TextView lastname = (TextView)findViewById(R.id.txt_friend_lastname);
        TextView email = (TextView)findViewById(R.id.txt_friend_email);
        TextView phonenumber = (TextView)findViewById(R.id.txt_friend_phone);
        TextView description = (TextView)findViewById(R.id.txt_friend_description);
        Button btn_back = (Button) findViewById(R.id.btn_friend_profile_back);
        usersprofilesinfo = new TableUsersProfilesItem();

        userdb = new DBUsersManager(this);
        usersprofilesdb = new DBUsersprofilesManager(this);

        try {
            userdb.open();
            usersprofilesdb.open();

            TableUserItem getuserData = userdb.getuserdbItem(friend_id);

            firstname.setText(getuserData.getFirstname());
            lastname.setText(getuserData.getLastname());
            email.setText(getuserData.getEmail());

            String fullnamestr = firstname.getText().toString() + " " + lastname.getText().toString();

            if(!getuserData.getImage_path().equals("")){
                String picturePath = getuserData.getImage_path();
                File file = new File(picturePath);
                if(file.exists()) {
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    Bitmap adjustedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight());
                    Bitmap profileImage = Bitmap.createScaledBitmap(adjustedBitmap, 200, 200, true);
                    profile_image.setImageBitmap(profileImage);
                }
            }

            fullname.setText(fullnamestr);

            boolean userflag = usersprofilesdb.getdbItem(friend_id);
            if(userflag) {
                TableUsersProfilesItem getuserprofileData = usersprofilesdb.getuserprofiledbItem(friend_id);

                phonenumber.setText(getuserprofileData.getPhone_number_cell());
                description.setText(getuserprofileData.getDetails());
            } else {
                phonenumber.setText("");
                description.setText("");
            }

            userdb.close();
            usersprofilesdb.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
