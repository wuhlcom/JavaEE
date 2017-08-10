/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月8日 下午7:27:45 * 
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
@Table(name = "lr_gateway")
public class LoraGateway {
	// `id` int(11) NOT NULL AUTO_INCREMENT,
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	// `lr_user_uid` char(64) NOT NULL,
	@Column(name = "lr_user_uid")
	private String userid;
	
	// `lr_uuid` char(16) NOT NULL,
	@Column(name = "lr_uuid")
	private String uuid;
	
	// `lr_type` varchar(32) NOT NULL COMMENT '网关型号',
	@Column(name = "lr_type")
	private String type;
	
	// `lr_name` varchar(32) NOT NULL COMMENT '网关名称',	
	@Column(name = "lr_name")
	private String name;
	
	// `lr_mac` char(17) NOT NULL,
	@Column(name = "lr_mac")
	private String mac;
	
	// `lr_city` varchar(32) NOT NULL COMMENT '城市',
	@Column(name = "lr_city")
	private String city="";
	
	// `lr_area` varchar(32) NOT NULL COMMENT '区域',
	@Column(name = "lr_area")
	private String area="";
	
	// `lr_addr` varchar(32) NOT NULL COMMENT '地址',
	@Column(name = "lr_addr")
	private String addr="";
	
	// `lr_createtime` datetime NOT NULL,
	@Column(name = "lr_createtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	
	// `lr_channel_plan_id` int(15) NOT NULL COMMENT 'lora参数 信道计划id',
	@Column(name = "lr_channel_plan_id")
	private Long channel_plan_id=0L;
	
	// `lr_secret` char(32) NOT NULL DEFAULT 'ffffffffffffffffffffffffffffffff',
	@Column(name = "lr_secret")
	private String secret = "ffffffffffffffffffffffffffffffff";
	
	// `lr_powe` tinyint(15) NOT NULL DEFAULT '14' COMMENT 'lora参数',
	@Column(name = "lr_powe")
	private Integer power = 14;
	
	// `lr_status` tinyint(4) NOT NULL DEFAULT '0',
	@Column(name = "lr_status")
	private Integer status=0;
	
	// `lr_coordinate` varchar(30) NOT NULL COMMENT '坐标',
	@Column(name = "lr_coordinate")
	private String coordinate="";
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
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
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Long getChannel_plan_id() {
		return channel_plan_id;
	}
	public void setChannel_plan_id(Long channel_plan_id) {
		this.channel_plan_id = channel_plan_id;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoraGateway [id=" + id + ", userid=" + userid + ", uuid=" + uuid + ", type=" + type + ", name=" + name
				+ ", mac=" + mac + ", city=" + city + ", area=" + area + ", addr=" + addr + ", createtime=" + createtime
				+ ", channel_plan_id=" + channel_plan_id + ", secret=" + secret + ", power=" + power + ", status="
				+ status + ", coordinate=" + coordinate + "]";
	}
	

}
