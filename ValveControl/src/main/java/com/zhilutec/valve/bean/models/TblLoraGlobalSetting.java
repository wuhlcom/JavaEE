/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月20日 下午6:22:51 * 
*/
package com.zhilutec.valve.bean.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "zl_lora_global_setting")
public class TblLoraGlobalSetting {
	// `id` tinyint(4) NOT NULL,
	@Id
	private Integer id;
	// `period_setting` int(11) DEFAULT '-1' COMMENT '上报周期',
	private Long period_setting;
	// `timing_operate` int(11) DEFAULT '0' COMMENT '定时操作间隔。单位天',
	private Long timing_operate;
	// `temp_upper_limit` double DEFAULT '0' COMMENT '温控器温度上限',
	private Double temp_upper_limit;
	// `temp_lower_limit` double DEFAULT '0' COMMENT '温控器温度下限',
	private Double temp_lower_limit;	
	// `temp_upper_limit` double DEFAULT '0' COMMENT '温控器温度上限',
	private Double temp_setting;
	// `temp_lower_limit` double DEFAULT '0' COMMENT '温控器温度下限',
	private Double anti_freezing_temp;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getPeriod_setting() {
		return period_setting;
	}
	public void setPeriod_setting(Long period_setting) {
		this.period_setting = period_setting;
	}
	public Long getTiming_operate() {
		return timing_operate;
	}
	public void setTiming_operate(Long timing_operate) {
		this.timing_operate = timing_operate;
	}
	public Double getTemp_upper_limit() {
		return temp_upper_limit;
	}
	public void setTemp_upper_limit(Double temp_upper_limit) {
		this.temp_upper_limit = temp_upper_limit;
	}
	public Double getTemp_lower_limit() {
		return temp_lower_limit;
	}
	public void setTemp_lower_limit(Double temp_lower_limit) {
		this.temp_lower_limit = temp_lower_limit;
	}
	public Double getTemp_setting() {
		return temp_setting;
	}
	public void setTemp_setting(Double temp_setting) {
		this.temp_setting = temp_setting;
	}
	public Double getAnti_freezing_temp() {
		return anti_freezing_temp;
	}
	public void setAnti_freezing_temp(Double anti_freezing_temp) {
		this.anti_freezing_temp = anti_freezing_temp;
	}	
}
