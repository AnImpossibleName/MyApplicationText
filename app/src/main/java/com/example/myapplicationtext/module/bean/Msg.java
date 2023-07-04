package com.example.myapplicationtext.module.bean;

import java.util.Date;

public class Msg {
    int id;
    int sendid;
    int receiveid;
    String content;
    Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSendid() {
        return sendid;
    }

    public void setSendid(int sendid) {
        this.sendid = sendid;
    }

    public int getReceiveid() {
        return receiveid;
    }

    public void setReceiveid(int receiveid) {
        this.receiveid = receiveid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public Msg(int id, int sendid, int receiveid, String content, Date time) {
        this.id = id;
        this.sendid = sendid;
        this.receiveid = receiveid;
        this.content = content;
        this.time = time;
    }
}
