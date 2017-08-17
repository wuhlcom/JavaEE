package com.dazk.db.model;

/**
 * Created by Administrator on 2017/7/29.
 */
public class BuildingValveLog {
    private Integer id;
    private String building_unique_code;
    private Long record_time;
    private Integer logstatus;
    private Integer user_id;
    private String username;
    private String record;
    private Double kp;
    private Double ki;
    private Double kd;
    private Double target;
    private Double max_target;
    private Double min_target;
    private Double created_at;
    private Double isdel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuilding_unique_code() {
        return building_unique_code;
    }

    public void setBuilding_unique_code(String building_unique_code) {
        this.building_unique_code = building_unique_code;
    }

    public Long getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Long record_time) {
        this.record_time = record_time;
    }

    public Integer getLogstatus() {
        return logstatus;
    }

    public void setLogstatus(Integer logstatus) {
        this.logstatus = logstatus;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Double getKp() {
        return kp;
    }

    public void setKp(Double kp) {
        this.kp = kp;
    }

    public Double getKi() {
        return ki;
    }

    public void setKi(Double ki) {
        this.ki = ki;
    }

    public Double getKd() {
        return kd;
    }

    public void setKd(Double kd) {
        this.kd = kd;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    public Double getMax_target() {
        return max_target;
    }

    public void setMax_target(Double max_target) {
        this.max_target = max_target;
    }

    public Double getMin_target() {
        return min_target;
    }

    public void setMin_target(Double min_target) {
        this.min_target = min_target;
    }

    public Double getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Double created_at) {
        this.created_at = created_at;
    }

    public Double getIsdel() {
        return isdel;
    }

    public void setIsdel(Double isdel) {
        this.isdel = isdel;
    }
}
