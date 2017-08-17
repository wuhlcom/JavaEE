package com.dazk.db.param;

/**
 * Created by Administrator on 2017/8/15.
 */
public class StealHeatParam {
    private String comm_address;
    private Long collect_time;
    private Double wit;
    private Double wot;
    private Integer opening;

    public String getComm_address() {
        return comm_address;
    }

    public void setComm_address(String comm_address) {
        this.comm_address = comm_address;
    }

    public Long getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Long collect_time) {
        this.collect_time = collect_time;
    }

    public Double getWit() {
        return wit;
    }

    public void setWit(Double wit) {
        this.wit = wit;
    }

    public Double getWot() {
        return wot;
    }

    public void setWot(Double wot) {
        this.wot = wot;
    }

    public Integer getOpening() {
        return opening;
    }

    public void setOpening(Integer opening) {
        this.opening = opening;
    }
}
