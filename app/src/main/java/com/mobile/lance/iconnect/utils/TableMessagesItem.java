package com.mobile.lance.iconnect.utils;

import java.util.Date;

/**
 * Created by administrator on 7/1/16.
 */
public class TableMessagesItem {

    private long id;
    private long receiver_id;
    private long sender_id;
    private String message;
    private String message_created_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(long receiver_id_int) {
        this.receiver_id= receiver_id_int;
    }

    public long getSender_id() {
        return sender_id;
    }

    public void setSender_id(long sender_id_int) {
        this.sender_id= sender_id_int;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String messagestr) {
        this.message = messagestr;
    }

    public String getMessage_created_at(){
        return message_created_at;
    }

    public void setMessage_created_at(String message_created_at_date){
        this.message_created_at = message_created_at_date;
    }
}
