/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月8日 下午7:27:45 * 
*/
package com.zhilu.device.bean.secondary;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity()
@Table(name = "lr_gateway")
public class LoraGateway {
	// `id` int(11) NOT NULL AUTO_INCREMENT,
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	// `lr_user_uid` char(64) NOT NULL,
	private String lr_user_uid;
	// `lr_uuid` char(16) NOT NULL,
	private String lr_uuid;
	// `lr_type` varchar(32) NOT NULL COMMENT '网关型号',
	private String lr_type;
	// `lr_name` varchar(32) NOT NULL COMMENT '网关名称',
	private String lr_name;
	// `lr_mac` char(17) NOT NULL,
	private String lr_mac;
	// `lr_city` varchar(32) NOT NULL COMMENT '城市',
	private String lr_city;
	// `lr_area` varchar(32) NOT NULL COMMENT '区域',
	private String lr_area;
	// `lr_addr` varchar(32) NOT NULL COMMENT '地址',
	private String lr_addr;
	// `lr_createtime` datetime NOT NULL,
	@Temporal(TemporalType.TIMESTAMP)
	private Date lr_createtime;
	// `lr_channel_plan_id` int(15) NOT NULL COMMENT 'lora参数 信道计划id',
	private Long lr_channel_plan_id;
	// `lr_secret` char(32) NOT NULL DEFAULT 'ffffffffffffffffffffffffffffffff',
	private String lr_secret = "ffffffffffffffffffffffffffffffff";
	// `lr_powe` tinyint(15) NOT NULL DEFAULT '14' COMMENT 'lora参数',
	private Integer lr_powe = 14;
	// `lr_status` tinyint(4) NOT NULL DEFAULT '0',
	private Integer lr_status;
	// `lr_coordinate` varchar(30) NOT NULL COMMENT '坐标',
	private String lr_coordinate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLr_user_uid() {
		return lr_user_uid;
	}
	public void setLr_user_uid(String lr_user_uid) {
		this.lr_user_uid = lr_user_uid;
	}
	public String getLr_uuid() {
		return lr_uuid;
	}
	public void setLr_uuid(String lr_uuid) {
		this.lr_uuid = lr_uuid;
	}
	public String getLr_type() {
		return lr_type;
	}
	public void setLr_type(String lr_type) {
		this.lr_type = lr_type;
	}
	public String getLr_name() {
		return lr_name;
	}
	public void setLr_name(String lr_name) {
		this.lr_name = lr_name;
	}
	public String getLr_mac() {
		return lr_mac;
	}
	public void setLr_mac(String lr_mac) {
		this.lr_mac = lr_mac;
	}
	public String getLr_city() {
		return lr_city;
	}
	public void setLr_city(String lr_city) {
		this.lr_city = lr_city;
	}
	public String getLr_area() {
		return lr_area;
	}
	public void setLr_area(String lr_area) {
		this.lr_area = lr_area;
	}
	public String getLr_addr() {
		return lr_addr;
	}
	public void setLr_addr(String lr_addr) {
		this.lr_addr = lr_addr;
	}
	public Date getLr_createtime() {
		return lr_createtime;
	}
	public void setLr_createtime(Date lr_createtime) {
		this.lr_createtime = lr_createtime;
	}
	public Long getLr_channel_plan_id() {
		return lr_channel_plan_id;
	}
	public void setLr_channel_plan_id(Long lr_channel_plan_id) {
		this.lr_channel_plan_id = lr_channel_plan_id;
	}
	public String getLr_secret() {
		return lr_secret;
	}
	public void setLr_secret(String lr_secret) {
		this.lr_secret = lr_secret;
	}
	public Integer getLr_powe() {
		return lr_powe;
	}
	public void setLr_powe(Integer lr_powe) {
		this.lr_powe = lr_powe;
	}
	public Integer getLr_status() {
		return lr_status;
	}
	public void setLr_status(Integer lr_status) {
		this.lr_status = lr_status;
	}
	public String getLr_coordinate() {
		return lr_coordinate;
	}
	public void setLr_coordinate(String lr_coordinate) {
		this.lr_coordinate = lr_coordinate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoraGateway [id=" + id + ", lr_name=" + lr_name + ", lr_mac=" + lr_mac + ", lr_addr=" + lr_addr + "]";
	}	
	
}
