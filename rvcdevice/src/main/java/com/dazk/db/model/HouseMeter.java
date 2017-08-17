package com.dazk.db.model;

import javax.persistence.Transient;

/**
 * Created by Administrator on 2017/7/28.
 */
public class HouseMeter extends HouseHolder {
    private int valve_state;
    private int supply_temp;
    private int return_temp;
    private String local;
    private String valve_comm_addr;
    private String cal_comm_addr;

    public HouseMeter(HouseHolder houseHolder){
        setCode(houseHolder.getCode());

        setId_card(houseHolder.getId_card());
        setUnit_code(houseHolder.getUnit_code());

        setArea(houseHolder.getArea());

        setPhone(houseHolder.getPhone());

        setName(houseHolder.getName());

        setMes_status(houseHolder.getMes_status());
    }

    public int getValve_state() {
        return valve_state;
    }

    public void setValve_state(int valve_state) {
        this.valve_state = valve_state;
    }

    public int getSupply_temp() {
        return supply_temp;
    }

    public void setSupply_temp(int supply_temp) {
        this.supply_temp = supply_temp;
    }

    public int getReturn_temp() {
        return return_temp;
    }

    public void setReturn_temp(int return_temp) {
        this.return_temp = return_temp;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getValve_comm_addr() {
        return valve_comm_addr;
    }

    public void setValve_comm_addr(String valve_comm_addr) {
        this.valve_comm_addr = valve_comm_addr;
    }

    public String getCal_comm_addr() {
        return cal_comm_addr;
    }

    public void setCal_comm_addr(String cal_comm_addr) {
        this.cal_comm_addr = cal_comm_addr;
    }
}
