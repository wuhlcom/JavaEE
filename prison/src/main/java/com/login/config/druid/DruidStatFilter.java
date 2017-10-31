/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月30日 上午9:53:39 * 
*/ 
package com.login.config.druid;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
initParams={
         @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
 }
)
public class DruidStatFilter extends WebStatFilter{

}

