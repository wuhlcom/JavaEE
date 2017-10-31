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
 * 初始化权限表
 */

@TableName("t_permission_init")
public class MyPermissionInit extends Model<MyPermissionInit>{
	
	@TableId(type=IdType.ID_WORKER)
	private  String   id;
	
	private  String   url;//资源路径
	
	@TableField("permission_init")
	private  String   permissionInit;//权限集合
	
	private  Integer  sort;

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

	

	public String getPermissionInit() {
		return permissionInit;
	}

	public void setPermissionInit(String permissionInit) {
		this.permissionInit = permissionInit;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	 @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MyPermissionInit打印  url："+url+"  permissionInit:"+permissionInit+"  sort:"+sort+"  id:"+id;
	}
	

}
