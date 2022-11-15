package com.yeeshop.controller;

import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yeeshop.dao.ICategoryDAO;
import com.yeeshop.dao.IProductDAO;
import com.yeeshop.entity.Category;
import com.yeeshop.entity.Product;
import com.yeeshop.service.CartService;
import com.yeeshop.service.CookieService;

@Controller
public class ProductController {
    
    @Autowired
    CartService cart;
    @Autowired
    ICategoryDAO categoryDAO;

    @Autowired
    IProductDAO productDAO;

    @Autowired
    CookieService cookieService;

    @RequestMapping(value = "/product-detail/pid={id}",method = RequestMethod.GET)
    public String product( Model model, @PathVariable("id") Integer id){
        // All Categoties
         List<Category> categories= categoryDAO.findAll();
         model.addAttribute("cates", categories);

        // Lasted product list
        List<Product> lastedProducts= productDAO.findLastestProducts();
        model.addAttribute("lastestProducts", lastedProducts);

   

        // Find Product by Product id
        Product product= productDAO.findById(id);
        model.addAttribute("product",product);
        // Similar Product
        List<Product> similaProducts=productDAO.findByCategoryId(product.getCategory().getId());
        model.addAttribute("similarProd", similaProducts);
        // set viewCount +1
        product.setViewCount(product.getViewCount()+1);
        productDAO.update(product);
        Cookie viewed=cookieService.read("viewed");
        String value= id.toString();
        if(viewed != null){
            value=viewed.getValue();
            value += ","+id.toString();
        }
        cookieService.create("viewed", value, 10);
                // cart info:
		model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartCount", cart.getCount());
        model.addAttribute("cartAmount", cart.getAmount());
        return "product/product";
    }
    @RequestMapping(value="/product-list-by-cate/cid={cid}",method= RequestMethod.GET)
    public String listProduct(Model model, @PathVariable("cid") Integer cid){
        
        // All Categoties
        List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
        
        //Find Category by cid
        if(cid>0){
            Category category= categories.get(categories.size()-1);
            model.addAttribute("CATE", category);
            // List Products by CateID
            List<Product> productsByCid=productDAO.findByCategoryId(cid);
            model.addAttribute("productsByCid", productsByCid);
        }
        else{
            List<Product> productsByCid=productDAO.findAll();
            model.addAttribute("productsByCid", productsByCid);
        }
        // cart info:
		model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartCount", cart.getCount());
        model.addAttribute("cartAmount", cart.getAmount()); 
        return "product/productlist";
    }
    
    // find product by keywords
    @RequestMapping(value="/product/list-by-keywords",method = RequestMethod.POST)  
	public String listByKeywords(Model model, @RequestParam("keywords") String keywords) {
        // All Categoties
        List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
        // cart info:
		model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartCount", cart.getCount());
        model.addAttribute("cartAmount", cart.getAmount()); 
		List<Product> list = productDAO.findByKeywords(keywords);
		model.addAttribute("productsByCid", list);
		return "product/productlist";
	}

    // add to favo
    @ResponseBody
    @RequestMapping(value = "/product/add-to-favo/{id}", method = RequestMethod.GET)
    public String addtoWishList( Model model,@PathVariable("id") Integer id){
        Cookie favo = cookieService.read("favo");
		String value = id.toString();
		if (favo != null) {
			value = favo.getValue();
			if (!value.contains(id.toString())) {
				value += "," + id.toString();
			}else {
				return "false";
			}
		}
			cookieService.create("favo", value, 30);
			return "true";
    }
    @RequestMapping(value="/product/wishlist",method = RequestMethod.GET)
    public String wishListProducts(Model model){
		Cookie favo = cookieService.read("favo");
		if (favo != null) {
			String ids = favo.getValue();
			List<Product> favo_list = productDAO.findbyIds(ids);
			model.addAttribute("favo", favo_list);
		}
        // All Categoties
        List<Category> categories= categoryDAO.findAll();
        model.addAttribute("cates", categories);
        // cart info:
		model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("cartCount", cart.getCount());
        model.addAttribute("cartAmount", cart.getAmount()); 
		return "product/favo";
    }
}
