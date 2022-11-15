package com.yeeshop.controller_admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yeeshop.dao.ICategoryDAO;
import com.yeeshop.entity.Category;
import com.yeeshop.entity.User;

@Controller
public class AdminCategoryController {
    
    @Autowired 
    ICategoryDAO categoryDAO;

    @Autowired
    HttpSession session;
    @RequestMapping(value="/admin/category-manage", method = RequestMethod.GET)
    public String categoryManagement(Model model){
        // Category category= new Category();
        // check session
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        // find all category
        List<Category> categories= categoryDAO.findAll(); 
        model.addAttribute("cates", categories);
        return "admin/category/category-list";
    }

    // add new category
    @RequestMapping(value = "/admin/category/add", method = RequestMethod.GET)
    public String categoryAdd(){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        return "admin/category/category-add";
    }

    @RequestMapping(value = "/admin/category/add", method = RequestMethod.POST)
    public String categoryAdd(Model model, @ModelAttribute("category") Category cate,BindingResult errorResult){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        categoryDAO.create(cate);
		model.addAttribute("message", "Thêm mới thành công!");
        return "redirect:/admin/category-manage";
    }
    // update category
    @RequestMapping("/admin/category/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Category category = categoryDAO.findById(id);
		model.addAttribute("cate", category);
		model.addAttribute("list", categoryDAO.findAll());
		return "admin/category-manage";
	}
    @RequestMapping(value = "/admin/category/update/{id}", method = RequestMethod.GET)
    public String categoryUpdate(Model model, @PathVariable("id") Integer id){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        Category category=categoryDAO.findById(id);
        model.addAttribute("cate", category);
        return "admin/category/category-update";
    }
    @RequestMapping(value = "/admin/category/update/{id}", method = RequestMethod.POST)
    public String categoryUpdate(@PathVariable("id") Integer id,@ModelAttribute("category") Category category,BindingResult errorResult){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        category.setId(id);
        categoryDAO.update(category);
        return "redirect:/admin/category-manage";
    }

    // delete category
    @RequestMapping(value = {"/admin/category/delete","/admin/category/delete/{id}"})
	public String delete(RedirectAttributes model, 
			@RequestParam(value="id", required = false) Integer id1, 
			@PathVariable(value="id", required = false) Integer id2) {
		if(id1 != null) {
			categoryDAO.delete(id1);
		}else {
			categoryDAO.delete(id2);
		}
		
		//model.addAttribute("message", "Delete Success");
		return "redirect:/admin/category-manage";
	}
}
