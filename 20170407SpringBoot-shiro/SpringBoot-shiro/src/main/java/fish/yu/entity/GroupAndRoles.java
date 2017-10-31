package fish.yu.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * 
 * @author yuliang-ds1
 * 组+角色集合
 */

public class GroupAndRoles  {
	
	private static final long serialVersionUID = 1L;
	
	private  String gid;//组Id集合
	
	private  String gname;//组名称
	
	private  String gtype;//组类型
	
	private List<Role> roles;

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getGtype() {
		return gtype;
	}

	public void setGtype(String gtype) {
		this.gtype = gtype;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	


	
	
}
