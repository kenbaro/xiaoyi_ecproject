package com.yeeshop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yeeshop.interceptor.Sharelnterceptor;
import com.yeeshop.interceptor.AuthorizeAdminlnterceptor;

@Configuration
public class InterceptorAdminConfig implements WebMvcConfigurer {
    
    @Autowired 
    Sharelnterceptor share;

    @Autowired
    AuthorizeAdminlnterceptor auth;

    

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(share).addPathPatterns("/**");
        registry.addInterceptor(auth).addPathPatterns(
				"/admin/home", 
				"/admin/category-manage", 
				"/admin/category/**",
				"/admin/order-manage",
				"/admin/order-update/*",
				"/admin/order-add/*",
				"/admin/order/**",
				"/admin/home/prod-manage",
				"/admin/prod/add",
				"/admin/prod-update/*",
				"/admin/prod-delete/*",
				"/admin/user-manage",
				"/admin/user/**",
				"/admin/report-by-cate",
				"/admin/reven-by-cate",
				"/admin/reven-by-customer",
				"/admin/reven-by-month",
				"/admin/reven-by-quarter",
				"/admin/reven-by-year"
				);
	}
}
