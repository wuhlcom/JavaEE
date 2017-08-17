package com.dazk.db.param;

/**
 * Created by Administrator on 2017/8/11.
 */
public class BatchStatusDataParam {
    private Integer comm_type;
    private String key;

    public BatchStatusDataParam(Integer comm_type, String key) {
        this.comm_type = comm_type;
        this.key = key;
    }

    public Integer getComm_type() {

        return comm_type;
    }

    public void setComm_type(Integer comm_type) {
        this.comm_type = comm_type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
