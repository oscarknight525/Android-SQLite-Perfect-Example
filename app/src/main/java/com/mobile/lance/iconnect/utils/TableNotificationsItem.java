package com.mobile.lance.iconnect.utils;

/**
 * Created by administrator on 7/1/16.
 */
public class TableNotificationsItem {

    private long id;
    private long receiver_id;
    private long sender_id;
    private String message;
    private String type;
    private String status;
    private String notification_created_at;

    public long getNotificationId() {
        return id;
    }

    public void setNotificationId(long id) {
        this.id = id;
    }

    public long getNotificationReceiver_id() {
        return receiver_id;
    }

    public void setNotificationReceiver_id(long receiver_id_int) {
        this.receiver_id= receiver_id_int;
    }

    public long getNotificationSender_id() {
        return sender_id;
    }

    public void setNotificationSender_id(long sender_id_int) {
        this.sender_id= sender_id_int;
    }

    public String getNotificationMessage() {
        return message;
    }

    public void setNotificationMessage(String messagestr) {
        this.message = messagestr;
    }

    public String getNotificationType() {
        return type;
    }

    public void setNotificationType(String type) {
        this.type = type;
    }

    public String getNotificationStatus() {
        return status;
    }

    public void setNotificationStatus(String status) {
        this.status = status;
    }

    public String getNotification_created_at(){
        return notification_created_at;
    }

    public void setNotification_created_at(String notification_created_at_date){
        this.notification_created_at = notification_created_at_date;
    }
}
