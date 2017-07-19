/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月19日 下午2:22:24 * 
*/
package com.zhilu.device.bean;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tbl_iot_device_basic")
@Entity
public class TblIotDeviceBasic {
	// `deviceid` varchar(16) NOT NULL,
	@Id
	private String deviceid;
	// `userid` varchar(64) DEFAULT NULL,
	private String userid;
	// `name` varchar(64) NOT NULL,
	private String name;
	// `wanmac` varchar(20) NOT NULL,
	private String wanmac;
	// `secret` varchar(36) DEFAULT 'ffffffffffffffffffffffffffffffff',
	private String secret = "ffffffffffffffffffffffffffffffff";
	// `secret_bak` varchar(36) NOT NULL DEFAULT
	// 'ffffffffffffffffffffffffffffffff' COMMENT '保存上次更新的secret',
	private String secret_bak = "ffffffffffffffffffffffffffffffff";
	// `code` varchar(16) DEFAULT NULL,
	private String code;
	// `citycode` varchar(16) DEFAULT NULL,
	private String citycode;
	// `region` varchar(256) DEFAULT NULL,
	private String region;
	// `fwversion` varchar(32) DEFAULT NULL,
	private String fwversion;
	// `apversion` varchar(32) DEFAULT NULL,
	private String apversion;
	// `hardware` varchar(32) DEFAULT NULL,
	private String hardware;
	// `versionid` varchar(16) DEFAULT NULL,
	private String versionid;
	// `type` tinyint(1) DEFAULT '1' COMMENT '1：网关 2：Thing',
	private Integer type;
	// `devstate` tinyint(1) DEFAULT '0',
	private Integer devstate;
	// `status` tinyint(1) DEFAULT '2' COMMENT '0-离线，1-在线, 2-未激活',
	private Integer status;
	// `routerswitch` tinyint(4) DEFAULT '0' COMMENT '路由开关，默认关闭',
	private Integer routerswitch;
	// `createuser` int(10) DEFAULT NULL,
	private String createuser;
	// `createtime` datetime DEFAULT NULL,
	private Timestamp createtime;
	// `logintime` datetime DEFAULT NULL,
	private Timestamp logintime;
	// `upgradeno` int(5) DEFAULT '0',
	private Long upgradeno;
	// `description` varchar(256) DEFAULT NULL,
	private String description;
	// `offlinetime` datetime DEFAULT NULL,
	private Timestamp offlinetime;
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWanmac() {
		return wanmac;
	}
	public void setWanmac(String wanmac) {
		this.wanmac = wanmac;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getSecret_bak() {
		return secret_bak;
	}
	public void setSecret_bak(String secret_bak) {
		this.secret_bak = secret_bak;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getFwversion() {
		return fwversion;
	}
	public void setFwversion(String fwversion) {
		this.fwversion = fwversion;
	}
	public String getApversion() {
		return apversion;
	}
	public void setApversion(String apversion) {
		this.apversion = apversion;
	}
	public String getHardware() {
		return hardware;
	}
	public void setHardware(String hardware) {
		this.hardware = hardware;
	}
	public String getVersionid() {
		return versionid;
	}
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getDevstate() {
		return devstate;
	}
	public void setDevstate(Integer devstate) {
		this.devstate = devstate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRouterswitch() {
		return routerswitch;
	}
	public void setRouterswitch(Integer routerswitch) {
		this.routerswitch = routerswitch;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getLogintime() {
		return logintime;
	}
	public void setLogintime(Timestamp logintime) {
		this.logintime = logintime;
	}
	public Long getUpgradeno() {
		return upgradeno;
	}
	public void setUpgradeno(Long upgradeno) {
		this.upgradeno = upgradeno;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getOfflinetime() {
		return offlinetime;
	}
	public void setOfflinetime(Timestamp offlinetime) {
		this.offlinetime = offlinetime;
	}
	
}
