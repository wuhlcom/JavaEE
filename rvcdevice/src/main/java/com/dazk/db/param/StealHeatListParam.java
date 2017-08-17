package com.dazk.db.param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
public class StealHeatListParam {
    private String errcode;
    private String msg;
    private List<StealHeatParam> result;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<StealHeatParam> getResult() {
        return result;
    }

    public void setResult(List<StealHeatParam> result) {
        this.result = result;
    }
}
