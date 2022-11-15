package com.yeeshop.controller_admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yeeshop.dao.IUserDAO;
import com.yeeshop.entity.User;

@Controller
public class AdminUserMngController {
    
    @Autowired
    IUserDAO userDAO;

    @Autowired
    HttpSession session;

    @Autowired
	ServletContext app;

    // list user
    @RequestMapping(value = "/admin/user-manage",method = RequestMethod.GET)
    public String userManagement(Model model){

        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        List<User> users= userDAO.findAll();
        model.addAttribute("users", users);
        return "/admin/user/user-list";
    }
    
    // delete user
    
    @RequestMapping(value = { "/admin/user/delete", "/admin/user/delete/{id}" })
	public String delete(@RequestParam(value = "id", required = false) String id1,
			@PathVariable(value = "id", required = false) String id2) {
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        
        if (id1 != null) {
			userDAO.delete(id1);
		} else {
			userDAO.delete(id2);
		}

		// model.addAttribute("message", "Xóa thành công!");
		return "redirect:/admin/user-manage";
	}

    // update user
    @RequestMapping(value = "/admin/user/update/{id}",method = RequestMethod.GET)
    public String userUpdate(Model model, @PathVariable("id") String id){
        
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        User user1=userDAO.findById(id);
        model.addAttribute("user1", user1);
        //System.out.println(user.getFullname());
        return "/admin/user/user-update";
    }

    @RequestMapping(value="/admin/user/update/{id}", method= RequestMethod.POST)
    public String userUpdate(@ModelAttribute("userentity") User user1,@RequestParam("photo_file") MultipartFile file) throws IllegalStateException, IOException{
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        } 
        //System.out.println(user1.getPassword());
        // user1.setPassword(pwd);
        if (!file.isEmpty()) {
			user1.setPhoto(file.getOriginalFilename());
			// String path = app.getRealPath("/static/image/customer" + user1.getPhoto());
            String path = new File("src\\main\\resources\\static\\image\\customer").getAbsolutePath() + "\\" + file.getOriginalFilename();
			
            file.transferTo(new File(path));
		}
        else{
            User user2=userDAO.findById(user1.getId());
            user1.setPhoto(user2.getPhoto());
        }
        userDAO.update(user1);
        return "redirect:/admin/user-manage";
    }


    // add user
    @RequestMapping(value = "/admin/user/add", method = RequestMethod.GET)
    public String addUser(){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        } 
        return "/admin/user/user-add";
    }

    @RequestMapping(value="/admin/user/add",method = RequestMethod.POST)
    public String addUser(Model model,@ModelAttribute("entity") User user1,@RequestParam("photo_file") MultipartFile file,@RequestParam("password") String pwd ) throws IllegalStateException, IOException{
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        } 
        if (file.isEmpty()){
			user1.setPhoto("user.png");
		}
        else{
            user1.setPhoto(file.getOriginalFilename());
			// String path = app.getRealPath("/static/image/customer" + user1.getPhoto());
            String path = new File("src\\main\\resources\\static\\image\\customer").getAbsolutePath() + "\\" + file.getOriginalFilename();
            file.transferTo(new File(path));
        }
        userDAO.create(user1);
        return "redirect:/admin/user-manage";
    }
}
