package com.dazk.db.model;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by Administrator on 2017/7/20.
 */
@Table(name = "t_gateway")
public class Gateway extends BaseEntity{
    private String company_code;
    private String name;
    private String mac;
    private String sim_code;
    private String gprsid;
    private String version;
    private String city;
    private String area;
    private String address;
    private String lng;
    private String lat;
    private Integer txpower;
    private Integer latency;
    private String ip;
    private Integer status;
    private Integer debug_status;
    private Integer anti_freeze_temper;
    private Long lastOn;
    private Long lastOff;
    private Long created_at;
    private String remark;
    private Integer isdel;
    private String err_code;
    private Integer online;

    @Transient
    private String err_state;
    @Transient
    private Long err_time;

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSim_code() {
        return sim_code;
    }

    public void setSim_code(String sim_code) {
        this.sim_code = sim_code;
    }

    public String getGprsid() {
        return gprsid;
    }

    public void setGprsid(String gprsid) {
        this.gprsid = gprsid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getTxpower() {
        return txpower;
    }

    public void setTxpower(Integer txpower) {
        this.txpower = txpower;
    }

    public Integer getLatency() {
        return latency;
    }

    public void setLatency(Integer latency) {
        this.latency = latency;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getLastOn() {
        return lastOn;
    }

    public void setLastOn(Long lastOn) {
        this.lastOn = lastOn;
    }

    public Long getLastOff() {
        return lastOff;
    }

    public void setLastOff(Long lastOff) {
        this.lastOff = lastOff;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getDebug_status() {
        return debug_status;
    }

    public void setDebug_status(Integer debug_status) {
        this.debug_status = debug_status;
    }

    public Integer getAnti_freeze_temper() {
        return anti_freeze_temper;
    }

    public void setAnti_freeze_temper(Integer anti_freeze_temper) {
        this.anti_freeze_temper = anti_freeze_temper;
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

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Integer getOnline() {
        return online;
    }
}
