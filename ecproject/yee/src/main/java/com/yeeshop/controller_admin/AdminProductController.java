package com.yeeshop.controller_admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yeeshop.dao.ICategoryDAO;
import com.yeeshop.dao.IProductDAO;
import com.yeeshop.entity.Product;
import com.yeeshop.entity.User;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@Controller
public class AdminProductController {
    
    @Autowired
    HttpSession session;
    @Autowired
	IProductDAO productDAO;
	@Autowired
	ICategoryDAO categoryDAO;
	@Autowired
	ServletContext app;


    // list product
    @RequestMapping(value = "/admin/home/prod-manage",method = RequestMethod.GET)
    public String productManagement(Model model){
        
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            return "redirect:/admin/login" ;
        }
        model.addAttribute("prods", productDAO.findAll());
        return "/admin/product/product-list";
    }

    // add-product
    @RequestMapping(value="/admin/prod/add",method= RequestMethod.GET)
    public String addProd(Model model){
        
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        model.addAttribute("cates", categoryDAO.findAll());
        return "/admin/product/product-add";
    }

    @RequestMapping(value="/admin/prod/add",method = RequestMethod.POST)
    public String addProd(@ModelAttribute("entity") Product prod,@RequestParam("image_file") MultipartFile file) throws IllegalStateException, IOException {
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            return "redirect:/admin/login" ;
        }
        
        if(file.isEmpty()){
            prod.setImage("ip12_pro_max.png");
        }
        else {
            prod.setImage(file.getOriginalFilename());
            String path = new File("src\\main\\resources\\static\\image\\catalog\\products").getAbsolutePath() + "\\" + file.getOriginalFilename();
            file.transferTo(new File(path));
           
        }
        prod.setProductDate(new Date());
        productDAO.create(prod);
        return "redirect:/admin/home/prod-manage";
    }

    // update-product 

    @RequestMapping(value="/admin/prod-update/{id}",method = RequestMethod.GET)
    public String updateProd(Model model, @PathVariable("id") Integer id){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }

        Product prod= productDAO.findById(id);
        model.addAttribute("cates", categoryDAO.findAll());
        model.addAttribute("prod", prod);
        return "/admin/product/product-update";
    }
    @RequestMapping(value="admin/prod-update/{id}",method = RequestMethod.POST)
    public String updateProd(@ModelAttribute("entity") Product prod,@RequestParam("image_file") MultipartFile file, @RequestParam("uPrice") String uPrice) throws IllegalStateException, IOException, ParseException {
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        Product product=productDAO.findById(prod.getId());
        if(file.isEmpty()){
            prod.setImage(product.getImage());
        }
        else{
            prod.setImage(file.getOriginalFilename());
            String path = new File("src\\main\\resources\\static\\image\\catalog\\products").getAbsolutePath() + "\\" + file.getOriginalFilename();
            file.transferTo(new File(path));
        }
		NumberFormat format= NumberFormat.getInstance(Locale.GERMAN);
        Double unitprice= format.parse(uPrice).doubleValue();
        prod.setUnitPrice(unitprice);
        prod.setProductDate(product.getProductDate());
        productDAO.update(prod);
        return "redirect:/admin/home/prod-manage";
    }
    // delete product

    @RequestMapping(value="/admin/prod-delete/{id}", method=RequestMethod.GET)
    public String removeProd(@PathVariable("id") Integer id){
        User user= (User) session.getAttribute("user");
        if(user ==null || !user.getAdmin()){
            System.out.println("nulluser");
            return "redirect:/admin/login" ;
        }
        productDAO.delete(id);
        return "redirect:/admin/home/prod-manage";
    }
}
