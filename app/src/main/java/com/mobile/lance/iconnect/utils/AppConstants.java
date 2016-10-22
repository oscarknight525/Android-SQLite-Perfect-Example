package com.mobile.lance.iconnect.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.json.JSONObject;

/**
 * Created by administrator on 7/1/16.
 */
public class AppConstants {
//    public static final String   SERVER_USER_LOGIN      = "http://spacare.co/merchant/users";           // Fetching all Users
//    public static final String   SERVER_CUSTOMERS_LOAD  = "http://spacare.co/merchant/customers";       // Fetching all Customers
//    public static final String   SERVER_CONTACTS_LOAD   = "http://spacare.co/merchant/contacts";        // Fetching all Contacts
//    public static final String   SERVER_ITEMS_LOAD      = "http://spacare.co/merchant/items";           // Fetching all Items
//    public static final String   SERVER_VISITS_LOAD     = "http://spacare.co/merchant/visits";          // Fetching all visits
//    public static final String   SERVER_VISIT_INSERT    = "http://spacare.co/merchant/visitdetails";    // Insert visit details
//    public static final String   SERVER_CONTACT_CREATE  = "http://spacare.co/merchant/createcontact";   // Insert new contact
//    public static final String   SERVER_CONTACT_UPDATE  = "http://spacare.co/merchant/updatecontact";
//    public static final String   SERVER_CONTACT_DELETE  = "http://spacare.co/merchant/deletecontact/";
//    public static final String   SERVER_VISIT_UPDATE    = "http://spacare.co/merchant/visits/";

//    public static JSONObject choosed_visit_obg = null;
//    public static JSONObject choosed_contact_obg = null;
//    public static JSONObject choosed_item_obg = null;

    // functions
    public static float Screen_Density = 1.0f;

    public static void alertShow(Context context, String title, String message)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        // Canceled.
                    }
                });
        alert.show();
    }



}
