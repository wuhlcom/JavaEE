package com.dazk.db.model;

import javax.persistence.Transient;

/**
 * Created by Administrator on 2017/7/25.
 */
public class BuildingValveData extends BuildingValve{
    private Long collect_time;
    private Integer opening;
    private Integer status;

    @Transient
    private String err_state;
    @Transient
    private Long err_time;

    public BuildingValveData(BuildingValve buildingValve){
        setBuilding_unique_code(buildingValve.getBuilding_unique_code());
    }

    public Long getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Long collect_time) {
        this.collect_time = collect_time;
    }

    public Integer getOpening() {
        return opening;
    }

    public void setOpening(Integer opening) {
        this.opening = opening;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErr_state() {
        return err_state;
    }

    public void setErr_state(String err_state) {
        this.err_state = err_state;
    }

    public Long getErr_time() {
        return err_time;
    }

    public void setErr_time(Long err_time) {
        this.err_time = err_time;
    }
}
