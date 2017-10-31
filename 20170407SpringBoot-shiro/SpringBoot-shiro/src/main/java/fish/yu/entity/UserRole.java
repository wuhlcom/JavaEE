package fish.yu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * @author yuliang-ds1
 * 用户角色关系表
 */
@TableName("t_user_role")
public class UserRole  extends Model<UserRole> {
	
	private static final long serialVersionUID = 1L;
	
	
	@TableId(type=IdType.AUTO)
	private  String id;
	
	@TableField("user_id")
	private  String userId;//用户id
	
	@TableField("role_id")
	private  String roleId;//角色id
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	

}
