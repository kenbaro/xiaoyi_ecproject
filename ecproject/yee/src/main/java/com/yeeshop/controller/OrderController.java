package com.yeeshop.controller;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yeeshop.dao.ICategoryDAO;
import com.yeeshop.dao.IOrderDAO;
import com.yeeshop.dao.IOrderDetailDAO;
import com.yeeshop.dao.IProductDAO;
import com.yeeshop.entity.Category;
import com.yeeshop.entity.Order;
import com.yeeshop.entity.OrderDetail;
import com.yeeshop.entity.Product;
import com.yeeshop.entity.User;
import com.yeeshop.service.CartService;

@Controller
public class OrderController {

    @Autowired ICategoryDAO categoryDAO;

    @Autowired IProductDAO productDAO;

    @Autowired CartService cartService;

    @Autowired HttpSession session;

    @Autowired IOrderDAO orderDAO;

    @Autowired IOrderDetailDAO orderDetailDAO;
    
    @RequestMapping(value = "/order-history", method = RequestMethod.GET)
    public String orderHistory(Model model){
        
        List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
		// order list
		User user = (User) session.getAttribute("user");
		List<Order> orders = orderDAO.findByUser(user);
		model.addAttribute("orders", orders);
		// cart info:
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("cartCount", cartService.getCount());
		model.addAttribute("cartAmount", cartService.getAmount());
        return "order/order-history";
    }

    @RequestMapping(value = "/order-detail/{id}", method = RequestMethod.GET)
    public String orderDetail(Model model, @PathVariable("id") Integer id){
        List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
		// cart info:
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("cartCount", cartService.getCount());
		model.addAttribute("cartAmount", cartService.getAmount());

		// order detail by id
		Order order = orderDAO.findById(id);
		model.addAttribute("order", order);
		List<OrderDetail> orderDetails= orderDetailDAO.findByOrder(order);
		model.addAttribute("orderDetails", orderDetails); 
        return "order/order-detail";
    }
    @RequestMapping(value = "/check-out", method = RequestMethod.GET)
	public String checkOut(Model model){
		User user= (User) session.getAttribute("user");
        if(user==null || user!=null && user.getAdmin() ){
            return "redirect:/login" ;
        }
		List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
		// cart info:
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("cartCount", cartService.getCount());
		model.addAttribute("cartAmount", cartService.getAmount());
		return "cart/check-out";
	}

	@RequestMapping(value = "/check-out",method = RequestMethod.POST)
	public String checkOut(Model model,
						@ModelAttribute("order") Order order,
						@RequestParam("toTal")String amount) throws ParseException{
		List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
		// cart info:
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("cartCount", cartService.getCount());
		model.addAttribute("cartAmount", cartService.getAmount());
		Collection<Product> list = cartService.getItems();
		List<OrderDetail> details = new  ArrayList<>();
		for(Product product:list) {
			OrderDetail detail =new OrderDetail();
			detail.setOrder(order);
			detail.setProduct(product);
			detail.setUnitprice(product.getUnitPrice());
			detail.setQuantity(product.getQuantity());
			detail.setDiscount(product.getDiscount());
			details.add(detail);

		}
		NumberFormat format= NumberFormat.getInstance(Locale.GERMAN);
        Double amount1= format.parse(amount).doubleValue();
        order.setAmount(amount1);
		order.setStatus(1);
		orderDAO.create(order,details);
		cartService.clear();
		return "redirect:/order-history";
	}

	// list product bought
	@RequestMapping(value="/user/order-items",method=RequestMethod.GET)
	public String orderItems(Model model){
		List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
		User user = (User) session.getAttribute("user");
		List<Product> list = orderDAO.findItemsByUser(user);
		model.addAttribute("list", list);
		// cart info:
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("cartCount", cartService.getCount());
		model.addAttribute("cartAmount", cartService.getAmount());
		return "/product/list-order-item";
	}

}   
