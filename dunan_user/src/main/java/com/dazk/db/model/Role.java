/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月26日 上午8:28:04 * 
*/
package com.dazk.db.model;

import java.math.BigInteger;

import javax.persistence.Table;

@Table(name = "role")
public class Role extends BaseEntity{
	// `id` int(11) NOT NULL AUTO_INCREMENT,
	private Long id;

	// `name` varchar(20) NOT NULL COMMENT '角色名称',
	private String name;
	
	// `code` varchar(20) NOT NULL COMMENT '角色编号',
	private String code;
	
	// `disuserd` tinyint(4) NOT NULL COMMENT '是否废弃',
	private Integer disuserd;
	
	// `remark` varchar(45) NOT NULL DEFAULT '' COMMENT '备注',
	private String remark;
	
	// `created_at` bigint(20) NOT NULL,
	private BigInteger created_at;
	
	// `isdel` tinyint(4) NOT NULL DEFAULT '0',
	private Integer isdel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getDisuserd() {
		return disuserd;
	}

	public void setDisuserd(Integer disuserd) {
		this.disuserd = disuserd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigInteger getCreated_at() {
		return created_at;
	}

	public void setCreated_at(BigInteger created_at) {
		this.created_at = created_at;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

}
