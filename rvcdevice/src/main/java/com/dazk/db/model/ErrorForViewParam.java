package com.dazk.db.model;

/**
 * Created by Administrator on 2017/8/11.
 */
public class ErrorForViewParam extends ErrorForView {
    private String address;
    public ErrorForViewParam(String device_type, String device_code, Long occur_time, Long recover_time, String error_type,String build_code,String address) {
        super(device_type, device_code, occur_time, recover_time, error_type,build_code);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
