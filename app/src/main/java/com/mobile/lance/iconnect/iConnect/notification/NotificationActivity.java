package com.mobile.lance.iconnect.iConnect.notification;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListView;

import com.mobile.lance.iconnect.R;
import com.mobile.lance.iconnect.iConnect.friends.friendslist.FragmentFriends;
import com.mobile.lance.iconnect.iConnect.FragmentMe;
import com.mobile.lance.iconnect.iConnect.message.FragmentMessages;
import com.mobile.lance.iconnect.utils.AppConstants;

public class NotificationActivity extends ActionBarActivity{

    int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        setTitle("Notifications");
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        AppConstants.Screen_Density = metrics.density;
        selectItem(1);
    }

    // when press the button
    public void FragmentNotificationsClick(View view) {
        selectItem(1);
    }

    public void FragmentMessagesClick(View view) {
        selectItem(2);
    }

    public void FragmentFriendsClick(View view) {
        selectItem(3);
    }

    public void FragmentMeClick(View view) {
        selectItem(4);
    }

    // when press the image
    public void onNotificationImageClick(View v){
        selectItem(1);
    }

    public void onMessagesImagesClick(View v){
        selectItem(2);
    }

    public void onFriendsImageClick(View v){
        selectItem(3);
    }

    public void onMeImageClcik(View v){
        selectItem(4);
    }

    public void selectItem(int position) {

        Fragment fragment = getFragment(position);
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position - 1);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_switch, fragment).commit();
    }

    private Fragment getFragment(int position) {

        currentIndex = position;

        Fragment fragment = null;
        switch (position) {
            case 1:
                fragment = new FragmentNotification();
                break;
            case 2:
                fragment = new FragmentMessages();
                break;
            case 3:
                fragment = new FragmentFriends();
                break;
            case 4:
                fragment = new FragmentMe();
                break;
        }
        return fragment;
    }
}


