package com.yeeshop.controller_admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yeeshop.dao.ICategoryDAO;
import com.yeeshop.dao.IOrderDAO;
import com.yeeshop.dao.IProductDAO;
import com.yeeshop.dao.IUserDAO;
import com.yeeshop.entity.Category;
import com.yeeshop.entity.Order;
import com.yeeshop.entity.Product;
import com.yeeshop.entity.User;

@Controller
public class AdminHomeController {
    
    @Autowired
    HttpSession session;

    @Autowired IUserDAO userDAO;
    @Autowired IProductDAO productDAO;
    @Autowired IOrderDAO orderDAO;
    @Autowired ICategoryDAO categoryDAO;

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String adminHome(Model model){

        // String backUrl = (String) session.getAttribute("back-url-admin");
		// 	if(backUrl != null) {
		// 		// return "redirect:" + backUrl;
		// 		return "redirect:/admin/login" ;
		// 	}
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }

        // User statistic
        User user1 = new User();
		model.addAttribute("user1", user1);
		model.addAttribute("user1", userDAO.findAll());
        
        //Product statistic
		Product product = new Product();
		model.addAttribute("product1", product);
		model.addAttribute("product1", productDAO.findAll());
		
        //Order statistic 
		Order order = new Order();
		model.addAttribute("order1", order);
		model.addAttribute("order1", orderDAO.findAll());

        // Brand statistic
        Category cate= new Category();
        model.addAttribute("cate1", cate);
        model.addAttribute("cate1", categoryDAO.findAll());
        // if(!auth.authUser().equals(null)){
        //     return auth.authUser();
        // };
        return "admin/admin_home";
    }
}
