package com.mobile.lance.iconnect.utils;

import java.util.Date;

/**
 * Created by administrator on 7/1/16.
 */
public class TableCommentsItem {

    private long id;
    private String comment;
    private long comment_user_id;
    private long comment_post_id;
    private String comment_created_at;
    private String comment_updated_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id= id;
    }

    public long getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(long id) {
        this.comment_user_id= id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String commentstr) {
        this.comment= commentstr;
    }

    public long getComment_post_id() {
        return comment_post_id;
    }

    public void setComment_post_id(long id) {
        this.comment_post_id= id;
    }

    public String getComment_created_at(){
        return comment_created_at;
    }

    public void setComment_created_at(String comment_created_at_date){
        this.comment_created_at= comment_created_at_date;
    }

    public String getComment_updated_at(){
        return comment_updated_at;
    }

    public void setComment_updated_at(String comment_updated_at_date){
        this.comment_updated_at = comment_updated_at_date;
    }
}
