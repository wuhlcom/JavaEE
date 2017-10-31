/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 上午11:46:02 * 
*/
package com.enviroment.db.entity;
import javax.persistence.Table;

@Table(name = "en_power")
public class PowerEntity extends BaseEntity {
	private String dev_address; // `dev_address` varchar(20) NOT NULL COMMENT
								// '设备识别编号',
	private Integer dev_type; // `dev_type` tinyint(2) DEFAULT NULL COMMENT
								// '设备类型',
	private Double power; // `power` float(32,0) NOT NULL COMMENT '电量 kWh',


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

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PowerEntity [dev_address=" + dev_address + ", dev_type=" + dev_type + ", power=" + power ;
	}

}
