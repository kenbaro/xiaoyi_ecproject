package com.yeeshop.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yeeshop.dao.ICategoryDAO;
import com.yeeshop.dao.IProductDAO;
import com.yeeshop.entity.Category;
import com.yeeshop.entity.Product;
import com.yeeshop.entity.User;
import com.yeeshop.service.CartService;
import com.yeeshop.service.CookieService;

@Controller
public class HomeController {
    
    @Autowired
    CartService cart;
    @Autowired
    ICategoryDAO categoryDAO;

    @Autowired
    IProductDAO productDAO;

    @Autowired
    CookieService cookieService;

    @Autowired HttpSession session;

    @RequestMapping(value = {"","/home"} ,method = RequestMethod.GET)
    public String index(Model model){

        User user= (User) session.getAttribute("user");
        if(user!=null && user.getAdmin()){
            session.invalidate();
            return "redirect:/login" ;
        }
        // All Categoties
        List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);

        // Special Products
        List<Product> products= productDAO.findSpecialProducts();
        model.addAttribute("specialProducts", products);

        // Lasted product list
        List<Product> lastedProducts= productDAO.findLastestProducts();
        model.addAttribute("lastestProducts", lastedProducts);

        // Top Viewed Product
        List<Product> topViewProducts= productDAO.findTopViewProducts();
        model.addAttribute("topviewproducts", topViewProducts); 
        
        // On Sale Product
        List<Product> onSaleProducts= productDAO.findOnsaleProducts();
        model.addAttribute("onSaleProducts", onSaleProducts);
        
        // Hot Sale Product

        List<Product> hotSaleProducts = productDAO.findHotSaleProduct();
        Product hotSaleProduct= hotSaleProducts.get(0);
        model.addAttribute("hotSaleProduct", hotSaleProduct);

        // list recently viewed products
        Cookie viewed=cookieService.read("viewed");
        String value=viewed.getValue();
        List<Product> recentViewedProducts=productDAO.findbyIds(value);
        model.addAttribute("viewed", recentViewedProducts);

        // special Price

        List<Product> sProducts= productDAO.findSpecialPrice();
        model.addAttribute("specialPrice", sProducts);

        // cart info:
		model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartCount", cart.getCount());
        model.addAttribute("cartAmount", cart.getAmount());
        return "home";
    }
    // redirect to anotherpage
    @RequestMapping(value = "/about",method = RequestMethod.GET)
	public String about(Model model) {

        // User user= (User) session.getAttribute("user");
        // if(user!=null && user.getAdmin()){
        //     session.invalidate();
        //     return "redirect:/login" ;
        // }
        // All Categoties
        List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
        // cart info:
		model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartCount", cart.getCount());
        model.addAttribute("cartAmount", cart.getAmount());
		return "about-us";
	}
	@RequestMapping("/contact")
	public String contact(Model model) {
        // User user= (User) session.getAttribute("user");
        // if(user!=null && user.getAdmin()){
        //     session.invalidate();
        //     return "redirect:/login" ;
        // }
        List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
        // cart info:
		model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartCount", cart.getCount());
        model.addAttribute("cartAmount", cart.getAmount());
		return "contact-us";
	}
}
