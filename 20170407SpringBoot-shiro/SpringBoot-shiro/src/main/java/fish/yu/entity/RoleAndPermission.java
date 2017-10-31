package fish.yu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("t_role_permission")
public class RoleAndPermission extends Model<RoleAndPermission> {
	
	private static final long serialVersionUID = 1L;
	
	@TableId(type=IdType.AUTO)
	private  String  id;
	
	@TableField("role_id")
	private  String  roleId; //角色id
	
	@TableField("permission_id")
	private  String  permissionId;//权限id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}
	

}
