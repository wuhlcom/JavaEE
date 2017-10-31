package fish.yu.entity;

/**
 * 
 * @author yuliang-ds1
 * 权限表
 */
public class PermissionVO extends Permission {
	
	
	public PermissionVO() {
		
	}
	
	
	public PermissionVO(Permission permission) {
		super(permission);
	}
	
	private static final long serialVersionUID = 1L;
	
	
	private  String  permissionIdentify;//权限资源标识字符串  展示需要不作为表字段
	
	public String getPermissionIdentify() {
		
		return permissionIdentify;
	}


	public void setPermissionIdentify(String permissionIdentify) {
		this.permissionIdentify = permissionIdentify;
	}

	
}
