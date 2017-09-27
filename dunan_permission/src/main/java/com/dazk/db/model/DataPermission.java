/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 下午1:50:54 * 
*/
package com.dazk.db.model;

import javax.persistence.Column;

public class DataPermission extends BaseEntity {

	private Long user_id;
	// `code` varchar(32) NOT NULL COMMENT '公司编号,热站编号，小区编号',
	@Column(name="code_value")
	private String code_value;
	// `dataType` tinyint(4) NOT NULL COMMENT '数据权限类型,1 增删改查 0：查询',
	@Column(name="data_type")
	private Integer data_type;
	// `codeType` tinyint(4) NOT NULL COMMENT '1 公司编号 2 热站编号 3 小区编号',
	@Column(name="code_type")
	private Integer code_type;

	public String getCode_value() {
		return code_value;
	}

	public void setCode_value(String code_value) {
		this.code_value = code_value;
	}

	public Integer getData_type() {
		return data_type;
	}

	public void setData_type(Integer data_type) {
		this.data_type = data_type;
	}

	public Integer getCode_type() {
		return code_type;
	}

	public void setCode_type(Integer code_type) {
		this.code_type = code_type;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataPermission [user_id=" + user_id + ", code_value=" + code_value + ", data_type=" + data_type
				+ ", code_type=" + code_type + "]";
	}


}
