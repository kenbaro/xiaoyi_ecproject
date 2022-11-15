package com.yeeshop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeeshop.entity.User;
import com.yeeshop.service.CartService;


@Controller
public class CartController {


    @Autowired
	CartService cart;

	@Autowired HttpSession session;

    @RequestMapping(value="/cart/view",method = RequestMethod.GET)
	public String view(Model model) {
		
        User user= (User) session.getAttribute("user");
        if(user!=null && user.getAdmin() || user==null){
            return "redirect:/login" ;
        }
		// cart info:
		model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartCount", cart.getCount());
        model.addAttribute("cartAmount", cart.getAmount());
		return "cart/shoping-cart";
	}
	// @RequestMapping(value ="/check-out",method = RequestMethod.GET)
	// public String checkOut(){
	// 	User user= (User) session.getAttribute("user");
    //     if(user!=null && user.getAdmin() || user==null){
    //         return "redirect:/login" ;
    //     }
	// 	return "cart/check-out";
	// }

	@ResponseBody
	@RequestMapping(value = "/cart/add/{id}")
	public Object[] cartAdd(@PathVariable("id") Integer id){
		cart.add(id);
		Object[] info= {
			cart.getCount(),
			cart.getAmount()
		};
		return info;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cart/add2/{id}/{qty}")
	public Object[] cartAddw(@PathVariable("id") Integer id,@PathVariable("qty") Integer qty){
		cart.add2(id,qty);
		Object[] info= {
			cart.getCount(),
			cart.getAmount()
		};
		return info;
	}
	@ResponseBody
	@RequestMapping(value="/cart/remove/{id}")
	public Object cartRemove(@PathVariable("id") Integer id){
		cart.remove(id);
		Object[] info = {
			cart.getCount(),
			cart.getAmount()
		};
		return info;
	}

	@ResponseBody
	@RequestMapping(value = "/cart/update/{id}/{qty}")
	public Object cartUpdate(@PathVariable("id") Integer id,@PathVariable("qty") Integer qty){
		cart.update(id, qty);
		Object[] info= {
			cart.getCount(),
			cart.getAmount()
		};
		return info;
	}

	@ResponseBody
	@RequestMapping(value="/cart/clear")
	public void clear(){
		cart.clear();
	}
}
