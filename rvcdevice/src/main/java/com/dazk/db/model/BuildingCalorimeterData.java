package com.dazk.db.model;

import javax.persistence.Transient;

/**
 * Created by Administrator on 2017/7/25.
 */
public class BuildingCalorimeterData extends BuildingCalorimeter{
    private Long collect_time;
    private Double collect_heat;
    private Double use_heat;
    private Double supply_temp;
    private Double return_temp;
    private Double temper_diff;
    private Double use_flow;
    private Double total_flow;
    private Integer status;
    private Integer data_type;

    @Transient
    private String err_state;
    @Transient
    private Long err_time;

    public BuildingCalorimeterData(BuildingCalorimeter buildingCalorimeter){
        setBuilding_unique_code(buildingCalorimeter.getBuilding_unique_code());
        setName(buildingCalorimeter.getName());
        setType(buildingCalorimeter.getType());
        setPeriod(buildingCalorimeter.getPeriod());
        setComm_address(buildingCalorimeter.getComm_address());
        setUnresolved(buildingCalorimeter.getUnresolved());
        setPro_type(buildingCalorimeter.getPro_type());
        setPipe_size(buildingCalorimeter.getPipe_size());
        setRemark(buildingCalorimeter.getRemark());

    }

    public Long getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Long collect_time) {
        this.collect_time = collect_time;
    }

    public Double getCollect_heat() {
        return collect_heat;
    }

    public void setCollect_heat(Double collect_heat) {
        this.collect_heat = collect_heat;
    }

    public Double getUse_heat() {
        return use_heat;
    }

    public void setUse_heat(Double use_heat) {
        this.use_heat = use_heat;
    }

    public Double getSupply_temp() {
        return supply_temp;
    }

    public void setSupply_temp(Double supply_temp) {
        this.supply_temp = supply_temp;
    }

    public Double getReturn_temp() {
        return return_temp;
    }

    public void setReturn_temp(Double return_temp) {
        this.return_temp = return_temp;
    }

    public Double getTemper_diff() {
        return temper_diff;
    }

    public void setTemper_diff(Double temper_diff) {
        this.temper_diff = temper_diff;
    }

    public Double getUse_flow() {
        return use_flow;
    }

    public void setUse_flow(Double use_flow) {
        this.use_flow = use_flow;
    }

    public Double getTotal_flow() {
        return total_flow;
    }

    public void setTotal_flow(Double total_flow) {
        this.total_flow = total_flow;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getData_type() {
        return data_type;
    }

    public void setData_type(Integer data_type) {
        this.data_type = data_type;
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
