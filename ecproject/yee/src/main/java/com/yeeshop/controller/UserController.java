package com.yeeshop.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.yeeshop.dao.ICategoryDAO;
import com.yeeshop.dao.IUserDAO;
import com.yeeshop.entity.Category;
import com.yeeshop.entity.User;
import com.yeeshop.service.CartService;
import com.yeeshop.service.CookieService;
import com.yeeshop.service.MailService;

@Controller
public class UserController {
    @Autowired IUserDAO userDAO;

	@Autowired ICategoryDAO categoryDAO;

	@Autowired HttpSession session;

	@Autowired CookieService cookie;

	@Autowired ServletContext app;

	@Autowired MailService mailer;

	@Autowired HttpServletRequest request;
	@Autowired CartService cartService;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    
    public String loginUser(Model model){
        Cookie ckid = cookie.read("userid");
		Cookie ckpw = cookie.read("pass");
		if (ckid != null) { 
			String uid = ckid.getValue();
			String pwd = ckpw.getValue();

			model.addAttribute("uid", uid);
			model.addAttribute("pwd", pwd);
		}
        return "/user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(Model model,@RequestParam("id") String id, 
    @RequestParam("pw") String pw, @RequestParam(value = "rm", defaultValue = "false") boolean rm
	){
        User user = userDAO.findById(id); 
		if (user == null) { 
			model.addAttribute("message", "Username or Password is not correct !!!");
		} else if (!pw.equals(user.getPassword())) { 
			model.addAttribute("message", "Password is not correct !"); 

			
		} else if (!user.getActivated()) {
			model.addAttribute("message", "Your account is not activated!");
		}else if (user.getAdmin()) {
			model.addAttribute("message", "Your account don't have permission!");
		} else {
			model.addAttribute("message", "Loggin successful!");
			session.setAttribute("user", user); 
			
			if (rm == true) { 
				cookie.create("pass", user.getPassword(), 30); 
			} else { 
				cookie.delete("userid");
				cookie.delete("pass");
			}
			
			String backUrl = (String) session.getAttribute("back-url");
			if (backUrl != null) {
				System.out.println("back-url is: "+ backUrl);
				return "redirect:" + backUrl;
			}
			return "redirect:/home";
		}
        return "/user/login";
    }

	// log out controller

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logOut(){
		session.invalidate();
		return "redirect:/login";
	}
	
	// register 
	@RequestMapping(value="/register",method = RequestMethod.GET)
	public String registerUser(Model model){
		User user=new User();
		model.addAttribute(user);
		return "/user/register";
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(Model model, @Validated @ModelAttribute("user") User user,BindingResult errorResult,// read form data request
	@RequestParam("photo_file") MultipartFile file,@RequestParam("password") String pwd,@RequestParam("cpassword") String cpwd) throws IllegalStateException, IOException, MessagingException{
		if (errorResult.hasErrors()){
			model.addAttribute("message", "Error");
			return "/user/register";
		}
		else {
			User existUser=userDAO.findById(user.getId());
			if(existUser != null){
				model.addAttribute("message", "This username has been used");
				return "/user/register";
			}
		}
		if(!pwd.equals(cpwd)){
			model.addAttribute("message","Password Confirm is not correct !!! Please try again.");
			return "/user/register";
		}
		if (file.isEmpty()){
			user.setPhoto("user.png");
		}
		else{
			user.setPhoto(file.getOriginalFilename()); 
			String path = new File("src\\main\\resources\\static\\image\\customer").getAbsolutePath() + "\\" + file.getOriginalFilename();
            file.transferTo(new File(path));
			// String dir= app.getRealPath("/static/image/customer");
			// File f= new File(dir,file.getOriginalFilename());
			// file.transferTo(f);
		}
		user.setActivated(true);
		user.setAdmin(false);
		userDAO.create(user);
		model.addAttribute("message", "Register Successful. Get to Login Page to login.");
		return "/user/register";
	}

	// Manage User Info

	@RequestMapping(value = "/user/change-info", method = RequestMethod.GET)
	public String myAccount(Model model){
		List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		// cart info:
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("cartCount", cartService.getCount());
		model.addAttribute("cartAmount", cartService.getAmount());
		return "/user/my-account";
	}

	// Change password
	@RequestMapping(value = "/user/change-password", method = RequestMethod.GET)
	public String changePassword(Model model){
		List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
		User user= (User) session.getAttribute("user");
		model.addAttribute("user", user);
		// cart info:
		model.addAttribute("cartItems", cartService.getItems());
		model.addAttribute("cartCount", cartService.getCount());
		model.addAttribute("cartAmount", cartService.getAmount());
		return "/user/change-password";
	}

	@RequestMapping(value="/user/change-password",method=RequestMethod.POST)
	public String changePassword(){
		// User user=(User) session.getAttribute("user");
		return "redirect:/user/change-password";
	}

	// test
	@RequestMapping(value = "/api/activate/{id}", method = RequestMethod.GET)
	public String activate(Model model, @PathVariable("id") String id) {
		User user = userDAO.findById(id);
		user.setActivated(true);
		userDAO.update(user);
		return "redirect:/login";
	}
}
