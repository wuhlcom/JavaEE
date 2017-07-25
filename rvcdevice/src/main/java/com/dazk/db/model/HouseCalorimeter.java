package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/7/20.
 */
@Table(name = "t_house_calorimeter")
public class HouseCalorimeter extends BaseEntity{
    private String house_code;
    private Integer type;
    private Integer period;
    private String comm_address;
    private Integer unresolved;
    private String pro_type;
    private Integer pipe_size;
    private String remark;
    private Long created_at;
    private Integer isdel;

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
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
}
