package com.yeeshop.controller_admin;

import java.text.NumberFormat;
import java.text.ParseException;
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

import com.yeeshop.dao.IOrderDAO;
import com.yeeshop.dao.IUserDAO;
import com.yeeshop.entity.Order;
import com.yeeshop.entity.User;

@Controller
public class AdminOrderController {
    
    @Autowired
    IOrderDAO orderDAO;
    @Autowired
    IUserDAO userDAO;

    @Autowired
    HttpSession session;
    // order-list
    @RequestMapping(value="/admin/order-manage",method = RequestMethod.GET)
    public String orderManagement(Model model){

        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            return "redirect:/admin/login" ;
        }
        model.addAttribute("orders", orderDAO.findAll());
        return "/admin/order/order-list";
    }

    // edit order
    @RequestMapping(value= "/admin/order-update/{id}",method = RequestMethod.GET)
    public String orderUpdate(Model model, @PathVariable("id") Integer id){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            return "redirect:/admin/login" ;
        }
        model.addAttribute("order", orderDAO.findById(id));
        return "/admin/order/order-update";
    }

    @RequestMapping(value="/admin/order-update/{id}",method = RequestMethod.POST)
    public String orderUpdate(@ModelAttribute("entity") Order order,@RequestParam("orderTotal") String amount) throws ParseException{
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            return "redirect:/admin/login" ;
        }
        NumberFormat format= NumberFormat.getInstance(Locale.GERMAN);
        Double amount1= format.parse(amount).doubleValue();
        order.setAmount(amount1);
        orderDAO.update(order);
        return "redirect:/admin/order-manage";
    }
    
    // order add
    @RequestMapping(value="/admin/order-add",method = RequestMethod.GET)
    public String orderAdd(Model model){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            return "redirect:/admin/login" ;
        }
        model.addAttribute("users",userDAO.findAll());
        return "/admin/order/order-add";
    }

    @RequestMapping(value="/admin/order-add",method = RequestMethod.POST)
    public String orderAdd(Model model,@ModelAttribute("entity") Order order,@RequestParam("orderTotal") String amount) throws ParseException{
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            return "redirect:/admin/login" ;
        }
        NumberFormat format= NumberFormat.getInstance(Locale.GERMAN);
        Double amount1= format.parse(amount).doubleValue();
        order.setAmount(amount1);
        orderDAO.create(order);
        return "redirect:/admin/order-manage";
    }
    // delete order


    @RequestMapping(value="/admin/order/delete/{id}")
    public String removeOrder(@PathVariable("id") Integer id){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            return "redirect:/admin/login" ;
        }
        if(id !=null){
            orderDAO.delete(id);
        }
        return "redirect:/admin/order-manage";
    }
}
