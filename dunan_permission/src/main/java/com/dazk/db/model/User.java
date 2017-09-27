/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午11:04:22 * 
*/
package com.dazk.db.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "user")
public class User extends BaseEntity {
	// `id` int(11) NOT NULL AUTO_INCREMENT,
//	private Long id;
	// `name` varchar(64) NOT NULL,
	private String nickname;
	// `name` varchar(32) NOT NULL,
	private String username;
	// `sex` tinyint(4) NOT NULL COMMENT '性别',
	private Integer sex = 0;
	// `age` tinyint(4) NOT NULL COMMENT '年龄',
	private Integer age;
	// `email` varchar(20) NOT NULL,
	private String email;
	// `company` varchar(20) NOT NULL COMMENT '公司',
	private String company;
	// `address` varchar(20) NOT NULL COMMENT '地址',
	private String address;
	// `telephone` varchar(20) NOT NULL COMMENT '电话',
	private String telephone;
	// `created_at` bigint(20) NOT NULL,
	private Long created_at;
	// `isdel` tinyint(4) NOT NULL DEFAULT '0',
	private Integer isdel;
	// `password` varchar(20) NOT NULL COMMENT '密码',
	private String password;
	// `lv` tinyint(4) DEFAULT NULL COMMENT '账号等级',
	private String lv;
	// `disused` tinyint(4) NOT NULL COMMENT '是否废弃',1表示使用
	private Integer disused = 1;
	// `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '说明',
	private String remark;

	private Long role_id;
	
	private String idcard;
	private String position;

	private String name;
	
	@Column(name="parent_user")
	private Long parent_user;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Long created_at) {
		this.created_at = created_at;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLv() {
		return lv;
	}

	public void setLv(String lv) {
		this.lv = lv;
	}

	public Integer getDisused() {
		return disused;
	}

	public void setDisused(Integer disused) {
		this.disused = disused;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	

	public Long getParent_user() {
		return parent_user;
	}

	public void setParent_user(Long parent_user) {
		this.parent_user = parent_user;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [nickname=" + nickname + ", username=" + username + ", sex=" + sex + ", age=" + age
				+ ", email=" + email + ", company=" + company + ", address=" + address + ", telephone=" + telephone
				+ ", created_at=" + created_at + ", isdel=" + isdel + ", password=" + password + ", lv=" + lv
				+ ", disused=" + disused + ", remark=" + remark + ", role_id=" + role_id + ", idcard=" + idcard
				+ ", position=" + position + ", name=" + name + ", parent_user=" + parent_user + "]";
	}

	
}
