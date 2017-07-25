package com.dazk.db.model;

import javax.persistence.Table;

/**
 * Created by Administrator on 2017/7/23.
 */
@Table(name = "t_community")
public class Community extends BaseEntity {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
