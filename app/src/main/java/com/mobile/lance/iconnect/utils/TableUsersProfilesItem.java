package com.mobile.lance.iconnect.utils;

import java.util.Date;

/**
 * Created by administrator on 7/1/16.
 */
public class TableUsersProfilesItem {

    private long user_profile_id;
    private long user_id;
    private String user_name;
    private String phone_number_cell;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String birth_date;
    private String gender;
    private String details;
    private Date user_profile_created_at;
    private Date user_profile_updated_at;

    public long getUser_profile_id() {
        return user_profile_id;
    }

    public void setUser_profile_id(long id) {
        this.user_profile_id= id;
    }

    public long getId() {
        return user_id;
    }

    public void setId(long id) {
        this.user_id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name_str) {
        this.user_name= user_name_str;
    }

    public String getPhone_number_cell() {
        return phone_number_cell;
    }

    public void setPhone_number_cell(String phone_number_cell_str) {
        this.phone_number_cell = phone_number_cell_str;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String addressstr) {
        this.address = addressstr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String citystr) {
        this.city= citystr;
    }

    public String getState() {
        return state;
    }

    public void setState(String statestr) {
        this.state= statestr;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcodestr) {
        this.zipcode= zipcodestr;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date_str) {
        this.birth_date= birth_date_str;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String genderstr) {
        this.gender= genderstr;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String detailstr) {
        this.details= detailstr;
    }

    public Date getUser_profile_created_at(){
        return user_profile_created_at;
    }

    public void setUser_profile_created_at(Date user_profile_created_at_date){
        this.user_profile_created_at = user_profile_created_at_date;
    }

    public Date getUser_profile_updated_at(){
        return user_profile_updated_at;
    }

    public void setUser_profile_updated_at(Date user_profile_updated_at_date){
        this.user_profile_updated_at = user_profile_updated_at_date;
    }
}
