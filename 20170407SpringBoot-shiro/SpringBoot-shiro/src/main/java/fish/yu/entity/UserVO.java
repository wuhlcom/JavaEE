package fish.yu.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author yuliang-ds1
 * 前台在线用户展示VO
 */
public class UserVO  extends User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private   String  sessionId;//sessionId
	
	private   String  hostName;//主机名称
	
	private   Date  startTime;//开始时间
	
	private   Date  lastAccess;//最后交互时间
	
	private   String  roles;//角色集合
	
	private   String  perms;//权限集合
	
	private   String  groups;//用户组集合
	
	private   String  groupRoleNames;//用户组集合
	
	private   String  groupPerms;//用户组权限集合
	
	private  long  timeout;//session有效时间
	
	private  Boolean  sessionStatus=Boolean.TRUE;//是否被踢出的状态

	public UserVO() {}
	
	public UserVO(User user) {
		super(user);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public Boolean getSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(Boolean sessionStatus) {
		this.sessionStatus = sessionStatus;
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

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getGroupPerms() {
		return groupPerms;
	}

	public void setGroupPerms(String groupPerms) {
		this.groupPerms = groupPerms;
	}

	public String getGroupRoleNames() {
		return groupRoleNames;
	}

	public void setGroupRoleNames(String groupRoleNames) {
		this.groupRoleNames = groupRoleNames;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " roles:"+roles+" perms:"+perms+" groups:"+groups+"  groupRoleNames"+groupRoleNames+"  groupPerms:"+groupPerms;
	}
	

}
