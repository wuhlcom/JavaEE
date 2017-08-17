package com.dazk.db.model;

import javax.persistence.Transient;

/**
 * Created by Administrator on 2017/7/25.
 */
public class HouseCalorimeterData extends HouseCalorimeter{
    private Long collect_time;
    private Double value;
    private Double use_flow;
    private Double total_flow;
    private Double supply_temp;
    private Double return_temp;

    @Transient
    private String err_state;
    @Transient
    private Long err_time;

    public HouseCalorimeterData(HouseCalorimeter houseCalorimeter){
        setHouse_code(houseCalorimeter.getHouse_code());
        setType(houseCalorimeter.getType());
        setPeriod(houseCalorimeter.getPeriod());
        setComm_address(houseCalorimeter.getComm_address());
        setUnresolved(houseCalorimeter.getUnresolved());
        setPro_type(houseCalorimeter.getPro_type());
        setPipe_size(houseCalorimeter.getPipe_size());
        setRemark(houseCalorimeter.getRemark());
    }

    public Long getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Long collect_time) {
        this.collect_time = collect_time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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
