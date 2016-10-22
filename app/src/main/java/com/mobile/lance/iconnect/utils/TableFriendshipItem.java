package com.mobile.lance.iconnect.utils;

import java.util.Date;

/**
 * Created by administrator on 7/1/16.
 */
public class TableFriendshipItem {

    private long friendship_id;
    private long friend_id;
    private String type;
    private long user_id;
    private String friendship_created_at;

    public long getFriendship_id() {
        return friendship_id;
    }

    public void setFriendship_id(long id) {
        this.friendship_id= id;
    }

    public long getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(long id) {
        this.friend_id= id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long id) {
        this.user_id= id;
    }

    public String getType() {
        return type;
    }

    public void setType(String typestr) {
        this.type= typestr;
    }

    public String getFriendship_created_at(){
        return friendship_created_at;
    }

    public void setFriendship_created_at(String friendship_created_at_date){
        this.friendship_created_at = friendship_created_at_date;
    }
}
