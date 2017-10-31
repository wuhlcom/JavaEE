package fish.yu.annotation;
public interface ShiroAopPermission {
	
	//对查询方法拥有权限
    String QUERY_METHOD="[group:query]";
    
    //对更新方法拥有权限
    String UPDATE_METHOD="[group:update]";
    
    //对更新方法拥有权限
    String ADD_METHOD="[group:add]";
    
    //对更新方法拥有权限
    String DELETE_METHOD="[group:delete]";
    
    
    //对更新方法拥有权限
    String EDIT_METHOD="[group:edit]";
    
    
    
}
