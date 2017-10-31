/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 上午11:46:02 * 
*/
package com.enviroment.db.entity;
import javax.persistence.Table;

@Table(name = "en_water")
public class WaterEntity extends BaseEntity {
	private String dev_address; // `dev_address` varchar(20) NOT NULL COMMENT
								// '设备识别编号',
	private Integer dev_type; // `dev_type` tinyint(2) DEFAULT NULL COMMENT
								// '设备类型',
	private Double  positive_flow;//`positive_flow` float(10,0) DEFAULT NULL COMMENT '正累计流量 单位立方米',
	private Double  negative_flow; //`negative_flow` float(10,0) DEFAULT NULL COMMENT '负累计流量 单位立方米',
	private String  realy_time; //`realy_time` int(16) DEFAULT NULL COMMENT '统计时间',


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

	public Double getPositive_flow() {
		return positive_flow;
	}

	public void setPositive_flow(Double positive_flow) {
		this.positive_flow = positive_flow;
	}

	public Double getNegative_flow() {
		return negative_flow;
	}

	public void setNegative_flow(Double negative_flow) {
		this.negative_flow = negative_flow;
	}

	public String getRealy_time() {
		return realy_time;
	}

	public void setRealy_time(String realy_time) {
		this.realy_time = realy_time;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WaterEntity [dev_address=" + dev_address + ", dev_type=" + dev_type + ", positive_flow=" + positive_flow
				+ ", negative_flow=" + negative_flow + ", realy_time=" + realy_time + "]";
	}

}
