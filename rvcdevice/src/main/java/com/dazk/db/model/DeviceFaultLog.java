package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/8/2.
 */
@Table(name = "device_fault_log")
public class DeviceFaultLog extends BaseEntity{
    private Long time;
    private String code;
    private String device_type;
    private String event;

    public DeviceFaultLog(Long time, String code, String device_type, String event) {
        this.time = time;
        this.code = code;
        this.device_type = device_type;
        this.event = event;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
