package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/7/20.
 */
@Table(name = "t_gateway")
public class Gateway extends BaseEntity{
    private Integer company_id;
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
    private Long lastOn;
    private Long lastOff;
    private Long created_at;
    private String remark;
    private Integer isdel;

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
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
}
