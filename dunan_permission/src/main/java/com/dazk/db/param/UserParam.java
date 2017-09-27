/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月15日 下午5:37:15 * 
*/
package com.dazk.db.param;

import java.util.List;

public class UserParam {
	private Integer type;
	private Long parentUser;
	private Long userId;
	private List<Long> userIds;
	private Long roleId;
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
	public UserParam(Integer type, Long parentUser,Long userId,Long roleId, String userName, String roleName, Integer page, Integer listRows,List<Long> userIds) {
		super();
		this.type = type;
		this.parentUser = parentUser;
		this.userId = userId;
		this.roleId = roleId;
		this.userName = userName;
		this.roleName = roleName;
		this.page = page;	
		this.listRows = listRows;
		this.userIds = userIds;
		if (this.listRows != null) {
			start = (this.page - 1) * this.listRows;
		}
	}
	
	public UserParam(Long userId,Integer page, Integer listRows) {
		super();
		this.userId = userId;		
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

	

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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


	public List<Long> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<Long> userIds) {
		this.userIds = userIds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserParam [type=" + type + ", parentUser=" + parentUser + ", userId=" + userId + ", userIds=" + userIds
				+ ", roleId=" + roleId + ", userName=" + userName + ", roleName=" + roleName + ", page=" + page
				+ ", start=" + start + ", listRows=" + listRows + "]";
	}


}
