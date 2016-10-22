package com.mobile.lance.iconnect.iConnect.notification;

import java.util.ArrayList;

/**
 * Created by administrator on 7/8/16.
 */
public class NotificationCell {
    public String name;
    public String description;

    public NotificationCell(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static ArrayList<NotificationCell> getNotification() {
        ArrayList<NotificationCell> notifications = new ArrayList<NotificationCell>();
        notifications.add(new NotificationCell("You have accepted friend request from Bruce Handson", "Here is...."));
        notifications.add(new NotificationCell("Penny West", "Here is..."));

        return notifications;
    }
}
