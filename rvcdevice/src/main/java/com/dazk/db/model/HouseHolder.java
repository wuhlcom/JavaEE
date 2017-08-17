package com.dazk.db.model;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Administrator on 2017/7/23.
 */
@Table(name = "t_householder")
public class HouseHolder extends BaseEntity{
    private String code;
    private String id_card;
    private String unit_code;
    private String area;
    private String phone;
    private String name;
    private Integer mes_status;
    private Long created_at;
    private Integer isdel;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getUnit_code() {
        return unit_code;
    }

    public void setUnit_code(String unit_code) {
        this.unit_code = unit_code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMes_status() {
        return mes_status;
    }

    public void setMes_status(Integer mes_status) {
        this.mes_status = mes_status;
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
