package com.dazk.db.model;

/**
 * Created by Administrator on 2017/8/6.
 */
public class HouseValveImport {
    private String house_code;
    private String type;
    private String power_type;
    private String comm_address;
    private String version;

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPower_type() {
        return power_type;
    }

    public void setPower_type(String power_type) {
        this.power_type = power_type;
    }

    public String getComm_address() {
        return comm_address;
    }

    public void setComm_address(String comm_address) {
        this.comm_address = comm_address;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
