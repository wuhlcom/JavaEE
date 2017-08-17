package com.dazk.db.param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
public class StealHeatCodeListParam {
    private String errcode;
    private String msg;
    private List<StealHeatCodeParam> result;

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

    public List<StealHeatCodeParam> getResult() {
        return result;
    }

    public void setResult(List<StealHeatCodeParam> result) {
        this.result = result;
    }
}
