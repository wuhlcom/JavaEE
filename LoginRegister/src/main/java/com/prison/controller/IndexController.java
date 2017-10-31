/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月13日 上午11:16:37 * 
*/ 
package com.prison.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prison.dao.UserDao;
import com.prison.entity.UserEntity;

@Controller  
@RequestMapping("/front/*")  
public class IndexController { 
  
    @Autowired  
    private UserDao userDao;  
  
    //index页面  
    @RequestMapping("/index")  
    public String index() {  
        return "index";  
    }  
  
    //登录页面  
    @RequestMapping("/register")  
    public String register(){  
        return "register";  
    }  
  
    //注册方法  
    @RequestMapping("/addregister")  
    public String register(HttpServletRequest request){  
        String username = request.getParameter("username");  
        String password = request.getParameter("password");  
        String password2 = request.getParameter("password2");  
        if (password.equals(password2)){  
            UserEntity userEntity = new UserEntity();  
            userEntity.setUsername(username);  
            userEntity.setPassword(password);  
            userDao.save(userEntity);  
            return "index";  
        }else {  
            return "register";  
        }  
    }  
  
}  
