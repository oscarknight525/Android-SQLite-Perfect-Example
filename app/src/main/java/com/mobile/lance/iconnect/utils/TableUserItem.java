package com.mobile.lance.iconnect.utils;

import java.util.Date;

/**
 * Created by administrator on 6/30/16.
 */
public class TableUserItem {

    private long user_id;
    private long log_in_count;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String image_path;
    private String created_at_users;
    private String updated_at_users;

    public TableUserItem(){
        user_id = 0;
        log_in_count = 0;
        firstname = "";
        lastname = "";
        email = "";
        password = "";
        image_path = "";
        created_at_users = "";
        updated_at_users = "";
    }

    public long getId() {
        return user_id;
    }

    public void setId(long id) {
        this.user_id = id;
    }

    public long getLog_in_count() {
        return log_in_count;
    }

    public void setLog_in_count(long id) {
        this.log_in_count = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstnamestr) {
        this.firstname = firstnamestr;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastnamestr) {
        this.lastname = lastnamestr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emailstr) {
        this.email = emailstr;
    }

    public String getCreated_at_users(){
        return created_at_users;
    }

    public void setCreated_at_users(String created_at_users_str){
        this.created_at_users = created_at_users_str;
    }

    public String getUpdated_at_users(){
        return updated_at_users;
    }

    public void setUpdated_at_users(String updated_at_users_str){
        this.updated_at_users= updated_at_users_str;
    }
}




