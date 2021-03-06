/**
 * 
 */
package com.zhilu.device.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author zhilu1234
 * 
 *         userid string 32     是 	平台用户编号 
 *         name string 64  	  是	设备名称 
 *         product string 16 是    所属产品
 *         protocol int 4   是   通讯协议 HTTP：0 HTTPS：1 MQTT：2 COAP：3 devices
 *         List<string>		  是   设备IMEI编号 
 *         province string 6 否   所属省份代码 
 *         city stirng 6 	   否      所属城市代码
 *         address string 256 否   详细地址
 *         description string 256 否 详细描述
 *
 */
@Entity()
@Table(name = "tbl_iot_device")
public class TblIotDevice {
	@Id
	private String id;
	@NotNull
	private String name;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the basename
	 */
	public String getBasename() {
		return basename;
	}
	/**
	 * @param basename the basename to set
	 */
	public void setBasename(String basename) {
		this.basename = basename;
	}
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the sceneid
	 */
	public String getSceneid() {
		return sceneid;
	}
	/**
	 * @param sceneid the sceneid to set
	 */
	public void setSceneid(String sceneid) {
		this.sceneid = sceneid;
	}
	/**
	 * @return the productid
	 */
	public String getProductid() {
		return productid;
	}
	/**
	 * @param productid the productid to set
	 */
	public void setProductid(String productid) {
		this.productid = productid;
	}
	/**
	 * @return the protocol
	 */
	public int getProtocol() {
		return protocol;
	}
	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}
	/**
	 * @param mac the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}
	/**
	 * @return the groupid
	 */
	public String getGroupid() {
		return groupid;
	}
	/**
	 * @param groupid the groupid to set
	 */
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the tlsswitch
	 */
	public int getTlsswitch() {
		return tlsswitch;
	}
	/**
	 * @param tlsswitch the tlsswitch to set
	 */
	public void setTlsswitch(int tlsswitch) {
		this.tlsswitch = tlsswitch;
	}
	/**
	 * @return the tlsprotocol
	 */
	public int getTlsprotocol() {
		return tlsprotocol;
	}
	/**
	 * @param tlsprotocol the tlsprotocol to set
	 */
	public void setTlsprotocol(int tlsprotocol) {
		this.tlsprotocol = tlsprotocol;
	}
	/**
	 * @return the tlsencrypt
	 */
	public int getTlsencrypt() {
		return tlsencrypt;
	}
	/**
	 * @param tlsencrypt the tlsencrypt to set
	 */
	public void setTlsencrypt(int tlsencrypt) {
		this.tlsencrypt = tlsencrypt;
	}
	/**
	 * @return the tlscheck
	 */
	public int getTlscheck() {
		return tlscheck;
	}
	/**
	 * @param tlscheck the tlscheck to set
	 */
	public void setTlscheck(int tlscheck) {
		this.tlscheck = tlscheck;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the confpath
	 */
	public String getConfpath() {
		return confpath;
	}
	/**
	 * @param confpath the confpath to set
	 */
	public void setConfpath(String confpath) {
		this.confpath = confpath;
	}
	/**
	 * @return the iotmodelconf
	 */
	public String getIotmodelconf() {
		return iotmodelconf;
	}
	/**
	 * @param iotmodelconf the iotmodelconf to set
	 */
	public void setIotmodelconf(String iotmodelconf) {
		this.iotmodelconf = iotmodelconf;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * @return the identification
	 */
	public String getIdentification() {
		return identification;
	}
	/**
	 * @param identification the identification to set
	 */
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the thing_id
	 */
	public String getThing_id() {
		return thing_id;
	}
	/**
	 * @param thing_id the thing_id to set
	 */
	public void setThing_id(String thing_id) {
		this.thing_id = thing_id;
	}
	/**
	 * @return the llegal_mac
	 */
	public int getLlegal_mac() {
		return llegal_mac;
	}
	/**
	 * @param llegal_mac the llegal_mac to set
	 */
	public void setLlegal_mac(int llegal_mac) {
		this.llegal_mac = llegal_mac;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	private String basename="";
	private String userid="";
	private String sceneid;
	private String productid="";
	private int protocol=0;
	private String mac="";
	private String groupid;	
	private String province="";
	private String city="";
	private String address="";
	private int tlsswitch;
	private int tlsprotocol;
	private int tlsencrypt;
	private int tlscheck;
	private String description="";
	private String confpath="";
	private String iotmodelconf="";
	private String username="";
	private Date createtime=new Date();
	private String identification="";
	private int type;
	private String thing_id;
	private int llegal_mac;
	private String longitude;
	private String latitude;
	private String product;	
}
