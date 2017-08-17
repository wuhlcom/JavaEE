package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/7/23.
 */
@Table(name = "t_hotstation")
public class HotStation extends BaseEntity{
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
