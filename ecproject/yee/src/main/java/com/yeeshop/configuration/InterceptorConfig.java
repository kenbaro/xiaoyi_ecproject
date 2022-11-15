package com.yeeshop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yeeshop.interceptor.Authorizelnterceptor;
import com.yeeshop.interceptor.Sharelnterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Autowired
	Sharelnterceptor share;

	@Autowired
	Authorizelnterceptor auth;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(share).addPathPatterns("/**");

		registry.addInterceptor(auth).addPathPatterns(
				"/user/change-info", 
				"/user/change-password", 
				"/check-out", 
				"/logout",
				"/user/edit", 
				"/order/list",
				"/order-detail/*",
				"/order-history",
				"/user/order-items",
				"/cart/view",
				"/product/wishlist");
	}
}
