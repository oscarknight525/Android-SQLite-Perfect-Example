package com.mobile.lance.iconnect.iConnect;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.Credential.MainActivity;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.DBUsersprofilesManager;
import com.mobile.lance.iconnect.utils.TableUserItem;
import com.mobile.lance.iconnect.utils.TableUsersProfilesItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by administrator on 6/22/16.
 */
public class EditProfileActivity extends AppCompatActivity{

    private TableUsersProfilesItem usersprofilesinfo;
    private DBUsersprofilesManager usersprofilesdb;

    private DBUsersManager userdb;
    private TableUserItem userData;

    private Context context;
    private int logined_user_id = 0;

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    ImageView imgPicture;

    Bitmap profileImage = null;
    private String picturePath;

    private EditText uname;
    private EditText fname;
    private EditText lname;
    private EditText address;
    private EditText city;
    private EditText state;
    private EditText zipcode;
    private EditText phonenumber;
    private EditText birthdate;
    private EditText gender;
    private EditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imgPicture = (ImageView)findViewById(R.id.img_editprofile_photo);
        picturePath = "";

        context = this;
        usersprofilesdb = new DBUsersprofilesManager(context);
        userdb = new DBUsersManager(this);

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String restoredText = prefs.getString("userid", null);
        if (restoredText != null) {
            logined_user_id = Integer.parseInt(restoredText); //0 is the default value.
        }

        uname = (EditText) findViewById(R.id.editTextUserName);
        fname = (EditText) findViewById(R.id.editTextF);
        lname = (EditText) findViewById(R.id.editTextL);
        address = (EditText) findViewById(R.id.editTextAddress);
        city = (EditText) findViewById(R.id.editTextCity);
        state = (EditText) findViewById(R.id.editTextState);
        zipcode = (EditText) findViewById(R.id.editTextCode);
        phonenumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        birthdate = (EditText) findViewById(R.id.editTextBirth);
        gender = (EditText) findViewById(R.id.editTextGender);
        description = (EditText) findViewById(R.id.editTextDescription);

        try {
            userdb.open();
            usersprofilesdb.open();
            userData = userdb.getuserdbItem(logined_user_id);

            fname.setText(userData.getFirstname());
            lname.setText(userData.getLastname());

            boolean userflag = usersprofilesdb.getdbItem(logined_user_id);
            if(userflag) {
                TableUsersProfilesItem getuserprofileData = usersprofilesdb.getuserprofiledbItem(logined_user_id);

                uname.setText(getuserprofileData.getUser_name());
                phonenumber.setText(getuserprofileData.getPhone_number_cell());
                address.setText(getuserprofileData.getAddress());
                city.setText(getuserprofileData.getCity());
                state.setText(getuserprofileData.getState());
                zipcode.setText(getuserprofileData.getZipcode());
                birthdate.setText(getuserprofileData.getBirth_date());
                gender.setText(getuserprofileData.getGender());
                description.setText(getuserprofileData.getDetails());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateBirthdate();
            }

        };

        birthdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateBirthdate() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdate.setText(sdf.format(myCalendar.getTime()));
    }

    // press the signup button
    public void onSaveProfileClick(View v) {

        String unamestr = uname.getText().toString();
        String fnamestr = fname.getText().toString();
        String lnamestr = lname.getText().toString();
        String addresstr = address.getText().toString();
        String citystr = city.getText().toString();
        String statestr = state.getText().toString();
        String zipcodestr = zipcode.getText().toString();
        String phonenumberstr = phonenumber.getText().toString();
        String birthdatestr = birthdate.getText().toString();
        String genderstr = gender.getText().toString();
        String descriptionstr = description.getText().toString();

        String numberPattern = "\\d+(?:\\.\\d+)?";
        String genderPattern = "[a-zA-Z]+";
        // checking password
        if (unamestr.isEmpty() || fnamestr.isEmpty() || lnamestr.isEmpty() || addresstr.isEmpty() || citystr.isEmpty() || statestr.isEmpty() || zipcodestr.isEmpty() || phonenumberstr.isEmpty() || birthdatestr.isEmpty() || genderstr.isEmpty() || descriptionstr.isEmpty()) {
            AppConstants.alertShow(context, "Alert", "Please fill in all fields");
        } else if (lnamestr.isEmpty()) {
            AppConstants.alertShow(context, "Alert", "Please upload your image");
        }
        else if(addresstr.matches(numberPattern)){
            AppConstants.alertShow(context, "Alert", "Please input correct address");
        }
        else if(citystr.matches(numberPattern)){
            AppConstants.alertShow(context, "Alert", "Please input correct city");
        }
        else if(statestr.matches(numberPattern)){
            AppConstants.alertShow(context, "Alert", "Please input correct state");
        }
        else if(!genderstr.matches(genderPattern)){
            AppConstants.alertShow(context, "Alert", "Please input correct gender");
        }
        else {
            usersprofilesinfo = new TableUsersProfilesItem();

            try {
                usersprofilesdb.open();
                userdb.open();

                userdb.updateUserImagePath(logined_user_id, picturePath);

                usersprofilesinfo.setUser_name(unamestr);
                usersprofilesinfo.setAddress(addresstr);
                usersprofilesinfo.setCity(citystr);
                usersprofilesinfo.setState(statestr);
                usersprofilesinfo.setZipcode(zipcodestr);
                usersprofilesinfo.setPhone_number_cell(phonenumberstr);
                usersprofilesinfo.setBirth_date(birthdatestr);
                usersprofilesinfo.setGender(genderstr);
                usersprofilesinfo.setDetails(descriptionstr);

                boolean userflag = usersprofilesdb.getdbItem(logined_user_id);
                if(userflag) {
                    usersprofilesdb.updateUsersProfiles(usersprofilesinfo, logined_user_id);
                    AppConstants.alertShow(context, "Alert", "All data is updated successfully");
                    finish();
                } else {
                    usersprofilesdb.insertUsersProfiles(usersprofilesinfo, logined_user_id);
                    AppConstants.alertShow(context, "Alert", "All data is registered successfully");
                    finish();
                }

                userdb.close();
                usersprofilesdb.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // press the cancel button
    public void onClearFormButtonClick(View v) {
        uname.setText("");
        fname.setText("");
        lname.setText("");
        address.setText("");
        city.setText("");
        state.setText("");
        zipcode.setText("");
        phonenumber.setText("");
        birthdate.setText("");
        gender.setText("");
        description.setText("");
    }

    public void onImageGalleryClicked(View v){
        final CharSequence[] items = { "Take Photo", "Choose from Gallery", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        Uri selectedImage = data.getData();
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        picturePath = c.getString(columnIndex);
        c.close();
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
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(picturePath);
        } catch (IOException e) {
            picturePath = "";
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        int degree;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
            default:
                degree = 0;
                break;
        }
        Matrix matrix = new Matrix();
        if (degree != 0f) {
            matrix.preRotate(degree);
        }
        Bitmap adjustedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(), thumbnail.getHeight(), matrix, true);
        profileImage = Bitmap.createScaledBitmap(adjustedBitmap, 200, 200, true);
        imgPicture.setImageBitmap(profileImage);
    }
}


