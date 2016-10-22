package com.mobile.lance.iconnect.iConnect.Credential;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.FragmentMe;
import com.mobile.lance.iconnect.iConnect.notification.NotificationActivity;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private FragmentMe fragmentMe;

    private Context context;
    private boolean userlogin_flag;

    // sqlite database
    private TableUserItem userinfo;
    private DBUsersManager userdb;

    // user texts
    private EditText email_txt;
    private EditText password_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        userdb = new DBUsersManager(context);
        userlogin_flag = false;

        email_txt    = (EditText)findViewById(R.id.TextLoginEmail);
        password_txt = (EditText)findViewById(R.id.TextLoginPassword);
    }

    public void onRegisterClick(View v)
    {
        Intent intent = new Intent (this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onLoginClick(View v)
    {
        String user_email = email_txt.getText().toString();
        String user_password = password_txt.getText().toString();

        // valid email address
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        try{
            userdb.open();

            long userid = userdb.loginsearchusername(user_email, user_password);

            if (user_email.isEmpty()){
                AppConstants.alertShow(context, "Alert", "Please enter your email address");
            } else if (user_password.isEmpty()){
                AppConstants.alertShow(context, "Alert", "Please enter your password");
            } else if (!user_email.matches(emailPattern)){
                AppConstants.alertShow(context, "Alert", "Invalid Email address!");
            } else if (userid == -2){
                AppConstants.alertShow(context, "Alert", "User doesn't exist. Please sign up first!");
            } else if (userid == -1){
                AppConstants.alertShow(context, "Error", "Password is invalid");
            } else {

                SharedPreferences sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("userid",  "" + userid);
                editor.commit();

                Intent intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                finish();
            }

            userdb.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
