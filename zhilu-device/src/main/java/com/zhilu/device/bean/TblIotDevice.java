package com.zhilu.device.bean;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.zhilu.device.util.PubMethod;

@Entity()
@Table(name = "tbl_iot_device")
public class TblIotDevice {
	// `id` varchar(16) NOT NULL,
	@Id
	private String id;
	
	// `name` varchar(70) NOT NULL DEFAULT '',
	@NotNull
	private String name="";
	
	// `basename` varchar(64) NOT NULL,
	private String basename = "";
	
	// `userid` varchar(64) NOT NULL,
	private String userid = "";
	
	// `sceneid` varchar(16) DEFAULT NULL COMMENT '场景ID',
	private String sceneid;
	
	// `productid` varchar(16) NOT NULL COMMENT '所属产品id',
	private String productid = "";
	
	// `protocol` tinyint(4) NOT NULL COMMENT '协议',
	private Integer protocol = 0;
	
	// `mac` varchar(20) NOT NULL COMMENT 'mac地址',
	private String mac = "FF:FF:FF:FF:FF:FF";
	
	// `groupid` varchar(16) DEFAULT NULL COMMENT '所属设备组',
	private String groupid;
	
	// `province` char(7) NOT NULL,
	private String province = "";
	
	// `city` char(7) NOT NULL,
	private String city = "";
	
	// `address` varchar(125) NOT NULL,
	private String address = "";
	
	// `tlsswitch` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'tsl开关',
	private Integer tlsswitch=0;
	
	// `tlsprotocol` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'TLS协议版本',
	private Integer tlsprotocol=0;
	
	// `tlsencrypt` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'TSL加密算法',
	private Integer tlsencrypt=0;
	
	// `tlscheck` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'TLS校验算法',
	private Integer tlscheck=0;
	// `description` varchar(256) NOT NULL COMMENT '设备描述',	
	private String description = "";	
	
	// `confpath` varchar(64) NOT NULL,
	private String confpath = "";
	
	// `iotmodelconf` varchar(565) NOT NULL,
	private String iotmodelconf = "";
	
	// `username` varchar(64) NOT NULL DEFAULT '',
	private String username = "";
	
	// `createtime` datetime NOT NULL,
	private Timestamp createtime = PubMethod.str2timestamp();
	
	// `identification` varchar(64) NOT NULL COMMENT '设备唯一表示',
	private String identification = "";
	
	// `type` tinyint(5) NOT NULL DEFAULT '1' COMMENT '设备类型，1：网关，2：thing物体',
	private Integer type=1;
	
	// `thing_id` varchar(64) DEFAULT NULL COMMENT '场景下设备关联的thing',
	private String thing_id;
	
	// `llegal_mac` tinyint(4) NOT NULL DEFAULT '0' COMMENT	'是否是黑名单mac,默认为0代表不是',
	private Integer llegal_mac=0;
	
	// `longitude` varchar(50) DEFAULT NULL COMMENT '经度',
	private String longitude;
	
	// `latitude` varchar(50) DEFAULT NULL COMMENT '纬度',
	private String latitude;
	
	// `product` varchar(255) DEFAULT NULL,
	private String product;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBasename() {
		return basename;
	}

	public void setBasename(String basename) {
		this.basename = basename;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSceneid() {
		return sceneid;
	}

	public void setSceneid(String sceneid) {
		this.sceneid = sceneid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public Integer getProtocol() {
		return protocol;
	}

	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTlsswitch() {
		return tlsswitch;
	}

	public void setTlsswitch(Integer tlsswitch) {
		this.tlsswitch = tlsswitch;
	}

	public Integer getTlsprotocol() {
		return tlsprotocol;
	}

	public void setTlsprotocol(Integer tlsprotocol) {
		this.tlsprotocol = tlsprotocol;
	}

	public Integer getTlsencrypt() {
		return tlsencrypt;
	}

	public void setTlsencrypt(Integer tlsencrypt) {
		this.tlsencrypt = tlsencrypt;
	}

	public Integer getTlscheck() {
		return tlscheck;
	}

	public void setTlscheck(Integer tlscheck) {
		this.tlscheck = tlscheck;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConfpath() {
		return confpath;
	}

	public void setConfpath(String confpath) {
		this.confpath = confpath;
	}

	public String getIotmodelconf() {
		return iotmodelconf;
	}

	public void setIotmodelconf(String iotmodelconf) {
		this.iotmodelconf = iotmodelconf;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getThing_id() {
		return thing_id;
	}

	public void setThing_id(String thing_id) {
		this.thing_id = thing_id;
	}

	public Integer getLlegal_mac() {
		return llegal_mac;
	}

	public void setLlegal_mac(Integer llegal_mac) {
		this.llegal_mac = llegal_mac;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
}
