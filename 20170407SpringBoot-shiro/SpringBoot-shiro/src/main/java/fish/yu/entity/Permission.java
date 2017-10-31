package fish.yu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import fish.yu.util.StringConstant;
/**
 * 
 * @author yuliang-ds1
 * 权限表
 */
@TableName("t_permission")
public class Permission extends Model<Permission> {
	
	private static final long serialVersionUID = 1L;
	
	@TableId(type=IdType.AUTO)
	private  String  id;//用户唯一标示
	
	private  String  url;//用户访问URL
	
	private  String  name;//路径描述
	
	
	public Permission(){
		
	}
	
	public Permission(Permission permission){
		super();
		this.id=permission.getId();
		this.url=permission.getUrl();
		this.name=permission.getName();
		
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	
	
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Permission打印  name："+name+ "  url:"+url;
	}
	
}
