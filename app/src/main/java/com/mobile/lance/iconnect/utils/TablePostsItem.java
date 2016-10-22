package com.mobile.lance.iconnect.utils;

import java.util.Date;

/**
 * Created by administrator on 7/1/16.
 */
public class TablePostsItem {

    private long post_id;
    private long post_user_id;
    private String post;
    private String post_imagepath;
    private String post_created_at;
    private String post_updated_at;


    public long getId() {
        return post_id;
    }

    public void setId(long id) {
        this.post_id = id;
    }

    public long getPostUserId() {
        return post_user_id;
    }

    public void setPostUserId(long id) {
        this.post_user_id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String poststr) {
        this.post = poststr;
    }

    public String getPostImagePath() {
        return post_imagepath;
    }

    public void setPostImagePath(String post_imagepath) {
        this.post_imagepath = post_imagepath;
    }

    public String getPost_created_at(){
        return post_created_at;
    }

    public void setPost_created_at(String post_created_at_str){
        this.post_created_at = post_created_at_str;
    }

    public String getPost_updated_at(){
        return post_updated_at;
    }

    public void setPost_updated_at(String post_updated_at_str){
        this.post_updated_at = post_updated_at_str;
    }
}
