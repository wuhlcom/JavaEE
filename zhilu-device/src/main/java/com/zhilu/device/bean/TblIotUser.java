/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月21日 上午10:26:09 * 
*/
package com.zhilu.device.bean;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="tbl_iot_user")
@Entity
public class TblIotUser {
	// `id` varchar(16) NOT NULL,
	@Id
	private String id;
	// `account` varchar(32) NOT NULL,
	private String account;
	// `password` varchar(32) NOT NULL,
	private String password;
	// `createtime` datetime DEFAULT NULL,
	private Timestamp createtime;
	// `userid` varchar(64) NOT NULL COMMENT '用户ID',
	private String userid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
