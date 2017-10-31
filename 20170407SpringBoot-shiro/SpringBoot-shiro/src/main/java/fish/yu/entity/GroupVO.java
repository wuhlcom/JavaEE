package fish.yu.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author yuliang-ds1
 * 前台在线用户展示VO
 */
public class GroupVO  extends Group implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private   String  roles;//角色集合
	
	private   String  perms;//权限集合
	

	public GroupVO() {}
	
	public GroupVO(Group group) {
		super(group);
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}


}
