package com.common.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.common.exception.MyException;
import com.user.entity.User;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException, MyException {

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		IsCheckUserLogin auth = handlerMethod.getMethodAnnotation(IsCheckUserLogin.class);

		/**
		 * 如果在controller中的方法没有使用IsCheckUserLogin注解或者check=false, 就不需要判断在请求时用户是否已经登录.
		 */
		if (auth == null || !auth.check()) {
			return true;
		}

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null) {
			return true;
		} else {
			returnFalseMessage();
			System.out.println("没有登录,跳转到登录页面");
			return false;
		}
	}
	
	public Map<String, Object> returnFalseMessage() {
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20005);
		res.put("message", "用户未登录");
		return res;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
	}
}
