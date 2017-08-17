package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/7/20.
 */
@Table(name = "t_building_calorimeter")
public class BuildingCalorimeter extends BaseEntity{
    private String building_unique_code;
    private String code;
    private String name;
    private Integer type;
    private Integer period;
    private String comm_address;
    private String mbus;
    private Integer unresolved;
    private String pro_type;
    private Integer pipe_size;
    private String remark;
    private Long created_at;
    private Integer isdel;
    private String err_code;
    private Integer online;
    private String address;

    public String getBuilding_unique_code() {
        return building_unique_code;
    }

    public void setBuilding_unique_code(String building_unique_code) {
        this.building_unique_code = building_unique_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getComm_address() {
        return comm_address;
    }

    public void setComm_address(String comm_address) {
        this.comm_address = comm_address;
    }

    public Integer getUnresolved() {
        return unresolved;
    }

    public void setUnresolved(Integer unresolved) {
        this.unresolved = unresolved;
    }

    public String getPro_type() {
        return pro_type;
    }

    public void setPro_type(String pro_type) {
        this.pro_type = pro_type;
    }

    public Integer getPipe_size() {
        return pipe_size;
    }

    public void setPipe_size(Integer pipe_size) {
        this.pipe_size = pipe_size;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getMbus() {
        return mbus;
    }

    public void setMbus(String mbus) {
        this.mbus = mbus;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
