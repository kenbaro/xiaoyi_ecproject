package com.yeeshop.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.yeeshop.dao.IProductDAO;
import com.yeeshop.entity.Product;

@Component
@SessionScope //Name: scopedTarget.cartService
@Service
public class CartService {
    @Autowired
	IProductDAO productDAO;

	@Autowired HttpSession session;
    Map<Integer, Product> map = new HashMap<>();
	public void add(Integer id) {
		Product p = map.get(id);
		if (p == null) {
			p = productDAO.findById(id);
			p.setQuantity(1);
			map.put(id, p);	
			
		} else {
			p.setQuantity(p.getQuantity() + 1);
			
		}
	}

	public void add2(Integer id, Integer qty){
		Product p = map.get(id);
		if (p == null) {
			p = productDAO.findById(id);
			p.setQuantity(qty);
			map.put(id, p);	
			
		} else {
			p.setQuantity(p.getQuantity() + qty);
			
		}
	}
	public void remove(Integer id) {
		map.remove(id);
	}

	public void update(Integer id, int qty) {
		Product p = map.get(id);
		p.setQuantity(qty);
	}

	public void clear() {
		map.clear();
	}

	public int getCount() {
		Collection<Product> ps = this.getItems();
		int count = 0;
		for (Product p : ps) {
			count += p.getQuantity();
		}
		return count;
	}

	public double getAmount() {
		Collection<Product> ps = this.getItems();
		double amount = 0;
		for (Product p : ps) {
			amount += p.getQuantity() * p.getUnitPrice() * (1 - p.getDiscount());
		}
		return amount;
	}

	public Collection<Product> getItems() {
		return map.values();
	}
}
