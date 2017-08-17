package com.dazk.db.param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */
public class BatchStatusListParam {
    private List<BatchStatusDataResParam> data;

    public List<BatchStatusDataResParam> getData() {
        return data;
    }

    public void setData(List<BatchStatusDataResParam> data) {
        this.data = data;
    }
}
