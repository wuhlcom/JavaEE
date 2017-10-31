/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 上午11:46:02 * 
*/
package com.enviroment.db.entity;

import javax.persistence.Table;

@Table(name = "en_gas")
public class GasEntity extends BaseEntity {
	private String dev_address; // `dev_address` varchar(20) NOT NULL COMMENT
								// '设备识别编号',
	private Integer dev_type; // `dev_type` tinyint(2) DEFAULT NULL COMMENT
								// '设备类型',
	private Double cumulant;// `cumulant` float(10,0) DEFAULT NULL COMMENT '累计量
							// 单位立方米',
	private String status;// `status` varchar(10) DEFAULT NULL,
	private String attacks;// `attacks` varchar(10) DEFAULT NULL,

	public String getDev_address() {
		return dev_address;
	}

	public void setDev_address(String dev_address) {
		this.dev_address = dev_address;
	}

	public Integer getDev_type() {
		return dev_type;
	}

	public Double getCumulant() {
		return cumulant;
	}

	public void setCumulant(Double cumulant) {
		this.cumulant = cumulant;
	}

	public void setDev_type(Integer dev_type) {
		this.dev_type = dev_type;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttacks() {
		return attacks;
	}

	public void setAttacks(String attacks) {
		this.attacks = attacks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GasEntity [dev_address=" + dev_address + ", dev_type=" + dev_type + ", cumulant=" + cumulant
				+ ", status=" + status + ", attacks=" + attacks + "]";
	}

}
