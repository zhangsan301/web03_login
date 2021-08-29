<%@ page import="com.itheima.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/8/28
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    if(request.getSession().getAttribute("existUser")==null){
%>
    <h1>您还没有登录！请先去<a href="/web03_login/login.jsp">登录</a>!</h1>
<%
    }else{
%>
<h1>用户登录成功!</h1>
<%
    User existUser =(User)request.getSession().getAttribute("existUser");
%>
<h3>您好:<%= existUser.getNickname()  %><a href="/web03_login/LogoutServlet">退出</a></h3>
<%
 }
%>
</body>
</html>
