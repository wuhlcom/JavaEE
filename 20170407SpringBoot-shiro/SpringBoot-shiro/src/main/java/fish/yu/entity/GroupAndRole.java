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
 * 组——角色
 */
@TableName("t_group_role")
public class GroupAndRole extends Model<GroupAndRole> {
	
	 private static final long serialVersionUID = 1L;
	
	 @TableId(type=IdType.AUTO)
	 private String id;//gourp唯一标示
	 
	 
	 @TableField("group_id")
	 private String groupId;//gourp名称
	 
	 
	 @TableField("role_id")
	 private String roleId;//gourp类型
	 
	 
	 public GroupAndRole(){
		 
	 }
	 
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "Gourp-role 打印-group_id："+groupId+ " role_id:"+roleId;
    }
    
	 
    @Override
    protected Serializable pkVal() {
    	// TODO Auto-generated method stub
    	return this.id;
    }

}
