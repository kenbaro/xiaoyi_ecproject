package com.yeeshop.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import com.yeeshop.entity.User;

@Component
public class Authorizelnterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user == null) {
			session.setAttribute("back-url", request.getRequestURI());
			response.sendRedirect("/login");
			return false;
		}
		return true;
	}
}
