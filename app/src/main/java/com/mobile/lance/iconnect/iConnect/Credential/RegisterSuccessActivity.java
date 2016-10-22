package com.mobile.lance.iconnect.iConnect.Credential;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mobile.lance.iconnect.R;


public class RegisterSuccessActivity extends AppCompatActivity {

    TextView fname;
    TextView lname;
    TextView email;

    String fName, lName, emailstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register2);

        fname = (TextView)findViewById(R.id.textViewF);
        lname = (TextView)findViewById(R.id.textViewL);
        email = (TextView)findViewById(R.id.textViewE);

        Intent intent = getIntent();
        String fName = intent.getStringExtra("FirstName");
        String lName = intent.getStringExtra("LastName");
        String emailstr = intent.getStringExtra("Email");

        fname.setText(fName);
        lname.setText(lName);
        email.setText(emailstr);
    }

    public void onBackToLoginClick(View v)
    {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
