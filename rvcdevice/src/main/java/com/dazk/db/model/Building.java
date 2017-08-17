package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/7/23.
 */
@Table(name = "t_building")
public class Building extends BaseEntity{
    private String unique_code;
    private String name;
    private Integer isdel;

    public String getUnique_code() {
        return unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}
