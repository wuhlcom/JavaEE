package fish.yu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * @author yuliang-ds1
 * 角色表
 */
@TableName("t_role")
public class Role extends Model<Role> {
	
	 private static final long serialVersionUID = 1L;
	
	 @TableId(type=IdType.AUTO)
	 private String id;//角色唯一标示
	 
	 
	 private String name;//用户名称
	 
	 
	 private String type;//用户类型

   
	 public Role(){
		 
	 }

	 public Role(Role role){
		 this.id=role.getId();
		 this.name=role.getName();
		 this.type=role.getType();
	 }
	 
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	 
    @Override
    protected Serializable pkVal() {
    	// TODO Auto-generated method stub
    	return this.id;
    }
	 
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "Role打印-角色name："+name+ " 角色type:"+type;
    }
    
	 

}
