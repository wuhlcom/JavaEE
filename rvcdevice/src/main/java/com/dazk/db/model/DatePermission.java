package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/8/3.
 */
@Table(name = "data_permission")
public class DatePermission extends BaseEntity {
    private Integer user_id;
    private String code_valve;
    private Integer code_type;
    private Integer data_type;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCode_valve() {
        return code_valve;
    }

    public void setCode_valve(String code_valve) {
        this.code_valve = code_valve;
    }

    public Integer getCode_type() {
        return code_type;
    }

    public void setCode_type(Integer code_type) {
        this.code_type = code_type;
    }

    public Integer getData_type() {
        return data_type;
    }

    public void setData_type(Integer data_type) {
        this.data_type = data_type;
    }
}
