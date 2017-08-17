package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/8/10.
 */
@Table(name = "t_collect_error")
public class ErrorForView extends BaseEntity{
    private String device_type;
    private String device_code;
    private Long occur_time;
    private Long recover_time;
    private String error_type;
    private String build_code;

    public ErrorForView(String device_type, String device_code, Long occur_time, Long recover_time, String error_type,String build_code) {
        this.device_type = device_type;
        this.device_code = device_code;
        this.occur_time = occur_time;
        this.recover_time = recover_time;
        this.error_type = error_type;
        this.build_code = build_code;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public Long getOccur_time() {
        return occur_time;
    }

    public void setOccur_time(Long occur_time) {
        this.occur_time = occur_time;
    }

    public Long getRecover_time() {
        return recover_time;
    }

    public void setRecover_time(Long recover_time) {
        this.recover_time = recover_time;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public String getBuild_code() {
        return build_code;
    }

    public void setBuild_code(String build_code) {
        this.build_code = build_code;
    }
}
