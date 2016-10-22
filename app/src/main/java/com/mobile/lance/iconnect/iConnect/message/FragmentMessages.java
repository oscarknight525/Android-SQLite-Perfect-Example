package com.mobile.lance.iconnect.iConnect.message;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.utils.AppConstants;
import com.mobile.lance.iconnect.utils.DBFriendshipManager;
import com.mobile.lance.iconnect.utils.DBMessageManager;
import com.mobile.lance.iconnect.utils.DBUsersManager;
import com.mobile.lance.iconnect.utils.TableCommentsItem;
import com.mobile.lance.iconnect.utils.TableFriendshipItem;
import com.mobile.lance.iconnect.utils.TableMessagesItem;
import com.mobile.lance.iconnect.utils.TableUserItem;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentMessages extends Fragment {

    private ListView message_listview;
    private CustomMessagesAdapter adapter;
    private ArrayList<TableMessagesItem> arrayOfMessage;
    private Button btn_send;
    private EditText edt_sendmessage;
    private Spinner spinner_friend;
    private String message;

    private DBFriendshipManager friendshipDB;
    private DBUsersManager userDB;
    private DBMessageManager messageDB;

    private List<Long> friendshipidList;
    private List<Long> friendidList;
    private List<String> frindsnamelist;
    private List<Long> messageidList;

    long my_id;
    long receiver_id;
    long message_id;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {

        // Change action bar title whenever fragment is switched
        getActivity().setTitle("Messages");

        View messagesview = inflater.inflate(R.layout.fragment_message, container,false);

        getActivity().setTitle("Messages");

        message_listview = (ListView) messagesview.findViewById(R.id.messages_list);
        btn_send = (Button) messagesview.findViewById(R.id.btn_sendmessage);
        edt_sendmessage = (EditText) messagesview.findViewById(R.id.edt_sendmessage);
        spinner_friend = (Spinner) messagesview.findViewById(R.id.spinner_friend);

        arrayOfMessage = new ArrayList<>();
        frindsnamelist = new ArrayList<>();
        friendshipidList = new ArrayList<>();
        friendidList = new ArrayList<>();
        messageidList = new ArrayList<>();

        friendshipDB = new DBFriendshipManager(getActivity());
        userDB = new DBUsersManager(getActivity());
        messageDB = new DBMessageManager(getActivity());

        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", getActivity().MODE_PRIVATE);
        String userid_loggedin = prefs.getString("userid", null);
        my_id = Long.parseLong(userid_loggedin);
        receiver_id = 0;
        try {
            friendshipDB.open();
            userDB.open();

            friendshipidList = friendshipDB.getFriendsshipId(my_id);

            for (int i = 0; i < friendshipidList.size(); i++) {
                TableFriendshipItem item = friendshipDB.getFriendshipData(friendshipidList.get(i));

                long friends_userid = item.getFriend_id();
                if(my_id == friends_userid){
                    friends_userid = item.getUser_id();
                }
                friendidList.add(friends_userid);
                TableUserItem getfriends = userDB.getuserdbItem(friends_userid);
                frindsnamelist.add(getfriends.getFirstname() + " " + getfriends.getLastname());
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        if(frindsnamelist.size() == 0){
            receiver_id = - 2;
            frindsnamelist.add("None");
        }

        ArrayAdapter<String> sendToAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, frindsnamelist);

        // Drop down layout style - list view with radio button
        sendToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_friend.setAdapter(sendToAdapter);

        spinner_friend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(receiver_id != -2) {
                    receiver_id = friendidList.get(position);
                }
                Log.d("receiver_id =", "" + receiver_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            messageDB.open();
            messageidList = messageDB.getMessageIDs(my_id);

            for (int i = 0; i < messageidList.size(); i++){
                message_id = messageidList.get(i);
                if(message_id != -2) {
                    TableMessagesItem item = messageDB.getMessageInfo(message_id);
                    arrayOfMessage.add(item);
                }
            }
            messageDB.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        adapter = new CustomMessagesAdapter(getActivity(),arrayOfMessage);
        message_listview.setAdapter(adapter);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(receiver_id != -2) {
                    message = edt_sendmessage.getText().toString();
                    if (message.isEmpty()) {
                        AppConstants.alertShow(getActivity(), "Alert", "Please input message");
                    } else {
                        try {
                            messageDB.open();
                            TableMessagesItem item = new TableMessagesItem();
                            item.setReceiver_id(receiver_id);
                            item.setSender_id(my_id);
                            item.setMessage(message);
                            item.setMessage_created_at(getCurrentDate());

                            arrayOfMessage.add(item);
                            messageDB.insertMessage(item);
                            adapter.notifyDataSetChanged();
                            message_listview.setSelection(adapter.getCount() - 1);
                            edt_sendmessage.setText("");
                            messageDB.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    AppConstants.alertShow(getActivity(), "Alert", "You don't have any friend");
                }
            }
        });

        return  messagesview;
    }

    private  static String  getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

}
