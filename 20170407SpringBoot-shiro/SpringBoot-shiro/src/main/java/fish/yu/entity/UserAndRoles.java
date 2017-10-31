package fish.yu.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * 
 * @author yuliang-ds1
 * 用户and角色集合
 */

public class UserAndRoles  {
	
	private static final long serialVersionUID = 1L;
	
	private  String id;
	
	private  String name;//用户name
	
	private List<Role> roles;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	
	
	

}
