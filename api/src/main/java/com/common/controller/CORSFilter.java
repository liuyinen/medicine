package com.common.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

//		response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

		response.addHeader("Access-Control-Allow-Methods", "PUT,GET,POST,DELETE,OPTIONS");

		response.setHeader("Access-Control-Allow-Credentials", "true");

		response.addHeader("Access-Control-Allow-Headers",
				"Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");

		response.addHeader("Access-Control-Max-Age", "1800");// 30 min

		filterChain.doFilter(request, response);

	}

	@Override
	public void destroy() {

	}
}
