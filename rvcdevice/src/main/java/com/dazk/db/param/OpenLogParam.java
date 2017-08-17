package com.dazk.db.param;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
public class OpenLogParam {
    private List<String> codes;
    private Long start_time;
    private Long end_time;
    private Integer page = 1;
    private Integer start = 0;
    private Integer listRows;
    private String username;
    private String name;
    private Integer operate;

    public OpenLogParam(List<String> codes, Long start_time, Long end_time,String username,String name,Integer operate, Integer page, Integer listRows) {
        this.codes = codes;
        this.start_time = start_time;
        this.end_time = end_time;
        this.username = username;
        this.name = name;
        this.operate = operate;
        this.page = page;
        this.listRows = listRows;
        if(this.listRows != null){
            start = (this.page-1)*this.listRows;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOperate() {
        return operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
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

    public Integer getListRows() {
        return listRows;
    }

    public void setListRows(Integer listRows) {
        this.listRows = listRows;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
