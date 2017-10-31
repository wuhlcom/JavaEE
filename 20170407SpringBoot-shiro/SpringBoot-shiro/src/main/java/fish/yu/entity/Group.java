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
@TableName("t_group")
public class Group extends Model<Group> {
	
	 private static final long serialVersionUID = 1L;
	 
	 @TableId(type=IdType.AUTO)
	 private String id;//gourp唯一标示
	 
	 
	 private String name;//gourp名称
	 
	 
	 private String type;//gourp类型
	 
	 
	 public Group(){
		 
	 }
	 
	 public Group(Group group){
		 super();
		 this.id=group.getId();
		 this.name=group.getName();
		 this.type=group.getType();
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
    	return "Gourp打印-gour-pname："+name+ " gourp-type:"+type;
    }
    
	 

}
