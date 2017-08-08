/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月8日 下午7:27:06 * 
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
@Table(name = "lr_device")
public class LoraDevice {
	// `id` int(11) NOT NULL AUTO_INCREMENT,
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;

	// `lr_uuid` char(16) NOT NULL,
	private String lr_uuid;
	// `lr_deveui` char(23) NOT NULL COMMENT '设备EUI',
	private String lr_deveui;
	// `lr_devaddr` char(11) NOT NULL COMMENT '设备devaddr',
	private String lr_devaddr;
	// `lr_nwkskey` char(32) NOT NULL,
	private String lr_nwkskey;
	// `lr_appskey` char(32) NOT NULL,
	private String lr_appskey;
	// `lr_appkey` char(32) NOT NULL,
	private String lr_appkey;
	// `lr_createtime` datetime NOT NULL,
	@Temporal(TemporalType.TIMESTAMP)
	private Date lr_createtime;
	// `lr_app_id` char(16) NOT NULL,
	private String lr_app_id;
	// `lr_name` varchar(15) NOT NULL COMMENT '设备名称',
	private String lr_name;
	// `lr_type` char(1) NOT NULL DEFAULT 'C' COMMENT '终端类型(A 或 C)',
	private String lr_type = "C";
	// `lr_city` varchar(15) NOT NULL,
	private String lr_city;
	// `lr_area` varchar(15) NOT NULL,
	private String lr_area;
	// `lr_addr` varchar(15) NOT NULL,
	private String lr_addr;
	// `lr_protocol` varchar(15) NOT NULL DEFAULT 'LoRaWAN1.0.1' COMMENT '协议版本',
	private String lr_protocol = "LoRaWAN1.0.1";
	// `lr_rxwindow` varchar(10) NOT NULL DEFAULT 'RX1' COMMENT 'lora wan参数',
	private String lr_rxwindow = "RX1";
	// `lr_rx1offset` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'lorawan 参数',
	private String lr_rx1offset = "0";
	// `lr_rxdelay` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'lorawan 参数',
	private String lr_rxdelay = "1";
	// `lr_rx2dr` tinyint(4) NOT NULL DEFAULT '3' COMMENT 'lorawan参数',
	private String lr_rx2dr = "3";
	// `lr_rx2frequency` varchar(15) NOT NULL DEFAULT '471.3000' COMMENT
	// 'lorawan参数',
	private String lr_rx2frequency = "471.3000";
	// `lr_class` char(2) NOT NULL COMMENT '设备类型(例如：水表 电表等)',
	private String lr_class;
	// `lr_ism` varchar(25) NOT NULL COMMENT '频段',
	private String lr_ism;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLr_uuid() {
		return lr_uuid;
	}

	public void setLr_uuid(String lr_uuid) {
		this.lr_uuid = lr_uuid;
	}

	public String getLr_deveui() {
		return lr_deveui;
	}

	public void setLr_deveui(String lr_deveui) {
		this.lr_deveui = lr_deveui;
	}

	public String getLr_devaddr() {
		return lr_devaddr;
	}

	public void setLr_devaddr(String lr_devaddr) {
		this.lr_devaddr = lr_devaddr;
	}

	public String getLr_nwkskey() {
		return lr_nwkskey;
	}

	public void setLr_nwkskey(String lr_nwkskey) {
		this.lr_nwkskey = lr_nwkskey;
	}

	public String getLr_appskey() {
		return lr_appskey;
	}

	public void setLr_appskey(String lr_appskey) {
		this.lr_appskey = lr_appskey;
	}

	public String getLr_appkey() {
		return lr_appkey;
	}

	public void setLr_appkey(String lr_appkey) {
		this.lr_appkey = lr_appkey;
	}

	public Date getLr_createtime() {
		return lr_createtime;
	}

	public void setLr_createtime(Date lr_createtime) {
		this.lr_createtime = lr_createtime;
	}

	public String getLr_app_id() {
		return lr_app_id;
	}

	public void setLr_app_id(String lr_app_id) {
		this.lr_app_id = lr_app_id;
	}

	public String getLr_name() {
		return lr_name;
	}

	public void setLr_name(String lr_name) {
		this.lr_name = lr_name;
	}

	public String getLr_type() {
		return lr_type;
	}

	public void setLr_type(String lr_type) {
		this.lr_type = lr_type;
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

	public String getLr_protocol() {
		return lr_protocol;
	}

	public void setLr_protocol(String lr_protocol) {
		this.lr_protocol = lr_protocol;
	}

	public String getLr_rxwindow() {
		return lr_rxwindow;
	}

	public void setLr_rxwindow(String lr_rxwindow) {
		this.lr_rxwindow = lr_rxwindow;
	}

	public String getLr_rx1offset() {
		return lr_rx1offset;
	}

	public void setLr_rx1offset(String lr_rx1offset) {
		this.lr_rx1offset = lr_rx1offset;
	}

	public String getLr_rxdelay() {
		return lr_rxdelay;
	}

	public void setLr_rxdelay(String lr_rxdelay) {
		this.lr_rxdelay = lr_rxdelay;
	}

	public String getLr_rx2dr() {
		return lr_rx2dr;
	}

	public void setLr_rx2dr(String lr_rx2dr) {
		this.lr_rx2dr = lr_rx2dr;
	}

	public String getLr_rx2frequency() {
		return lr_rx2frequency;
	}

	public void setLr_rx2frequency(String lr_rx2frequency) {
		this.lr_rx2frequency = lr_rx2frequency;
	}

	public String getLr_class() {
		return lr_class;
	}

	public void setLr_class(String lr_class) {
		this.lr_class = lr_class;
	}

	public String getLr_ism() {
		return lr_ism;
	}

	public void setLr_ism(String lr_ism) {
		this.lr_ism = lr_ism;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoraDevice [id=" + id + ", lr_deveui=" + lr_deveui + ", lr_devaddr=" + lr_devaddr + ", lr_name="
				+ lr_name + "]";
	}

}
