/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月8日 下午7:27:06 * 
*/
package com.zhilu.device.bean.secondary;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity()
@Table(name = "lr_device")
public class LoraDevice {
	// `id` int(11) NOT NULL AUTO_INCREMENT,
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// `lr_uuid` char(16) NOT NULL,
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "lr_uuid")
	private String uuid;

	// `lr_deveui` char(23) NOT NULL COMMENT '设备EUI',
	@Column(name = "lr_deveui")
	private String deveui;

	// `lr_devaddr` char(11) NOT NULL COMMENT '设备devaddr',
	@Column(name = "lr_devaddr")
	private String devaddr;

	// `lr_nwkskey` char(32) NOT NULL,
	@Column(name = "lr_nwkskey")
	private String nwkskey;

	// `lr_appskey` char(32) NOT NULL,
	@Column(name = "lr_appskey")
	private String appskey;

	// `lr_appkey` char(32) NOT NULL,
	@Column(name = "lr_appkey")
	private String appkey;

	// `lr_createtime` datetime NOT NULL,
	@Column(name = "lr_createtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	// `lr_app_id` char(16) NOT NULL,
	@Column(name = "lr_app_id")
	private String app_id;

	// `lr_name` varchar(15) NOT NULL COMMENT '设备名称',
	@Column(name = "lr_name")
	private String name;

	// `lr_type` char(1) NOT NULL DEFAULT 'C' COMMENT '终端类型(A 或 C)',
	@Column(name = "lr_type")
	private String type = "C";

	// `lr_city` varchar(15) NOT NULL,
	@Column(name = "lr_city")
	private String city="";

	// `lr_area` varchar(15) NOT NULL,
	@Column(name = "lr_area")
	private String area="";

	// `lr_addr` varchar(15) NOT NULL,
	@Column(name = "lr_addr")
	private String addr="";

	// `lr_protocol` varchar(15) NOT NULL DEFAULT 'LoRaWAN1.0.1' COMMENT '协议版本',
	@Column(name = "lr_protocol")
	private String protocol = "LoRaWAN1.0.1";

	// `lr_rxwindow` varchar(10) NOT NULL DEFAULT 'RX1' COMMENT 'lora wan参数',
	@Column(name = "lr_rxwindow")
	private String rxwindow = "RX1";

	// `lr_rx1offset` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'lorawan 参数',
	@Column(name = "lr_rx1offset")
	private String rx1offset = "0";

	// `lr_rxdelay` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'lorawan 参数',
	@Column(name = "lr_rxdelay")
	private String rxdelay = "1";

	// `lr_rx2dr` tinyint(4) NOT NULL DEFAULT '3' COMMENT 'lorawan参数',
	@Column(name = "lr_rx2dr")
	private String rx2dr = "3";

	// `lr_rx2frequency` varchar(15) NOT NULL DEFAULT '471.3000' COMMENT
	// 'lorawan参数',
	@Column(name = "lr_rx2frequency")
	private String rx2frequency = "471.3000";

	// `lr_class` char(2) NOT NULL COMMENT '设备类型(例如：水表 电表等)',
	@Column(name = "lr_class")
	private String clazz="电表";

	// `lr_ism` varchar(25) NOT NULL COMMENT '频段',
	@Column(name = "lr_ism")
	private String ism="";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDeveui() {
		return deveui;
	}

	public void setDeveui(String deveui) {
		this.deveui = deveui;
	}

	public String getDevaddr() {
		return devaddr;
	}

	public void setDevaddr(String devaddr) {
		this.devaddr = devaddr;
	}

	public String getNwkskey() {
		return nwkskey;
	}

	public void setNwkskey(String nwkskey) {
		this.nwkskey = nwkskey;
	}

	public String getAppskey() {
		return appskey;
	}

	public void setAppskey(String appskey) {
		this.appskey = appskey;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getRxwindow() {
		return rxwindow;
	}

	public void setRxwindow(String rxwindow) {
		this.rxwindow = rxwindow;
	}

	public String getRx1offset() {
		return rx1offset;
	}

	public void setRx1offset(String rx1offset) {
		this.rx1offset = rx1offset;
	}

	public String getRxdelay() {
		return rxdelay;
	}

	public void setRxdelay(String rxdelay) {
		this.rxdelay = rxdelay;
	}

	public String getRx2dr() {
		return rx2dr;
	}

	public void setRx2dr(String rx2dr) {
		this.rx2dr = rx2dr;
	}

	public String getRx2frequency() {
		return rx2frequency;
	}

	public void setRx2frequency(String rx2frequency) {
		this.rx2frequency = rx2frequency;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getIsm() {
		return ism;
	}

	public void setIsm(String ism) {
		this.ism = ism;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoraDevice [id=" + id + ", deveui=" + deveui + ", devaddr=" + devaddr + ", name=" + name + "]";
	}

	
}
