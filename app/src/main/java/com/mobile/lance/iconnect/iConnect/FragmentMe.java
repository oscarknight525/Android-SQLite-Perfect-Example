package com.mobile.lance.iconnect.iConnect;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.Credential.MainActivity;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.DBUsersprofilesManager;
import com.mobile.lance.iconnect.utils.MySQLiteHelper;
import com.mobile.lance.iconnect.utils.TableUserItem;
import com.mobile.lance.iconnect.utils.TableUsersProfilesItem;

import java.io.File;
import java.sql.SQLException;
import java.lang.String;

public class FragmentMe extends Fragment {

    private TableUserItem userinfo;
    private DBUsersManager userdb;

    private TableUsersProfilesItem usersprofilesinfo;
    private DBUsersprofilesManager usersprofilesdb;

    private Context context;
    private int logined_user_id = 0;

    ImageView imgPicture;
    Bitmap profileImage = null;

    TextView fullname;
    TextView firstname;
    TextView lastname;
    TextView email;
    TextView phonenumber;
    TextView address;
    TextView city;
    TextView state;
    TextView zipcode;
    TextView birthDate;
    TextView gender;
    TextView description;

    long userid;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPref = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userid = Long.parseLong(sharedPref.getString("userid", "0"));

        context = getActivity().getApplicationContext();

        View view = inflater.inflate(R.layout.fragment_me, container, false);

        // Change action bar title whenever fragment is switched
        getActivity().setTitle("Me");

        imgPicture = (ImageView)view.findViewById(R.id.img_viewprofile_photo);

        fullname = (TextView)view.findViewById(R.id.textViewName);
        firstname = (TextView)view.findViewById(R.id.textViewFName);
        lastname = (TextView)view.findViewById(R.id.textViewLName);
        email = (TextView)view.findViewById(R.id.textViewEmailAddress);
        phonenumber = (TextView)view.findViewById(R.id.textViewPhonenumber);
        address = (TextView)view.findViewById(R.id.textViewAddress);
        city = (TextView)view.findViewById(R.id.textViewCity);
        state = (TextView)view.findViewById(R.id.textViewState);
        zipcode = (TextView)view.findViewById(R.id.textViewZipCode);
        birthDate = (TextView)view.findViewById(R.id.textViewBirthDate);
        gender = (TextView)view.findViewById(R.id.textViewGender);
        description = (TextView)view.findViewById(R.id.textViewDescription);



        userdb = new DBUsersManager(context);
        usersprofilesdb = new DBUsersprofilesManager(context);


        Button editProfileButton = (Button) view.findViewById(R.id.EditProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(newIntent);
            }
        });

        Button logoutButton = (Button) view.findViewById(R.id.LogoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(newIntent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        usersprofilesinfo = new TableUsersProfilesItem();
        try {
            userdb.open();
            usersprofilesdb.open();

            TableUserItem getuserData = userdb.getuserdbItem(userid);

            firstname.setText(getuserData.getFirstname());
            lastname.setText(getuserData.getLastname());
            email.setText(getuserData.getEmail());

            String fullnamestr = firstname.getText().toString() + " " + lastname.getText().toString();

            fullname.setText(fullnamestr);

            if(!getuserData.getImage_path().equals("")){
                String picturePath = getuserData.getImage_path();
                File file = new File(picturePath);
                if(file.exists()) {
                    Bitmap thumbnail = null;
                    try {
                        thumbnail = (BitmapFactory.decodeFile(picturePath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (OutOfMemoryError e) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 10;
                        thumbnail = (BitmapFactory.decodeFile(picturePath, options));
                    }
                    profileImage = Bitmap.createScaledBitmap(thumbnail, 200, 200, true);
                    imgPicture.setImageBitmap(profileImage);
                }
            }

            boolean userflag = usersprofilesdb.getdbItem(userid);
            if(userflag) {
                TableUsersProfilesItem getuserprofileData = usersprofilesdb.getuserprofiledbItem(userid);

                phonenumber.setText(getuserprofileData.getPhone_number_cell());
                address.setText(getuserprofileData.getAddress());
                city.setText(getuserprofileData.getCity());
                state.setText(getuserprofileData.getState());
                zipcode.setText(getuserprofileData.getZipcode());
                birthDate.setText(getuserprofileData.getBirth_date());
                gender.setText(getuserprofileData.getGender());
                description.setText(getuserprofileData.getDetails());
            } else {
                phonenumber.setText("");
                address.setText("");
                city.setText("");
                state.setText("");
                zipcode.setText("");
                birthDate.setText("");
                gender.setText("");
                description.setText("");
            }

            userdb.close();
            usersprofilesdb.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
