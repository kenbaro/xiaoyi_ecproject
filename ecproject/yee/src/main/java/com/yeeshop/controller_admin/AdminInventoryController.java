package com.yeeshop.controller_admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yeeshop.dao.IReportDAO;
import com.yeeshop.entity.User;

@Controller
public class AdminInventoryController {
    
    @Autowired IReportDAO reportDAO;
    @Autowired HttpSession session;
    @RequestMapping(value="/admin/report-by-cate")
    public String statisticByCate(Model model){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        model.addAttribute("data", reportDAO.inventory());
		return "/admin/report/cate-report/cate-report";
    }

    @RequestMapping(value="/admin/reven-by-cate")
    public String revenueByCateString(Model model){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        model.addAttribute("data", reportDAO.revenueByCategory());
        return "/admin/report/revenue-by-cate/revenue-report";
    }

    @RequestMapping(value="/admin/reven-by-customer")
    public String revenueByCustomer(Model model){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        model.addAttribute("data", reportDAO.revenueByCustomer());
        return "/admin/report/revenue-by-user/revenue-report";
    }

    @RequestMapping(value="/admin/reven-by-month")
    public String revenueByMonth(Model model){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        model.addAttribute("data", reportDAO.revenueByMonth());
        return "/admin/report/revenue-by-month/revenue-report";
    }
    @RequestMapping(value="/admin/reven-by-quarter")
    public String revenueByQuarter(Model model){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        model.addAttribute("data", reportDAO.revenueByQuarter());
        return "/admin/report/revenue-by-quarter/revenue-report";
    }
    @RequestMapping(value="/admin/reven-by-year")
    public String revenueByYear(Model model){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        model.addAttribute("data", reportDAO.revenueByYear());
        return "/admin/report/revenue-by-year/revenue-report";
    }
}
