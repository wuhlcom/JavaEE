package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/7/20.
 */
@Table(name = "t_concentrator")
public class Concentrator extends BaseEntity{
    private String building_unique_code;
    private String name;
    private String code;
    private String address;
    private String gprsid;
    private String ip;
    private Long login_time;
    private Long refresh_time;
    private Integer unresolved;
    private String sim_code;
    private Integer protocol_type;
    private Integer debug_status;
    private Integer anti_freeze_temper;
    private String remark;
    private String err_code;
    private Integer online;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGprsid() {
        return gprsid;
    }

    public void setGprsid(String gprsid) {
        this.gprsid = gprsid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Long login_time) {
        this.login_time = login_time;
    }

    public Long getRefresh_time() {
        return refresh_time;
    }

    public void setRefresh_time(Long refresh_time) {
        this.refresh_time = refresh_time;
    }

    public Integer getUnresolved() {
        return unresolved;
    }

    public void setUnresolved(Integer unresolved) {
        this.unresolved = unresolved;
    }

    public String getSim_code() {
        return sim_code;
    }

    public void setSim_code(String sim_code) {
        this.sim_code = sim_code;
    }

    public Integer getProtocol_type() {
        return protocol_type;
    }

    public void setProtocol_type(Integer protocol_type) {
        this.protocol_type = protocol_type;
    }

    public Integer getDebug_status() {
        return debug_status;
    }

    public void setDebug_status(Integer debug_status) {
        this.debug_status = debug_status;
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

    public Integer getAnti_freeze_temper() {
        return anti_freeze_temper;
    }

    public void setAnti_freeze_temper(Integer anti_freeze_temper) {
        this.anti_freeze_temper = anti_freeze_temper;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }
}
