package fish.yu.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author yuliang-ds1
 * 前台在线用户展示VO
 */
public class RoleVO  extends Role implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String perms;

	public RoleVO() {}
	
	public RoleVO(Role role) {
		super(role);
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}
	
	

}
