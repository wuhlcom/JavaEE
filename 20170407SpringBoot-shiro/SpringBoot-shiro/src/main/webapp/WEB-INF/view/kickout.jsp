<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<%=basePath%>/static/js/jquery-1.11.3.js"></script>
<title>被踢出</title>
</head>
<body>


<div style="position: absolute;width:400px;height:200px;left:50%;top:30%; margin-left:-200px;margin-top:-100px;">
<h1>        KICK　OUT !       </h1>
<p>当前用户已经被踢出 ，可能有其他用户使用相同账号在其他地方登录，或已经达到此类型账号登录人数上限被挤掉。</p>

<input type="button" id="login" value="重新登录" />

</div>

</body>
<script type="text/javascript">
$("#login").click(function(){
	window.open("<%=basePath%>/login"); 
});
</script>
</html>