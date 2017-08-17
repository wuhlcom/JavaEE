package com.dazk.db.model;

/**
 * Created by Administrator on 2017/8/13.
 */
public class StealHeatData extends BaseEntity {
    private String house_code;
    private String name;
    private String Id_card;
    private Integer mes_status;
    private Double temdif ;
    private Double supply_temp;
    private Double return_temp;
    private Long collect_time;
    private Integer valve_state;

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return Id_card;
    }

    public void setId_card(String id_card) {
        Id_card = id_card;
    }

    public Integer getMes_status() {
        return mes_status;
    }

    public void setMes_status(Integer mes_status) {
        this.mes_status = mes_status;
    }

    public Double getTemdif() {
        return temdif;
    }

    public void setTemdif(Double temdif) {
        this.temdif = temdif;
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

    public Long getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Long collect_time) {
        this.collect_time = collect_time;
    }

    public Integer getValve_state() {
        return valve_state;
    }

    public void setValve_state(Integer valve_state) {
        this.valve_state = valve_state;
    }
}
