package com.dazk.db.model;

import javax.persistence.Transient;

/**
 * Created by Administrator on 2017/7/25.
 */
public class HouseValveData extends HouseValve{
    private Long collect_time;
    private Double supply_temp;
    private Double return_temp;
    private Integer valve_state;
    private Double set_temper;
    private Double room_temper;

    @Transient
    private String err_state;
    @Transient
    private Long err_time;

    public HouseValveData(HouseValve houseValve){
        setHouse_code(houseValve.getHouse_code());
        setType(houseValve.getType());
        setPeriod(houseValve.getPeriod());
        setPower_type(houseValve.getPower_type());
        setVersion(houseValve.getVersion());
        setUnresolved(houseValve.getUnresolved());
        setOpen(houseValve.getOpen());
        setOpening(houseValve.getOpening());
        setRemark(houseValve.getRemark());
        setCreated_at(houseValve.getCreated_at());
        setIsdel(houseValve.getIsdel());
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

    public Integer getValve_state() {
        return valve_state;
    }

    public void setValve_state(Integer valve_state) {
        this.valve_state = valve_state;
    }

    public Double getSet_temper() {
        return set_temper;
    }

    public void setSet_temper(Double set_temper) {
        this.set_temper = set_temper;
    }

    public Double getRoom_temper() {
        return room_temper;
    }

    public void setRoom_temper(Double room_temper) {
        this.room_temper = room_temper;
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
