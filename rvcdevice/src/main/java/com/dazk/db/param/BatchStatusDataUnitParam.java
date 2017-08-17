package com.dazk.db.param;

/**
 * Created by Administrator on 2017/8/12.
 */
public class BatchStatusDataUnitParam {
    //公用
    private Integer comm_type;
    private String dtu_code;
    private Long collect_time;
    private String comm_address;
    //住户
    private String house_code;
    private Integer comm_status;
    private Integer valve_status;
    private Integer open_time;
    private Double sharing_heat;
    private Double set_temper;
    private Double average_temper;
    private Double accumulated_heat;
    private Double supply_temp;// 楼栋热表
    private Double return_temp;// 楼栋热表
    private Double open_ratio;

    // 楼栋热表
    private Double temper_diff;
    private Double use_flow;
    private Double total_flow;
    private Double use_heat;
    private Double period_heat;
    private Double collect_heat;
    private Double value;

    //楼栋调节阀
    private Integer opening;

    public Integer getComm_type() {
        return comm_type;
    }

    public void setComm_type(Integer comm_type) {
        this.comm_type = comm_type;
    }

    public String getDtu_code() {
        return dtu_code;
    }

    public void setDtu_code(String dtu_code) {
        this.dtu_code = dtu_code;
    }

    public Long getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Long collect_time) {
        this.collect_time = collect_time;
    }

    public String getHouse_code() {
        return house_code;
    }

    public void setHouse_code(String house_code) {
        this.house_code = house_code;
    }

    public Integer getComm_status() {
        return comm_status;
    }

    public void setComm_status(Integer comm_status) {
        this.comm_status = comm_status;
    }

    public Integer getValve_status() {
        return valve_status;
    }

    public void setValve_status(Integer valve_status) {
        this.valve_status = valve_status;
    }

    public Integer getOpen_time() {
        return open_time;
    }

    public void setOpen_time(Integer open_time) {
        this.open_time = open_time;
    }

    public Double getSharing_heat() {
        return sharing_heat;
    }

    public void setSharing_heat(Double sharing_heat) {
        this.sharing_heat = sharing_heat;
    }

    public Double getSet_temper() {
        return set_temper;
    }

    public void setSet_temper(Double set_temper) {
        this.set_temper = set_temper;
    }

    public Double getAverage_temper() {
        return average_temper;
    }

    public void setAverage_temper(Double average_temper) {
        this.average_temper = average_temper;
    }

    public Double getAccumulated_heat() {
        return accumulated_heat;
    }

    public void setAccumulated_heat(Double accumulated_heat) {
        this.accumulated_heat = accumulated_heat;
    }

    public Double getSupply_temp() {
        return supply_temp;
    }

    public void setSupply_temp(Double supply_temp) {
        this.supply_temp = supply_temp;
    }

    public Double getReturn_temp() {
        return return_temp;
    }

    public void setReturn_temp(Double return_temp) {
        this.return_temp = return_temp;
    }

    public Double getOpen_ratio() {
        return open_ratio;
    }

    public void setOpen_ratio(Double open_ratio) {
        this.open_ratio = open_ratio;
    }

    public String getComm_address() {
        return comm_address;
    }

    public void setComm_address(String comm_address) {
        this.comm_address = comm_address;
    }

    public Double getTemper_diff() {
        return temper_diff;
    }

    public void setTemper_diff(Double temper_diff) {
        this.temper_diff = temper_diff;
    }

    public Double getUse_flow() {
        return use_flow;
    }

    public void setUse_flow(Double use_flow) {
        this.use_flow = use_flow;
    }

    public Double getCollect_heat() {
        return collect_heat;
    }

    public void setCollect_heat(Double collect_heat) {
        this.collect_heat = collect_heat;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getOpening() {
        return opening;
    }

    public void setOpening(Integer opening) {
        this.opening = opening;
    }

    public Double getTotal_flow() {
        return total_flow;
    }

    public void setTotal_flow(Double total_flow) {
        this.total_flow = total_flow;
    }

    public Double getUse_heat() {
        return use_heat;
    }

    public void setUse_heat(Double use_heat) {
        this.use_heat = use_heat;
    }

    public Double getPeriod_heat() {
        return period_heat;
    }

    public void setPeriod_heat(Double period_heat) {
        this.period_heat = period_heat;
    }
}
