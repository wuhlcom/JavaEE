package com.dazk.db.param;

/**
 * Created by Administrator on 2017/8/11.
 */
public class BuildingValveStatus {
    private String building_unique_code;
    private String comm_type;
    private String dtu_code;
    private String comm_address;
    private Long collect_time;
    private Double supply_temp;
    private Double return_temp;
    private Double temper_diff;
    private Double use_flow;
    private Double total_flow;
    private Double use_heat;
    private Double period_heat;
    private Double collect_heat;
    private Double value;

    public String getBuilding_unique_code() {
        return building_unique_code;
    }

    public void setBuilding_unique_code(String building_unique_code) {
        this.building_unique_code = building_unique_code;
    }

    public String getComm_type() {
        return comm_type;
    }

    public void setComm_type(String comm_type) {
        this.comm_type = comm_type;
    }

    public String getDtu_code() {
        return dtu_code;
    }

    public void setDtu_code(String dtu_code) {
        this.dtu_code = dtu_code;
    }

    public String getComm_address() {
        return comm_address;
    }

    public void setComm_address(String comm_address) {
        this.comm_address = comm_address;
    }

    public Long getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Long collect_time) {
        this.collect_time = collect_time;
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

    public Double getUse_heat() {
        return use_heat;
    }

    public void setUse_heat(Double use_heat) {
        this.use_heat = use_heat;
    }

    public Double getPeriod_heat() {
        return period_heat;
    }

    public void setPeriod_heat(Double period_heat) {
        this.period_heat = period_heat;
    }

    public Double getCollect_heat() {
        return collect_heat;
    }

    public void setCollect_heat(Double collect_heat) {
        this.collect_heat = collect_heat;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
