package com.spinach.interceptor;

import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.spinach.domain.UserInfo;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

	private UserInfo loginUser;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		
		String returnUrl = request.getRequestURI();
		System.out.println("return Url = "+returnUrl);
		Object user = session.getAttribute("loginUser");
		boolean flag = true;
		
		if (StringUtils.isEmpty(user) && returnUrl.contains("message")) {
			//loginUser = baseService.getUserInfo(userId);
			loginUser = new UserInfo();
			Random random = new Random();
			loginUser.setName("spinach"+random.nextInt(10));
			session.setAttribute("loginUser", loginUser);
			return flag;
		} else if(returnUrl.contains("message")) {
			/*request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write("<script languge='javascript'>alert('您还没有岗位信息，请先联系管理员设置！');window.location.href='/logout'</script>");
			out.flush();
			out.close();
			flag = false;*/
			return true ;
		}else{
			return true;
		}
	}

	/**
	 * <p>
	 * 处理XXXXXXXXXXXXXXX。
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 处理XXXXXXXXXXXXXXX。
	 * 
	 * @author lidan
	 * @Date:2016-10-13
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

}
