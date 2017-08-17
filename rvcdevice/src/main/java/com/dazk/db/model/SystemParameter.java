package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/8/6.
 */
@Table(name = "t_system_parameter")
public class SystemParameter extends BaseEntity{
    private String name;
    private String value;
    private String remark;
    private Integer created_at;
    private Integer isdel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Integer created_at) {
        this.created_at = created_at;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}
