package com.dazk.db.param;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
public class BuildingValveLogParam {
    private List<String> codes;
    private Long start_time;
    private Long end_time;
    private Integer page = 1;
    private Integer start = 0;
    private Integer listRows;

    public BuildingValveLogParam(List<String> codes, Long start_time, Long end_time, Integer page, Integer listRows) {
        this.codes = codes;
        this.start_time = start_time;
        this.end_time = end_time;
        this.page = page;
        this.listRows = listRows;
        if(this.listRows != null){
            start = (this.page-1)*this.listRows;
        }
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getListRows() {
        return listRows;
    }

    public void setListRows(Integer listRows) {
        this.listRows = listRows;
    }
}
