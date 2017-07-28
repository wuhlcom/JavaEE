/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 下午2:30:41 * 
*/
package com.dazk.db.model;

import javax.persistence.Table;

@Table(name = "role_permission")
public class RolePermission extends BaseEntity {
	// id` int(11) NOT NULL AUTO_INCREMENT,
	private Long id;
	// `reso_code` int(20) NOT NULL COMMENT '菜单ID',
	private String reso_code;
	// `disused` int(4) NOT NULL COMMENT '是否废弃',
	private Integer disused;
	// `role_code` int(20) NOT NULL,
	private String role_code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDisused() {
		return disused;
	}

	public void setDisused(Integer disused) {
		this.disused = disused;
	}

	public String getReso_code() {
		return reso_code;
	}

	public void setReso_code(String reso_code) {
		this.reso_code = reso_code;
	}

	public String getRole_code() {
		return role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

}
