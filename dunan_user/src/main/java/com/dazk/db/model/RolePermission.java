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
	private Long reso_id;
	private Integer disused=1;
	private Long role_id;	
	/**
	 * 
	 */
	public RolePermission() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
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

	public Long getReso_id() {
		return reso_id;
	}

	public void setReso_id(Long reso_id) {
		this.reso_id = reso_id;
	}

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}


	@Override
	public String toString() {
		return "RolePermission [id=" + id + ", reso_id=" + reso_id + ", disused=" + disused + ", role_id=" + role_id
				+ "]";
	}	

}
