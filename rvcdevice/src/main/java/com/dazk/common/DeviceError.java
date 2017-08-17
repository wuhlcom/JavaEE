package com.dazk.common;

/**
 * Created by Administrator on 2017/8/14.
 */
public class DeviceError
{
    public static String getErrStr(String err_code){
        String error = null;
        switch (err_code){
            case "01":error = "MBUS通信异常";break;
            case "81":error = "MBUS通信异常恢复";break;
            case "02":error = "阀异常";break;
            case "82":error = "阀异常恢复";break;
            case "03":error = "电池故障";break;
            case "83":error = "电池故障恢复";break;
            case "04":error = "无线通信故障";break;
            case "84":error = "无线通信故障恢复";break;
            case "05":error = "传感器故障";break;
            case "85":error = "传感器故障恢复";break;
            case "06":error = "开阀操作失败";break;
            case "86":error = "开阀操作成功";break;
            case "07":error = "关阀操作失败";break;
            case "87":error = "关阀操作成功";break;
            default:break;
        }
        return error;
    }
}
