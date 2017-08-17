package com.dazk.db.model;

/**
 * Created by Administrator on 2017/7/29.
 */
public class OpenLog {
    private Integer id;
    private String house_code;
    private Long record_time;
    private Integer user_id;
    private String record;
    private Integer logstatus;
    private Long created_at;
    private Integer isdel;
    private String address;
    private String username;
    private String name;
    private Integer operate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }

    public Long getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Long record_time) {
        this.record_time = record_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Integer getLogstatus() {
        return logstatus;
    }

    public void setLogstatus(Integer logstatus) {
        this.logstatus = logstatus;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOperate() {
        return operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
    }
}
