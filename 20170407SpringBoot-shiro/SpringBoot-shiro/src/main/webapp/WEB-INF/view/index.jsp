<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<% String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(); %>
 <head>
  <title>FISH-YU权限管理DEMO|首页</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <link href="<%=contextPath%>/static/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
   <link href="<%=contextPath%>/static/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
   <link href="<%=contextPath%>/static/assets/css/main.css" rel="stylesheet" type="text/css" />
   <script src="<%=contextPath%>/static/js/jquery-1.8.3.js"></script>
 </head>
 <body>
   <div class="header">
    <div class="dl-title">
      <a href="http://www.baidu.com/" title="百度首页" target="_blank">
        <span class="lp-title-port">Fishing</span><span class="dl-title-text">--权限管理Demo</span>
      </a>
    </div>
    <div class="dl-log">欢迎您，<span class="dl-log-user">${user.nickName}</span>
    	<span class="admin"></span>
        <a href="logout" title="退出系统" class="dl-log-quit">[退出]</a>
    </div>
   </div>
   <div class="content">
    <div class="dl-main-nav">
      <ul id="J_Nav"  class="nav-list ks-clear">
        <li class="nav-item dl-selected"><div class="nav-item-inner nav-storage">首页</div></li>
      </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">
    </ul>
   </div>
  <script type="text/javascript" src="<%=contextPath%>/static/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=contextPath%>/static/assets/js/bui-min.js"></script>
  <script type="text/javascript" src="<%=contextPath%>/static/assets/js/config-min.js"></script>
  <script type="text/javascript" src="<%=contextPath%>/static/lib/layer/1.9.3/layer.js"></script>
  <script>
	//
	BUI.use('common/main',function(){
      var config = [{
          id:'system',
          menu:[{
              text:'系统管理',
              items:[
                {id:'yhglmk',text:'用户管理模块',href:'/user/queryAllUserPage'},
                {id:'qxglmk',text:'权限管理模块',href:'/permission/permissionPage' },
				{id:'jsglmk',text:'角色管理模块',href:'/role/rolePage' },
				{id:'csqxglmk',text:'初始权限管理模块',href:'/initPermission/initPermissionPage'},
				{id:'zxyhglmk',text:'在线用户管理模块',href:'/onlineUser/onlineUserPage'},
				{id:'yhzglmk',text:'用户组管理模块',href:'/group/groupPage'}
              ]
          	 }]
            }];
      new PageUtil.MainPage({
        modulesConfig : config
      });
      
      var redirect="${redirectUrl}";
		console.log(redirect)
		if(redirect!=null&&redirect!=""){
			console.log("index-redirectUrl:"+redirect)
			//location.href=redirect;
			console.log("ul[class='bui-menu']-length:"+$("ul[class='bui-menu']").find("li").length);
			
			$("ul[class='bui-menu']").find("li").each(function(){
				
				var  ahref=$(this).find("a").attr("href");
				console.log("ahref"+ahref)
				console.log("ahref==redirect"+(ahref==redirect))
				if(ahref==redirect){
					console.log(ahref)
					$(this).find("a").trigger('click');
				}
				
			})
		}
		
      
    });
	
	
	
	
	
	
	
	//
  </script>
 <%--  <input name="redirectUrl"  value="${redirectUrl}"> --%>
 </body>
</html>