
package fish.yu.shiro.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;

import fish.yu.util.RequestUtils;

/**
 * 自定义默认角色鉴权
 * @author yuliang-ds1
 *
 */
public class CustomRoleOrAuthorizationFilter extends AuthorizationFilter {

    // 登录认证失败
    public static final String AUTHENTICATION_ACCESSDENIED = "808";

    // 授权失败
    public static final String AUTHORIZATION_ACCESSDENIED  = "809";

    // 访问受限制
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response)
            throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //HttpServletResponse httpResponse = (HttpServletResponse) response;

        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL

        if (subject.getPrincipal() == null) {

            if (RequestUtils.isAjax(httpRequest)) {
            	
                Map<String, String> resultMap = new HashMap<String, String>();
 				resultMap.put("user_status", AUTHENTICATION_ACCESSDENIED);
 				resultMap.put("message", "您尚未登录或登录时间过长,请重新登录!");
     				
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().print( JSON.toJSONString(resultMap));

            } else {
                saveRequestAndRedirectToLogin(request, response);
            }

        } else {
            // If subject is known but not authorized, redirect to the
            // unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized
            // HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            // SHIRO-142 - ensure that redirect _or_ error code occurs - both
            // cannot happen due to response commit:

            if (RequestUtils.isAjax(httpRequest)) {

                Map<String, String> resultMap = new HashMap<String, String>();
 				resultMap.put("user_status", AUTHORIZATION_ACCESSDENIED);
 				resultMap.put("message", "您没有足够的权限执行该操作!");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().print(JSON.toJSONString(resultMap));

            } else {
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(401);
                }
            }

        }
        return false;
    }

    // TODO - complete JavaDoc
    // pers权限形式的认证的验证
    public boolean isAccessAllowed(ServletRequest request,
                                   ServletResponse response,
                                   Object mappedValue) throws IOException {
    	System.out.println("开始ROlEOR鉴权==================");
    	HttpServletRequest   httpRequest=(HttpServletRequest)request;
        System.out.println("  当前请求URL==="+httpRequest.getRequestURL());
         
        Subject subject = getSubject(request, response);
       
        
        String[] rolesArray = (String[]) mappedValue;  
        
        if (rolesArray == null || rolesArray.length == 0) { //没有角色限制，有权限访问  
        	 System.out.println("结束ROlEOR鉴权==================：true");
            return true;  
        }  
        for (int i = 0; i < rolesArray.length; i++) {  
            if (subject.hasRole(rolesArray[i])) { //若当前用户是rolesArray中的任何一个，则有权限访问  
            	 System.out.println("结束ROlEOR鉴权==================：true");
                return true;  
            }  
        }  
        System.out.println("结束ROlEOR鉴权==================：false");
        return false;  
  
    }

    
}
