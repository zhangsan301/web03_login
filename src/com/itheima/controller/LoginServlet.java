package com.itheima.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.itheima.domain.User;
import com.itheima.model.UserModel;

/**
 * 登录的Servlet的编写
 */
@WebServlet(name = "/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			// 1.接收数据
			// 处理中文乱码
			request.setCharacterEncoding("UTF-8");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//一次性验证码校验
			//接收验证码
			String checkcode1 = request.getParameter("checkcode");
			//从session中获取一次性验证码值
			String checkcode2 = (String) request.getSession().getAttribute("checkcode");
			//为了保证验证码使用一次:应该将session中的验证码值清空
			request.getSession().removeAttribute("checkcode");
			//校验一次性验证码
			if(!checkcode1.equalsIgnoreCase(checkcode2)){
			   request.setAttribute("msg","验证码输入错误!");
			   request.getRequestDispatcher("/login.jsp").forward(request,response);
			   return;
			}


			// 2.封装数据
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			// 3.处理数据
			UserModel userModel = new UserModel();
			User existUser = userModel.login(user);
			// 4.页面跳转
			if(existUser == null){
				// 登录失败
				// 向request域中保存一个错误信息：
				request.setAttribute("msg", "用户名或密码错误！");
				// 使用请求转发进行页面跳转
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}else{
				// 登录成功
				//保存用户的信息:保存到会话中
				HttpSession session =  request.getSession();
                //保存数据
				session.setAttribute("existUser",existUser);
				//记住用户名
				//判断复选框是否勾选
				String remember = request.getParameter("remember");
				if("true".equals(remember)){
					//已经勾选了
					Cookie cookie = new Cookie("remember",existUser.getUsername());
					//设置有效路径
					cookie.setPath("/web03_login");
					//设置有效时长
					cookie.setMaxAge(60*60*24);
					//将cookie回写到浏览器
					response.addCookie(cookie);
				}
				// 重定向到成功页面
				response.sendRedirect("/web03_login/success.jsp");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
