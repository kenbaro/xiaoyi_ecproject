package com.yeeshop.controller_admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yeeshop.dao.IUserDAO;
import com.yeeshop.entity.User;
import com.yeeshop.service.CookieService;

@Controller
public class AdminAccountController {
    
    @Autowired
    CookieService cookieService;

    @Autowired
    IUserDAO userDAO;

    @Autowired
    HttpSession session;

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String loginAdmin(Model model){
        Cookie cookieId=cookieService.read("userid");
        Cookie cookiePw = cookieService.read("pass");
        if(cookieId !=null){
            String uid=cookieId.getValue();
            String pwd=cookiePw.getValue();
            model.addAttribute("uid", uid);
            model.addAttribute("pwd", pwd);
        }
        return "/admin/admin_login";
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public String loginAdmin(Model model, 
                                @RequestParam("id") String username,
                                @RequestParam("pw") String pwd,
                                @RequestParam(value = "rm", defaultValue = "false") boolean rm){
        User user= userDAO.findById(username); 
        if(user == null){
            model.addAttribute("message", "Username or Password is incorrect !!!");
        }                           
        else if(!pwd.equals(user.getPassword())){ // incorrect password
            model.addAttribute("message", "Password is incorrect");
        }
        else if(!user.getAdmin()){ // not admin account
            model.addAttribute("message", "Only Admin can login!!! If you intentionally login more than 3 times, your account will be deactivate");
        }
        else{ // success
            //model.addAttribute("message", "Login successfully!");
			session.setAttribute("user", user);
			// remember me
			if (rm == true) {
				cookieService.create("userid", user.getId(), 30);
				cookieService.create("pass", user.getPassword(), 30);
			} else {
				cookieService.delete("userid");
				cookieService.delete("pass");
			}
			//back to security url
            String backUrl = (String) session.getAttribute("back-url-admin");
			if (backUrl != null) {
				System.out.println("back-url is: "+ backUrl);
				return "redirect:" + backUrl;
			}
			return "redirect:/admin/home";
        }
        return "/admin/admin_login";     
    }

    // log out
    @RequestMapping(value = "/admin/logout",method = RequestMethod.GET)
    public String logOut(){
        session.invalidate();
        return "redirect:/admin/login";
    }
}
