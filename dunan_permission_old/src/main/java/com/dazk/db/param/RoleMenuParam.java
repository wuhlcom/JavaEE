/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月15日 下午5:37:15 * 
*/
package com.dazk.db.param;

public class RoleMenuParam {
	private Long roleId;
	private Integer page = 1;
	private Integer start = 0;
	private Integer listRows;		

	/**
	 * @param type
	 * @param roleId
	 * @param page
	 * @param start
	 * @param listRows
	 */
	public RoleMenuParam(Long roleId, Integer page, Integer listRows) {
		super();
		this.roleId = roleId;	
		this.page = page;	
		this.listRows = listRows;
		if (this.listRows != null) {
			start = (this.page - 1) * this.listRows;
		}
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getListRows() {
		return listRows;
	}

	public void setListRows(Integer listRows) {
		this.listRows = listRows;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RoleMenuParam [roleId=" + roleId + ", page=" + page + ", start=" + start + ", listRows=" + listRows
				+ "]";
	}


}
