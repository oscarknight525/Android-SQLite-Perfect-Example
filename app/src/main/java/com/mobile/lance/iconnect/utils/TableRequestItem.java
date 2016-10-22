package com.mobile.lance.iconnect.utils;

import java.util.Date;

/**
 * Created by administrator on 7/1/16.
 */
public class TableRequestItem {

    private long request_id;
    private long from_id;
    private long to_id;
    private Boolean accepted_yn;
    private String request;
    private String status;
    private String request_created_at;
    private String request_updated_at;

    public long getRequest_id() {
        return request_id;
    }

    public void setRequest_id(long id) {
        this.request_id = id;
    }

    public long getFrom_id() {
        return from_id;
    }

    public void setFrom_id(long id) {
        this.from_id= id;
    }

    public long getTo_id() {
        return to_id;
    }

    public void setTo_id(long id) {
        this.to_id= id;
    }

    public Boolean getAccepted_yn(){ return accepted_yn;}

    public void setAccepted_yn(Boolean flag)
    {
        this.accepted_yn = flag;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String requeststr) {
        this.request= requeststr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String statusstr) {
        this.status = statusstr;
    }

    public String getRequest_created_at(){
        return request_created_at;
    }

    public void setRequest_created_at(String request_created_at_date){
        this.request_created_at= request_created_at_date;
    }

    public String getRequest_updated_at(){
        return request_updated_at;
    }

    public void setRequest_updated_at(String request_updated_at_date){
        this.request_updated_at = request_updated_at_date;
    }
}
