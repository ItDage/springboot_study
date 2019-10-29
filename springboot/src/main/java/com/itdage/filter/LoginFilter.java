//package com.itdage.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//@Component
//@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
//public class LoginFilter implements Filter {
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// TODO Auto-generated method stub
//	}
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
//		String username = (String) req.getSession().getAttribute("username");
//		if (req.getRequestURI().contains("login") || !StringUtils.isEmpty(username)
//				|| req.getRequestURI().endsWith(".js") || req.getRequestURI().endsWith(".css")
//				|| req.getRequestURI().endsWith(".png") || req.getRequestURI().endsWith(".jpg")
//				|| req.getRequestURI().contains("chatWS") || req.getRequestURI().contains("userList")) {
//			chain.doFilter(req, res);
//		} else {
//			// 未登录
//			res.sendRedirect("login");
//		}
//	}
//	@Override
//	public void destroy() {
//	}
//}
