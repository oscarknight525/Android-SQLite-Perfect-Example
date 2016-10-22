package com.mobile.lance.iconnect.iConnect.Credential;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private TableUserItem userinfo;
    private DBUsersManager userdb;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register1);
        context = this;
        userdb = new DBUsersManager(context);
    }

    // press the signup button
    public void onSignupClick(View v)
    {
        if (v.getId() == R.id.SignupButton){

            EditText fname = (EditText) findViewById(R.id.TextFirstName);
            EditText lname = (EditText) findViewById(R.id.TextLastName);
            EditText email = (EditText) findViewById(R.id.TextEmail);
            EditText password = (EditText) findViewById(R.id.TextPassword);
            EditText confirm_password = (EditText) findViewById(R.id.TextConfirmPassword);

            String fnamestr = fname.getText().toString();
            String lnamestr = lname.getText().toString();
            String emailstr = email.getText().toString().trim();
            String passwordstr = password.getText().toString();
            String confirm_passwordstr = confirm_password.getText().toString();

            // valid email address
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            // checking password
            if (fnamestr.isEmpty()) {
                AppConstants.alertShow(context, "Alert", "Please enter your first name");
            } else if (lnamestr.isEmpty()){
                AppConstants.alertShow(context, "Alert", "Please enter your last name");
            } else if (emailstr.isEmpty()){
                AppConstants.alertShow(context, "Alert", "Please enter your email address");
            } else if (passwordstr.isEmpty()){
                AppConstants.alertShow(context, "Alert", "Please enter your password");
            } else if (!emailstr.matches(emailPattern)){
                AppConstants.alertShow(context, "Alert", "Invalid Email Address!");
            } else if (!(passwordstr.equals(confirm_passwordstr))){
                AppConstants.alertShow(context, "Alert", "Password doesn't match!");
            } else {

                userinfo = new TableUserItem();
                boolean isEmailExist = false;
                try {
                    userdb.open();

                    isEmailExist = userdb.checkEmailExist(emailstr);

                    if(!isEmailExist) {
                        userinfo.setFirstname(fnamestr);
                        userinfo.setLastname(lnamestr);
                        userinfo.setEmail(emailstr);
                        userinfo.setPassword(passwordstr);
                        userinfo.setImage_path("");
                        userinfo.setCreated_at_users(getCurrentDate());
                        userinfo.setUpdated_at_users(getCurrentDate());
                        userinfo.setLog_in_count(1);
                        userdb.insertusers(userinfo);
                    }
                    userdb.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(!isEmailExist) {
                    Intent intent = new Intent(this, RegisterSuccessActivity.class);
                    intent.putExtra("FirstName", fnamestr);
                    intent.putExtra("LastName", lnamestr);
                    intent.putExtra("Email", emailstr);
                    startActivity(intent);
                }
                else{
                    AppConstants.alertShow(this,"Alert","Email Exist! Please input other email.");
                }
            }


        }
    }

    // press the cancel button
    public void onCancelLoginClick(View v)
    {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    private  static String  getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }
}
