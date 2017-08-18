/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月15日 下午5:37:15 * 
*/
package com.dazk.db.param;

public class UserParam {
	private Integer type;
	private Long parentUser;
	private String userName;
	private String roleName;
	private Integer page = 1;
	private Integer start = 0;
	private Integer listRows;		

	/**
	 * @param type
	 * @param parentUser
	 * @param userName
	 * @param roleName
	 * @param page
	 * @param start
	 * @param listRows
	 */
	public UserParam(Integer type, Long parentUser, String userName, String roleName, Integer page, Integer listRows) {
		super();
		this.type = type;
		this.parentUser = parentUser;
		this.userName = userName;
		this.roleName = roleName;
		this.page = page;	
		this.listRows = listRows;
		if (this.listRows != null) {
			start = (this.page - 1) * this.listRows;
		}
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


	public Long getParentUser() {
		return parentUser;
	}


	public void setParentUser(Long parentUser) {
		this.parentUser = parentUser;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}
	

	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserParam [type=" + type + ", parentUser=" + parentUser + ", userName=" + userName + ", roleName="
				+ roleName + ", page=" + page + ", start=" + start + ", listRows=" + listRows + "]";
	}
	
	

}
