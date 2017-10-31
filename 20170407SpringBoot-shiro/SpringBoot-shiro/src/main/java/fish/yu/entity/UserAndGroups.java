package fish.yu.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * 
 * @author yuliang-ds1
 * 用户and角色集合
 */

public class UserAndGroups  {
	
	private static final long serialVersionUID = 1L;
	
	private  String id;
	
	private  String name;//用户name
	
	private List<Group> groups;

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

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	

}
