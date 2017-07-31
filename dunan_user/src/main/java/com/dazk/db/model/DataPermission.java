/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 下午1:50:54 * 
*/
package com.dazk.db.model;

import javax.persistence.Column;

public class DataPermission extends BaseEntity {

	// `id` int(11) NOT NULL AUTO_INCREMENT,
	private Long id;
	// `user_id` int(11) NOT NULL COMMENT '账号ID',
	private Long user_id;
	// `code` varchar(32) NOT NULL COMMENT '公司编号,热站编号，小区编号',
	@Column(name="codeValue")
	private String codeValue;
	// `dataType` tinyint(4) NOT NULL COMMENT '数据权限类型,1 增删改查 0：查询',
	@Column(name="dataType")
	private Integer dataType;
	// `codeType` tinyint(4) NOT NULL COMMENT '1 公司编号 2 热站编号 3 小区编号',
	@Column(name="codeType")
	private Integer codeType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}


	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

}
