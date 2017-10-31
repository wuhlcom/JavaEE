
package fish.yu.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtils {

    private static final Logger logger = LoggerFactory
                                               .getLogger(RequestUtils.class);
 
    
    /**
     * 判断ajax请求
     * 
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {

        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With").toString()));
    }
}
