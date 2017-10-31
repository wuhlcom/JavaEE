package fish.yu.util;

/**
 * 公开静态常量申明
 * @author yuliang-ds1
 *
 */
public class StringConstant {
	

	//用户登录次数计数  redisKey 前缀
	public static final String SHIRO_LOGIN_COUNT = "shiro_login_count_";
	
	//用户登录是否被锁定    一小时 redisKey 前缀
	public static final String SHIRO_IS_LOCK = "shiro_is_lock_";
	
	//用户登录是否被锁定    一小时 redisKey 前缀
	public static final String SHIRO_USER = "shiro_user_";
	
	//用户资源权限前缀标识
	public static final String USER_COLON = "user:";
	
	
	//用户 分组资源权限前缀标识
	public static final String GROUP_COLON = "group:";
	
	//编辑数据库数据库
	public static final String DATA_EDIT="edit";
	
	//添加数据到数据库
	public static final String DATA_ADD="add";
	
	
	//删除数据到数据库
    public static final String DATA_DELETE="del";
	
	

}
