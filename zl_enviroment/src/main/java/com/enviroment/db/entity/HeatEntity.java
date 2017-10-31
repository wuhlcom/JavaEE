/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 上午11:46:02 * 
*/
package com.enviroment.db.entity;

import javax.persistence.Table;

@Table(name = "en_heat")
public class HeatEntity extends BaseEntity {
	private String dev_address; // `dev_address` varchar(20) NOT NULL COMMENT
								// '设备识别编号',
	private Integer dev_type; // `dev_type` tinyint(2) DEFAULT NULL COMMENT
	private Double energy; // `energy` float(20,0) DEFAULT NULL COMMENT '累计热量
							// kWh',
	private Double flow;// `flow` float(20,0) DEFAULT NULL COMMENT '瞬时流量 单位L/h',
	private Double cumulative_flow;// `cumulative_flow` float(10,0) DEFAULT NULL,
	private Double supply_water_temperature; // `return_water_temperature`
											// float(10,0) DEFAULT NULL COMMENT
											// '进水温度 摄氏度',
	private Double return_water_temperature;// `return_water_temperature`
											// float(10,0) DEFAULT NULL COMMENT
											// '回水温度 单位摄氏度',
	private Double cumulative_time;// `cumulative_time` int(16) DEFAULT NULL,
	private String realy_time;// `realy_time` int(16) DEFAULT NULL,
	private String st;// `st` varchar(20) CHARACTER SET utf8 DEFAULT NULL,

	

	public String getDev_address() {
		return dev_address;
	}



	public void setDev_address(String dev_address) {
		this.dev_address = dev_address;
	}



	public Integer getDev_type() {
		return dev_type;
	}



	public void setDev_type(Integer dev_type) {
		this.dev_type = dev_type;
	}



	public Double getEnergy() {
		return energy;
	}



	public void setEnergy(Double energy) {
		this.energy = energy;
	}



	public Double getFlow() {
		return flow;
	}



	public void setFlow(Double flow) {
		this.flow = flow;
	}



	public Double getCumulative_flow() {
		return cumulative_flow;
	}



	public void setCumulative_flow(Double cumulative_flow) {
		this.cumulative_flow = cumulative_flow;
	}



	public Double getSupply_water_temperature() {
		return supply_water_temperature;
	}



	public void setSupply_water_temperature(Double supply_water_temperature) {
		this.supply_water_temperature = supply_water_temperature;
	}



	public Double getReturn_water_temperature() {
		return return_water_temperature;
	}



	public void setReturn_water_temperature(Double return_water_temperature) {
		this.return_water_temperature = return_water_temperature;
	}



	public Double getCumulative_time() {
		return cumulative_time;
	}



	public void setCumulative_time(Double cumulative_time) {
		this.cumulative_time = cumulative_time;
	}



	public String getRealy_time() {
		return realy_time;
	}



	public void setRealy_time(String realy_time) {
		this.realy_time = realy_time;
	}



	public String getSt() {
		return st;
	}



	public void setSt(String st) {
		this.st = st;
	}

	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HeatEntity [dev_address=" + dev_address + ", dev_type=" + dev_type + ", energy=" + energy + ", flow="
				+ flow + ", cumulative_flow=" + cumulative_flow + ", supply_water_temperature="
				+ supply_water_temperature + ", return_water_temperature=" + return_water_temperature
				+ ", cumulative_time=" + cumulative_time + ", realy_time=" + realy_time + ", st=" + st + "]";
	}

}
