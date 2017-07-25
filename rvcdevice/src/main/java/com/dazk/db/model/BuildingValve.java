package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/7/20.
 */
@Table(name = "t_building_valve")
public class BuildingValve extends BaseEntity{
    private String building_unique_code;
    private String name;
    private Integer type;
    private Integer period;
    private String comm_address;
    private Integer unresolved;
    private Integer open;
    private Long search_time;
    private Integer pipe_size;
    private String remark;
    private Integer strategy;
    private Double kp;
    private Double ki;
    private Double kd;
    private Double target;
    private Double max_target;
    private Double min_target;
    private Long created_at;
    private Integer isdel;

    public String getBuilding_unique_code() {
        return building_unique_code;
    }

    public void setBuilding_unique_code(String building_unique_code) {
        this.building_unique_code = building_unique_code;
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

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Long getSearch_time() {
        return search_time;
    }

    public void setSearch_time(Long search_time) {
        this.search_time = search_time;
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

    public Integer getStrategy() {
        return strategy;
    }

    public void setStrategy(Integer strategy) {
        this.strategy = strategy;
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
