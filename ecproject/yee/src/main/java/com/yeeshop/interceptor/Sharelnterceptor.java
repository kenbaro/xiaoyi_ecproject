package com.yeeshop.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yeeshop.dao.ICategoryDAO;
import com.yeeshop.dao.IUserDAO;
import com.yeeshop.entity.Category;
import com.yeeshop.entity.User;

@Component
public class Sharelnterceptor implements HandlerInterceptor{
	
	@Autowired
	ICategoryDAO dao;
	
	@Autowired
	IUserDAO userDAO;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	@Nullable ModelAndView modelAndView) throws Exception {
				// List<Category> list=dao.findAll();
				// modelAndView.addObject("cates", list);
				
				// List<User> listUser = userDAO.findAll();
				// modelAndView.addObject("users", listUser);
	}
	
}

